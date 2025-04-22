package vortek.sistponto.vortekponto.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "registro_ponto")
@Getter
@Setter
public class RegistroPonto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "colaborador_empresa_id", nullable = false)
    private ColaboradorEmpresa colaboradorEmpresa;

    private LocalDate data;
    private LocalTime horaEntrada;
    private LocalTime horaSaida;
    private LocalTime tempoTotal;

    // Getters e Setters
}
