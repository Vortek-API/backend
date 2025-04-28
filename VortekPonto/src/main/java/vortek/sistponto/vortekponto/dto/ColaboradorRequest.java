package vortek.sistponto.vortekponto.dto;

public record ColaboradorRequest(
    ColaboradorDto colaborador,
    Integer[] empresasId
) {}
