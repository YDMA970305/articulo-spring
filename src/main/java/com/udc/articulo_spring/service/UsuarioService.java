package com.udc.articulo_spring.service;

import com.udc.articulo_spring.model.Usuario;
import com.udc.articulo_spring.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario saveUsuario(Usuario usuario) {
        if (usuario.getContrasena() != null && !usuario.getContrasena().startsWith("$2a$")) {
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        }
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findUsuarioByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findById(nombreUsuario);
    }

    public List<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public void deleteUsuario(String nombreUsuario) {
        usuarioRepository.deleteById(nombreUsuario);
    }

    public Optional<Usuario> authenticate(String nombreUsuario, String contrasena) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByNombreUsuario(nombreUsuario);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

    public boolean changePassword(String nombreUsuario, String newPassword) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(nombreUsuario);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setContrasena(passwordEncoder.encode(newPassword));
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public void initializeRolesAndUsuariosForDev() {
        // Crea un usuario administrador si no existe
        if (usuarioRepository.findByNombreUsuario("admin_user").isEmpty()) {
            Usuario adminUsuario = new Usuario();
            adminUsuario.setNombreUsuario("admin_user");
            adminUsuario.setContrasena("adminpass");
            adminUsuario.setEmail("admin@example.com");
            adminUsuario.setNombre("Administrador Demo");
            adminUsuario.setTipo("ADMIN");
            saveUsuario(adminUsuario);
        }
        // Crea un usuario normal si no existe
        if (usuarioRepository.findByNombreUsuario("regular_user").isEmpty()) {
            Usuario regularUsuario = new Usuario();
            regularUsuario.setNombreUsuario("regular_user");
            regularUsuario.setContrasena("userpass");
            regularUsuario.setEmail("user@example.com");
            regularUsuario.setNombre("Usuario Regular Demo");
            regularUsuario.setTipo("NORMAL");
            saveUsuario(regularUsuario);
        }
        // *** AÑADIR CÓDIGO PARA EL USUARIO 'yesid' AQUÍ ***
        if (usuarioRepository.findByNombreUsuario("yesid").isEmpty()) {
            Usuario yesidUsuario = new Usuario();
            yesidUsuario.setNombreUsuario("yesid");
            yesidUsuario.setContrasena("tu_contrasena_de_yesid"); // Asegúrate de usar la contraseña correcta
            yesidUsuario.setEmail("yesidmarti123@gmail.com");
            yesidUsuario.setNombre("yesid david martinez arrieta");
            yesidUsuario.setTipo("ADMIN"); // O el tipo correcto según tu DB
            saveUsuario(yesidUsuario);
        }
    }
}