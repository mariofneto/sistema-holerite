package com.example.sistemaHolerite.rh.controller;

import com.example.sistemaHolerite.funcionario.repository.FuncionarioRepository;
import com.example.sistemaHolerite.rh.service.RhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RhController {

    @Autowired
    RhService rhService;

    @Autowired
    FuncionarioRepository funcionarioRepository;

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
