package vortek.sistponto.VortekPonto.Models;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Integer id;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cargo;

    @Column(name = "horario_entrada", nullable = false)
    private LocalTime horarioEntrada;

    @Column(name = "horario_saida")
    private LocalTime horarioSaida;

    @Column(name = "status_ativo", nullable = false)
    private boolean statusAtivo;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    private String foto;
}
