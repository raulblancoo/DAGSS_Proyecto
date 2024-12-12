package es.uvigo.dagss.recetas.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombreComercial;

    @Column(nullable = false, length = 100)
    private String principioActivo;

    @Column(nullable = false, length = 100)
    private String fabricante;

    @Column(length = 50)
    private String familia;

    @Column(nullable = false)
    private Integer numeroDosis;

    @Column(nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "medicamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Receta> recetas;
}