package vortek.sistponto.VortekPonto.Models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import vortek.sistponto.VortekPonto.Configs.Json.BlobSerializer;

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
    @JsonSerialize(using = BlobSerializer.class)
    private Blob foto;

    @ManyToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "id", nullable = false)
    private Empresa empresa;

}