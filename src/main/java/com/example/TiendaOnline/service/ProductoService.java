package com.example.TiendaOnline.service;

import com.example.TiendaOnline.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> getProductos();
    Optional<Producto> getProductoPorNombre(String nombre);
    Optional<Producto> getProductoPorId(Long id);
    Producto guardarProducto(Producto producto);
    boolean existeProductoPorId(Long id);
    void eliminarProducto(Long id);
}

