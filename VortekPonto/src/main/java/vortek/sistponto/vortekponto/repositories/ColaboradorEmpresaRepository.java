package vortek.sistponto.vortekponto.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vortek.sistponto.vortekponto.models.Colaborador;
import vortek.sistponto.vortekponto.models.ColaboradorEmpresa;
import vortek.sistponto.vortekponto.models.Empresa;

@Repository
public interface ColaboradorEmpresaRepository extends JpaRepository<ColaboradorEmpresa, Integer> {
    @Query("SELECT ce.empresa FROM ColaboradorEmpresa ce WHERE ce.colaborador.id = :colaboradorId")
    List<Empresa> findEmpresasByColaboradorId(@Param("colaboradorId") Integer colaboradorId);

    @Query("SELECT ce.colaborador FROM ColaboradorEmpresa ce WHERE ce.empresa.id = :empresaId")
    List<Colaborador> findColaboradoresByEmpresaId(@Param("empresaId") Integer empresaId);

    @Query("SELECT ce FROM ColaboradorEmpresa ce WHERE ce.colaborador.id = :colaboradorId AND ce.empresa.id = :empresaId")
    Optional<ColaboradorEmpresa> findByColaboradorIdAndEmpresaId(@Param("colaboradorId") Integer colaboradorId, @Param("empresaId") Integer empresaId);

}
