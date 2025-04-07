package vortek.sistponto.VortekPonto.Models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "colaborador")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cargo", nullable = false)
    private String cargo;

    @Column(name = "horario_entrada", nullable = false)
    private LocalTime horarioEntrada;

    @Column(name = "horario_saida")
    private LocalTime horarioSaida;

    @Column(name = "status_ativo", nullable = false)
    private boolean status_ativo;

    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "foto", nullable = false)
    private Blob foto;

    @ManyToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "id", nullable = false)
    private Empresa empresa;

    /*public Colaborador() {
    }

    public Colaborador(String cpf, String nome, String cargo, LocalTime horarioEntrada, LocalTime horarioSaida, boolean status_ativo, String endereco, String email, LocalDateTime dataCadastro, Blob foto, Empresa empresa) {
        this.cpf = cpf;
        this.nome = nome;
        this.cargo = cargo;
        this.horarioEntrada = horarioEntrada;
        this.horarioSaida = horarioSaida;
        this.status_ativo = status_ativo;
        this.empresa = empresa;
        this.endereco = endereco;
        this.email =  email;
        this.dataCadastro = dataCadastro;
        this.foto = foto;
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

    public LocalTime getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(LocalTime hora_ent) {
        this.horarioEntrada = hora_ent;
    }

    public LocalTime getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(LocalTime hora_sai) {
        this.horarioSaida = hora_sai;
    }

    public boolean isStatus() {
        return status_ativo;
    }

    public void setStatus(boolean st_col) {
        this.status_ativo = st_col;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }*/
}