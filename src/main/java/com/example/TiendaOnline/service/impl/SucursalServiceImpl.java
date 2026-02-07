package com.example.TiendaOnline.service.impl;

import com.example.TiendaOnline.model.Producto;
import com.example.TiendaOnline.model.Sucursal;
import com.example.TiendaOnline.model.SucursalProducto;
import com.example.TiendaOnline.repository.SucursalRepository;
import com.example.TiendaOnline.service.ProductoService;
import com.example.TiendaOnline.service.SucursalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SucursalServiceImpl implements SucursalService {

    private final SucursalRepository sucursalRepository;
    private final ProductoService productoService;

    public SucursalServiceImpl(SucursalRepository sucursalRepository,
                               ProductoService productoService) {
        this.sucursalRepository = sucursalRepository;
        this.productoService = productoService;
    }

    @Override
    public List<Sucursal> getSucursales() {
        return sucursalRepository.findAll();
    }

    @Override
    public Optional<Sucursal> getSucursalPorNombre(String nombre) {
        return sucursalRepository.findSucursalByNombre(nombre);
    }

    @Override
    public Sucursal guardarSucursal(Sucursal sucursal) {
        return procesarSucursal(sucursal);
    }

    @Override
    public Sucursal actualizarSucursal(Sucursal sucursal) {
        Sucursal existente = sucursalRepository.findById(sucursal.getId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        existente.setNombre(sucursal.getNombre());
        existente.setDireccion(sucursal.getDireccion());

        List<SucursalProducto> listaFinal = procesarProductos(sucursal.getProductos(), existente);
        existente.getProductos().clear();
        existente.getProductos().addAll(listaFinal);

        return sucursalRepository.save(existente);
    }

    @Override
    public Optional<Sucursal> findByNombreAndFranquiciaId(String nombre, Long franquiciaId) {
        return sucursalRepository.findByNombreAndFranquicia_Id(nombre, franquiciaId);
    }


    @Override
    @Transactional
    public void eliminarSucursal(Long id) {
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        for (SucursalProducto sp : sucursal.getProductos()) {
            productoService.eliminarProducto(sp.getProducto().getId());
        }

        sucursalRepository.delete(sucursal);
    }


    @Override
    public Optional<Sucursal> getSucursalPorId(Long id) {
        return sucursalRepository.findById(id);
    }

    @Override
    public boolean existeSucursalPorId(Long id) {
        return sucursalRepository.existsById(id);
    }

    private Sucursal procesarSucursal(Sucursal sucursal) {
        List<SucursalProducto> listaProductos = procesarProductos(sucursal.getProductos(), sucursal);
        sucursal.setProductos(listaProductos);
        return sucursalRepository.save(sucursal);
    }

    private List<SucursalProducto> procesarProductos(List<SucursalProducto> productos, Sucursal sucursal) {
        List<SucursalProducto> listaFinal = new ArrayList<>();

        if (productos != null) {
            for (SucursalProducto sp : productos) {
                Producto producto = sp.getProducto();

                if (producto.getId() != null && productoService.existeProductoPorId(producto.getId())) {
                    Producto existente = productoService.getProductoPorId(producto.getId())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                    existente.setNombre(producto.getNombre());
                    existente.setPrecio(producto.getPrecio());
                    existente.setFecha(producto.getFecha());
                    productoService.guardarProducto(existente);
                    sp.setProducto(existente);
                } else {
                    Producto nuevo = productoService.guardarProducto(producto);
                    sp.setProducto(nuevo);
                }

                sp.setSucursal(sucursal);
                listaFinal.add(sp);
            }
        }

        return listaFinal;
    }

    @Override
    @Transactional
    public void eliminarProductoDeSucursal(Long sucursalId, Long productoId) {
        Sucursal sucursal = sucursalRepository.findById(sucursalId)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

        boolean eliminado = false;

        var iterator = sucursal.getProductos().iterator();
        while (iterator.hasNext()) {
            SucursalProducto sp = iterator.next();
            if (sp.getProducto().getId().equals(productoId)) {
                sucursal.removeProducto(sp);
                eliminado = true;
                break;
            }
        }

        if (!eliminado) {
            throw new RuntimeException("Producto no encontrado en la sucursal");
        }

        sucursalRepository.save(sucursal);
    }

}
