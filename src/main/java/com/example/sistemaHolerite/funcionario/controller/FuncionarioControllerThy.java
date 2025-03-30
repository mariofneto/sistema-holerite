package com.example.sistemaHolerite.funcionario.controller;


import com.example.sistemaHolerite.funcionario.dto.FuncionarioDto;
import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.funcionario.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class FuncionarioControllerThy {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/funcionario/create")
    public String homeCreate(){
        return "createFuncionario";
    }

    @PostMapping("/funcionario/create")
    public String create(FuncionarioModel funcionarioModel){
        funcionarioService.create(funcionarioModel);
        return "redirect:/funcionario/create";
    }

    @GetMapping("/funcionario/")
    public String getAll(Model model){
        List<FuncionarioModel> funcionarios = funcionarioService.findAll();
        model.addAttribute("funcionarios", funcionarios);
        return "getAllFuncionarios";
    }

    @GetMapping("/funcionario/login")
    public String homeLogin(){
        return "loginFuncionario";
    }

    @PostMapping("/funcionario/login")
    public String login(@ModelAttribute FuncionarioDto funcionarioDto, RedirectAttributes redirectAttributes){

        boolean autenticado = funcionarioService.validarLogin(funcionarioDto.getNome(), funcionarioDto.getSenha());
        String nomeFuncionario = funcionarioDto.getNome();

        if(autenticado) {
            redirectAttributes.addFlashAttribute("nome", nomeFuncionario);
            return "redirect:/funcionario/logado";
        }
        else{
            return "redirect:/funcionario/create";
        }
    }

    @GetMapping("/funcionario/logado")
    public String homeLogado(){
        return "logadoFuncionario";
    }


}
