package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "receta")
public class Receta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prescripcion_id", nullable = false)
    private Prescripcion prescripcion;

    @Column(nullable = false)
    private LocalDate fechaValidezInicial;

    @Column(nullable = false)
    private LocalDate fechaValidezFinal;

    @Column(nullable = false)
    private Integer numeroUnidades;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estado estado;

    public enum Estado {
        PLANIFICADA,
        SERVIDA,
        ANULADA
    }

    // Relación muchos a uno con Farmacia (puede ser nula)
    @ManyToOne
    @JoinColumn(name = "farmacia_id")
    private Farmacia farmacia;
}
