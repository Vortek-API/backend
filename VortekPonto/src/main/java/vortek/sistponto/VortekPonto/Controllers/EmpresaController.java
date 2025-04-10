package vortek.sistponto.VortekPonto.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vortek.sistponto.VortekPonto.Dto.EmpresaDto;
import vortek.sistponto.VortekPonto.Services.EmpresaService;
import vortek.sistponto.VortekPonto.Services.Exceptions.ObjectNotFoundException;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<?> cadastrarEmpresa(@RequestBody EmpresaDto empresa) {
        try {
            empresa = empresaService.cadastrarEmpresa(empresa);
            return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Erro ao criar empresa: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<EmpresaDto>> listar() {
        List<EmpresaDto> emp = empresaService.listarEmpresas();
        if (emp.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(emp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDto> buscarPorId(@PathVariable Integer id) {
        EmpresaDto emp = empresaService.buscarPorId(id);
        if (emp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(emp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarEmpresa(@PathVariable Integer id) {
        try {
            String resultado = empresaService.deletarEmpresa(id);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Empresa não encontrada!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDto> atualizarEmpresa(@PathVariable Integer id, @RequestBody EmpresaDto empresa) {
        try {
            EmpresaDto emp = empresaService.atualizarEmpresa(id, empresa);
            return ResponseEntity.ok(emp);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

}
