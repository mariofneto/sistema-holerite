package com.example.sistemaHolerite.rh.controller;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.funcionario.repository.FuncionarioRepository;
import com.example.sistemaHolerite.funcionario.service.FuncionarioService;
import com.example.sistemaHolerite.rh.service.RhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class RhController {

    @Autowired
    RhService rhService;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    FuncionarioService funcionarioService;


    @GetMapping("/Allfuncionarios")
    public String getAllFuncionarios(Model model) throws IOException {
        List<FuncionarioModel> funcionarios = funcionarioRepository.findAll();
        model.addAttribute("funcionarios", funcionarios);

        return "getAllFuncionarios";
    }

    @GetMapping("/funcionario/update")
    public String updateFuncionario(){
        return "updateFuncionario";
    }

    @PostMapping("/funcionario/update")
    public String updateFuncionario(@RequestParam String nome, @RequestParam Double salarioBruto){
        rhService.editarSalarioFuncionario(nome, salarioBruto);

        return "redirect:/funcionario/logado/holerites/" + nome;
    }
}
