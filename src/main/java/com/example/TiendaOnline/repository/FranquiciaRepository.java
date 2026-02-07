package com.example.TiendaOnline.repository;

import com.example.TiendaOnline.model.Franquicia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FranquiciaRepository extends JpaRepository<Franquicia, Long> {
    Optional<Franquicia> findByNombre(String nombre);
}
