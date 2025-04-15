package vortek.sistponto.vortekponto.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vortek.sistponto.vortekponto.dto.EmpresaDto;
import vortek.sistponto.vortekponto.exceptions.CNPJInvalidoException;
import vortek.sistponto.vortekponto.exceptions.ObjectNotFoundException;
import vortek.sistponto.vortekponto.models.Empresa;
import vortek.sistponto.vortekponto.repositories.EmpresaRepository;
import vortek.sistponto.vortekponto.utils.ValidadorCNPJ;


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
                empresa.getCnpj(),
                empresa.getDataCadastro()
        );
    }
}
