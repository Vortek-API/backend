package vortek.sistponto.vortekponto.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record RegistroPontoDto(
        Integer id,
        Integer colaboradorId,
        Integer empresaId,
        LocalDate data,
        LocalTime horaEntrada,
        LocalTime horaSaida,
        LocalTime tempoTotal,
        String justificativa
) {}
