package br.ufpr.dac.ms_auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EnviarEmailService {
  @Autowired
  private JavaMailSender emailSender;

  public void enviarEmail(String para, String assunto, String texto) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("msdac20242@gmail.com");
    message.setTo(para);
    message.setSubject(assunto);
    message.setText(texto);

    emailSender.send(message);
  }
}
