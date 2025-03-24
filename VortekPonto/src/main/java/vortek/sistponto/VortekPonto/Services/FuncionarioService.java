package vortek.sistponto.VortekPonto.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vortek.sistponto.VortekPonto.Dto.FuncionarioDto;
import vortek.sistponto.VortekPonto.Models.Funcionario;
import vortek.sistponto.VortekPonto.Repositories.FuncionarioRepository;
import vortek.sistponto.VortekPonto.Services.Exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }

    public Funcionario salvar(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario buscarPorId(long id) {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
        return funcionario.orElseThrow(() -> new ObjectNotFoundException("Funcionário com id " + id + " não encontrado!"));
    }

    public Boolean excluirFunc(long id) {
        if (funcionarioRepository.existsById(id)) { // Verifica se o Funcionario existe
            funcionarioRepository.deleteById(id); // Deleta o Funcionario pelo ID
            return true;
        }
        return false; // Caso o Funcionario não exista
    }
}

