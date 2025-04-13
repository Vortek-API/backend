package vortek.sistponto.VortekPonto.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vortek.sistponto.VortekPonto.Models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByLogin(String login);
}
