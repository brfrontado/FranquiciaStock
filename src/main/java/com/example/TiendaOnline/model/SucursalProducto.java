package com.example.TiendaOnline.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "sucursal_producto")
public class SucursalProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    @JsonBackReference
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private int stock;

    public SucursalProducto() {}

    public SucursalProducto(Sucursal sucursal, Producto producto, int stock) {
        this.sucursal = sucursal;
        this.producto = producto;
        this.stock = stock;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
