package com.example.sistemaHolerite.funcionario.controller;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.funcionario.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/")
    public List<FuncionarioModel> getAll(){
        return funcionarioService.getAll();
    }

    @GetMapping("/{id}")
    public FuncionarioModel getById(@PathVariable Long id){
        return funcionarioService.getById(id);
    }

    @PostMapping("/")
    public void create(@RequestBody FuncionarioModel funcionarioModel){
        funcionarioService.create(funcionarioModel);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody FuncionarioModel funcionarioModel){
        funcionarioService.update(id, funcionarioModel);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        funcionarioService.delete(id);
    }

    @PostMapping("/holerite/gerar/{id}")
    public String gerarHolerite(@PathVariable Long id){
        funcionarioService.gerarHolerite(id);
        return "Holerite criado na entidade funcionario com sucesso!";
    }

}
