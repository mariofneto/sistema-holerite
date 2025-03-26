package com.example.sistemaHolerite.funcionario.service;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.funcionario.repository.FuncionarioRepository;
import com.example.sistemaHolerite.salario.service.SalarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    // famosa injecao de dependencia
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private SalarioService salarioService;

    // buscar todos funcionarios
    public List<FuncionarioModel> getAll(){
        return funcionarioRepository.findAll();
    }

    //buscar um funcionario
    public FuncionarioModel getById(Long id){
        return funcionarioRepository.findById(id).orElseThrow();
    }

    // criar um funcionario
    public void create(FuncionarioModel funcionarioModel){
        funcionarioRepository.save(funcionarioModel);
    }

    // editar um funcionario
    public void update(Long id, FuncionarioModel funcionarioAtualizado){
        FuncionarioModel funcionario = funcionarioRepository.findById(id).orElseThrow();

        funcionario.setNome(funcionarioAtualizado.getNome());
        funcionario.setDependentes(funcionarioAtualizado.getDependentes());

        funcionarioRepository.save(funcionario);
    }

    // deletar um funcionario
    public void delete(Long id){
        funcionarioRepository.deleteById(id);
    }

    public void gerarHolerite(Long id){

        FuncionarioModel funcionario = funcionarioRepository.findById(id).orElseThrow();

        //SalarioModel salarioModel = salarioRepository.findById(salario.getId()).orElseThrow();
        Double inss = salarioService.inss(funcionario.getId());
        Double irrf = salarioService.irrf(funcionario.getId());
        Double valeTransporte = salarioService.valeTransporte(funcionario.getId());
        Double salarioLiquido = salarioService.salarioLiquido(funcionario.getId());


        // Exibindo os resultados com System.out.println()
        System.out.println("------------------------");
        System.out.println("Holerite -> " + salarioService.dataHolerite());
        System.out.println("------------------------");
        System.out.println("Nome do Funcionário: " + funcionario.getNome());
        System.out.printf("Salário Bruto: R$ %.2f%n", funcionario.getSalarioBruto());
        System.out.printf("Desconto INSS: R$ %.2f%n", inss);
        System.out.printf("Desconto IRRF: R$ %.2f%n", irrf);
        System.out.printf("Vale Transporte: R$ %.2f%n", valeTransporte);
        System.out.printf("Salário Líquido: R$ %.2f%n", salarioLiquido);
    }
}
