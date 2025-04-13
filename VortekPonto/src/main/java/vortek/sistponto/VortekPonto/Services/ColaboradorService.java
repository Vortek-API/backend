package vortek.sistponto.VortekPonto.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vortek.sistponto.VortekPonto.Dto.ColaboradorDto;
import vortek.sistponto.VortekPonto.Exceptions.CpfInvalidoException;
import vortek.sistponto.VortekPonto.Exceptions.ObjectNotFoundException;
import vortek.sistponto.VortekPonto.Models.Colaborador;
import vortek.sistponto.VortekPonto.Repositories.ColaboradorRepository;
import vortek.sistponto.VortekPonto.Utils.ValidadorCPF;

@Service
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private ValidadorCPF validadorCPF;

    public List<ColaboradorDto> listarTodos() {
        return colaboradorRepository.findAll()
                .stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    public ColaboradorDto salvar(ColaboradorDto dto) {
        if (colaboradorRepository.findByCpf(dto.cpf()) != null) {
            throw new CpfInvalidoException("CPF já cadastrado: " + dto.cpf());
        }

        if (!validadorCPF.isValidCpf(dto.cpf())) {
            throw new CpfInvalidoException("CPF inválido: " + dto.cpf());
        }

        Colaborador novo = criaColaborador(dto);
        return converterParaDto(colaboradorRepository.save(novo));
    }

    public ColaboradorDto buscarPorId(Integer id) {
        Colaborador c = colaboradorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Colaborador não encontrado com o ID: " + id));
        return converterParaDto(c);
    }

    public Boolean excluirFunc(Integer id) {
        if (colaboradorRepository.existsById(id)) {
            colaboradorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ColaboradorDto atualizar(Integer id, ColaboradorDto dto) {
        Colaborador colaborador = colaboradorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Colaborador não encontrado com o ID: " + id));

        colaborador.setCpf(dto.cpf());
        colaborador.setNome(dto.nome());
        colaborador.setCargo(dto.cargo());
        colaborador.setHorarioEntrada(dto.horarioEntrada());
        colaborador.setHorarioSaida(dto.horarioSaida());
        colaborador.setStatusAtivo(dto.statusAtivo());
        colaborador.setFoto(dto.foto());

        return converterParaDto(colaboradorRepository.save(colaborador));
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
                c.getFoto()
        );
    }
}
