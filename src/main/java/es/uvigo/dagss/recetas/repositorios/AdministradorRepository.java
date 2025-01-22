package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    List<Administrador> findByActivo(boolean activo);
    boolean existsByEmail(String email);
}