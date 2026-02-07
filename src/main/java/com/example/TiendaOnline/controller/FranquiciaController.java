package com.example.TiendaOnline.controller;

import com.example.TiendaOnline.model.Franquicia;
import com.example.TiendaOnline.service.FranquiciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/franquicias")
public class FranquiciaController {

    private final FranquiciaService franquiciaService;

    public FranquiciaController(FranquiciaService franquiciaService) {
        this.franquiciaService = franquiciaService;
    }

    @PostMapping
    public ResponseEntity<Object> crearFranquicia(@RequestBody Franquicia franquicia) {
        Map<String, Object> datos = new HashMap<>();
        try {
            Franquicia guardada = franquiciaService.crearFranquicia(franquicia);
            datos.put("message", "Franquicia creada con éxito");
            datos.put("data", guardada);
            return ResponseEntity.status(HttpStatus.CREATED).body(datos);
        } catch (RuntimeException e) {
            datos.put("error", true);
            datos.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(datos);
        }
    }

    @GetMapping
    public List<Franquicia> getTodas() {
        return franquiciaService.getTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPorId(@PathVariable Long id) {
        Optional<Franquicia> franquiciaOpt = franquiciaService.getFranquiciaPorId(id);

        Map<String, Object> datos = new HashMap<>();

        if (franquiciaOpt.isPresent()) {
            datos.put("data", franquiciaOpt.get());
            return ResponseEntity.ok(datos);
        } else {
            datos.put("error", true);
            datos.put("message", "Franquicia no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(datos);
        }
    }

    @PutMapping
    public ResponseEntity<Object> actualizar(@RequestBody Franquicia franquicia) {
        Map<String, Object> datos = new HashMap<>();
        try {
            Franquicia actualizada = franquiciaService.actualizarFranquicia(franquicia);
            datos.put("message", "Franquicia actualizada con éxito");
            datos.put("data", actualizada);
            return ResponseEntity.ok(datos);
        } catch (RuntimeException e) {
            datos.put("error", true);
            datos.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(datos);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable Long id) {
        Map<String, Object> datos = new HashMap<>();
        try {
            franquiciaService.eliminarFranquicia(id);
            datos.put("message", "Franquicia eliminada con éxito");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(datos);
        } catch (RuntimeException e) {
            datos.put("error", true);
            datos.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(datos);
        }
    }
}
