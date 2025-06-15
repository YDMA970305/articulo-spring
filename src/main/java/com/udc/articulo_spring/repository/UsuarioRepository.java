package  com.udc.articulo_spring.repository;

import com.udc.articulo_spring.model.Usuario; 
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}