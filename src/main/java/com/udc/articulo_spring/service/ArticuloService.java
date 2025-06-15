package com.udc.articulo_spring.service;

import com.udc.articulo_spring.model.Articulo; 
import com.udc.articulo_spring.repository.ArticuloRepository; 
import org.springframework.stereotype.Service; 

import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {

    private final ArticuloRepository articuloRepository;

    // Inyección de dependencia a través del constructor
    public ArticuloService(ArticuloRepository articuloRepository) {
        this.articuloRepository = articuloRepository;
    }

    // Lista todos los artículos
    public List<Articulo> findAllArticulos() {
        return articuloRepository.findAll();
    }

    // Busca un artículo por su ID
    public Optional<Articulo> findArticuloById(Integer idArticulo) {
        return articuloRepository.findById(idArticulo);
    }

    // Guarda un nuevo artículo o actualiza uno existente
    public Articulo saveArticulo(Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    // Elimina un artículo por su ID
    public void deleteArticulo(Integer idArticulo) {
        articuloRepository.deleteById(idArticulo);
    }
}