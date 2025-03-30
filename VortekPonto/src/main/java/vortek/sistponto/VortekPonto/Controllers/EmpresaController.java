package vortek.sistponto.VortekPonto.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import vortek.sistponto.VortekPonto.Dto.EmpresaDto;
import vortek.sistponto.VortekPonto.Models.Empresa;
import vortek.sistponto.VortekPonto.Services.EmpresaService;
import vortek.sistponto.VortekPonto.Services.Exceptions.ObjectNotFoundException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/empresa")
@CrossOrigin(origins = "http://localhost:4200")
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

    @PostMapping("/salvar")
    public ResponseEntity<EmpresaDto> salvar(@RequestBody EmpresaDto empresa) {
        EmpresaDto emp = empresaService.criarEmpresa(empresa);
        try {
            if (emp != null) {
                URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(emp.getId()).toUri();
                return ResponseEntity.created(uri).body(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
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
    public ResponseEntity<EmpresaDto> atualizarEmpresa(@PathVariable Integer id, @RequestBody EmpresaDto empresaDto) {
        try {
            EmpresaDto updatedEmpresa = empresaService.atualizarEmpresa(id, empresaDto);
            return ResponseEntity.ok(updatedEmpresa);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

}
