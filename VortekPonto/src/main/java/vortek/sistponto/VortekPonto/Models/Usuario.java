package vortek.sistponto.VortekPonto.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String senha;

    @Enumerated(EnumType.STRING)  // Utilizando o Enum e armazenando o nome da constante como String
    private TipoUsuario tipoUsuario;

    public TipoUsuario getTipoUsuar() {
        return tipoUsuario;
    }

    public void setTipoUsuar(TipoUsuario tipoUsuar) {
        this.tipoUsuario = tipoUsuar;
    }
}
