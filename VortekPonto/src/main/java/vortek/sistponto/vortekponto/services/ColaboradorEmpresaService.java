package vortek.sistponto.vortekponto.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vortek.sistponto.vortekponto.dto.ColaboradorDto;
import vortek.sistponto.vortekponto.dto.ColaboradorEmpresaDto;
import vortek.sistponto.vortekponto.dto.EmpresaDto;
import vortek.sistponto.vortekponto.exceptions.ObjectNotFoundException;
import vortek.sistponto.vortekponto.models.Colaborador;
import vortek.sistponto.vortekponto.models.ColaboradorEmpresa;
import vortek.sistponto.vortekponto.models.Empresa;
import vortek.sistponto.vortekponto.repositories.ColaboradorEmpresaRepository;
import vortek.sistponto.vortekponto.repositories.ColaboradorRepository;
import vortek.sistponto.vortekponto.repositories.EmpresaRepository;

@Service
public class ColaboradorEmpresaService {

    @Autowired
    private ColaboradorEmpresaRepository repository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaService empresaService;

    public List<ColaboradorEmpresaDto> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ColaboradorEmpresaDto buscarPorId(Integer id) {
        ColaboradorEmpresa ce = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Associação não encontrada: " + id));
        return toDto(ce);
    }

    public ColaboradorEmpresaDto associarColaboradorAEmpresa(EmpresaDto empresaDto, ColaboradorDto colaboradorDto) {
        Colaborador colaborador = colaboradorRepository.findById(colaboradorDto.id())
                .orElseThrow(() -> new ObjectNotFoundException("Colaborador não encontrado: " + colaboradorDto.id()));

        Empresa empresa = empresaRepository.findById(empresaDto.id())
                .orElseThrow(() -> new ObjectNotFoundException("Empresa não encontrada: " + empresaDto.id()));

        ColaboradorEmpresa ce = new ColaboradorEmpresa();
        ce.setColaborador(colaborador);
        ce.setEmpresa(empresa);

        return toDto(repository.save(ce));
    }

    public List<ColaboradorEmpresaDto> associarColaboradorAEmpresas(ColaboradorDto colaboradorDto,
            Integer[] empresasId) {

        Colaborador colaborador = colaboradorRepository.findById(colaboradorDto.id())
                .orElseThrow(() -> new ObjectNotFoundException("Colaborador não encontrado: " + colaboradorDto.id()));

        List<ColaboradorEmpresaDto> associacoesDto = new ArrayList<>();

        for (Integer empresaId : empresasId) {
            EmpresaDto empresaDto = empresaService.buscarPorId(empresaId);
            associacoesDto.add(associarColaboradorAEmpresa(empresaDto, colaboradorDto));
        }

        return associacoesDto;
    }

    public Boolean excluirPorId(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<EmpresaDto> buscarEmpresasDoColaborador(Integer colaboradorId) {
        return repository.findEmpresasByColaboradorId(colaboradorId)
                .stream()
                .map(emp -> new EmpresaDto(emp.getId(), emp.getNome(), emp.getCnpj(), emp.getDataCadastro()))
                .collect(Collectors.toList());
    }

    @Autowired
    private ColaboradorEmpresaRepository colaboradorEmpresaRepository;

    public List<ColaboradorDto> buscarColaboradoresPorEmpresaId(Integer empresaId) {
        return colaboradorEmpresaRepository.findColaboradoresByEmpresaId(empresaId)
                .stream()
                .map(colab -> new ColaboradorDto(
                        colab.getId(),
                        colab.getCpf(),
                        colab.getNome(),
                        colab.getCargo(),
                        colab.getHorarioEntrada(),
                        colab.getHorarioSaida(),
                        colab.isStatusAtivo(),
                        colab.getDataCadastro(),
                        colab.getFoto()))
                .collect(Collectors.toList());
    }

    public List<ColaboradorEmpresa> buscarAssociacoesPorColaborador(Integer colaboradorId) {
        return colaboradorEmpresaRepository.findAll()
                .stream()
                .filter(ce -> ce.getColaborador().getId().equals(colaboradorId))
                .collect(Collectors.toList());
    }

    public void excluirAssociacoes(List<ColaboradorEmpresa> associacoes) {
        if (associacoes != null && !associacoes.isEmpty()) {
            colaboradorEmpresaRepository.deleteAll(associacoes);
        }
    }

    private ColaboradorEmpresaDto toDto(ColaboradorEmpresa ce) {
        return new ColaboradorEmpresaDto(
                ce.getId(),
                ce.getColaborador().getId(),
                ce.getEmpresa().getId());
    }
}
