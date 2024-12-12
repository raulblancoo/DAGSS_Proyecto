package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    // Buscar administradores por nombre o email
    @Query("SELECT a FROM Administrador a WHERE " +
            "LOWER(a.nombre) LIKE LOWER(CONCAT('%', :term, '%')) " +
            "OR LOWER(a.email) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Administrador> buscarPorNombreOEmail(String term);

    List<Administrador> findByActivoTrue();

    // Buscar por login
//    @Query("SELECT a FROM Administrador a WHERE LOWER(a.usuario.login) = LOWER(:login)")
//    Administrador findByLogin(String login);
}