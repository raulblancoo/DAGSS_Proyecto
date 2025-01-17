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
    boolean existsByDniOrNumeroColegiado(String dni, String numeroColegiado);

    List<Medico> findByCentroSalud_LocalidadLike(String localidad);
    List<Medico> findByNombreLike(String nombre);
    List<Medico> findByCentroSalud_Id(Long id);



    List<Medico> findByNombreLikeOrApellidosLike(String nombre, String apellidos);
}
