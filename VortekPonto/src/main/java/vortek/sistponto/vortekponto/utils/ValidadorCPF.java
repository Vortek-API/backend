package vortek.sistponto.vortekponto.utils;

import org.springframework.stereotype.Component;

import vortek.sistponto.vortekponto.exceptions.CpfInvalidoException;

@Component
public class ValidadorCPF {

    public Boolean isValidCpf(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            throw new CpfInvalidoException("Erro: O CPF deve conter exatamente 11 dígitos.");
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            throw new CpfInvalidoException("Erro: O CPF não pode ter todos os dígitos iguais.");
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
            throw new CpfInvalidoException( "Erro: O primeiro dígito verificador está incorreto.");
        }
        if (segundoDigito != (cpf.charAt(10) - '0')) {
            throw new CpfInvalidoException( "Erro: O segundo dígito verificador está incorreto.");
        }

        return true;
    }
}
