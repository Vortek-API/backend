package vortek.sistponto.VortekPonto.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name="Colaborador")
@EqualsAndHashCode(of = "id")
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    private String nome;
    private String cargo;
    private LocalTime hora_ent;
    private LocalTime hora_sai;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "Empresa_id")
    private Empresa id_empresa;

    public Colaborador() {
    }

    public Colaborador(String cpf, String nome, String cargo, LocalTime hora_ent, LocalTime hora_sai, boolean status, Empresa id_empresa) {
        this.cpf = cpf;
        this.nome = nome;
        this.cargo = cargo;
        this.hora_ent = hora_ent;
        this.hora_sai = hora_sai;
        this.status = status;
        this.id_empresa = id_empresa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Empresa getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(Empresa id_empresa) {
        this.id_empresa = id_empresa;
    }
}
