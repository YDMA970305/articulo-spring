package com.udc.articulo_spring;

//import com.udc.articulo_spring.service.UsuarioService; // Asegúrate de importar tu UsuarioService
//import org.springframework.boot.CommandLineRunner; // Importa esta clase
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean; // Importa esta anotación

@SpringBootApplication
public class ArticuloSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticuloSpringApplication.class, args);
    }


    /*@Bean
    public CommandLineRunner initData(UsuarioService usuarioService) {
        return args -> {
            // Llama al método de inicialización de datos de tu UsuarioService
            usuarioService.initializeRolesAndUsuariosForDev();
            System.out.println("Usuarios de prueba inicializados."); // Mensaje para el log
        };
    }*/
}