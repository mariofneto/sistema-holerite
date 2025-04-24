package com.example.sistemaHolerite.holerite.service;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.funcionario.repository.FuncionarioRepository;
import com.example.sistemaHolerite.funcionario.service.FuncionarioService;
import com.example.sistemaHolerite.holerite.model.HoleriteModel;
import com.example.sistemaHolerite.holerite.repository.HoleriteRepository;
import com.example.sistemaHolerite.salario.model.SalarioModel;
import com.example.sistemaHolerite.salario.service.SalarioService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private EmailService emailService;

    // retorna todos os holerites de um funcionario
    public List<SalarioModel> findAllByFuncionario(String nome){
        FuncionarioModel funcionario = funcionarioRepository.findByNome(nome).orElseThrow();

        return funcionario.getSalarios();
    }

    public List<HoleriteModel> findByFuncionarioNome(String nome) {
        return holeriteRepository.findByFuncionarioModel_Nome(nome);
    }

    public List<HoleriteModel> findAll(){
        return holeriteRepository.findAll();
    }

    // recebe o nome do funcionario para gerar um holerite para ele
    public void gerarHolerite(String nome) {

        FuncionarioModel funcionario = funcionarioRepository.findByNome(nome).orElseThrow();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

        LocalDateTime agora = LocalDateTime.now();
        String horaFormatada = agora.format(formatter);

        LocalDateTime dataSalario = LocalDateTime.parse(horaFormatada, formatter);
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

    }


    // recebe o id do holerite para gerar o pdf dele
    public byte[] holeriteEmPdf(Long id) throws IOException, InterruptedException {

        return gerarPdf(id);

    }

    public byte[] gerarPdf(Long id) throws IOException {
        HoleriteModel holerite = holeriteRepository.findById(id).orElseThrow();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        String horaFormatada = holerite.getSalarioModel().getDataSalario().format(formatter);

        //String destino = "Z:/pdfExample/sample.pdf";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);

        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("------------------------"));
        document.add(new Paragraph("Holerite -> " + horaFormatada));
        document.add(new Paragraph("------------------------"));
        document.add(new Paragraph("Nome do Funcionário: " + holerite.getFuncionarioModel().getNome()));
        document.add(new Paragraph(String.format("Salário Bruto: R$ %.2f%n", holerite.getFuncionarioModel().getSalarioBruto())));
        document.add(new Paragraph(String.format("(-)Desconto INSS: R$ %.2f%n", holerite.getSalarioModel().getDescontoInss())));
        document.add(new Paragraph(String.format("(-)Desconto IRRF: R$ %.2f%n", holerite.getSalarioModel().getDescontoIrrf())));
        document.add(new Paragraph(String.format("(-)Vale Transporte: R$ %.2f%n", holerite.getSalarioModel().getDescontoValeTransporte())));
        document.add(new Paragraph(String.format("Salário Líquido: R$ %.2f%n", holerite.getSalarioModel().getSalarioLiquido())));
        document.close();

        return baos.toByteArray();
    }

    // enviar holerite em pdf para email
    public void envioHoleriteEmail(Long id) throws MessagingException, IOException {

        emailService.envioHoleriteEmail(id, gerarPdf(id));
    }


}
