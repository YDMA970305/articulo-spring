package com.udc.articulo_spring.service;

import com.udc.articulo_spring.model.Usuario; 
import com.udc.articulo_spring.repository.UsuarioRepository; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service; 
import jakarta.annotation.PostConstruct; 

import java.util.List;
import java.util.Optional;

@Service 
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Inyección de dependencias a través del constructor
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Método para guardar un usuario. Codifica la contraseña antes de guardar.
    public Usuario saveUsuario(Usuario usuario) {
        // Solo codifica la contraseña si no está ya codificada
        if (usuario.getContrasena() != null && !usuario.getContrasena().startsWith("$2a$")) {
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        }
        return usuarioRepository.save(usuario);
    }

    // Busca un usuario por su nombre de usuario (clave primaria)
    public Optional<Usuario> findUsuarioByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findById(nombreUsuario);
    }

    // Lista todos los usuarios
    public List<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Elimina un usuario por su nombre de usuario
    public void deleteUsuario(String nombreUsuario) {
        usuarioRepository.deleteById(nombreUsuario);
    }

    // Método para autenticar un usuario
    public Optional<Usuario> authenticate(String nombreUsuario, String contrasena) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByNombreUsuario(nombreUsuario);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            // Compara la contraseña ingresada con la contraseña codificada en la DB
            if (passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty(); // Retorna vacío si no se encuentra o la contraseña no coincide
    }

    // Método para cambiar la contraseña de un usuario 
    public boolean changePassword(String nombreUsuario, String newPassword) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(nombreUsuario);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setContrasena(passwordEncoder.encode(newPassword)); // Codifica la nueva contraseña
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    // Método para inicializar usuarios de prueba al iniciar la aplicación
    // Se ejecuta después de que el bean ha sido construido.
    @PostConstruct
    public void initializeRolesAndUsuariosForDev() {
        // Crea un usuario administrador si no existe
        if (usuarioRepository.findByNombreUsuario("admin_user").isEmpty()) {
            Usuario adminUsuario = new Usuario();
            adminUsuario.setNombreUsuario("admin_user");
            adminUsuario.setContrasena("adminpass"); // La contraseña será codificada por saveUsuario
            adminUsuario.setEmail("admin@example.com");
            adminUsuario.setNombre("Administrador Demo");
            adminUsuario.setTipo("ADMIN");
            saveUsuario(adminUsuario); // Usa saveUsuario para que la contraseña se codifique
        }
        // Crea un usuario normal si no existe
        if (usuarioRepository.findByNombreUsuario("regular_user").isEmpty()) {
            Usuario regularUsuario = new Usuario();
            regularUsuario.setNombreUsuario("regular_user");
            regularUsuario.setContrasena("userpass"); // La contraseña será codificada por saveUsuario
            regularUsuario.setEmail("user@example.com");
            regularUsuario.setNombre("Usuario Regular Demo");
            regularUsuario.setTipo("NORMAL");
            saveUsuario(regularUsuario); // Usa saveUsuario para que la contraseña se codifique
        }
    }
}