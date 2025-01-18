package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Medico findByNumeroColegiado(String numeroColegiado);
    boolean existsByDniOrNumeroColegiado(String dni, String numeroColegiado);

    List<Medico> findByCentroSalud_Direccion_LocalidadLike(String direccion);
    List<Medico> findByNombreLike(String nombre);
    List<Medico> findByCentroSalud_Id(Long id);
}
