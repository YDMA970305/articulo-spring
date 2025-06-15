package com.udc.articulo_spring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario") 
public class Usuario {
    @Id
    @Column(nullable = false, unique = true, length = 50) 
    private String nombreUsuario; 

    @Column(nullable = false, length = 100)
    private String contrasena; 

    @Column(nullable = false, unique = true, length = 100)
    private String email; 

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 20)
    private String tipo; 

    // Constructor vacío requerido por JPA
    public Usuario() {}

    // Getters y Setters
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}