package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.CentroDeSalud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CentroDeSaludRepository extends JpaRepository<CentroDeSalud, Long> {

    // Buscar centros por nombre o localidad (LIKE en SQL)
    @Query("SELECT c FROM CentroDeSalud c WHERE " +
            "LOWER(c.nombre) LIKE LOWER(CONCAT('%', :term, '%')) " +
            "OR LOWER(c.direccion) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<CentroDeSalud> buscarPorNombreOLocalidad(String term);

    List<CentroDeSalud> findByActivoTrue();
}