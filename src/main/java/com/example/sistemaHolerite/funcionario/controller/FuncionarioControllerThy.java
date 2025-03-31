package com.example.sistemaHolerite.funcionario.controller;

import com.example.sistemaHolerite.funcionario.dto.FuncionarioDto;
import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.funcionario.service.FuncionarioService;
import jakarta.servlet.http.HttpSession; // Importação para sessão
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FuncionarioControllerThy {

    @Autowired
    private FuncionarioService funcionarioService;

    // Criar um funcionário
    @GetMapping("/funcionario/create")
    public String homeCreate() {
        return "createFuncionario";
    }

    @PostMapping("/funcionario/create")
    public String create(FuncionarioModel funcionarioModel) {
        funcionarioService.create(funcionarioModel);
        return "redirect:/funcionario/create";
    }

    // Receber uma lista de funcionários
    @GetMapping("/funcionario/")
    public String getAll(Model model) {
        List<FuncionarioModel> funcionarios = funcionarioService.findAll();
        model.addAttribute("funcionarios", funcionarios);
        return "getAllFuncionarios";
    }

    // Tela de login
    @GetMapping("/funcionario/login")
    public String homeLogin() {
        return "loginFuncionario";
    }

    // Processar login
    @PostMapping("/funcionario/login")
    public String login(@ModelAttribute FuncionarioDto funcionarioDto, HttpSession session) {

        boolean autenticado = funcionarioService.validarLogin(funcionarioDto.getNome(), funcionarioDto.getSenha());

        if (autenticado) {
            session.setAttribute("funcionarioLogado", funcionarioDto.getNome()); // Armazena na sessão
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
