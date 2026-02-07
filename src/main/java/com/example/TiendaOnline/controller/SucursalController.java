package com.example.TiendaOnline.controller;

import com.example.TiendaOnline.model.Franquicia;
import com.example.TiendaOnline.model.Sucursal;
import com.example.TiendaOnline.service.SucursalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/sucursales")
public class SucursalController {

    private final SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @GetMapping
    public List<Sucursal> getSucursales() {
        return sucursalService.getSucursales();
    }

    @PostMapping
    public ResponseEntity<Object> registrarSucursal(@RequestBody Sucursal sucursal) {

        Map<String, Object> datos = new HashMap<>();

        Optional<Sucursal> existente =
                sucursalService.findByNombreAndFranquiciaId(
                        sucursal.getNombre(),
                        sucursal.getFranquicia().getId()
                );

        if (existente.isPresent() && sucursal.getId() == null) {
            datos.put("error", true);
            datos.put("message", "Ya existe una sucursal con ese nombre en la franquicia");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(datos);
        }

        Sucursal guardada = sucursalService.guardarSucursal(sucursal);
        datos.put("message", sucursal.getId() == null ? "Sucursal creada con éxito" : "Sucursal actualizada con éxito");
        datos.put("data", guardada);

        return ResponseEntity.status(HttpStatus.CREATED).body(datos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarSucursal(
            @PathVariable Long id,
            @RequestBody Sucursal sucursal) {

        Map<String, Object> datos = new HashMap<>();

        Optional<Sucursal> existente = sucursalService.getSucursalPorId(id);

        if (existente.isEmpty()) {
            datos.put("error", true);
            datos.put("message", "Sucursal no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(datos);
        }

        sucursal.setId(id);
        sucursal.setFranquicia(existente.get().getFranquicia());

        Sucursal actualizada = sucursalService.actualizarSucursal(sucursal);

        datos.put("message", "Sucursal actualizada con éxito");
        datos.put("data", actualizada);

        return ResponseEntity.ok(datos);
    }

    @DeleteMapping(path = "{sucursalId}")
    public ResponseEntity<Object> eliminar(@PathVariable("sucursalId") Long id) {

        Map<String, Object> datos = new HashMap<>();

        if (!sucursalService.existeSucursalPorId(id)) {
            datos.put("error", true);
            datos.put("message", "No existe una sucursal con ese id");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(datos);
        }

        sucursalService.eliminarSucursal(id);
        datos.put("message", "Sucursal eliminada");

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(datos);
    }

    @DeleteMapping("/{sucursalId}/productos/{productoId}")
    public ResponseEntity<Object> eliminarProductoDeSucursal(
            @PathVariable Long sucursalId,
            @PathVariable Long productoId) {

        Map<String, Object> datos = new HashMap<>();

        try {
            sucursalService.eliminarProductoDeSucursal(sucursalId, productoId);
            datos.put("message", "Producto eliminado de la sucursal");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(datos);
        } catch (RuntimeException e) {
            datos.put("error", true);
            datos.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(datos);
        }
    }

}
