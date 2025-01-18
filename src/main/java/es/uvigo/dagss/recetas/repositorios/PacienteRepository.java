package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente findByTarjetaSanitaria(String tarjetaSanitaria);
    Paciente findByNumeroSeguridadSocial(String numeroSeguridadSocial);
    boolean existsByTarjetaSanitaria(String tarjetaSanitaria);

    List<Paciente> findByNombreLike(String nombre);
    List<Paciente> findByDireccion_LocalidadLike(String localidad);
    List<Paciente> findByCentroSalud_Id(Long id);
    List<Paciente> findByMedico_Id(Long id);

    List<Paciente> findByMedico(Medico medico);
    List<Paciente> findByActivoTrue();
}
