package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
    List<Medicamento> findByNombreComercialContaining(String nombreComercial);
    List<Medicamento> findByFabricanteContaining(String fabricante);
    List<Medicamento> findByFamiliaContaining(String familia);
    List<Medicamento> findByPrincipioActivoContaining(String principioActivo);

}
