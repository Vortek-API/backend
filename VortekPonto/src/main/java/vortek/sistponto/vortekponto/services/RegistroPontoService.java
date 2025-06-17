package vortek.sistponto.vortekponto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vortek.sistponto.vortekponto.dto.RegistroPontoDto;
import vortek.sistponto.vortekponto.dto.RegistroPontoResponseDto;
import vortek.sistponto.vortekponto.exceptions.ObjectNotFoundException;
import vortek.sistponto.vortekponto.models.Colaborador;
import vortek.sistponto.vortekponto.models.Empresa;
import vortek.sistponto.vortekponto.models.RegistroPonto;
import vortek.sistponto.vortekponto.repositories.ColaboradorRepository;
import vortek.sistponto.vortekponto.repositories.EmpresaRepository;
import vortek.sistponto.vortekponto.repositories.RegistroPontoRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RegistroPontoService {

    @Autowired
    private RegistroPontoRepository repository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public RegistroPontoDto salvar(RegistroPontoDto dto) {
        Colaborador colaborador = colaboradorRepository.findById(dto.colaboradorId())
                .orElseThrow(() -> new ObjectNotFoundException("Colaborador não encontrado"));
        
        Empresa empresa = empresaRepository.findById(dto.empresaId())
                .orElseThrow(() -> new ObjectNotFoundException("Empresa não encontrada"));

        RegistroPonto rp = new RegistroPonto();
        rp.setColaborador(colaborador);
        rp.setEmpresa(empresa);
        rp.setData(dto.data());
        rp.setHoraEntrada(dto.horaEntrada());
        rp.setHoraSaida(dto.horaSaida());
        rp.setTempoTotal(dto.tempoTotal());
        rp.setJustificativa(dto.justificativa());

        return toDto(repository.save(rp));
    }

    public RegistroPontoDto salvar(RegistroPontoResponseDto dto) {
        Colaborador colaborador = colaboradorRepository.findById(dto.colaboradorId())
                .orElseThrow(() -> new ObjectNotFoundException("Colaborador não encontrado"));
        
        Empresa empresa = empresaRepository.findById(dto.empresaId())
                .orElseThrow(() -> new ObjectNotFoundException("Empresa não encontrada"));

        RegistroPonto rp = new RegistroPonto();
        rp.setColaborador(colaborador);
        rp.setEmpresa(empresa);
        rp.setData(dto.data());
        rp.setHoraEntrada(dto.horaEntrada());
        rp.setHoraSaida(dto.horaSaida());
        rp.setTempoTotal(dto.tempoTotal());
        rp.setJustificativa(dto.justificativa());

        return toDto(repository.save(rp));
    }

    public RegistroPontoDto editar(Integer id, RegistroPontoDto dto) {
        RegistroPonto rp = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Registro não encontrado"));

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
                rp.getColaborador().getId(),
                rp.getEmpresa().getId(),
                rp.getData(),
                rp.getHoraEntrada(),
                rp.getHoraSaida(),
                rp.getTempoTotal(),
                rp.getJustificativa());
    }

    public List<RegistroPontoDto> buscarPorColaboradorEEmpresa(Integer colaboradorId, Integer empresaId) {
        List<RegistroPonto> registros = repository.findByColaboradorIdAndEmpresaId(colaboradorId, empresaId);
        return registros.stream().map(this::toDto).toList();
    }

    public List<RegistroPontoDto> buscarRegistros(
            Integer colaboradorId,
            List<Integer> empresasId,
            LocalDate dataInicio,
            LocalDate dataFim) {

        List<RegistroPonto> registros = repository.findByColaboradorEmpresasAndData(
                colaboradorId, empresasId, dataInicio, dataFim);

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
        List<RegistroPonto> registros = repository.findByData(dataInicio, dataFim);

        Map<Integer, Long> horasPorEmpresaMap = new HashMap<>();
        Map<Integer, String> empresaNomeMap = new HashMap<>();

        for (RegistroPonto registro : registros) {
            Integer empresaId = registro.getEmpresa().getId();
            String empresaNome = registro.getEmpresa().getNome();
            empresaNomeMap.put(empresaId, empresaNome);

            if (registro.getTempoTotal() != null) {
                long segundos = registro.getTempoTotal().toSecondOfDay();
                horasPorEmpresaMap.put(empresaId,
                        horasPorEmpresaMap.getOrDefault(empresaId, 0L) + segundos);
            }
        }

        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Map.Entry<Integer, Long> entry : horasPorEmpresaMap.entrySet()) {
            Integer empresaId = entry.getKey();
            Long segundosTotais = entry.getValue();

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
        return list.stream()
                .sorted(Comparator.comparing(
                        RegistroPontoDto::data, Comparator.reverseOrder())
                        .thenComparing(RegistroPontoDto::empresaId)
                        .thenComparing(RegistroPontoDto::colaboradorId))
                .map(dto -> new RegistroPontoResponseDto(
                        dto.id(),
                        dto.colaboradorId(),
                        dto.empresaId(),
                        dto.data(),
                        dto.horaEntrada(),
                        dto.horaSaida(),
                        dto.tempoTotal(),
                        dto.justificativa()))
                .toList();
    }

    public List<Map<String, Object>> contarColaboradoresPorEmpresaNoPeriodo(LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
        return repository.contarColaboradoresPorEmpresaEHorario(data, horaInicio, horaFim);
    }
}