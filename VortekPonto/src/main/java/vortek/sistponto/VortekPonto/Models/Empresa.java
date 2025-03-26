package vortek.sistponto.VortekPonto.Models;

import jakarta.persistence.*;
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
    @Column(name = "id_emp")
    private Integer id_emp;
    @Column(name = "nome_emp", nullable = false)
    private String nome;
    @Column(name = "cnpj_emp", nullable = false, unique = true)
    private String cnpj;


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

}
