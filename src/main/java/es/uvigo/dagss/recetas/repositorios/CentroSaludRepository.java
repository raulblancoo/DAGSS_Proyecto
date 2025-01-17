package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.CentroSalud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CentroSaludRepository extends JpaRepository<CentroSalud, Long> {
    List<CentroSalud> findByDireccion_LocalidadLike(String localidad);
    List<CentroSalud> findByNombreLike(String nombre);
}