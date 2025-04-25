package com.example.sistemaHolerite.rh.service;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.funcionario.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RhService {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    public void createFuncionario(FuncionarioModel funcionarioModel){
        funcionarioRepository.save(funcionarioModel);
    }

    public FuncionarioModel editarFuncionario(Long id, FuncionarioModel funcionarioModel){
        FuncionarioModel funcionarioAntigo = funcionarioRepository.findById(id).orElseThrow();

        funcionarioAntigo.setNome(funcionarioModel.getNome());
        funcionarioAntigo.setEmail(funcionarioModel.getEmail());
        funcionarioAntigo.setSenha(funcionarioModel.getSenha());
        funcionarioAntigo.setDependentes(funcionarioModel.getDependentes());
        funcionarioAntigo.setSalarioBruto(funcionarioModel.getSalarioBruto());
        funcionarioAntigo.setTemValeTransporte(funcionarioModel.getTemValeTransporte());

        funcionarioRepository.save(funcionarioAntigo);

        return funcionarioAntigo;
    }

    public void deletarFuncionario(Long id){
        funcionarioRepository.deleteById(id);
    }




}
