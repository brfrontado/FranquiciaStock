package com.example.TiendaOnline.service;

import com.example.TiendaOnline.model.Franquicia;
import java.util.List;
import java.util.Optional;

public interface FranquiciaService {

    Franquicia crearFranquicia(Franquicia franquicia);

    Optional<Franquicia> getFranquiciaPorId(Long id);

    List<Franquicia> getTodas();

    Franquicia actualizarFranquicia(Franquicia franquicia);

    void eliminarFranquicia(Long id);
}
