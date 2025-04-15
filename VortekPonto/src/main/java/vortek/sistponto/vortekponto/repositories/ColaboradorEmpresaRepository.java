package vortek.sistponto.vortekponto.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vortek.sistponto.vortekponto.models.Colaborador;
import vortek.sistponto.vortekponto.models.ColaboradorEmpresa;
import vortek.sistponto.vortekponto.models.Empresa;

public interface ColaboradorEmpresaRepository extends JpaRepository<ColaboradorEmpresa, Integer>{
    // Buscar empresas de um colaborador
    @Query("SELECT ec.empresa FROM ColaboradorEmpresa ec WHERE ec.colaborador.id = :colaboradorId")
    List<Empresa> findEmpresasByColaborador(@Param("colaboradorId") Integer colaboradorId);

    // Busca colaboradores de uma empresa
    @Query("SELECT ec.colaborador FROM ColaboradorEmpresa ec WHERE ec.empresa.id = :empresaId")
    List<Colaborador> findColaboradoresByEmpresa(@Param("empresaId") Integer empresaId);
}
