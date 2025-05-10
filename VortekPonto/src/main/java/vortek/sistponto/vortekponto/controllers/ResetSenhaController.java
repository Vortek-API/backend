package vortek.sistponto.vortekponto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vortek.sistponto.vortekponto.services.SenhaTokenService;

@RestController
@RequestMapping("api/reset-senha")
public class ResetSenhaController {

    @Autowired
    private SenhaTokenService senhaService;

    @PostMapping("/request")
    public ResponseEntity<String> requestResetSenha(@RequestParam String login) {
        try{
            senhaService.criarSenhaToken(login);
            return ResponseEntity.noContent().build();
        }catch(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/validar")
    public ResponseEntity<Boolean> validarToken(@RequestParam String token) {
        boolean isValid = !senhaService.isTokenExpired(token);
        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetSenha(@RequestParam String token, @RequestParam String novaSenha) {
        try {
        senhaService.redefinirSenha(token, novaSenha);
            return ResponseEntity.noContent().build();
    }catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}