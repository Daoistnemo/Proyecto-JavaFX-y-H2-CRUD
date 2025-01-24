package com.owl.Models;


import java.sql.Timestamp;

public class Pedidos {
    private int idPedido;
    private String estadoPedido;
    private String observaciones;
    private double precioTotal;
    private String cliente;
    private Timestamp fechaPedido;

    // Constructor vacío
    public Pedidos() {}

    // Constructor con todos los atributos
    public Pedidos(int idPedido, String estadoPedido, String observaciones, double precioTotal, String cliente, Timestamp fechaPedido) {
        this.idPedido = idPedido;
        this.estadoPedido = estadoPedido;
        this.observaciones = observaciones;
        this.precioTotal = precioTotal;
        this.cliente = cliente;
        this.fechaPedido = fechaPedido;
    }

    // Getters y Setters

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

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Timestamp getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Timestamp fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", estadoPedido='" + estadoPedido + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", precioTotal=" + precioTotal +
                ", cliente='" + cliente + '\'' +
                ", fechaPedido=" + fechaPedido +
                '}';
    }
}
