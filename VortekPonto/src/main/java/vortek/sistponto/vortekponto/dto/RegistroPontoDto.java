package vortek.sistponto.vortekponto.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record RegistroPontoDto(
        Integer id,
        Integer colaboradorEmpresaId,
        LocalDate data,
        LocalTime horaEntrada,
        LocalTime horaSaida,
        LocalTime tempoTotal,
        String justificativa
) {}
