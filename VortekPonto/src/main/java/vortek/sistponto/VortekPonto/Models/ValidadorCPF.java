package vortek.sistponto.VortekPonto.Models;

public class ValidadorCPF {

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

    public static void main(String[] args) {
       
        String cpf = "123.456...-09"; 
        System.out.println("Resultado da validação: " + validarCPF(cpf));
    }
}
