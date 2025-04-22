package vortek.sistponto.vortekponto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vortek.sistponto.vortekponto.dto.RegistroPontoDto;
import vortek.sistponto.vortekponto.exceptions.ObjectNotFoundException;
import vortek.sistponto.vortekponto.models.ColaboradorEmpresa;
import vortek.sistponto.vortekponto.models.RegistroPonto;
import vortek.sistponto.vortekponto.repositories.ColaboradorEmpresaRepository;
import vortek.sistponto.vortekponto.repositories.RegistroPontoRepository;

import java.util.List;

@Service
public class RegistroPontoService {

    @Autowired
    private RegistroPontoRepository repository;

    @Autowired
    private ColaboradorEmpresaRepository colaboradorEmpresaRepository;

    public RegistroPontoDto salvar(RegistroPontoDto dto) {
        ColaboradorEmpresa ce = colaboradorEmpresaRepository.findById(dto.colaboradorEmpresaId())
                .orElseThrow(() -> new ObjectNotFoundException("Associação não encontrada"));

        RegistroPonto rp = new RegistroPonto();
        rp.setColaboradorEmpresa(ce);
        rp.setData(dto.data());
        rp.setHoraEntrada(dto.horaEntrada());
        rp.setHoraSaida(dto.horaSaida());
        rp.setTempoTotal(dto.tempoTotal());

        return toDto(repository.save(rp));
    }

    public RegistroPontoDto buscarPorId(Integer id) {
        return toDto(repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Registro não encontrado")));
    }

    public void deletar(Integer id) {
        repository.deleteById(id);
    }

    public List<RegistroPontoDto> listarTodos() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    private RegistroPontoDto toDto(RegistroPonto rp) {
        return new RegistroPontoDto(
                rp.getId(),
                rp.getColaboradorEmpresa().getId(),
                rp.getData(),
                rp.getHoraEntrada(),
                rp.getHoraSaida(),
                rp.getTempoTotal()
        );
    }
    public List<RegistroPontoDto> buscarPorColaboradorEEmpresa(Integer colaboradorId, Integer empresaId) {
        ColaboradorEmpresa colaboradorEmpresa = colaboradorEmpresaRepository
                .findByColaboradorIdAndEmpresaId(colaboradorId, empresaId)
                .orElseThrow(() -> new ObjectNotFoundException("Associação não encontrada."));

        List<RegistroPonto> registros = repository
                .findByColaboradorEmpresaId(colaboradorEmpresa.getId());

        return registros.stream().map(this::toDto).toList();
    }

}

