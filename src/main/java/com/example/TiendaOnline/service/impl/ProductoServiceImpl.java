package com.example.TiendaOnline.service.impl;

import com.example.TiendaOnline.model.Producto;
import com.example.TiendaOnline.repository.ProductoRepository;
import com.example.TiendaOnline.service.ProductoService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> getProductoPorNombre(String nombre) {
        return productoRepository.findProductoByNombre(nombre);
    }

    @Override
    public Optional<Producto> getProductoPorId(Long id) {
        return productoRepository.findById(id); // <-- Implementación nueva
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public boolean existeProductoPorId(Long id) {
        return productoRepository.existsById(id);
    }

    @Override
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
