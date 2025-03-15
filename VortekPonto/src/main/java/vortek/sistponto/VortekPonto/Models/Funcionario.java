package vortek.sistponto.VortekPonto.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="funcionarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cargo;

    // Getters e Setters
}
