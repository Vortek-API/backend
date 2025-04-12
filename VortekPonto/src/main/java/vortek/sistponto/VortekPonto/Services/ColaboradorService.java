package vortek.sistponto.VortekPonto.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vortek.sistponto.VortekPonto.Dto.ColaboradorDto;
import vortek.sistponto.VortekPonto.Exceptions.CpfInvalidoException;
import vortek.sistponto.VortekPonto.Models.Colaborador;
import vortek.sistponto.VortekPonto.Models.Empresa;
import vortek.sistponto.VortekPonto.Repositories.ColaboradorRepository;
import vortek.sistponto.VortekPonto.Exceptions.ObjectNotFoundException;
import vortek.sistponto.VortekPonto.Utils.ValidadorCPF;


@Service
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private ValidadorCPF validadorCPF;

    public List<ColaboradorDto> listarTodos() {
        List<Colaborador> colaborador = colaboradorRepository.findAll();
        return colaborador.stream().map(this::converterParaDto).collect(Collectors.toList());
    }

    public ColaboradorDto salvar(ColaboradorDto funcionario) {
        Colaborador colaboradorExistente = colaboradorRepository.findByCpf(funcionario.cpf());

        if(colaboradorExistente != null){
            throw new CpfInvalidoException("CPF já cadastrado: " + funcionario.cpf());
        }

        if (!validadorCPF.isValidCpf(funcionario.cpf())) {
            throw new CpfInvalidoException("CPF inválido: " + funcionario.cpf());
        }

        Colaborador colaborador = criaColaborador(funcionario);

        colaborador = colaboradorRepository.save(colaborador);

        return converterParaDto(colaborador);
    }

    private static Colaborador criaColaborador(ColaboradorDto funcionario) {
        Colaborador colaborador = new Colaborador();

        colaborador.setCpf(funcionario.cpf());
        colaborador.setNome(funcionario.nome());
        colaborador.setCargo(funcionario.cargo());
        colaborador.setHorarioEntrada(funcionario.horarioEntrada());
        colaborador.setHorarioSaida(funcionario.horarioSaida());
        colaborador.setStatusAtivo(funcionario.statusAtivo());
        colaborador.setFoto(funcionario.foto());

        if(funcionario.empresaId() != null){
            Empresa empresa = new Empresa();
            empresa.setId(funcionario.empresaId());
            colaborador.setEmpresa(empresa);
        }else{
            throw new ObjectNotFoundException("O ID da empresa é obrigatório!");
        }
        return colaborador;
    }


    public ColaboradorDto buscarPorId(Integer id) {
        Colaborador funcionario = colaboradorRepository.findById(id).get();
        return converterParaDto(funcionario);
    }

    public Boolean excluirFunc(Integer id) {
        if (colaboradorRepository.existsById(id)) { // Verifica se o Funcionario existe
            colaboradorRepository.deleteById(id); // Deleta o Funcionario pelo ID
            return true;
        }
        return false; // Caso o Funcionario não exista
    }

    public ColaboradorDto atualizar(Integer id, ColaboradorDto colaboradorAtualizado) {
        Colaborador colaborador = colaboradorRepository.findById(id).orElse(null);

        if(colaborador == null){
            throw new ObjectNotFoundException("Colaborador não encontrado com o ID: " + id);
        }

        colaborador.setCpf(colaboradorAtualizado.cpf());
        colaborador.setNome(colaboradorAtualizado.nome());
        colaborador.setCargo(colaboradorAtualizado.cargo());
        colaborador.setHorarioEntrada(colaboradorAtualizado.horarioEntrada());
        colaborador.setHorarioSaida(colaboradorAtualizado.horarioSaida());
        colaborador.setStatusAtivo(colaboradorAtualizado.statusAtivo());

        if(colaboradorAtualizado.empresaId() != null){
            Empresa empresa = new Empresa();
            empresa.setId(colaboradorAtualizado.empresaId());
            colaborador.setEmpresa(empresa);
        }else{
            throw new ObjectNotFoundException("Erro: O ID da empresa é obrigatório!");
        }

        colaborador = colaboradorRepository.save(colaborador);

        return converterParaDto(colaborador);
    }

    private ColaboradorDto converterParaDto(Colaborador colaborador) {
        return new ColaboradorDto(
                colaborador.getId(),
                colaborador.getCpf(),
                colaborador.getNome(),
                colaborador.getCargo(),
                colaborador.getHorarioEntrada(),
                colaborador.getHorarioSaida(),
                colaborador.isStatusAtivo(),
                colaborador.getEndereco(),
                colaborador.getEmail(),
                colaborador.getDataCadastro(),
                "urlDaFoto", // Inserir lógica para obter a URL da foto
                colaborador.getEmpresa().getId()
        );
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

    //Extrair nome da foto
    public String extrairNomeArquivoDaUrl(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }
}