package com.example.sistemaHolerite.holerite.controller;

import com.example.sistemaHolerite.holerite.service.HoleriteService;
import com.example.sistemaHolerite.salario.model.SalarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/holerite")
public class HoleriteController {

    @Autowired
    private HoleriteService holeriteService;

    @GetMapping("/{id}")
    public List<SalarioModel> findAllByFuncionario(@PathVariable Long id){
        return holeriteService.findAllByFuncionario(id);
    }

    @PostMapping("/{id}")
    public void gerarHolerite(@PathVariable Long id){
        holeriteService.gerarHolerite(id);
    }
}
