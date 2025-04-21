package vortek.sistponto.vortekponto.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record ColaboradorComEmpresasDto(
        Integer id,
        String cpf,
        String nome,
        String cargo,
        LocalTime horarioEntrada,
        LocalTime horarioSaida,
        boolean statusAtivo,
        LocalDateTime dataCadastro,
        String foto,
        List<EmpresaDto> empresas
) {}
