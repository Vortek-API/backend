package vortek.sistponto.VortekPonto.Services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vortek.sistponto.VortekPonto.Models.TipoUsuario;

import vortek.sistponto.VortekPonto.Models.Usuario;
import vortek.sistponto.VortekPonto.Repositories.UsuarioRepository;
import vortek.sistponto.VortekPonto.Security.SenhaHashing;

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
            if (usuarioRepository.findByLogin("13364958000103") == null) {
                criarUsuario("13364958000103", "admin", TipoUsuario.ADMIN);
            }
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage() +  " " + e);
        }
    }

}
