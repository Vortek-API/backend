package vortek.sistponto.vortekponto.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "registro_ponto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroPonto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    private LocalDate data;
    private LocalTime horaEntrada;
    private LocalTime horaSaida;
    private LocalTime tempoTotal;
    private String justificativa;

    // Getters e Setters
}
