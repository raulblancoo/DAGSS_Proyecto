package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.CentroDeSalud;
import es.uvigo.dagss.recetas.entidades.Medico;
import es.uvigo.dagss.recetas.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente findByTarjetaSanitaria(String tarjetaSanitaria);

    // Filtrar por nombre, localidad o médico asignado
    List<Paciente> findByNombreContainingIgnoreCaseOrDireccionContainingIgnoreCase(String nombre, String direccion);
    List<Paciente> findByCentroDeSalud(CentroDeSalud centroDeSalud);
    List<Paciente> findByMedico(Medico medico);
    List<Paciente> findByActivoTrue();
}
