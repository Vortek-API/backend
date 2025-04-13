package vortek.sistponto.VortekPonto.Models;

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
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;
    @Column(name = "telefone", nullable = false)
    private String telefone;
    @Column(name = "logo")
    private String logo;

    
    // private List<ColaboradorEmpresa> colaboradorEmpresas;
    // private List<UsuarioEmpresa> usuarioEmpresas;
}
