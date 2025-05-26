package vortek.sistponto.vortekponto.dto;

import vortek.sistponto.vortekponto.models.TipoUsuario;

import java.util.List;

public record NovoUsuarioDto(String email, TipoUsuario grupo, List<Integer> empresasIds) {
}
