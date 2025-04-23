package vortek.sistponto.vortekponto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vortek.sistponto.vortekponto.dto.RegistroPontoDto;
import vortek.sistponto.vortekponto.services.RegistroPontoService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/ponto")
@CrossOrigin(origins = "http://localhost:4200")
public class RegistroPontoController {

    @Autowired
    private RegistroPontoService service;

    @PostMapping
    public ResponseEntity<RegistroPontoDto> criar(@RequestBody RegistroPontoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @GetMapping
    public ResponseEntity<List<RegistroPontoDto>> buscarRegistros(
            @RequestParam(required = false) Integer colaboradorId,
            @RequestParam(required = false) List<Integer> empresasId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        List<RegistroPontoDto> registros = service.buscarRegistros(colaboradorId, empresasId, dataInicio,
                dataFim);
        return ResponseEntity.ok(registros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroPontoDto> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/colaborador/{colaboradorId}/empresa/{empresaId}")
    public ResponseEntity<?> buscarRegistrosPorColaboradorEEmpresa(
            @PathVariable Integer colaboradorId,
            @PathVariable Integer empresaId) {
        try {
            List<RegistroPontoDto> registros = service.buscarPorColaboradorEEmpresa(colaboradorId, empresaId);
            return ResponseEntity.ok(registros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
