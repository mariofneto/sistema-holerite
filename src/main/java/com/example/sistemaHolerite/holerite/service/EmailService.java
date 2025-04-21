package com.example.sistemaHolerite.holerite.service;

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

//    @Autowired
//    private HoleriteRepository holeriteRepository;

    @Autowired
    private HoleriteService holeriteService;

    public void envioHoleriteEmail(Long id, byte[] pdfBytes) throws MessagingException, FileNotFoundException {

        ByteArrayResource recurso = new ByteArrayResource(pdfBytes);

        String destinatario = "marioo.netoo4@gmail.com";
        String assunto = "Sistema de Holerites";
        String mensagem = "Email com o holerite";

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
