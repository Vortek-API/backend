package vortek.sistponto.vortekponto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private int id;
    private String login;
    private String grupo;
}
