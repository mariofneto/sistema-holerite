package com.example.sistemaHolerite.holerite.controller;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.funcionario.repository.FuncionarioRepository;
import com.example.sistemaHolerite.funcionario.service.FuncionarioService;
import com.example.sistemaHolerite.holerite.model.HoleriteModel;
import com.example.sistemaHolerite.holerite.repository.HoleriteRepository;
import com.example.sistemaHolerite.holerite.service.HoleriteService;
import com.example.sistemaHolerite.salario.model.SalarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/funcionario/logado")
public class HoleriteController {

    @Autowired
    private HoleriteService holeriteService;

    @Autowired
    private HoleriteRepository holeriteRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/holerites/{nome}")
    public String getHoleritesPorNome(@PathVariable String nome, Model model) throws IOException {

        List<HoleriteModel> holerites = holeriteService.findByFuncionarioNome(nome);
        model.addAttribute("holerites", holerites);

        return "getAllHolerites";
    }


    @GetMapping("/{nome}")
    public List<SalarioModel> findAllByFuncionario(@PathVariable String nome){
        return holeriteService.findAllByFuncionario(nome);
    }

    @PostMapping("/holerites/gerar/{nome}")
    public String gerarHolerite(@PathVariable String nome, RedirectAttributes redirectAttributes) throws IOException {
        FuncionarioModel funcionarioModel = funcionarioRepository.findByNome(nome).orElseThrow();

        holeriteService.gerarHolerite(funcionarioModel.getNome());

        // Mensagem de sucesso
        redirectAttributes.addFlashAttribute("mensagem", "Holerite gerado com sucesso para " + nome);

        // Redirecionar para a página onde os holerites do funcionário são listados
        return "redirect:/funcionario/logado/holerites/" + nome;
    }

    @GetMapping("/holerites/gerar/{id}")
    public String holeriteEmPdf(@PathVariable Long id, RedirectAttributes redirectAttributes) throws IOException, InterruptedException {
        // Encontrar o Holerite pelo id
        HoleriteModel holeriteModel = holeriteRepository.findById(id).orElseThrow();

        holeriteService.holeriteEmPdf(holeriteModel.getId());

        String nome = holeriteModel.getFuncionarioModel().getNome();

        // Mensagem de sucesso
        redirectAttributes.addFlashAttribute("mensagem", "Holerite gerado com sucesso para " + nome);

        // Redirecionar para a página onde os holerites do funcionário são listados
        return "redirect:/funcionario/logado/holerites/" + nome;
    }



}
