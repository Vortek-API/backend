package vortek.sistponto.vortekponto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vortek.sistponto.vortekponto.models.RegistroPonto;
import java.util.List;

public interface RegistroPontoRepository extends JpaRepository<RegistroPonto, Integer> {
    List<RegistroPonto> findByColaboradorEmpresaId(Integer colaboradorEmpresaId);
}

