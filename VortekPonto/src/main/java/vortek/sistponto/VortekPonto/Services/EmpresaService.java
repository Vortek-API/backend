package vortek.sistponto.VortekPonto.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vortek.sistponto.VortekPonto.Dto.EmpresaDto;
import vortek.sistponto.VortekPonto.Models.Empresa;
import vortek.sistponto.VortekPonto.Repositories.EmpresaRepository;
import vortek.sistponto.VortekPonto.Services.Exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    @Autowired
    EmpresaRepository empresaRepository;

    public EmpresaDto criarEmpresa(EmpresaDto empresa) {
        Empresa emp = new Empresa();
        emp.setNome(empresa.getNome());
        emp.setCnpj(empresa.getCnpj());
        emp.setIdUsuario(empresa.getIdUsuario());
        emp = empresaRepository.save(emp);
        return new EmpresaDto(emp);
    }

    public List<EmpresaDto> listarEmpresas() {
        List<Empresa> empresas = empresaRepository.findAll();
        return empresas.stream().map(EmpresaDto::new).collect(Collectors.toList());

    }

    public EmpresaDto buscarPorId(Long id) {
        Empresa empresa = empresaRepository.findById(id).get();

        if (empresa == null) {
            throw new ObjectNotFoundException("Event: " + id + " not found!");
        }
        return new EmpresaDto(empresa);
    }

}
