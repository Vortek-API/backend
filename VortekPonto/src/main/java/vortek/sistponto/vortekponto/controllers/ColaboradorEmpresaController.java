package vortek.sistponto.vortekponto.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vortek.sistponto.vortekponto.models.Colaborador;
import vortek.sistponto.vortekponto.models.Empresa;
import vortek.sistponto.vortekponto.repositories.ColaboradorRepository;
import vortek.sistponto.vortekponto.repositories.EmpresaRepository;
import vortek.sistponto.vortekponto.services.ColaboradorEmpresaService;
import vortek.sistponto.vortekponto.dto.AssociarColaboradorEmpresaDto;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/colaborador-empresa")
public class ColaboradorEmpresaController {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ColaboradorEmpresaService service;


    @PostMapping
    public ResponseEntity<?> associar(@RequestBody AssociarColaboradorEmpresaDto dto) {
        Integer colaboradorId = dto.getColaboradorId();
        Integer empresaId = dto.getEmpresaId();

        var colaborador = colaboradorRepository.findById(colaboradorId);
        var empresa = empresaRepository.findById(empresaId);

        if (colaborador.isEmpty() || empresa.isEmpty()) {
            return ResponseEntity.badRequest().body("Colaborador ou Empresa não encontrada");
        }

        // Realizando a associação
        var associacao = service.associarColaboradorAEmpresa(empresa.get(), colaborador.get());
        return ResponseEntity.ok(associacao);
    }

    @GetMapping("/empresas/{colaboradorId}")
    public ResponseEntity<List<Empresa>> buscarEmpresas(@PathVariable Integer colaboradorId) {
        List<Empresa> empresas = service.buscarEmpresasPorColaborador(colaboradorId);
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/colaboradores/{empresaId}")
    public ResponseEntity<List<Colaborador>> buscarColaboradores(@PathVariable Integer empresaId) {
        List<Colaborador> colaboradores = service.buscarColaboradoresPorEmpresa(empresaId);
        return ResponseEntity.ok(colaboradores);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerAssociacao(@PathVariable Integer id) {
        service.removerAssociacao(id);
        return ResponseEntity.noContent().build();
    }

}
