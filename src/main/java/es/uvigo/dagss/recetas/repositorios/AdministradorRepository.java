package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    List<Administrador> findByActivo(boolean activo);
    boolean existsByEmail(String email);



    // Buscar administradores por nombre o email
    @Query("SELECT a FROM Administrador a WHERE " +
            "LOWER(a.nombre) LIKE LOWER(CONCAT('%', :term, '%')) " +
            "OR LOWER(a.email) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Administrador> buscarPorNombreOEmail(String term);

    List<Administrador> findByActivoTrue();

    List<Administrador> findByNombreContainingIgnoreCaseOrLoginContainingIgnoreCaseOrEmailContainingIgnoreCase(String nombre, String login, String email);


    // Buscar por login
//    @Query("SELECT a FROM Administrador a WHERE LOWER(a.usuario.login) = LOWER(:login)")
//    Administrador findByLogin(String login);
}