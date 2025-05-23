package com.example.sistemaHolerite.holerite.controller;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.funcionario.repository.FuncionarioRepository;
import com.example.sistemaHolerite.funcionario.service.FuncionarioService;
import com.example.sistemaHolerite.holerite.model.HoleriteModel;
import com.example.sistemaHolerite.holerite.repository.HoleriteRepository;
import com.example.sistemaHolerite.holerite.service.HoleriteService;
import com.example.sistemaHolerite.salario.model.SalarioModel;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/holerites")
    public String getHoleritesPorFuncionarioNomeHome() throws IOException {
        return "getAllHoleritesFuncionario";
    }

    @PostMapping("/holerites")
    public String getHoleritesPorFuncionarioNome(@RequestParam String nome, Model model) throws IOException {
        List<HoleriteModel> holerites = holeriteService.findByFuncionarioNome(nome);
        if(!holerites.isEmpty()){
            model.addAttribute("holerites", holerites);

            return "getAllHolerites";
        }
        return "redirect:/funcionario/logado/holerites";
    }


    @GetMapping("/{nome}")
    public List<SalarioModel> findAllByFuncionario(@PathVariable String nome){
        return holeriteService.findAllByFuncionario(nome);
    }

    @GetMapping("/holerites/gerar/")
    public String gerarHolerite(){
        return "createHoleriteFuncionario";
    }

    @PostMapping("/holerites/gerar/")
    public String gerarHoleriteFuncionario(@RequestParam String nome, RedirectAttributes redirectAttributes) throws IOException {

        Optional<FuncionarioModel> funcionarioModel = funcionarioRepository.findByNome(nome);

        if(funcionarioModel.isEmpty()){
            return "redirect:/funcionario/logado/holerites/gerar/";
        }else{
            holeriteService.gerarHolerite(funcionarioModel.get().getNome());

            // Mensagem de sucesso
            redirectAttributes.addFlashAttribute("mensagem", "Holerite gerado com sucesso para " + nome);

            // Redirecionar para a página onde os holerites do funcionário são listados
            return "redirect:/funcionario/logado/holerites/" + nome;
        }
    }

    @GetMapping("/holerites/gerar/{id}")
    public ResponseEntity<byte[]> holeriteEmPdf(@PathVariable Long id) throws IOException, InterruptedException {

        HoleriteModel holeriteModel = holeriteRepository.findById(id).orElseThrow();

        byte[] pdfBytes = holeriteService.holeriteEmPdf(holeriteModel.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("holerite.pdf").build());

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }

    @GetMapping("/holerites/envio/email/{id}")
    public String envioHoleriteEmail(@PathVariable Long id) throws MessagingException, IOException {
        HoleriteModel holeriteModel = holeriteRepository.findById(id).orElseThrow();
        holeriteService.envioHoleriteEmail(holeriteModel.getId());
        String nome = holeriteModel.getFuncionarioModel().getNome();

        return "redirect:/funcionario/logado/holerites/"+ nome;

    }



}
