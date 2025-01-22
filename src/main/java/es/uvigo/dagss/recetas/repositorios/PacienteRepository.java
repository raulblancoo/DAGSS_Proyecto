package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente findByTarjetaSanitaria(String tarjetaSanitaria);
    Paciente findByNumeroSeguridadSocial(String numeroSeguridadSocial);
    boolean existsByTarjetaSanitaria(String tarjetaSanitaria);
    boolean existsByNumeroSeguridadSocial(String numeroSeguridadSocial);
    boolean existsByDni(String dni);

    List<Paciente> findByNombreContaining(String nombre);
    List<Paciente> findByDireccion_LocalidadContaining(String localidad);
    List<Paciente> findByCentroSalud_Id(Long id);
    List<Paciente> findByMedico_Id(Long id);

    List<Paciente> findByMedico(Medico medico);
    List<Paciente> findByActivoTrue();
}
