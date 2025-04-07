package vortek.sistponto.VortekPonto.Models;

import java.sql.Blob;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vortek.sistponto.VortekPonto.Configs.Json.EmpresaSerializer;

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
    @Column(name = "telefone", nullable = false, unique = true)
    private String telefone;
    @Column(name = "logo", nullable = false, unique = true)
    private Blob logo;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    @JsonSerialize(using = EmpresaSerializer.class)
    private List<Colaborador> colaboradores;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    @JsonSerialize(using = EmpresaSerializer.class)
    private List<Usuario> usuarios;

    /*public Integer getId() {
        return id_emp;
    }
    public void setId(Integer id) {
        this.id_emp = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Blob getLogo() {
        return logo;
    }
    public void setLogo(Blob logo) {
        this.logo = logo;
    }*/
}
