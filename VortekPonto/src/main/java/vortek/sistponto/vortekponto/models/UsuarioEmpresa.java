package vortek.sistponto.vortekponto.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario_empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
}
