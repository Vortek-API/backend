package vortek.sistponto.vortekponto.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "colaborador_empresa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ColaboradorEmpresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // muitos registros de ColaboradorEmpresa podem apontar para o mesmo Colaborador
    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    // muitos registros de ColaboradorEmpresa podem apontar para a mesma Empresa
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    public ColaboradorEmpresa(Colaborador colaborador, Empresa empresa) {
        this.colaborador = colaborador;
        this.empresa = empresa;
    }
}
