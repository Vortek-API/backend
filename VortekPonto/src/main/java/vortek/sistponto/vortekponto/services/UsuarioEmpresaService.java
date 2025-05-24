package vortek.sistponto.vortekponto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public void vincularUsuarioEmpresas(Integer usuarioId, List<Integer> empresasIds) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }
        if (empresasIds == null || empresasIds.isEmpty()) {
            throw new IllegalArgumentException("A lista de empresas não pode ser nula ou vazia.");
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuario com ID " + usuarioId + "não encontrado.");
        }

        Usuario usuario = usuarioOptional.get();
        List<Empresa> empresas = empresaRepository.findAllById(empresasIds);
        if (empresas.size() != empresasIds.size()) {
            throw new IllegalArgumentException("Uma ou mais empresa não foram encontradas");
        }

        for (Empresa empresa : empresas) {
            UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
            usuarioEmpresa.setUsuario(usuario);
            usuarioEmpresa.setEmpresa(empresa);
            usuarioEmpresaRepository.save(usuarioEmpresa);
        }
    }

    public List<UsuarioEmpresaDto> listarTodos() {
        return usuarioEmpresaRepository.findAll()
                .stream()
                .map(ue -> new UsuarioEmpresaDto(
                        ue.getId(),
                        ue.getUsuario().getId(),
                        ue.getUsuario().getLogin(),
                        ue.getUsuario().getGrupo().name(),
                        ue.getEmpresa().getId(),
                        ue.getEmpresa().getNome()))
                .collect(Collectors.toList());
    }

    public void deletar(Integer id) {
        Optional<UsuarioEmpresa> optional = usuarioEmpresaRepository.findById(id);
        if (optional.isEmpty()) {
            return;
        }
        usuarioEmpresaRepository.delete(optional.get());
    }

    @Transactional
    public void atualizarEmpresas(Integer id, List<Integer> empresasIds) {
        usuarioEmpresaRepository.deleteByUsuarioId(id);
        if (empresasIds != null) {
            for (Integer empresaId : empresasIds) {
                UsuarioEmpresa usuarioEmpresa = new UsuarioEmpresa();
                usuarioEmpresa.setUsuario(usuarioRepository.getReferenceById(id));
                usuarioEmpresa.setEmpresa(empresaRepository.getReferenceById(empresaId));
                usuarioEmpresaRepository.save(usuarioEmpresa);
            }
        }
    }

    public List<Empresa> listarEmpresasPorUsuario(Integer usuarioId) {
        if (usuarioId == null) {
            throw new IllegalArgumentException("O ID do usuário não pode ser nulo.");
        }

        if (!usuarioRepository.existsById(usuarioId)) {
            throw new IllegalArgumentException("Usuário com ID " + usuarioId + " não encontrado.");
        }

        List<UsuarioEmpresa> vinculos = usuarioEmpresaRepository.findByUsuarioId(usuarioId);
        return vinculos.stream()
                .map(UsuarioEmpresa::getEmpresa) // Extrai o objeto Empresa de cada vínculo
                .collect(Collectors.toList());
    }
}