package vortek.sistponto.vortekponto.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;

    @Column(name = "data_cadastro", updatable = false, insertable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "status_ativo", nullable = false)
    private boolean statusAtivo = true;

    // private List<ColaboradorEmpresa> colaboradorEmpresas;
    // private List<UsuarioEmpresa> usuarioEmpresas;
}
