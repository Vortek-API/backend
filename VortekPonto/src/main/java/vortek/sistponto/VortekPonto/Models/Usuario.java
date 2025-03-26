package vortek.sistponto.VortekPonto.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;
    private String login;
    private String senha;

    @Enumerated(EnumType.STRING)  // Utilizando o Enum e armazenando o nome da constante como String
    private TipoUsuario tipoUsuario;

    @ManyToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "id_emp")
    private Empresa idEmpresa;

    public Usuario() {
    }

    public Usuario(String login, String senha, TipoUsuario tipoUsuario) {
        this.login = login;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Empresa getIdEmpresa() {
        return this.idEmpresa;
    }

    public void setIdEmpresa(Empresa idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}
