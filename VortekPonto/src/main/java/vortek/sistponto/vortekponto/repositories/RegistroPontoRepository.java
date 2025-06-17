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
    List<RegistroPonto> findByColaboradorIdAndEmpresaId(Integer colaboradorId, Integer empresaId);

    @Query("SELECT rp FROM RegistroPonto rp " +
            "WHERE (:colaboradorId IS NULL OR rp.colaborador.id = :colaboradorId) " +
            "AND (:empresasId IS NULL OR rp.empresa.id IN :empresasId) " +
            "AND (:dataInicio IS NULL OR rp.data >= :dataInicio) " +
            "AND (:dataFim IS NULL OR rp.data <= :dataFim)")
    List<RegistroPonto> findByColaboradorEmpresasAndData(
            @Param("colaboradorId") Integer colaboradorId,
            @Param("empresasId") List<Integer> empresasId,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);
            
    @Query("SELECT rp FROM RegistroPonto rp " +
            "WHERE (:dataInicio IS NULL OR rp.data >= :dataInicio) " +
            "AND (:dataFim IS NULL OR rp.data <= :dataFim)")
    List<RegistroPonto> findByData(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);

    @Query("SELECT new map(rp.empresa.id as empresaId, rp.empresa.nome as empresaNome, COUNT(DISTINCT rp.colaborador.id) as quantidade) " +
       "FROM RegistroPonto rp " +
       "WHERE rp.data = :data " +
       "AND rp.horaEntrada <= :horaFim " +
       "AND rp.horaSaida >= :horaInicio " +
       "GROUP BY rp.empresa.id, rp.empresa.nome")
    List<Map<String, Object>> contarColaboradoresPorEmpresaEHorario(
        @Param("data") LocalDate data,
        @Param("horaInicio") LocalTime horaInicio,
        @Param("horaFim") LocalTime horaFim);
}