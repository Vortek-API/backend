package vortek.sistponto.VortekPonto.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vortek.sistponto.VortekPonto.Dto.EmpresaDto;
import vortek.sistponto.VortekPonto.Exceptions.CNPJInvalidoException;
import vortek.sistponto.VortekPonto.Exceptions.ObjectNotFoundException;
import vortek.sistponto.VortekPonto.Models.Empresa;
import vortek.sistponto.VortekPonto.Repositories.EmpresaRepository;
import vortek.sistponto.VortekPonto.Utils.ValidadorCNPJ;


@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ValidadorCNPJ validadorCNPJ;

    public EmpresaDto cadastrarEmpresa(EmpresaDto empresa) {
        Empresa emp = new Empresa();

        if (empresaRepository.existsByCnpj(empresa.cnpj())) {
            throw new CNPJInvalidoException("CNPJ já cadastrado!");
        }

        if(!validadorCNPJ.isValidCNPJ(empresa.cnpj())) {
            throw new CNPJInvalidoException("CNPJ inválido: " + empresa.cnpj());
        }

        emp.setNome(empresa.nome());
        emp.setCnpj(empresa.cnpj());
        emp = empresaRepository.save(emp);

        return converterParaDto(emp);
    }

    public List<EmpresaDto> listarEmpresas() {
        List<Empresa> empresas = empresaRepository.findAll();
        return empresas.stream().map(this::converterParaDto).collect(Collectors.toList());

    }

    public EmpresaDto buscarPorId(Integer id) {
        Empresa empresa = empresaRepository.findById(id).orElse(null);
        if (empresa == null) {
            throw new ObjectNotFoundException("Empresa: " + id + " não encontrada!");
        }
        return converterParaDto(empresa);
    }

    public String deletarEmpresa(Integer id) {
        Empresa emp = empresaRepository.findById(id).orElse(null);

        if (emp == null) {
            throw new RuntimeException("Empresa não encontrada!");
        }

        empresaRepository.delete(emp);
        return "Empresa deletada com sucesso!";
    }

    public EmpresaDto atualizarEmpresa(Integer id, EmpresaDto empresa) {
        Empresa emp = empresaRepository.findById(id).orElse(null);

        if (emp == null) {
            throw new ObjectNotFoundException("Empresa não encontrada!");
        }

        emp.setNome(empresa.nome());
        emp.setCnpj(empresa.cnpj());
        emp = empresaRepository.save(emp);

        return converterParaDto(emp);
    }

    private EmpresaDto converterParaDto(Empresa empresa) {
        return new EmpresaDto(
                empresa.getId(),
                empresa.getNome(),
                empresa.getCnpj()
        );
    }
}
