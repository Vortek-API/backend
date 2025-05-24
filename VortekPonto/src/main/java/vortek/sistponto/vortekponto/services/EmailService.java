package vortek.sistponto.vortekponto.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmailBemVindo(String destinatario, String senha) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destinatario);
        message.setSubject("Bem-vindo ao Time Sync");
        message.setText("Conta criada!\nE-mail: " + destinatario + "\nSenha: " + senha + "\nAltere sua senha no primeiro login utilizando a opção: Problemas com a Senha?.");
        message.setFrom("vtk.timesync@gmail.com");
        mailSender.send(message);
    }
}