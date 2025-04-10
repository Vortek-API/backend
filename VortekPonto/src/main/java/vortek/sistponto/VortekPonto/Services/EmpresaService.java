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

    public EmpresaDto cadastrarEmpresa(EmpresaDto empresa) {
        if (empresaRepository.existsByCnpj(empresa.cnpj())) {
            throw new RuntimeException("CNPJ já cadastrado!");
        }

        Empresa emp = new Empresa();
        emp.setNome(empresa.nome());
        emp.setTelefone(empresa.telefone());
        emp.setCnpj(empresa.cnpj());
        emp.setLogo(empresa.logo());
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
        emp.setTelefone(empresa.telefone());
        emp.setCnpj(empresa.cnpj());
        emp.setLogo(empresa.logo());
        emp = empresaRepository.save(emp);

        return converterParaDto(emp);
    }

    public boolean validarCNPJ(String cnpj) {

        // Metodo de validação retona um boolean para verificação do CNPJ
        cnpj = cnpj.replaceAll("[^0-9]", "");

        if (cnpj.length() != 14) {
            return false;
        }

        boolean equalsDigits = true;
        for (int i = 0; i < 14; i++) {
            if (cnpj.charAt(i) != cnpj.charAt(0)) {
                equalsDigits = false;
                break;
            }
        }

        if (equalsDigits) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * peso1[i];
        }
        int digit1 = (sum % 11 < 2) ? 0 : 11 - (sum % 11);

        // Calcula o segundo dígito verificador
        int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * peso2[i];
        }
        sum += digit1 * 2;
        int digit2 = (sum % 11 < 2) ? 0 : 11 - (sum % 11);

        return Character.getNumericValue(cnpj.charAt(12)) == digit1 &&
                Character.getNumericValue(cnpj.charAt(13)) == digit2;
    }

    private EmpresaDto converterParaDto(Empresa empresa) {
        return new EmpresaDto(
                empresa.getId(),
                empresa.getNome(),
                empresa.getCnpj(),
                empresa.getTelefone(),
                empresa.getLogo()
        );
    }
}
