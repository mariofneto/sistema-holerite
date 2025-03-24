package com.example.sistemaHolerite.salario.service;

import com.example.sistemaHolerite.salario.model.SalarioModel;
import com.example.sistemaHolerite.salario.repository.SalarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalarioService {

    @Autowired
    private SalarioRepository salarioRepository;

    public List<SalarioModel> getAll(){return salarioRepository.findAll();}

    public SalarioModel getById(Long id){return salarioRepository.findById(id).orElseThrow();}

    public void create(SalarioModel salarioModel){
        salarioRepository.save(salarioModel);
    }

    public void update(Long id, SalarioModel salarioModel){
        SalarioModel salario = salarioRepository.findById(id).orElseThrow();

        salario.setSalarioBruto(salario.getSalarioBruto());
        salario.setValeTransporte(salario.getValeTransporte());

        salarioRepository.save(salario);
    }

    public void delete(Long id){
        salarioRepository.deleteById(id);
    }

}
