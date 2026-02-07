package com.example.TiendaOnline.service.impl;

import com.example.TiendaOnline.model.Franquicia;
import com.example.TiendaOnline.model.Sucursal;
import com.example.TiendaOnline.model.SucursalProducto;
import com.example.TiendaOnline.service.FranquiciaService;
import com.example.TiendaOnline.repository.FranquiciaRepository;
import com.example.TiendaOnline.service.ProductoService;
import com.example.TiendaOnline.service.SucursalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FranquiciaServiceImpl implements FranquiciaService {

    private final FranquiciaRepository franquiciaRepository;
    private final SucursalService sucursalService;
    private final ProductoService productoService;

    public FranquiciaServiceImpl(FranquiciaRepository franquiciaRepository,
                                 SucursalService sucursalService,
                                 ProductoService productoService) {
        this.franquiciaRepository = franquiciaRepository;
        this.sucursalService = sucursalService;
        this.productoService = productoService;
    }

    @Override
    @Transactional
    public Franquicia crearFranquicia(Franquicia franquicia) {
        if (franquicia.getSucursales() != null) {
            for (Sucursal sucursal : franquicia.getSucursales()) {
                sucursal.setFranquicia(franquicia);

                if (sucursal.getProductos() != null) {
                    for (SucursalProducto sp : sucursal.getProductos()) {
                        // Guardar producto si es nuevo
                        if (sp.getProducto().getId() == null) {
                            sp.setProducto(productoService.guardarProducto(sp.getProducto()));
                        }
                        sp.setSucursal(sucursal);
                    }
                }
            }
        }

        return franquiciaRepository.save(franquicia);
    }

    @Override
    public List<Franquicia> getTodas() {
        return franquiciaRepository.findAll();
    }

    @Override
    public Optional<Franquicia> getFranquiciaPorId(Long id) {
        return franquiciaRepository.findById(id);
    }

    @Override
    @Transactional
    public Franquicia actualizarFranquicia(Franquicia franquicia) {
        Franquicia existente = franquiciaRepository.findById(franquicia.getId())
                .orElseThrow(() -> new RuntimeException("Franquicia no encontrada"));

        existente.setNombre(franquicia.getNombre());

        // Procesar sucursales
        if (franquicia.getSucursales() != null) {
            for (Sucursal sucursal : franquicia.getSucursales()) {
                sucursal.setFranquicia(existente);
                sucursalService.guardarSucursal(sucursal); // ya procesa productos
            }
        }

        return franquiciaRepository.save(existente);
    }

    @Override
    @Transactional
    public void eliminarFranquicia(Long id) {
        Franquicia franquicia = franquiciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Franquicia no encontrada"));

        // Eliminar todas las sucursales (y sus productos)
        if (franquicia.getSucursales() != null) {
            for (Sucursal sucursal : franquicia.getSucursales()) {
                sucursalService.eliminarSucursal(sucursal.getId());
            }
        }

        franquiciaRepository.delete(franquicia);
    }

}
