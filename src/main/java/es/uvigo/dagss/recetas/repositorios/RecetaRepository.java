package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Paciente;
import es.uvigo.dagss.recetas.entidades.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    List<Receta> findByPrescripcion_Paciente_TarjetaSanitaria(String pacienteTarjetaSanitaria);
    List<Receta> findByPrescripcion_PacienteAndEstado(Paciente paciente, Receta.Estado estado);
    List<Receta> findByPrescripcion_Id(Long prescripcionId);
    List<Receta> findByPrescripcion_IdAndEstadoIn(Long prescripcionId, Collection<Receta.Estado> estados);
}
