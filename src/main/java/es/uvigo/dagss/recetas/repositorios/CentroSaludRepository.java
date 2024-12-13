package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.CentroSalud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CentroSaludRepository extends JpaRepository<CentroSalud, Long> {

    @Query("SELECT c FROM CentroSalud c WHERE " +
            "LOWER(c.nombre) LIKE LOWER(CONCAT('%', :criterio, '%')) OR " +
            "LOWER(c.localidad) LIKE LOWER(CONCAT('%', :criterio, '%'))")
    List<CentroSalud> buscarPorNombreOLocalidad(String criterio);
}