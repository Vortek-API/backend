package vortek.sistponto.VortekPonto.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Colaborador> colaboradores;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Usuario> usuarios;
}
