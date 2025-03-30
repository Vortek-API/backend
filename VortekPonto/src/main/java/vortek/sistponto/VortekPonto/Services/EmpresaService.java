package vortek.sistponto.VortekPonto.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vortek.sistponto.VortekPonto.Dto.EmpresaDto;
import vortek.sistponto.VortekPonto.Models.Empresa;
import vortek.sistponto.VortekPonto.Repositories.EmpresaRepository;
import vortek.sistponto.VortekPonto.Services.Exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;
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
            throw new ObjectNotFoundException("Empresa: " + id + " não encontrada!");
        }
        return new EmpresaDto(empresa);
    }

    public String deletarEmpresa(Integer id) {
        Empresa emp = empresaRepository.findById(id).get();
        if (emp != null) {
            System.out.println(emp.getCnpj());
            empresaRepository.delete(emp);
            return "Empresa deletada com sucesso!";
        } else {
            throw new RuntimeException("Empresa não encontrada!");
        }
    }

    public EmpresaDto atualizarEmpresa(Integer id, EmpresaDto empresaDto) {
        Optional<Empresa> empresaOptional = empresaRepository.findById(id);

        if (!empresaOptional.isPresent()) {
            throw new ObjectNotFoundException("Empresa não encontrada!");
        }

        Empresa empresa = empresaOptional.get();
        empresa.setNome(empresaDto.getNome());
        empresa.setCnpj(empresaDto.getCnpj());

        empresa = empresaRepository.save(empresa);

        return new EmpresaDto(empresa);
    }

    public boolean validarCNPJ(String cnpj) {
        // Método de validação retona um boolean para verificação do CNPJ

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

}
