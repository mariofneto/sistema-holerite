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
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
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

        HoleriteModel holerite  = new HoleriteModel();
        holerite.setSalarioBrutoNaData(salarioService.salarioBrutoAtual(funcionario.getId()));


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

        holerite.setFuncionarioModel(funcionario);
        holerite.setSalarioModel(salario);

        funcionarioRepository.save(funcionario);
        holeriteRepository.save(holerite);

    }


    // recebe o id do holerite para gerar o pdf dele
    public byte[] holeriteEmPdf(Long id) throws IOException, InterruptedException {

        return gerarPdf(id);

    }

    public byte[] gerarPdf(Long id) throws IOException {
        HoleriteModel holerite = holeriteRepository.findById(id).orElseThrow();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataPagamento = holerite.getSalarioModel().getDataSalario().format(formatter);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Cabeçalho
        document.add(new Paragraph("Recibo de Pagamento")
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(14));

        // Empresa / Funcionário
        Table header = new Table(UnitValue.createPercentArray(new float[]{1, 1}))
                .setWidth(UnitValue.createPercentValue(100));

        header.addCell(new Cell().add("Empresa: MarrecoTech").setBorder(null));
        header.addCell(new Cell().add("Período: " + dataPagamento).setBorder(null));
        header.addCell(new Cell().add("Endereço: Rua Javolândia, 27").setBorder(null));
        header.addCell(new Cell().add("CNPJ: 00.000.000/0001-00").setBorder(null));
        header.addCell(new Cell().add("Funcionário: " + holerite.getFuncionarioModel().getNome().toUpperCase()).setBorder(null));

        document.add(header);
        document.add(new Paragraph("\n"));

        float[] colWidths = {40, 200, 60, 80, 80};
        Table tabela = new Table(colWidths);

        tabela.addHeaderCell("Cód");
        tabela.addHeaderCell("Descrição");
        tabela.addHeaderCell("Ref");
        tabela.addHeaderCell("Vencimentos");
        tabela.addHeaderCell("Descontos");

        tabela.addCell("01");
        tabela.addCell("Salário-Mensal");
        tabela.addCell("30");
        tabela.addCell(formatar(holerite.getSalarioBrutoNaData()));
        tabela.addCell("");

        tabela.addCell("02");
        tabela.addCell("INSS s/ Salários");
        tabela.addCell("");
        tabela.addCell("");
        tabela.addCell(formatar(holerite.getSalarioModel().getDescontoInss()));

        tabela.addCell("03");
        tabela.addCell("IRRF");
        tabela.addCell("");
        tabela.addCell("");
        tabela.addCell(formatar(holerite.getSalarioModel().getDescontoIrrf()));

        tabela.addCell("04");
        tabela.addCell("Vale-transporte");
        tabela.addCell("");
        tabela.addCell("");
        tabela.addCell(formatar(holerite.getSalarioModel().getDescontoValeTransporte()));

        document.add(tabela);
        document.add(new Paragraph("\n"));

        // Resumo
        Table resumo = new Table(UnitValue.createPercentArray(new float[]{1, 1}))
                .setWidth(UnitValue.createPercentValue(100));

        resumo.addCell(new Cell().add("Total Vencimentos: " + formatar(holerite.getFuncionarioModel().getSalarioBruto())).setBorder(null));
        double totalDescontos = holerite.getSalarioModel().getDescontoInss() +
                holerite.getSalarioModel().getDescontoIrrf() +
                holerite.getSalarioModel().getDescontoValeTransporte();
        resumo.addCell(new Cell().add("Total Descontos: " + formatar(totalDescontos)).setBorder(null));

        resumo.addCell(new Cell(1, 2).add("Salário Líquido: R$ " + formatar(holerite.getSalarioModel().getSalarioLiquido()))
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(null));

        document.add(resumo);
        document.add(new Paragraph("\n\n"));

        // Assinatura
        document.add(new Paragraph("____________________________________").setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Assinatura").setTextAlignment(TextAlignment.CENTER));

        document.close();
        return baos.toByteArray();
    }

    private String formatar(double valor) {
        return String.format("R$ %.2f", valor);
    }

    // enviar holerite em pdf para email
    public void envioHoleriteEmail(Long id) throws MessagingException, IOException {

        emailService.envioHoleriteEmail(id, gerarPdf(id));
    }


}
