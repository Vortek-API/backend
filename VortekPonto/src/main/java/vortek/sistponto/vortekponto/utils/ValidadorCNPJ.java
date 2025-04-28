package vortek.sistponto.vortekponto.utils;

import org.springframework.stereotype.Component;

@Component
public class ValidadorCNPJ {

    public boolean isValidCNPJ(String cnpj) {

    //     // Metodo de validação retona um boolean para verificação do CNPJ
    //     cnpj = cnpj.replaceAll("[^0-9]", "");

    //     if (cnpj.length() != 14) {
    //         return false;
    //     }

    //     boolean equalsDigits = true;
    //     for (int i = 0; i < 14; i++) {
    //         if (cnpj.charAt(i) != cnpj.charAt(0)) {
    //             equalsDigits = false;
    //             break;
    //         }
    //     }

    //     if (equalsDigits) {
    //         return false;
    //     }

    //     // Calcula o primeiro dígito verificador
    //     int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    //     int sum = 0;
    //     for (int i = 0; i < 12; i++) {
    //         sum += Character.getNumericValue(cnpj.charAt(i)) * peso1[i];
    //     }
    //     int digit1 = (sum % 11 < 2) ? 0 : 11 - (sum % 11);

    //     // Calcula o segundo dígito verificador
    //     int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    //     sum = 0;
    //     for (int i = 0; i < 12; i++) {
    //         sum += Character.getNumericValue(cnpj.charAt(i)) * peso2[i];
    //     }
    //     sum += digit1 * 2;
    //     int digit2 = (sum % 11 < 2) ? 0 : 11 - (sum % 11);

    //     return Character.getNumericValue(cnpj.charAt(12)) == digit1 &&
    //             Character.getNumericValue(cnpj.charAt(13)) == digit2;

    return true;
    }
}
