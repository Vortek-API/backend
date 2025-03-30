package vortek.sistponto.VortekPonto.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vortek.sistponto.VortekPonto.Models.Colaborador;
import vortek.sistponto.VortekPonto.Repositories.ColaboradorRepository;
import vortek.sistponto.VortekPonto.Services.Exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public List<Colaborador> listarTodos() {
        return colaboradorRepository.findAll();
    }

    public Colaborador salvar(Colaborador funcionario) {
        String resultadoValidacao = validarCPF(funcionario.getCpf());

        if (!resultadoValidacao.equals("O CPF é válido!")) {
            throw new IllegalArgumentException(resultadoValidacao);
        }

        return colaboradorRepository.save(funcionario);
    }

    public static String validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return "Erro: O CPF deve conter exatamente 11 dígitos.";
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return "Erro: O CPF não pode ter todos os dígitos iguais.";
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }
        int primeiroDigito = (soma * 10) % 11;
        if (primeiroDigito == 10) primeiroDigito = 0;

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }
        int segundoDigito = (soma * 10) % 11;
        if (segundoDigito == 10) segundoDigito = 0;

        if (primeiroDigito != (cpf.charAt(9) - '0')) {
            return "Erro: O primeiro dígito verificador está incorreto.";
        }
        if (segundoDigito != (cpf.charAt(10) - '0')) {
            return "Erro: O segundo dígito verificador está incorreto.";
        }

        return "O CPF é válido!";
    }

    public Colaborador buscarPorId(Integer id) {
        Optional<Colaborador> funcionario = colaboradorRepository.findById(id);
        return funcionario.orElseThrow(() -> new ObjectNotFoundException("Funcionário com id " + id + " não encontrado!"));
    }

    public Boolean excluirFunc(Integer id) {
        if (colaboradorRepository.existsById(id)) { // Verifica se o Funcionario existe
            colaboradorRepository.deleteById(id); // Deleta o Funcionario pelo ID
            return true;
        }
        return false; // Caso o Funcionario não exista
    }

    public Colaborador atualizar(Integer id, Colaborador colaboradorAtualizado) {
        return colaboradorRepository.findById(id).map(colaborador -> {
            colaborador.setCpf(colaboradorAtualizado.getCpf());
            colaborador.setNome(colaboradorAtualizado.getNome());
            colaborador.setCargo(colaboradorAtualizado.getCargo());
            colaborador.setHora_ent(colaboradorAtualizado.getHora_ent());
            colaborador.setHora_sai(colaboradorAtualizado.getHora_sai());
            colaborador.setStatus(colaboradorAtualizado.isStatus());
            colaborador.setEmpresa(colaboradorAtualizado.getEmpresa());
            return colaboradorRepository.save(colaborador);
        }).orElse(null);
    }
}