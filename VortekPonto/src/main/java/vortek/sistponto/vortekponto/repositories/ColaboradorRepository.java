package vortek.sistponto.vortekponto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vortek.sistponto.vortekponto.models.Colaborador;

import java.util.List;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer> {

    
    Colaborador findByCpf(String cpf);

    
    @Query("SELECT c FROM Colaborador c JOIN c.colaboradorEmpresas ce WHERE ce.empresa.id = :empresaId")
    List<Colaborador> findByEmpresaId(Integer empresaId);
}
