package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.CentroSalud;
import es.uvigo.dagss.recetas.entidades.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    // Filtrar por nombre o localidad
//    @Query("SELECT m FROM Medico m WHERE " +
//            "LOWER(m.nombre) LIKE LOWER(CONCAT('%', :term, '%')) " +
//            "OR LOWER(m.centroSalud.direccion) LIKE LOWER(CONCAT('%', :term, '%'))")
//    List<Medico> buscarPorNombreOLocalidad(String term);

    List<Medico> findByCentroSalud(CentroSalud centroSalud);
    List<Medico> findByActivoTrue();


    boolean existsByEmail(String email);


    List<Medico> findByCentroSalud_LocalidadLike(String localidad);
    List<Medico> findByNombreLike(String nombre);
    List<Medico> findByCentroSalud_Id(Long id);

    // TODO: comprobar si es string o id
//    List<Medico> findByCentroSalud(String centroSalud);

    List<Medico> findByNombreLikeOrApellidosLike(String nombre, String apellidos);


}
