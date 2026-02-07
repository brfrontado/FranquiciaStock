package com.example.TiendaOnline.service.impl;

import com.example.TiendaOnline.model.Producto;
import com.example.TiendaOnline.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceImplTest {
    //Simulamos el repositorio con mockito
    @Mock
    private ProductoRepository productoRepository;

    //Inyectamos los mocks en la clase que vamos a probar
    @InjectMocks
    private ProductoServiceImpl productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);//Inicializamos los mocks
    }
    //Probamos el metodo getProductos()
    @Test
    void getProductos() {
        List<Producto> lista = Arrays.asList(new Producto("Mouse", 100.0f, LocalDate.now()));
        when(productoRepository.findAll()).thenReturn(lista);// Simulamos la respuesta del repositorio

        List<Producto> productos = productoService.getProductos();

        assertEquals(1, productos.size());
        assertEquals("Mouse", productos.get(0).getNombre());
        verify(productoRepository, times(1)).findAll();// Verificamos que se llamó al método findAll() una vez
    }

    @Test
    void getProductoPorNombre() {
        String nombre = "Mouse";
        Producto producto = new Producto("Mouse", 100.0f, LocalDate.now());

        when(productoRepository.findProductoByNombre(nombre)).thenReturn(Optional.of(producto));
        Optional<Producto> resultado = productoService.getProductoPorNombre(nombre);

        assertTrue(resultado.isPresent());
        assertEquals("Mouse", resultado.get().getNombre());

        verify(productoRepository, times(1)).findProductoByNombre(nombre);
    }

    @Test
    void guardarProducto() {
        Producto producto = new Producto("Monitor", 100.0f, LocalDate.now());
        when(productoRepository.save(producto)).thenReturn(producto);
        Producto resultado = productoService.guardarProducto(producto);

        assertNotNull(resultado);
        assertEquals("Monitor", resultado.getNombre());

        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void existeProductoPorId() {
        Long id = 1L;
        //Simulamos que el repositorio devuelve true para el id 1L
        when(productoRepository.existsById(id)).thenReturn(true);
        //Ejecutamos el metodo
        boolean existe = productoService.existeProductoPorId(id);
        //Verificamos que el resultado sea tru
        assertTrue(existe);
        //Verificamos que el metodo existsByid fue llamado con el id correcto
        verify(productoRepository, times(1)).existsById(id);
    }

    @Test
    void eliminarProducto() {
        long id = 1L;
        //ejecutamos el metodo
        productoService.eliminarProducto(id);
        //Validamos que el deleteById fue llamado exactamente una vez con el ID correcto
        verify(productoRepository, times(1)).deleteById(id);
    }
}