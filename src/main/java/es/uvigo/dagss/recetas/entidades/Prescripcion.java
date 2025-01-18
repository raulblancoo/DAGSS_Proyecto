package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "prescripcion")
public class Prescripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación muchos a uno con Medicamento
    @ManyToOne
    @JoinColumn(name = "medicamento_id", nullable = false)
    private Medicamento medicamento;

    // Relación muchos a uno con Paciente
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    // Relación muchos a uno con Medico
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(nullable = false)
    private Double dosisDiaria;

    @Column(nullable = false, length = 500)
    private String indicaciones;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estado estado; // Enum: ACTIVO, INACTIVO

    // Enumeración para los estados de la prescripción
    public enum Estado {
        ACTIVO,
        INACTIVO
    }

    // Relación uno a muchos con PrescripcionPlan (Recetas)
    @OneToMany(mappedBy = "prescripcion")
    private List<PrescripcionPlan> recetas;
}
