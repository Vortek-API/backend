package vortek.sistponto.VortekPonto.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import vortek.sistponto.VortekPonto.Dto.ColaboradorDto;
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
        return colaboradorRepository.save(funcionario);
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