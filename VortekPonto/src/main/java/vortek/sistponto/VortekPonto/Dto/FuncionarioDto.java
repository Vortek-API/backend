package vortek.sistponto.VortekPonto.Dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import vortek.sistponto.VortekPonto.Models.Empresa;
import vortek.sistponto.VortekPonto.Models.Funcionario;

import java.time.LocalTime;

public class FuncionarioDto {

    @NotBlank
    @Column(name = "id_col", nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "cpf_col", nullable = false, unique = true)
    private String cpf;

    @NotBlank
    @Column(name = "nome_col", nullable = false)
    private String nome;

    @NotBlank
    @Column(name = "cargo_col", nullable = false)
    private String cargo;

    @NotBlank
    @Column(name = "hor_ent", nullable = false)
    private LocalTime hora_ent;

    @NotBlank
    @Column(name = "hor_sai", nullable = false)
    private LocalTime hora_sai;

    @NotBlank
    @Column(name = "st_col", nullable = false)
    private boolean status;

    @NotBlank
    @Column(name = "Empresa_id", nullable = false)
    private Long id_empresa;

    public FuncionarioDto() {
    }

    public FuncionarioDto(String cpf, String nome, String cargo, LocalTime hora_ent, LocalTime hora_sai, boolean status, Long id_empresa) {
        this.cpf = cpf;
        this.nome = nome;
        this.cargo = cargo;
        this.hora_ent = hora_ent;
        this.hora_sai = hora_sai;
        this.status = status;
        this.id_empresa = id_empresa;
    }

    public FuncionarioDto(Funcionario func) {
        this.cpf = func.getCpf();
        this.nome = func.getNome();
        this.cargo = func.getCargo();
        this.hora_ent = func.getHora_ent();
        this.hora_sai = func.getHora_sai();
        this.status = func.isStatus();
    }

    public @NotBlank Long getId() {
        return id;
    }

    public void setId(@NotBlank Long id) {
        this.id = id;
    }

    public @NotBlank String getCpf() {
        return cpf;
    }

    public void setCpf(@NotBlank String cpf) {
        this.cpf = cpf;
    }

    public @NotBlank String getNome() {
        return nome;
    }

    public void setNome(@NotBlank String nome) {
        this.nome = nome;
    }

    public @NotBlank String getCargo() {
        return cargo;
    }

    public void setCargo(@NotBlank String cargo) {
        this.cargo = cargo;
    }

    public @NotBlank LocalTime getHora_ent() {
        return hora_ent;
    }

    public void setHora_ent(@NotBlank LocalTime hora_ent) {
        this.hora_ent = hora_ent;
    }

    public @NotBlank LocalTime getHora_sai() {
        return hora_sai;
    }

    public void setHora_sai(@NotBlank LocalTime hora_sai) {
        this.hora_sai = hora_sai;
    }

    @NotBlank
    public boolean isStatus() {
        return status;
    }

    public void setStatus(@NotBlank boolean status) {
        this.status = status;
    }

    public @NotBlank Long getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(@NotBlank Long id_empresa) {
        this.id_empresa = id_empresa;
    }
}
