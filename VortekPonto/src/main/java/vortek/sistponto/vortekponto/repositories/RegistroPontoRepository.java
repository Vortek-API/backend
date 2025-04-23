package vortek.sistponto.vortekponto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vortek.sistponto.vortekponto.models.RegistroPonto;

import java.time.LocalDate;
import java.util.List;

public interface RegistroPontoRepository extends JpaRepository<RegistroPonto, Integer> {
    List<RegistroPonto> findByColaboradorEmpresaId(Integer colaboradorEmpresaId);

    @Query("SELECT rp FROM RegistroPonto rp " +
            "WHERE rp.colaboradorEmpresa.id IN :colabEmpIds " +
            "AND (:dataInicio IS NULL OR rp.data >= :dataInicio) " +
            "AND (:dataFim IS NULL OR rp.data <= :dataFim)")
    List<RegistroPonto> findByColaboradorEmpresasAndData(@Param("colabEmpIds") List<Integer> colabEmpIds,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);

}
