package vortek.sistponto.VortekPonto.Models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;

@Entity
@Table(name = "colaborador")
@EqualsAndHashCode(of = "id")
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_col")
    private Integer id;

    @Column(name = "cpf_col", nullable = false, unique = true)
    private String cpf;

    @Column(name = "nome_col", nullable = false)
    private String nome;

    @Column(name = "cargo_col", nullable = false)
    private String cargo;

    @Column(name = "hor_ent", nullable = false)
    private LocalTime hora_ent;

    @Column(name = "hor_sai")
    private LocalTime hora_sai;

    @Column(name = "st_col", nullable = false)
    private boolean st_col;

    @ManyToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "id_emp", nullable = false)
    private Empresa empresa;

    public Colaborador() {
    }

    public Colaborador(String cpf, String nome, String cargo, LocalTime hora_ent, LocalTime hora_sai, boolean st_col, Empresa empresa) {
        this.cpf = cpf;
        this.nome = nome;
        this.cargo = cargo;
        this.hora_ent = hora_ent;
        this.hora_sai = hora_sai;
        this.st_col = st_col;
        this.empresa = empresa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public LocalTime getHora_ent() {
        return hora_ent;
    }

    public void setHora_ent(LocalTime hora_ent) {
        this.hora_ent = hora_ent;
    }

    public LocalTime getHora_sai() {
        return hora_sai;
    }

    public void setHora_sai(LocalTime hora_sai) {
        this.hora_sai = hora_sai;
    }

    public boolean isStatus() {
        return st_col;
    }

    public void setStatus(boolean st_col) {
        this.st_col = st_col;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}