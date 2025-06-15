package com.udc.articulo_spring.model;

import jakarta.persistence.*;
import java.math.BigDecimal; 

@Entity
@Table(name = "articulo") 
public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArticulo") 
    private Integer idArticulo; 

    @Column(length = 50)
    private String marca; 

    @Column(precision = 14, scale = 2) 
    private BigDecimal precioVenta; 

    @Column(precision = 14, scale = 2)
    private BigDecimal precioCompra; 

    @Column(precision = 14, scale = 2)
    private BigDecimal iva; 

    @Column(length = 50)
    private String modelo; 

    @Column(length = 100)
    private String proveedor; 

    @Column(length = 100)
    private String tienda; 

    private Integer cantidad; 

    @Column(length = 50)
    private String categoria; 

    @Column(length = 200)
    private String descripcion; 

    @Column(length = 50)
    private String nombreUsuario; 

    // Constructor vacío
    public Articulo() {}

    // Getters y Setters para todos los campos
    public Integer getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Integer idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}