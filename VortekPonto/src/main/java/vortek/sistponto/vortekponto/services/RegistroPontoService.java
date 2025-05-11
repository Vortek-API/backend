package vortek.sistponto.vortekponto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vortek.sistponto.vortekponto.dto.RegistroPontoDto;
import vortek.sistponto.vortekponto.dto.RegistroPontoResponseDto;
import vortek.sistponto.vortekponto.exceptions.ObjectNotFoundException;
import vortek.sistponto.vortekponto.models.ColaboradorEmpresa;
import vortek.sistponto.vortekponto.models.RegistroPonto;
import vortek.sistponto.vortekponto.repositories.ColaboradorEmpresaRepository;
import vortek.sistponto.vortekponto.repositories.RegistroPontoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public RegistroPontoDto editar(Integer id, RegistroPontoDto dto) {

        RegistroPonto rp = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Registro não encontrado"));

        if(dto.data() != null) {
            rp.setData(dto.data());
        }

        if(dto.horaEntrada() != null) {
            rp.setHoraEntrada(dto.horaEntrada());
        }

        if(dto.horaSaida() != null) {
            rp.setHoraSaida(dto.horaSaida());
        }

        if(dto.tempoTotal() != null) {
            rp.setTempoTotal(dto.tempoTotal());
        }

        if(dto.justificativa() != null) {
            rp.setJustificativa(dto.justificativa());
        }

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
                rp.getTempoTotal(),
                rp.getJustificativa());
    }

    public List<RegistroPontoDto> buscarPorColaboradorEEmpresa(Integer colaboradorId, Integer empresaId) {
        ColaboradorEmpresa colaboradorEmpresa = colaboradorEmpresaRepository
                .findByColaboradorIdAndEmpresaId(colaboradorId, empresaId)
                .orElseThrow(() -> new ObjectNotFoundException("Associação não encontrada."));

        List<RegistroPonto> registros = repository
                .findByColaboradorEmpresaId(colaboradorEmpresa.getId());

        return registros.stream().map(this::toDto).toList();
    }

    public List<RegistroPontoDto> buscarRegistros(
            Integer colaboradorId,
            List<Integer> empresasId,
            LocalDate dataInicio,
            LocalDate dataFim) {

        List<ColaboradorEmpresa> relacoes;

        if (colaboradorId != null && empresasId != null && !empresasId.isEmpty()) {
            relacoes = colaboradorEmpresaRepository.findByColaboradorIdAndEmpresaIdIn(colaboradorId, empresasId);
        } else if (colaboradorId != null) {
            relacoes = colaboradorEmpresaRepository.findByColaboradorId(colaboradorId);
        } else if (empresasId != null && !empresasId.isEmpty()) {
            relacoes = colaboradorEmpresaRepository.findByEmpresaIdIn(empresasId);
        } else {
            relacoes = colaboradorEmpresaRepository.findAll();
        }

        List<Integer> colabEmpIds = relacoes.stream()
                .map(ColaboradorEmpresa::getId)
                .toList();

        List<RegistroPonto> registros = repository.findByColaboradorEmpresasAndData(
                colabEmpIds, dataInicio, dataFim);

        return registros.stream().map(this::toDto).toList();
    }

    public List<RegistroPontoResponseDto> buscarRegistrosDetalhados(
            Integer colaboradorId,
            List<Integer> empresasId,
            LocalDate dataInicio,
            LocalDate dataFim) {

        return toDtoDetalhado(this.buscarRegistros(colaboradorId, empresasId, dataInicio, dataFim));
    }

    public List<Map<String, Object>> calcularHorasPorEmpresa(LocalDate dataInicio, LocalDate dataFim) {
        // Buscar todos os registros de ponto no período
        List<RegistroPonto> registros = repository.findByData(dataInicio, dataFim);

        // Agrupar por empresa e calcular horas totais
        Map<Integer, Long> horasPorEmpresaMap = new HashMap<>();
        Map<Integer, String> empresaNomeMap = new HashMap<>();

        for (RegistroPonto registro : registros) {
            Integer empresaId = registro.getColaboradorEmpresa().getEmpresa().getId();
            String empresaNome = registro.getColaboradorEmpresa().getEmpresa().getNome();
            empresaNomeMap.put(empresaId, empresaNome);

            // Calcular horas em segundos e adicionar ao total
            if (registro.getTempoTotal() != null) {
                long segundos = registro.getTempoTotal().toSecondOfDay();
                horasPorEmpresaMap.put(empresaId,
                        horasPorEmpresaMap.getOrDefault(empresaId, 0L) + segundos);
            }
        }

        // Converter para lista de mapas para resposta
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Map.Entry<Integer, Long> entry : horasPorEmpresaMap.entrySet()) {
            Integer empresaId = entry.getKey();
            Long segundosTotais = entry.getValue();

            // Converter segundos para horas:minutos:segundos
            long horas = segundosTotais / 3600;
            long minutos = (segundosTotais % 3600) / 60;
            long segundos = segundosTotais % 60;

            Map<String, Object> empresaHoras = new HashMap<>();
            empresaHoras.put("empresaId", empresaId);
            empresaHoras.put("empresaNome", empresaNomeMap.get(empresaId));
            empresaHoras.put("horasTotais", horas);
            empresaHoras.put("minutosTotais", minutos);
            empresaHoras.put("segundosTotais", segundos);
            empresaHoras.put("segundosAbsolutos", segundosTotais);

            resultado.add(empresaHoras);
        }

        return resultado;
    }

    public List<RegistroPontoResponseDto> toDtoDetalhado(List<RegistroPontoDto> list) {
        List<Integer> colaboradorEmpresaIds = list.stream()
                .map(RegistroPontoDto::colaboradorEmpresaId)
                .distinct()
                .toList();

        Map<Integer, ColaboradorEmpresa> colaboradorEmpresaMap = colaboradorEmpresaRepository
                .findAllById(colaboradorEmpresaIds)
                .stream()
                .collect(Collectors.toMap(ColaboradorEmpresa::getId, ce -> ce));

        return list.stream().map(dto -> {
            ColaboradorEmpresa ce = colaboradorEmpresaMap.get(dto.colaboradorEmpresaId());
            return new RegistroPontoResponseDto(
                    dto.id(),
                    ce.getColaborador().getId(),
                    ce.getEmpresa().getId(),
                    dto.data(),
                    dto.horaEntrada(),
                    dto.horaSaida(),
                    dto.tempoTotal(),
                    dto.justificativa()
            );
        }).toList();
    }

}