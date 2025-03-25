package vortek.sistponto.VortekPonto.Controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import vortek.sistponto.VortekPonto.Services.Exceptions.ObjectNotFoundException;

@RestController
@RequestMapping("/colaborador")
public class ColaboradorController {

    @Autowired
    private ColaboradorService colaboradorService;

    @GetMapping
    public List<Colaborador> listarTodos() {
        return colaboradorService.listarTodos();
    }

    @PostMapping
    public Colaborador criar(@RequestBody Colaborador funcionario) {
        return colaboradorService.salvar(funcionario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable long id) {
        try{
            Colaborador funcionario = colaboradorService.buscarPorId(id);
            if(funcionario == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(funcionario);
        }catch (ObjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirFunc(@PathVariable long id) {
        boolean isDeleted = colaboradorService.excluirFunc(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();  // 204 No Content
        } else {
            return ResponseEntity.notFound().build();  // 404 Not Found
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<Colaborador> atualizar(@PathVariable Long id, @RequestBody Colaborador colaboradorAtualizado) {
        Colaborador colaborador = colaboradorService.atualizar(id, colaboradorAtualizado);
        return colaborador != null ? ResponseEntity.ok(colaborador) : ResponseEntity.notFound().build();
    }

}