package com.example.sistemaHolerite.funcionario.service;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.funcionario.repository.FuncionarioRepository;
import com.example.sistemaHolerite.salario.service.SalarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    // famosa injecao de dependencia
    @Autowired
    private FuncionarioRepository funcionarioRepository;

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
        funcionarioRepository.save(funcionarioModel);
    }

    // deletar um funcionario
    public void delete(Long id){
        funcionarioRepository.deleteById(id);
    }

    public boolean validarLogin(String nome, String senha) {
        Optional<FuncionarioModel> funcionario = funcionarioRepository.findByNomeAndSenha(nome, senha);
        return funcionario.isPresent(); // Retorna true se encontrou, false se não
    }


}
