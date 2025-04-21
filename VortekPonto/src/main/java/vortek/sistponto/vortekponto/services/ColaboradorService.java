package vortek.sistponto.vortekponto.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vortek.sistponto.vortekponto.dto.ColaboradorComEmpresasDto;
import vortek.sistponto.vortekponto.dto.ColaboradorDto;
import vortek.sistponto.vortekponto.dto.EmpresaDto;
import vortek.sistponto.vortekponto.exceptions.CpfInvalidoException;
import vortek.sistponto.vortekponto.exceptions.ObjectNotFoundException;
import vortek.sistponto.vortekponto.models.Colaborador;
import vortek.sistponto.vortekponto.models.ColaboradorEmpresa;
import vortek.sistponto.vortekponto.repositories.ColaboradorRepository;
import vortek.sistponto.vortekponto.utils.ValidadorCPF;

@Service
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private ColaboradorEmpresaService colabEmpService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private ValidadorCPF validadorCPF;

    public List<ColaboradorComEmpresasDto> listarTodos() {
        return colaboradorRepository.findAll()
                .stream()
                .map(colab -> {
                    List<EmpresaDto> empresas = colabEmpService.buscarEmpresasDoColaborador(colab.getId());
                    return new ColaboradorComEmpresasDto(
                            colab.getId(),
                            colab.getCpf(),
                            colab.getNome(),
                            colab.getCargo(),
                            colab.getHorarioEntrada(),
                            colab.getHorarioSaida(),
                            colab.isStatusAtivo(),
                            colab.getDataCadastro(),
                            colab.getFoto(),
                            empresas);
                }).collect(Collectors.toList());
    }

    public ColaboradorDto salvar(ColaboradorDto dto, Integer[] empresasId) {
        if (colaboradorRepository.findByCpf(dto.cpf()) != null) {
            throw new CpfInvalidoException("CPF já cadastrado: " + dto.cpf());
        }

        if (!validadorCPF.isValidCpf(dto.cpf())) {
        throw new CpfInvalidoException("CPF inválido: " + dto.cpf());
        }

        Colaborador novo = criaColaborador(dto);

        ColaboradorDto save = converterParaDto(colaboradorRepository.save(novo));

        colabEmpService.associarColaboradorAEmpresas(save, empresasId);

        return save;
    }

    public ColaboradorComEmpresasDto buscarPorId(Integer id) {
        Colaborador colaborador = colaboradorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Colaborador não encontrado com o ID: " + id));

        List<EmpresaDto> empresasDto = colabEmpService.buscarEmpresasDoColaborador(id);

        return new ColaboradorComEmpresasDto(
                colaborador.getId(),
                colaborador.getCpf(),
                colaborador.getNome(),
                colaborador.getCargo(),
                colaborador.getHorarioEntrada(),
                colaborador.getHorarioSaida(),
                colaborador.isStatusAtivo(),
                colaborador.getDataCadastro(),
                colaborador.getFoto(),
                empresasDto);
    }

    public Boolean excluirFunc(Integer id) {
        if (colaboradorRepository.existsById(id)) {
            List<ColaboradorEmpresa> associacoes = colabEmpService.buscarAssociacoesPorColaborador(id);
            colabEmpService.excluirAssociacoes(associacoes);
            colaboradorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ColaboradorDto atualizar(Integer id, ColaboradorDto dto, Integer[] empresasId) {
        Colaborador colaborador = colaboradorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Colaborador não encontrado com o ID: " + id));

        colaborador.setCpf(dto.cpf());
        colaborador.setNome(dto.nome());
        colaborador.setCargo(dto.cargo());
        colaborador.setHorarioEntrada(dto.horarioEntrada());
        colaborador.setHorarioSaida(dto.horarioSaida());
        colaborador.setStatusAtivo(dto.statusAtivo());
        colaborador.setFoto(dto.foto());

        ColaboradorDto save = converterParaDto(colaboradorRepository.save(colaborador));

        List<ColaboradorEmpresa> associacoesAntigas = colabEmpService.buscarAssociacoesPorColaborador(id);
        colabEmpService.excluirAssociacoes(associacoesAntigas);

        colabEmpService.associarColaboradorAEmpresas(save, empresasId);

        return save;
    }

    public void atualizarFoto(Integer id, String imageUrl) {
        Colaborador colaborador = colaboradorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Colaborador não encontrado com o ID: " + id));
        colaborador.setFoto(imageUrl);
        colaboradorRepository.save(colaborador);
    }

    public String buscarFotoUrl(Integer id) {
        Colaborador colaborador = colaboradorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Colaborador não encontrado com o ID: " + id));
        return colaborador.getFoto();
    }

    public String extrairNomeArquivoDaUrl(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }

    private Colaborador criaColaborador(ColaboradorDto dto) {
        Colaborador c = new Colaborador();
        c.setCpf(dto.cpf());
        c.setNome(dto.nome());
        c.setCargo(dto.cargo());
        c.setHorarioEntrada(dto.horarioEntrada());
        c.setHorarioSaida(dto.horarioSaida());
        c.setStatusAtivo(dto.statusAtivo());
        c.setFoto(dto.foto());
        return c;
    }

    private ColaboradorDto converterParaDto(Colaborador c) {
        return new ColaboradorDto(
                c.getId(),
                c.getCpf(),
                c.getNome(),
                c.getCargo(),
                c.getHorarioEntrada(),
                c.getHorarioSaida(),
                c.isStatusAtivo(),
                c.getDataCadastro(),
                c.getFoto());
    }
}
