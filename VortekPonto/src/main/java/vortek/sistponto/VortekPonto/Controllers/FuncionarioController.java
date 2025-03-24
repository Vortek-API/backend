package vortek.sistponto.VortekPonto.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vortek.sistponto.VortekPonto.Models.Funcionario;
import vortek.sistponto.VortekPonto.Services.Exceptions.ObjectNotFoundException;
import vortek.sistponto.VortekPonto.Services.FuncionarioService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public List<Funcionario> listarTodos() {
        return funcionarioService.listarTodos();
    }

    @PostMapping
    public Funcionario criar(@RequestBody Funcionario funcionario) {
        return funcionarioService.salvar(funcionario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable long id) {
        try{
            Funcionario funcionario = funcionarioService.buscarPorId(id);
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
        boolean isDeleted = funcionarioService.excluirFunc(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();  // 204 No Content
        } else {
            return ResponseEntity.notFound().build();  // 404 Not Found
        }

    }

}