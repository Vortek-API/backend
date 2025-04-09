package vortek.sistponto.VortekPonto.Dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record ColaboradorDto (Integer id, String cpf, String nome, String cargo, LocalTime horarioEntrada,
                              LocalTime horarioSaida, boolean statusAtivo, String endereco, String email,
                              LocalDateTime dataCadastro, String foto, Integer empresaId) {
}
