package com.owl.Models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Pedidos {

    private int idPedido;
    private String estadoPedido;
    private String observaciones;
    private double precioTotal;
    private Cliente cliente;
    private Timestamp fechaPedido;
    private List<DetallePedido> detalles;  // Add the detalles field

    // Constructor con parámetros
    public Pedidos(int idPedido, String estadoPedido, String observaciones, double precioTotal, Cliente cliente, Timestamp fechaPedido) {
        this.idPedido = idPedido;
        this.estadoPedido = estadoPedido;
        this.observaciones = observaciones;
        this.precioTotal = precioTotal;
        this.cliente = cliente;
        this.fechaPedido = fechaPedido;
    }

        // Constructor sin parámetros
        public Pedidos() {
            this.detalles = new ArrayList<>();
        }

    // Getters y setters para los demás campos
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Timestamp getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Timestamp fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    // Getter y setter para detalles
    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }
    
    public void agregarDetalle(DetallePedido detalle) {
    if (this.detalles == null) {
        this.detalles = new ArrayList<>();
    }
    this.detalles.add(detalle);
}
}
