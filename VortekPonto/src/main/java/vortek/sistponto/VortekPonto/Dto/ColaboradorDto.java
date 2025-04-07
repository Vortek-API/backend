package vortek.sistponto.VortekPonto.Dto;

import jakarta.validation.constraints.NotNull;
import vortek.sistponto.VortekPonto.Models.Colaborador;

import java.time.LocalTime;

public class ColaboradorDto {

    @NotNull
    private Integer id;

    @NotNull
    private String cpf_col;

    @NotNull
    private String nome_col;

    @NotNull
    private String cargo_col;

    @NotNull
    private LocalTime hora_ent;

    @NotNull
    private LocalTime hora_sai;

    @NotNull
    private boolean status;

    @NotNull
    private Integer empresa;

    public ColaboradorDto() {
    }

    public ColaboradorDto(Integer id, String cpf, String nome, String cargo, LocalTime hora_ent, LocalTime hora_sai, boolean status, Integer empresa) {
        this.id = id;
        this.cpf_col = cpf;
        this.nome_col = nome;
        this.cargo_col = cargo;
        this.hora_ent = hora_ent;
        this.hora_sai = hora_sai;
        this.status = status;
        this.empresa = empresa;
    }

    /*public ColaboradorDto(Colaborador func) {
        this.id = func.getId();
        this.cpf_col = func.getCpf();
        this.nome_col = func.getNome();
        this.cargo_col = func.getCargo();
        this.hora_ent = func.getHorarioEntrada();
        this.hora_sai = func.getHorarioSaida();
        this.status = func.isStatus();
        this.empresa = func.getEmpresa().getId();
    }*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf_col;
    }

    public void setCpf(String cpf) {
        this.cpf_col = cpf;
    }

    public String getNome() {
        return nome_col;
    }

    public void setNome(String nome) {
        this.nome_col = nome;
    }

    public String getCargo() {
        return cargo_col;
    }

    public void setCargo(String cargo) {
        this.cargo_col = cargo;
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

    public Integer getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Integer empresa) {
        this.empresa = empresa;
    }
}