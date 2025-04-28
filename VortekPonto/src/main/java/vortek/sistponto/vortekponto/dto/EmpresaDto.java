package vortek.sistponto.vortekponto.dto;

import java.time.LocalDateTime;

public record EmpresaDto(Integer id, String nome, String cnpj, LocalDateTime dataCadastro) {
    
}


