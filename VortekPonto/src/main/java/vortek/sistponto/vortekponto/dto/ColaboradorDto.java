package vortek.sistponto.vortekponto.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record ColaboradorDto(
        Integer id,
        String cpf,
        String nome,
        String cargo,
        LocalTime horarioEntrada,
        LocalTime horarioSaida,
        boolean statusAtivo,
        LocalDateTime dataCadastro,
        String foto,
        Integer empresaId 
) {}
