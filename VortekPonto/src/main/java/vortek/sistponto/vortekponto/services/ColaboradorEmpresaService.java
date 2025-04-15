package vortek.sistponto.vortekponto.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vortek.sistponto.vortekponto.models.Colaborador;
import vortek.sistponto.vortekponto.models.ColaboradorEmpresa;
import vortek.sistponto.vortekponto.models.Empresa;
import vortek.sistponto.vortekponto.repositories.ColaboradorEmpresaRepository;


@Service
public class ColaboradorEmpresaService {
    private final ColaboradorEmpresaRepository repository;

    public ColaboradorEmpresaService(ColaboradorEmpresaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ColaboradorEmpresa associarColaboradorAEmpresa(Empresa empresa, Colaborador colaborador) {
        ColaboradorEmpresa associacao = new ColaboradorEmpresa(colaborador, empresa);
        return repository.save(associacao);
    }

    public List<Empresa> buscarEmpresasPorColaborador(Integer colaboradorId) {
        return repository.findEmpresasByColaborador(colaboradorId);
    }

    public List<Colaborador> buscarColaboradoresPorEmpresa(Integer empresaId) {
        return repository.findColaboradoresByEmpresa(empresaId);
    }

    @Transactional
    public void removerAssociacao(Integer id) {
        repository.deleteById(id);
    }
}
