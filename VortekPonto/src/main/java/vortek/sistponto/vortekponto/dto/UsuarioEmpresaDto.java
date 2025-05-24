package vortek.sistponto.vortekponto.dto;

public record UsuarioEmpresaDto(
        Integer id,
        Integer usuarioId,
        String usuarioEmail,
        String usuarioGrupo,
        Integer empresaId,
        String empresaNome
) {}