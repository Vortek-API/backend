package vortek.sistponto.vortekponto.controllers;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vortek.sistponto.vortekponto.dto.LoginResponse;
import vortek.sistponto.vortekponto.dto.NovoUsuarioDto;
import vortek.sistponto.vortekponto.dto.UsuarioEmpresaDto;
import vortek.sistponto.vortekponto.models.Usuario;
import vortek.sistponto.vortekponto.repositories.UsuarioEmpresaRepository;
import vortek.sistponto.vortekponto.repositories.UsuarioRepository;
import vortek.sistponto.vortekponto.services.EmailService;
import vortek.sistponto.vortekponto.services.UsuarioEmpresaService;
import vortek.sistponto.vortekponto.services.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioEmpresaService usuarioEmpresaService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private UsuarioEmpresaRepository usuarioEmpresaRepository;

    @GetMapping("/lista")
    public List<UsuarioEmpresaDto> lista() {
        return usuarioEmpresaService.listarTodos();
    }

    @GetMapping("/{id}")
    public LoginResponse buscarPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id);
    }

    @PostMapping("/novo-usuario")
    public ResponseEntity<Map<String, String>> novoUsuario(@Valid @RequestBody NovoUsuarioDto dto) {
        try {
            usuarioService.criarUsuario(dto.email(), "Senha@123", dto.grupo());
            Usuario usuario = usuarioRepository.findByLogin(dto.email());
            if (usuario == null) {
                throw new IllegalStateException("Usuário não encontrado após criação");
            }
            System.out.println("Empresas IDs recebidas: " + dto.empresasIds());
            System.out.println("Empresas IDs recebidas: " + dto.grupo());
            System.out.println("Empresas IDs recebidas: " + dto.email());
            usuarioEmpresaService.vincularUsuarioEmpresas(usuario.getId(), dto.empresasIds());
            emailService.enviarEmailBemVindo(dto.email(), "Senha@123");

            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuário criado e e-mail enviado.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Erro: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/empresas")
    public ResponseEntity<Map<String, String>> atualizarEmpresas(@PathVariable Integer id, @RequestBody List<Integer> empresasIds) {
        try {
            usuarioEmpresaService.atualizarEmpresas(id, empresasIds);
            return ResponseEntity.ok(Map.of("message", "Empresas atualizadas com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao atualizar empresas: " + e.getMessage()));
        }
    }

    @DeleteMapping("/deletar/{usuarioId}")
    public ResponseEntity<Map<String, String>> deletarUsuarioCompleto(@PathVariable Integer usuarioId) {
        try {
            usuarioService.excluirUsuarioCompleto(usuarioId);
            return ResponseEntity.ok(Map.of("message", "Usuario deletado com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao deletar usuario: " + e.getMessage()));
        }
    }
}