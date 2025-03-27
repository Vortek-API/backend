package vortek.sistponto.VortekPonto;

public class VortekPontoApplication {

	public static boolean validarCPF(String cpf) {
		// Remover caracteres não numéricos
		cpf = cpf.replaceAll("[^0-9]", "");

		// Verificar se tem 11 dígitos
		if (cpf.length() != 11) return false;

		// Verificar se todos os dígitos são iguais (ex: 111.111.111-11)
		if (cpf.matches("(\\d)\\1{10}")) return false;

		// Calcular o primeiro dígito verificador
		int soma = 0;
		for (int i = 0; i < 9; i++) {
			soma += (cpf.charAt(i) - '0') * (10 - i);
		}
		int primeiroDigito = 11 - (soma % 11);
		if (primeiroDigito >= 10) primeiroDigito = 0;

		// Calcular o segundo dígito verificador
		soma = 0;
		for (int i = 0; i < 10; i++) {
			soma += (cpf.charAt(i) - '0') * (11 - i);
		}
		int segundoDigito = 11 - (soma % 11);
		if (segundoDigito >= 10) segundoDigito = 0;

		// Verificar se os dígitos calculados são iguais aos informados
		return (primeiroDigito == (cpf.charAt(9) - '0') && segundoDigito == (cpf.charAt(10) - '0'));
	}

	public static void main(String[] args) {
		// Testando a função
		String cpfTeste = "123.456.789-09"; // Coloque um CPF real para testar
		if (validarCPF(cpfTeste)) {
			System.out.println("CPF válido!");
		} else {
			System.out.println("CPF inválido!");
		}
	}
}

