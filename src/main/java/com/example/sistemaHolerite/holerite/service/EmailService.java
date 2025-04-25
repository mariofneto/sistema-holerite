package com.example.sistemaHolerite.holerite.service;

import com.example.sistemaHolerite.funcionario.model.FuncionarioModel;
import com.example.sistemaHolerite.holerite.model.HoleriteModel;
import com.example.sistemaHolerite.holerite.repository.HoleriteRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private HoleriteService holeriteService;

    @Autowired
    private HoleriteRepository holeriteRepository;

    public void envioHoleriteEmail(Long id, byte[] pdfBytes) throws MessagingException, FileNotFoundException {
        HoleriteModel holerite = holeriteRepository.findById(id).orElseThrow();

        FuncionarioModel funcionario = holerite.getFuncionarioModel();

        ByteArrayResource recurso = new ByteArrayResource(pdfBytes);

        String destinatario = funcionario.getEmail();
        String assunto = "Holerite";
        String mensagem = "Olá " + funcionario.getNome() + ", segue em anexo o seu holerite :)";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(destinatario);
        helper.setSubject(assunto);
        helper.setText(mensagem);

        // Adiciona o PDF como anexo
        helper.addAttachment("holerite.pdf", recurso);

        javaMailSender.send(mimeMessage);

    }

}
