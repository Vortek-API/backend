package vortek.sistponto.VortekPonto.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vortek.sistponto.VortekPonto.Models.Empresa;
import vortek.sistponto.VortekPonto.Services.EmpresaService;
import vortek.sistponto.VortekPonto.Services.Exceptions.ObjectNotFoundException;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    EmpresaService empresaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrarEmpresa(@RequestBody Empresa empresa) {
        try {
            empresaService.cadastrarEmpresa(empresa);
            return ResponseEntity.ok("Empresa cadastrada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> listar() {
        List<Empresa> emp = empresaService.listarEmpresas();
        if (emp.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(emp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscarPorId(@PathVariable Integer id) {
        Empresa emp = empresaService.buscarPorId(id);
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
    public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable Integer id, @RequestBody Empresa empresa) {
        try {
            Empresa updatedEmpresa = empresaService.atualizarEmpresa(id, empresa);
            return ResponseEntity.ok(updatedEmpresa);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

}
