package vortek.sistponto.VortekPonto.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vortek.sistponto.VortekPonto.Models.Empresa;


@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    boolean existsByCnpj(String cnpj);
}
