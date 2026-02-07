package com.example.TiendaOnline.service;

import com.example.TiendaOnline.model.Sucursal;

import java.util.List;
import java.util.Optional;

public interface SucursalService {

    List<Sucursal> getSucursales();

    Optional<Sucursal> getSucursalPorNombre(String nombre);

    Sucursal guardarSucursal(Sucursal sucursal);

    Sucursal actualizarSucursal(Sucursal sucursal);

    Optional<Sucursal>getSucursalPorId(Long id);

    Optional<Sucursal>findByNombreAndFranquiciaId(String nombre, Long franquiciaId);

    void eliminarProductoDeSucursal(Long sucursalId, Long productoId);

    boolean existeSucursalPorId(Long id);

    void eliminarSucursal(Long id);
}
