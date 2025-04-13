package vortek.sistponto.VortekPonto.Controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vortek.sistponto.VortekPonto.Services.UsuarioService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login) {
        try {
            String tipo = usuarioService.autenticar(login.getLogin(), login.getSenha());
            return ResponseEntity.ok(new LoginResponse(tipo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }


    @Getter
    @Setter
    public static class LoginRequest {
        private String login;
        private String senha;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class LoginResponse {
        private String grupo;
    }

}
