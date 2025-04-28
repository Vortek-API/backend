package vortek.sistponto.vortekponto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import vortek.sistponto.vortekponto.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByLogin(String login);
}
