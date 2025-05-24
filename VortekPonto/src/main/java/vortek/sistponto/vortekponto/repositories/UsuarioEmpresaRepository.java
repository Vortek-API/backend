package vortek.sistponto.vortekponto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vortek.sistponto.vortekponto.models.UsuarioEmpresa;

import java.util.List;

@Repository
public interface UsuarioEmpresaRepository extends JpaRepository<UsuarioEmpresa, Integer> {
    List<UsuarioEmpresa> findByUsuarioId(Integer usuarioId);
    void deleteByUsuarioId(Integer usuarioId);
}
