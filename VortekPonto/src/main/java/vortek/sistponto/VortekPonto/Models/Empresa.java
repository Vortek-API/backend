package vortek.sistponto.VortekPonto.Models;

import java.sql.Blob;

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
    private Integer id_emp;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;
    @Column(name = "telefone", nullable = false, unique = true)
    private String telefone;
    @Column(name = "logo", nullable = false, unique = true)
    private Blob logo;


    public Integer getId() {
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
    }
}
