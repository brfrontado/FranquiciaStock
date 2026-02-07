package com.example.TiendaOnline.repository;

import com.example.TiendaOnline.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findProductoByNombre(String nombre);
}
