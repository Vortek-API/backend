package vortek.sistponto.vortekponto.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record RegistroPontoResponseDto(
        Integer id,
        Integer colaboradorId,
        Integer empresaId,
        LocalDate data,
        LocalTime horaEntrada,
        LocalTime horaSaida,
        LocalTime tempoTotal,
        String justificativa
) {}