package vortek.sistponto.vortekponto.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vortek.sistponto.vortekponto.dto.ColaboradorDto;
import vortek.sistponto.vortekponto.exceptions.CpfInvalidoException;
import vortek.sistponto.vortekponto.exceptions.ObjectNotFoundException;
import vortek.sistponto.vortekponto.models.Colaborador;
import vortek.sistponto.vortekponto.repositories.ColaboradorRepository;
import vortek.sistponto.vortekponto.utils.ValidadorCPF;

@Service
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private ValidadorCPF validadorCPF;

    // Lista todos os colaboradores
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

    
    public ColaboradorDto buscarEntidadePorId(Long id) {
        Colaborador c = colaboradorRepository.findById(id.intValue()) 
                .orElseThrow(() -> new ObjectNotFoundException("Colaborador não encontrado com o ID: " + id));
        return converterParaDto(c);
    }


    public Boolean excluirFunc(Long id) {
        if (!colaboradorRepository.existsById(id.intValue())) { 
            return false;  
        }
        colaboradorRepository.deleteById(id.intValue());
        return true;
    }

    public ColaboradorDto atualizar(Long id, ColaboradorDto dto) {
        Colaborador colaborador = colaboradorRepository.findById(id.intValue()) 
                .orElseThrow(() -> new ObjectNotFoundException("Colaborador não encontrado com o ID: " + id));

    
        if (!colaborador.getCpf().equals(dto.cpf()) && colaboradorRepository.findByCpf(dto.cpf()) != null) {
            throw new CpfInvalidoException("CPF já cadastrado: " + dto.cpf());
        }

    
        colaborador.setCpf(dto.cpf());
        colaborador.setNome(dto.nome());
        colaborador.setCargo(dto.cargo());
        colaborador.setHorarioEntrada(dto.horarioEntrada());
        colaborador.setHorarioSaida(dto.horarioSaida());
        colaborador.setStatusAtivo(dto.statusAtivo());
        colaborador.setFoto(dto.foto());

        return converterParaDto(colaboradorRepository.save(colaborador));
    }

    public ColaboradorDto converterParaDto(Colaborador colaborador) {
        return new ColaboradorDto(
                colaborador.getId(),
                colaborador.getNome(),
                colaborador.getCpf(),
                colaborador.getCargo(),
                colaborador.getHorarioEntrada(),
                colaborador.getHorarioSaida(),
                colaborador.getStatusAtivo(),
                colaborador.getFoto()
        );
    }

    public Colaborador criaColaborador(ColaboradorDto dto) {
        return new Colaborador(
                dto.cpf(),
                dto.nome(),
                dto.cargo(),
                dto.horarioEntrada(),
                dto.horarioSaida(),
                dto.statusAtivo(),
                dto.foto()
        );
    }

    public List<Colaborador> buscarTodos() {
        return colaboradorRepository.findAll();
    }
}
