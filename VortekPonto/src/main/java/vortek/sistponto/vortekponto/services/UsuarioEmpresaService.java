package vortek.sistponto.vortekponto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vortek.sistponto.vortekponto.dto.UsuarioEmpresaDto;
import vortek.sistponto.vortekponto.models.Empresa;
import vortek.sistponto.vortekponto.models.Usuario;
import vortek.sistponto.vortekponto.models.UsuarioEmpresa;
import vortek.sistponto.vortekponto.repositories.EmpresaRepository;
import vortek.sistponto.vortekponto.repositories.UsuarioEmpresaRepository;
import vortek.sistponto.vortekponto.repositories.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioEmpresaService {

    @Autowired
    private UsuarioEmpresaRepository usuarioEmpresaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public UsuarioEmpresaDto salvar(UsuarioEmpresaDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Empresa empresa = empresaRepository.findById(dto.empresaId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
        usuarioEmpresa.setUsuario(usuario);
        usuarioEmpresa.setEmpresa(empresa);

        usuarioEmpresa = usuarioEmpresaRepository.save(usuarioEmpresa);
        return new UsuarioEmpresaDto(usuarioEmpresa.getId(), usuario.getId(), empresa.getId());
    }

    public List<UsuarioEmpresaDto> listarTodos() {
        return usuarioEmpresaRepository.findAll()
                .stream()
                .map(ue -> new UsuarioEmpresaDto(ue.getId(), ue.getUsuario().getId(), ue.getEmpresa().getId()))
                .collect(Collectors.toList());
    }

    public UsuarioEmpresaDto buscarPorId(Integer id) {
        Optional<UsuarioEmpresa> optional = usuarioEmpresaRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Relação não encontrada");
        }
        UsuarioEmpresa ue = optional.get();
        return new UsuarioEmpresaDto(ue.getId(), ue.getUsuario().getId(), ue.getEmpresa().getId());
    }

    public boolean deletar(Integer id) {
        Optional<UsuarioEmpresa> optional = usuarioEmpresaRepository.findById(id);
        if (optional.isEmpty()) {
            return false;
        }
        usuarioEmpresaRepository.delete(optional.get());
        return true;
    }
}
