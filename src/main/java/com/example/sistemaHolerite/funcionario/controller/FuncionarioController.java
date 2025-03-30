package com.example.sistemaHolerite.funcionario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

   /* @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping("/")
    public List<FuncionarioModel> findAll(){
        return funcionarioService.findAll();
    }

    @GetMapping("/{id}")
    public FuncionarioModel findById(@PathVariable Long id){
        return funcionarioService.findById(id);
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
*/

}
