package vortek.sistponto.vortekponto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vortek.sistponto.vortekponto.exceptions.TokenInvalidoException;
import vortek.sistponto.vortekponto.models.SenhaToken;
import vortek.sistponto.vortekponto.models.Usuario;
import vortek.sistponto.vortekponto.repositories.SenhaTokenRepository;
import vortek.sistponto.vortekponto.repositories.UsuarioRepository;
import org.springframework.mail.javamail.JavaMailSender;
import vortek.sistponto.vortekponto.security.SenhaHashing;

import java.util.Date;
import java.util.UUID;

@Service
public class SenhaTokenService {

    @Autowired
    private SenhaTokenRepository senhaTokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Transactional
    public void criarSenhaToken(String login) {
        Usuario usuario = usuarioRepository.findByLogin(login);
        if (usuario == null) {
            throw new TokenInvalidoException("Usuário não encontrado com o email: " + login);
        }

        senhaTokenRepository.deleteByUsuarioId(usuario.getId());
        String token = UUID.randomUUID().toString();
        SenhaToken senhaToken = new SenhaToken(token, usuario, new Date(System.currentTimeMillis() + 3600000));
        senhaTokenRepository.save(senhaToken);
        sendEmail(usuario.getLogin(), token);
    }

    public void sendEmail(String login, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(login);
        System.out.println(message);
        message.setSubject("Recuperação de Senha");
        message.setText("Para redefinir sua senha, clique no link: " +
                "http://localhost:4200/reset-senha-reset?token=" + token);
        mailSender.send(message);
    }

    public boolean isTokenExpired(String senhaToken) {
        SenhaToken token = senhaTokenRepository.findByToken(senhaToken);
        if (token == null || token.getTempToken().before(new Date())) {
            return true;
        }
        return false;
    }

    @Transactional
    public void redefinirSenha(String token, String novaSenha) {
        SenhaToken senhaToken = senhaTokenRepository.findByToken(token);
        if (senhaToken == null || senhaToken.getTempToken().before(new Date())) {
            throw new RuntimeException("Token inválido ou expirado");
        }
        Usuario usuario = senhaToken.getUser();
        String novoSalt = SenhaHashing.gerarSalt();
        String novoHash = SenhaHashing.hashSenha(novaSenha, novoSalt);
        usuario.setSalt(novoSalt);
        usuario.setSenha(novoHash);
        usuarioRepository.save(usuario);
        senhaTokenRepository.delete(senhaToken);
    }
}