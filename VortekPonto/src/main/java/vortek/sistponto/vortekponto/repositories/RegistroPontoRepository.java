package vortek.sistponto.vortekponto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vortek.sistponto.vortekponto.models.RegistroPonto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface RegistroPontoRepository extends JpaRepository<RegistroPonto, Integer> {
    List<RegistroPonto> findByColaboradorEmpresaId(Integer colaboradorEmpresaId);

    @Query("SELECT rp FROM RegistroPonto rp " +
            "WHERE rp.colaboradorEmpresa.id IN :colabEmpIds " +
            "AND (:dataInicio IS NULL OR rp.data >= :dataInicio) " +
            "AND (:dataFim IS NULL OR rp.data <= :dataFim)")
    List<RegistroPonto> findByColaboradorEmpresasAndData(@Param("colabEmpIds") List<Integer> colabEmpIds,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);
            
    @Query("SELECT rp FROM RegistroPonto rp " +
            "WHERE (:dataInicio IS NULL OR rp.data >= :dataInicio) " +
            "AND (:dataFim IS NULL OR rp.data <= :dataFim)")
    List<RegistroPonto> findByData(@Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);



    @Query("SELECT new map(ce.empresa.id as empresaId, ce.empresa.nome as empresaNome, COUNT(DISTINCT rp.colaboradorEmpresa.colaborador.id) as quantidade) " +
       "FROM RegistroPonto rp " +
       "JOIN rp.colaboradorEmpresa ce " +
       "WHERE rp.data = :data " +
       "AND rp.horaEntrada <= :horaFim " +
       "AND rp.horaSaida >= :horaInicio " +
       "GROUP BY ce.empresa.id, ce.empresa.nome")
    List<Map<String, Object>> contarColaboradoresPorEmpresaEHorario(
        @Param("data") LocalDate data,
        @Param("horaInicio") LocalTime horaInicio,
        @Param("horaFim") LocalTime horaFim);

}