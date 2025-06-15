package com.udc.articulo_spring.repository;

import com.udc.articulo_spring.model.Articulo; // Importa tu clase de modelo Articulo
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticuloRepository extends JpaRepository<Articulo, Integer> {

}