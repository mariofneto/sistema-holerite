package com.example.sistemaHolerite.salario.controller;

import com.example.sistemaHolerite.salario.model.SalarioModel;
import com.example.sistemaHolerite.salario.service.SalarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salario")
public class SalarioController {

    @Autowired
    private SalarioService salarioService;

    @GetMapping("/")
    public List<SalarioModel> getAll(){
        return salarioService.getAll();
    }

    @GetMapping("/{id}")
    public SalarioModel getById(@PathVariable Long id){
        return salarioService.getById(id);
    }

    @PostMapping("/")
    public void create(@RequestBody SalarioModel salarioModel){
        salarioService.create(salarioModel);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody SalarioModel salarioModel){
        salarioService.update(id, salarioModel);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        salarioService.delete(id);
    }
}
