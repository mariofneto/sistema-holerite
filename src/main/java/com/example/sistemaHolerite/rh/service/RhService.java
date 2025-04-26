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

    public FuncionarioModel editarSalarioFuncionario(String nome, Double salarioBrutoFuncionario){
        FuncionarioModel funcionarioAntigo = funcionarioRepository.findByNome(nome).orElseThrow();

        funcionarioAntigo.setSalarioBruto(salarioBrutoFuncionario);

        funcionarioRepository.save(funcionarioAntigo);

        return funcionarioAntigo;
    }

    public void deletarFuncionario(Long id){
        funcionarioRepository.deleteById(id);
    }




}
