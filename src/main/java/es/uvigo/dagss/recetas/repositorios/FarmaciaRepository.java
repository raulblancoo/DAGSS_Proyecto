package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Farmacia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FarmaciaRepository extends JpaRepository<Farmacia, Long> {

    // Buscar farmacias por nombre de establecimiento o localidad (LIKE en SQL)
    @Query("SELECT f FROM Farmacia f WHERE " +
            "LOWER(f.nombreEstablecimiento) LIKE LOWER(CONCAT('%', :term, '%')) " +
            "OR LOWER(f.direccion) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Farmacia> buscarPorNombreOLocalidad(String term);

    List<Farmacia> findByActivoTrue();
    Farmacia findByNumeroColegiado(String numeroColegiado);
}
