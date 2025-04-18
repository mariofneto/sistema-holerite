package com.example.sistemaHolerite.holerite.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void envioEmailComPdf(String destinatario, String assunto, String mensagem, File arquivoPdf) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(destinatario);
        helper.setSubject(assunto);
        helper.setText(mensagem);

        // Adiciona o PDF como anexo
        FileSystemResource file = new FileSystemResource(arquivoPdf);
        helper.addAttachment("holerite.pdf", file);

        javaMailSender.send(mimeMessage);

    }

}
