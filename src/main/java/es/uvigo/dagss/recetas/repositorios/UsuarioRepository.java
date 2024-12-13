package es.uvigo.dagss.recetas.repositorios;

import es.uvigo.dagss.recetas.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {



    // Buscar usuario por login (caso típico para autenticación)
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.login) = LOWER(:login)")
    Optional<Usuario> findByLogin(String login);

    // Buscar usuarios activos
    @Query("SELECT u FROM Usuario u WHERE u.activo = true")
    Optional<Usuario> findActiveUsers();

}
