package vortek.sistponto.vortekponto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vortek.sistponto.vortekponto.models.Empresa;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private int id;
    private String login;
    private String grupo;
    private Empresa[] empresas;
}
