package com.example.sistemaHolerite.funcionario.service;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.funcionario.repository.FuncionarioRepository;
import com.example.sistemaHolerite.salario.service.SalarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // editar um funcionario
    public void update(Long id, FuncionarioModel funcionarioAtualizado){
        FuncionarioModel funcionario = funcionarioRepository.findById(id).orElseThrow();

        funcionario.setNome(funcionarioAtualizado.getNome());
        funcionario.setDependentes(funcionarioAtualizado.getDependentes());

        funcionarioRepository.save(funcionario);
    }

    // deletar um funcionario
    public void delete(Long id){
        funcionarioRepository.deleteById(id);
    }


}
