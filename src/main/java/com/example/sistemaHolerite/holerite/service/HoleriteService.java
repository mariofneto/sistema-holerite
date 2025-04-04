package com.example.sistemaHolerite.holerite.service;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.funcionario.repository.FuncionarioRepository;
import com.example.sistemaHolerite.funcionario.service.FuncionarioService;
import com.example.sistemaHolerite.holerite.model.HoleriteModel;
import com.example.sistemaHolerite.holerite.repository.HoleriteRepository;
import com.example.sistemaHolerite.salario.model.SalarioModel;
import com.example.sistemaHolerite.salario.service.SalarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HoleriteService {

    @Autowired
    private HoleriteRepository holeriteRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private SalarioService salarioService;

    // retorna todos os holerites de um funcionario
    public List<SalarioModel> findAllByFuncionario(Long id){
        FuncionarioModel funcionario = funcionarioRepository.findById(id).orElseThrow();

        return funcionario.getSalarios();
    }

    public List<HoleriteModel> findByFuncionarioNome(String nome) {
        return holeriteRepository.findByFuncionarioModel_Nome(nome);
    }

    public List<HoleriteModel> findAll(){
        return holeriteRepository.findAll();
    }

    public void gerarHolerite(String nome){
        FuncionarioModel funcionario = funcionarioRepository.findByNome(nome).orElseThrow();

        // verifica se já tem holerite nesse mês
        boolean temHoleriteNoMes = funcionario.getSalarios().stream()
                .anyMatch(salario ->
                        salario.getDataSalario().getMonthValue() == LocalDateTime.now().getMonthValue());

        if (temHoleriteNoMes) {
            System.out.println("Você já tem um holerite neste mês, colega!");
        }

        else{

        LocalDateTime dataSalario = LocalDateTime.now();
        Double inss = salarioService.inss(funcionario.getId());
        Double irrf = salarioService.irrf(funcionario.getId());
        Double valeTransporte = salarioService.valeTransporte(funcionario.getId());
        Double salarioLiquido = salarioService.salarioLiquido(funcionario.getId());

        SalarioModel salario = new SalarioModel(
                dataSalario,
                inss,
                irrf,
                valeTransporte,
                salarioLiquido,
                funcionario
        );

        funcionarioRepository.save(funcionario);

        HoleriteModel holerite = new HoleriteModel(
                funcionario,
                salario
        );
        holeriteRepository.save(holerite);

        System.out.println("------------------------");
        System.out.println("Holerite -> " + salarioService.dataHoraHolerite(dataSalario));
        System.out.println("------------------------");
        System.out.println("Nome do Funcionário: " + funcionario.getNome());
        System.out.printf("Salário Bruto: R$ %.2f%n", funcionario.getSalarioBruto());
        System.out.printf("(-)Desconto INSS: R$ %.2f%n", inss);
        System.out.printf("(-)Desconto IRRF: R$ %.2f%n", irrf);
        System.out.printf("(-)Vale Transporte: R$ %.2f%n", valeTransporte);
        System.out.printf("Salário Líquido: R$ %.2f%n", salarioLiquido);
    }}
    /*
    public void gerarHoleriteEmPdf(){
        String destino = "Z:/pdfExample/sample.pdf";
        PdfWriter writer = new PdfWriter(destino);

        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("------------------------"));
        document.add(new Paragraph("Holerite -> " + salarioService.dataHoraHolerite(dataSalario)));
        document.add(new Paragraph("------------------------"));
        document.add(new Paragraph("Nome do Funcionário: " + funcionario.getNome()));
        document.add(new Paragraph(String.format("Salário Bruto: R$ %.2f%n", funcionario.getSalarioBruto())));
        document.add(new Paragraph(String.format("(-)Desconto INSS: R$ %.2f%n", inss)));
        document.add(new Paragraph(String.format("(-)Desconto IRRF: R$ %.2f%n", irrf)));
        document.add(new Paragraph(String.format("(-)Vale Transporte: R$ %.2f%n", valeTransporte)));
        document.add(new Paragraph(String.format("Salário Líquido: R$ %.2f%n", salarioLiquido)));

        document.close();
    }
    */

    public HoleriteModel findById(Long id) {
        return holeriteRepository.findById(id).orElseThrow();
    }
}
