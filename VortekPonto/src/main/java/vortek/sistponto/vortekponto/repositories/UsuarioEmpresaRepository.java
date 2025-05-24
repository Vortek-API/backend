package vortek.sistponto.vortekponto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vortek.sistponto.vortekponto.models.UsuarioEmpresa;

import java.util.List;

@Repository
public interface UsuarioEmpresaRepository extends JpaRepository<UsuarioEmpresa, Integer> {
    void deleteByUsuarioId(Integer id);

    List<UsuarioEmpresa> findByUsuarioId(Integer usuarioId);
}
