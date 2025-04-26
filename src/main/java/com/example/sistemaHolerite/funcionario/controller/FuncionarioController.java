package com.example.sistemaHolerite.funcionario.controller;

import com.example.sistemaHolerite.funcionario.dto.LoginDto;
import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.funcionario.service.FuncionarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    // Criar um funcionário
    @GetMapping("/funcionario/create")
    public String homeCreate() {
        return "createFuncionario";
    }

    @PostMapping("/funcionario/create")
    public ModelAndView create(@Valid FuncionarioModel funcionarioModel, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("redirect:/funcionario/create");

            List<String> msg = new ArrayList<>();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                msg.add(objectError.getDefaultMessage());
            }

            modelAndView.addObject("msg", msg);
            return modelAndView;
        }

        funcionarioService.create(funcionarioModel);
        return new ModelAndView("redirect:/");

    }
    @GetMapping("/")
    public String redirecionar() {
        return "redirect:/funcionario/login";
    }

    // Tela de login
    @GetMapping("/funcionario/login")
    public String homeLogin() {
        return "loginFuncionario";
    }

    @PostMapping("/funcionario/login")
    public String login(@ModelAttribute LoginDto loginDto, HttpSession session) {

        boolean autenticado = funcionarioService.validarLogin(loginDto.getNome().toLowerCase(), loginDto.getSenha());

        if (autenticado) {
            session.setAttribute("funcionarioLogado", loginDto.getNome().toLowerCase()); // Armazena na sessão
            return "redirect:/funcionario/logado";
        } else {
            return "redirect:/funcionario/create"; // Redireciona para criar caso falhe
        }
    }

    // Página logada
    @GetMapping("/funcionario/logado")
    public String homeLogado(HttpSession session, Model model) {
        String nome = (String) session.getAttribute("funcionarioLogado");

        if (nome == null) {
            return "redirect:/funcionario/login"; // Se não houver usuário na sessão, volta para login
        }

        model.addAttribute("nome", nome);
        return "logadoFuncionario";
    }

    // Voltar para a página logada sem perder o nome
    @PostMapping("/funcionario/logado")
    public String voltar() {
        return "redirect:/funcionario/logado";
    }

    // Fazer logout
    @PostMapping("/funcionario/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Remove os dados da sessão
        return "redirect:/funcionario/login";
    }
}
