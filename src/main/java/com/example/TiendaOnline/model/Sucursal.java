package com.example.TiendaOnline.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    private String direccion;

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SucursalProducto> productos = new ArrayList<>();

    public Sucursal() {}

    public Sucursal(Long id, String nombre, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Sucursal(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    @ManyToOne
    @JoinColumn(name = "franquicia_id")
    private Franquicia franquicia;

    public Franquicia getFranquicia() { return franquicia; }
    public void setFranquicia(Franquicia franquicia) { this.franquicia = franquicia; }

    public List<SucursalProducto> getProductos() { return productos; }
    public void setProductos(List<SucursalProducto> productos) { this.productos = productos; }

    public void addProducto(SucursalProducto sp) {
        productos.add(sp);
        sp.setSucursal(this);
    }

    public void removeProducto(SucursalProducto sp) {
        productos.remove(sp);
        sp.setSucursal(null);
    }
}
