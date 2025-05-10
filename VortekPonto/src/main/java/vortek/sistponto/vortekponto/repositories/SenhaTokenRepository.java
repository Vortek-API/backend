package vortek.sistponto.vortekponto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vortek.sistponto.vortekponto.models.SenhaToken;
import vortek.sistponto.vortekponto.models.Usuario;

public interface SenhaTokenRepository extends JpaRepository<SenhaToken, Integer> {
    SenhaToken findByToken(String token);

    @Modifying
    @Query("DELETE FROM SenhaToken st WHERE st.user.id = :usuarioId")
    void deleteByUsuarioId(Integer usuarioId);
}
