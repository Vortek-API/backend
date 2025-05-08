package vortek.sistponto.vortekponto.services;

import jakarta.annotation.PostConstruct;
import vortek.sistponto.vortekponto.models.TipoUsuario;
import vortek.sistponto.vortekponto.models.Usuario;
import vortek.sistponto.vortekponto.repositories.UsuarioRepository;
import vortek.sistponto.vortekponto.security.SenhaHashing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void criarUsuario(String login, String senha, TipoUsuario grupo) {
        try {
            String salt = SenhaHashing.gerarSalt();
            String hashSenha = SenhaHashing.hashSenha(senha, salt);

            Usuario usuario = new Usuario();
            usuario.setLogin(login);
            usuario.setSalt(salt);
            usuario.setSenha(hashSenha);
            usuario.setGrupo(grupo);
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() +  " " + e);
        }
    }

    public String autenticar(String login, String senha) {
        try {
            initAdmin();
            Usuario usuario = usuarioRepository.findByLogin(login);
            if (usuario == null) {
                throw new RuntimeException("Usuário não encontrado");
            }
            boolean senhaValida = SenhaHashing.validaSenha(senha, usuario.getSalt(), usuario.getSenha());
            if (!senhaValida) {
                throw new RuntimeException("Senha incorreta");
            }

            TipoUsuario grupo = usuario.getGrupo();
            if (grupo == null) {
                throw new RuntimeException("Grupo inválido para o usuário");
            }

            return grupo.name();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao autenticar: " + e.getMessage(), e);
        }
    }

    @PostConstruct // Executa após a inicialização
    public void initAdmin() {
        try {
            if (usuarioRepository.findByLogin("vortek@altave.com.br") == null) {
                criarUsuario("vortek@altave.com.br", "admin", TipoUsuario.ADMIN);
            }
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage() +  " " + e);
        }
    }

}
