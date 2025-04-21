package vortek.sistponto.vortekponto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vortek.sistponto.vortekponto.dto.ColaboradorDto;
import vortek.sistponto.vortekponto.models.Colaborador;
import vortek.sistponto.vortekponto.services.ColaboradorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/colaborador")
public class ColaboradorController {

    @Autowired
    private ColaboradorService colaboradorService;

    @GetMapping("/empresas/{id}")
    public ResponseEntity<ColaboradorDto> buscarColaboradorComEmpresas(@PathVariable Long id) {
        Colaborador colaborador = colaboradorService.buscarEntidadePorId(id.intValue());  // Convertendo para Integer
        ColaboradorDto dto = colaboradorService.converterParaDto(colaborador);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<ColaboradorDto>> listarTodos() {
        List<Colaborador> colaboradores = colaboradorService.buscarTodos();
        List<ColaboradorDto> dtos = colaboradores.stream()
                .map(colaboradorService::converterParaDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<ColaboradorDto> buscarPorId(@PathVariable Long id) {
        Colaborador colaborador = colaboradorService.buscarEntidadePorId(id.intValue());
        ColaboradorDto dto = colaboradorService.converterParaDto(colaborador);
        return ResponseEntity.ok(dto);
    }
}
