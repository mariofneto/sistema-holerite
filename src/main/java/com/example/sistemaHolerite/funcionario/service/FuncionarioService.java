package com.example.sistemaHolerite.funcionario.service;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.funcionario.repository.FuncionarioRepository;
import com.example.sistemaHolerite.rh.model.RhModel;
import com.example.sistemaHolerite.rh.repository.RhRepository;
import com.example.sistemaHolerite.salario.service.SalarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private RhRepository rhRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private SalarioService salarioService;

    // buscar todos funcionarios
    public List<FuncionarioModel> findAll(){
        return funcionarioRepository.findAll();
    }

    //buscar um funcionario
    public FuncionarioModel findById(Long id){
        return funcionarioRepository.findById(id).orElseThrow();
    }

    // criar um funcionario
    public void create(FuncionarioModel funcionarioModel){
        if(funcionarioModel.getDependentes().equals(null) || funcionarioModel.getSalarioBruto().equals(null))
        {
            System.out.println("não pode ter esses atributos nulos!");
        }
        else{
            // encriptografando a senha
            funcionarioModel.setSenha(encoder.encode(funcionarioModel.getSenha()));

            String nomeToLowerCase = funcionarioModel.getNome().toLowerCase();

            funcionarioModel.setNome(nomeToLowerCase);

            funcionarioRepository.save(funcionarioModel);
        }
    }

    public boolean validarLogin(String nome, String senha) {
        switch(nome){
            case "admin":
                Optional<RhModel> rh = rhRepository.findByNome(nome);
                if(rh.get().getNome().compareTo(nome) == 0 && rh.get().getSenha().compareTo(senha) == 0){
                    return true;
                }
            default:
                Optional<FuncionarioModel> funcionario = funcionarioRepository.findByNome(nome);
                boolean valid = encoder.matches(senha, funcionario.get().getSenha());
                return valid;
        }
    }


}
