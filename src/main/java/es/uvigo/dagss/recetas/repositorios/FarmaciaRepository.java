package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Farmacia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FarmaciaRepository extends JpaRepository<Farmacia, Long> {
    boolean existsByDniOrNumeroColegiado(String dni, String numeroColegiado);

    List<Farmacia> findByNombreEstablecimientoLike(String nombreEstablecimiento);
    List<Farmacia> findByDireccion_LocalidadLike(String localidad);

    Farmacia findByNumeroColegiado(String numeroColegiado);
}
