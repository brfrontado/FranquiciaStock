package com.example.TiendaOnline.repository;

import com.example.TiendaOnline.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {

    Optional<Sucursal> findSucursalByNombre(String nombre);

    Optional<Sucursal> findByNombreAndFranquicia_Id(String nombre, Long franquiciaId);

}
