package vortek.sistponto.vortekponto.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SenhaToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String token;
    private Date tempToken;

    @ManyToOne (targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "usuario_id", referencedColumnName = "id")
    private Usuario user;


    public SenhaToken(String token, Usuario user, Date tempToken) {
        this.token = token;
        this.user = user;
        this.tempToken = tempToken;
    }
}
