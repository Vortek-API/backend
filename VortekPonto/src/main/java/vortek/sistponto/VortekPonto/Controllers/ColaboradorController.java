package vortek.sistponto.VortekPonto.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vortek.sistponto.VortekPonto.Models.Colaborador;
import vortek.sistponto.VortekPonto.Services.ColaboradorService;
import vortek.sistponto.VortekPonto.Services.EmpresaService;
import vortek.sistponto.VortekPonto.Services.Exceptions.ObjectNotFoundException;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/colaborador")
@CrossOrigin(origins = "http://localhost:4200")
public class ColaboradorController {

    @Autowired
    private ColaboradorService colaboradorService;

    @Autowired
    private EmpresaService empresaService;

    @GetMapping()
    public List<Colaborador> listarTodos() {
        List<Colaborador> colaboradores = colaboradorService.listarTodos();
        return (colaboradores != null) ? colaboradores : Collections.emptyList();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Colaborador colaborador) {
        try {
            if (colaborador.getEmpresa() == null || colaborador.getEmpresa().getId_emp() == null) {
                return ResponseEntity.badRequest().body("Erro: O ID da empresa é obrigatório!");
            }

            //EmpresaDto empresa = empresaService.buscarPorId(colaborador.getEmpresa().getId_emp());

            Colaborador novoColaborador = colaboradorService.salvar(colaborador);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoColaborador);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Erro ao criar colaborador: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            Colaborador colaborador = colaboradorService.buscarPorId(id);
            if (colaborador == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(colaborador);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirFunc(@PathVariable Integer id) {
        boolean isDeleted = colaboradorService.excluirFunc(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody Colaborador colaboradorAtualizado) {
        try {
            Colaborador colaborador = colaboradorService.atualizar(id, colaboradorAtualizado);
            return ResponseEntity.ok(colaborador);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }
    }
}