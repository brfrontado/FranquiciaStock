package com.example.TiendaOnline.controller;

import com.example.TiendaOnline.model.Producto;
import com.example.TiendaOnline.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> getProductos() {
        return productoService.getProductos();
    }

    @PostMapping
    public ResponseEntity<Object> registrarProducto(@RequestBody Producto producto) {
        Map<String, Object> datos = new HashMap<>();
        Optional<Producto> existente = productoService.getProductoPorNombre(producto.getNombre());

        if (existente.isPresent() && producto.getId() == null) {
            datos.put("error", true);
            datos.put("message", "Ya existe un producto con ese nombre");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(datos);
        }

        Producto guardado = productoService.guardarProducto(producto);
        datos.put("message", producto.getId() == null ? "Se guardó con éxito" : "Se actualizó con éxito");
        datos.put("data", guardado);

        return ResponseEntity.status(HttpStatus.CREATED).body(datos);
    }

    @PutMapping
    public ResponseEntity<Object> actualizarProducto(@RequestBody Producto producto) {
        Map<String, Object> datos = new HashMap<>();
        Optional<Producto> existente = productoService.getProductoPorNombre(producto.getNombre());

        if (existente.isPresent() && !existente.get().getId().equals(producto.getId())) {
            datos.put("error", true);
            datos.put("message", "Ya existe un producto con ese nombre");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(datos);
        }

        Producto guardado = productoService.guardarProducto(producto);
        datos.put("message", "Se actualizó con éxito");
        datos.put("data", guardado);

        return ResponseEntity.status(HttpStatus.OK).body(datos);
    }

    @DeleteMapping(path = "{productoId}")
    public ResponseEntity<Object> eliminar(@PathVariable("productoId") Long id) {
        Map<String, Object> datos = new HashMap<>();

        if (!productoService.existeProductoPorId(id)) {
            datos.put("error", true);
            datos.put("message", "No existe un producto con ese id");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(datos);
        }

        productoService.eliminarProducto(id);
        datos.put("message", "Producto eliminado");

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(datos);
    }
}

