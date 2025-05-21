package vortek.sistponto.vortekponto.services;

import jakarta.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;
import vortek.sistponto.vortekponto.dto.LoginResponse;
import vortek.sistponto.vortekponto.models.Empresa;
import vortek.sistponto.vortekponto.models.TipoUsuario;
import vortek.sistponto.vortekponto.models.Usuario;
import vortek.sistponto.vortekponto.repositories.UsuarioEmpresaRepository;
import vortek.sistponto.vortekponto.repositories.UsuarioRepository;
import vortek.sistponto.vortekponto.security.SenhaHashing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioEmpresaService usuarioEmpresaService;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public void criarUsuario(String login, String senha, TipoUsuario grupo) {
        try {

            if (usuarioRepository.findByLogin(login) != null) {
                throw new IllegalArgumentException("E-mail já registrado " + login);
            }

            String salt = SenhaHashing.gerarSalt();
            String hashSenha = SenhaHashing.hashSenha(senha, salt);

            Usuario usuario = new Usuario();
            usuario.setLogin(login);
            usuario.setSalt(salt);
            usuario.setSenha(hashSenha);
            usuario.setGrupo(grupo);
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() + " " + e);
        }
    }

    public LoginResponse autenticar(String login, String senha) {
        try {
            initAdmin();
            initEmpresa();
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
            Empresa[] empresas = new Empresa[0];
            if (grupo == TipoUsuario.EMPRESA) {
                empresas = usuarioEmpresaService.listarEmpresasPorUsuario(usuario.getId()).toArray(new Empresa[0]);
            }
            if (grupo == TipoUsuario.ADMIN) empresas = new Empresa[0];

        return new LoginResponse(usuario.getId(), usuario.getLogin(), grupo.name(), empresas);
    } catch(
    Exception e)

    {
        throw new RuntimeException("Erro ao autenticar: " + e.getMessage(), e);
    }
}

@PostConstruct // Executa após a inicialização
public void initAdmin() {
    try {
        if (usuarioRepository.findByLogin("vortek@altave.com.br") == null) {
            criarUsuario("vortek@altave.com.br", "admin", TipoUsuario.ADMIN);
        }
    } catch (Exception e) {
        throw new RuntimeException(e.getMessage() + " " + e);
    }
}

@PostConstruct // Executa após a inicialização
public void initEmpresa() {
    try {
        if (usuarioRepository.findByLogin("vortekEmp@altave.com.br") == null) {
            criarUsuario("vortekEmp@altave.com.br", "emp", TipoUsuario.EMPRESA);
        }
    } catch (Exception e) {
        throw new RuntimeException(e.getMessage() + " " + e);
    }
}


@Transactional
public void excluirUsuarioCompleto(Integer usuarioId) {
    usuarioEmpresaService.atualizarEmpresas(usuarioId, List.of());
    usuarioRepository.deleteById(usuarioId);
}
}
