package com.example.sistemaHolerite.rh.service;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.funcionario.repository.FuncionarioRepository;
import com.example.sistemaHolerite.funcionario.service.FuncionarioService;
import com.example.sistemaHolerite.holerite.model.HoleriteModel;
import com.example.sistemaHolerite.holerite.repository.HoleriteRepository;
import com.example.sistemaHolerite.holerite.service.HoleriteService;
import com.example.sistemaHolerite.salario.repository.SalarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RhService {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    SalarioRepository salarioRepository;

    @Autowired
    HoleriteRepository holeriteRepository;

    @Autowired
    HoleriteService holeriteService;

    @Autowired
    FuncionarioService funcionarioService;

    public void createFuncionario(FuncionarioModel funcionarioModel){
        funcionarioRepository.save(funcionarioModel);
    }

    public FuncionarioModel editarSalarioFuncionario(String nome, Double salarioBrutoFuncionario){
        FuncionarioModel funcionarioAntigo = funcionarioRepository.findByNome(nome).orElseThrow();

        funcionarioAntigo.setSalarioBruto(salarioBrutoFuncionario);

        funcionarioRepository.save(funcionarioAntigo);

        return funcionarioAntigo;
    }

    public void deletarFuncionario(String nome){
        FuncionarioModel funcionario = funcionarioService.findByNome(nome);
        List<HoleriteModel> holerites = holeriteService.findByFuncionarioNome(funcionario.getNome());


        holeriteRepository.deleteAll(holerites);
        funcionarioRepository.deleteById(funcionario.getId());
    }




}
