package vortek.sistponto.vortekponto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vortek.sistponto.vortekponto.dto.RegistroPontoDto;
import vortek.sistponto.vortekponto.dto.RegistroPontoResponseDto;
import vortek.sistponto.vortekponto.services.RegistroPontoService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/{colaboradorId}/{empresaId}")
    public ResponseEntity<RegistroPontoDto> criarColabEmp(@RequestBody RegistroPontoResponseDto dto) {
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

    @GetMapping("/detalhado")
    public ResponseEntity<List<RegistroPontoResponseDto>> buscarRegistrosDetalhados(
            @RequestParam(required = false) Integer colaboradorId,
            @RequestParam(required = false) List<Integer> empresasId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        List<RegistroPontoResponseDto> registros = service.buscarRegistrosDetalhados(colaboradorId, empresasId,
                dataInicio, dataFim);
        return ResponseEntity.ok(registros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroPontoDto> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/{id}/detalhado")
    public ResponseEntity<RegistroPontoResponseDto> buscarDetalhado(@PathVariable Integer id) {
        RegistroPontoDto dto = service.buscarPorId(id);
        RegistroPontoResponseDto response = service.toDtoDetalhado(List.of(dto)).get(0);
        return ResponseEntity.ok(response);
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

    @GetMapping("/horas-por-empresa")
    public ResponseEntity<List<Map<String, Object>>> horasPorEmpresa(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        List<Map<String, Object>> horasPorEmpresa = service.calcularHorasPorEmpresa(dataInicio, dataFim);
        return ResponseEntity.ok(horasPorEmpresa);
    }

    @PatchMapping("/{id}/editar")
    public ResponseEntity<RegistroPontoDto> editar(@PathVariable Integer id, @RequestBody RegistroPontoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.editar(id,dto));
    }

   @GetMapping("/contar-colaboradores")
    public List<Map<String, Object>> contarColaboradoresPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaFim) {
        return service.contarColaboradoresPorEmpresaNoPeriodo(data, horaInicio, horaFim);
    }
}