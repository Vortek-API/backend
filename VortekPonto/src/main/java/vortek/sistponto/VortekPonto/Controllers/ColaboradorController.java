package vortek.sistponto.VortekPonto.Controllers;

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

import vortek.sistponto.VortekPonto.Models.Colaborador;
import vortek.sistponto.VortekPonto.Services.ColaboradorService;
import vortek.sistponto.VortekPonto.Services.EmpresaService;
import vortek.sistponto.VortekPonto.Services.Exceptions.ObjectNotFoundException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/colaborador")
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
            if (colaborador.getEmpresa() == null || colaborador.getEmpresa().getId() == null) {
                return ResponseEntity.badRequest().body("Erro: O ID da empresa é obrigatório!");
            }

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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> excluirFunc(@PathVariable Integer id) {
        boolean isDeleted = colaboradorService.excluirFunc(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/{id}")
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