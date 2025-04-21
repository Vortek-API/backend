package vortek.sistponto.vortekponto.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vortek.sistponto.vortekponto.dto.ColaboradorDto;
import vortek.sistponto.vortekponto.dto.EmpresaDto;
import vortek.sistponto.vortekponto.exceptions.ObjectNotFoundException;
import vortek.sistponto.vortekponto.services.ColaboradorEmpresaService;
import vortek.sistponto.vortekponto.services.EmpresaService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    EmpresaService empresaService;

    @Autowired
    ColaboradorEmpresaService colabEmpService;

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
    @GetMapping("/colabs/{id}")
    public ResponseEntity<List<ColaboradorDto>> buscarColabs(@PathVariable Integer id) {
        List<ColaboradorDto> colabEmp = colabEmpService.buscarColaboradoresPorEmpresaId(id);
        if (colabEmp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(colabEmp);
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
