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
    private EmpresaRepository empresaRepository;

    public Empresa cadastrarEmpresa(Empresa empresa) {
        if (empresaRepository.existsByCnpj(empresa.getCnpj())) {
            throw new RuntimeException("CNPJ já cadastrado!");
        }
        return empresaRepository.save(empresa);
    }

    public EmpresaDto criarEmpresa(EmpresaDto empresa) {
        Empresa emp = new Empresa();
        emp.setNome(empresa.getNome());
        emp.setCnpj(empresa.getCnpj());
        emp = empresaRepository.save(emp);
        return new EmpresaDto(emp);
    }

    public List<EmpresaDto> listarEmpresas() {
        List<Empresa> empresas = empresaRepository.findAll();
        return empresas.stream().map(EmpresaDto::new).collect(Collectors.toList());

    }

    public EmpresaDto buscarPorId(Integer id) {
        Empresa empresa = empresaRepository.findById(id).get();
        if (empresa == null) {
            throw new ObjectNotFoundException("Event: " + id + " not found!");
        }
        return new EmpresaDto(empresa);
    }

}
