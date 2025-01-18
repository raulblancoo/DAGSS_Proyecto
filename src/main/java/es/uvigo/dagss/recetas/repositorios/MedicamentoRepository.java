package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    // Buscar por nombre, principio activo, fabricante o familia
    @Query("SELECT m FROM Medicamento m WHERE " +
            "LOWER(m.nombreComercial) LIKE LOWER(CONCAT('%', :term, '%')) " +
            "OR LOWER(m.principioActivo) LIKE LOWER(CONCAT('%', :term, '%')) " +
            "OR LOWER(m.fabricante) LIKE LOWER(CONCAT('%', :term, '%')) " +
            "OR LOWER(m.familia) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Medicamento> buscarPorCriterio(String term);

    List<Medicamento> findByActivoTrue();

    List<Medicamento> findByNombreComercialLike(String nombreComercial);
    List<Medicamento> findByFabricanteLike(String fabricante);
    List<Medicamento> findByFamiliaLike(String familia);
    List<Medicamento> findByPrincipioActivoLike(String principioActivo);

}
