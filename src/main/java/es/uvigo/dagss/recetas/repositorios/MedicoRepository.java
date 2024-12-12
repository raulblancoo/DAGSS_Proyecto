package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.CentroDeSalud;
import es.uvigo.dagss.recetas.entidades.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    // Filtrar por nombre o localidad
    @Query("SELECT m FROM Medico m WHERE " +
            "LOWER(m.nombre) LIKE LOWER(CONCAT('%', :term, '%')) " +
            "OR LOWER(m.centroDeSalud.direccion) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Medico> buscarPorNombreOLocalidad(String term);

    List<Medico> findByCentroDeSalud(CentroDeSalud centroDeSalud);
    List<Medico> findByActivoTrue();
}
