package vortek.sistponto.vortekponto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vortek.sistponto.vortekponto.models.Empresa;


@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    boolean existsByCnpj(String cnpj);
}
