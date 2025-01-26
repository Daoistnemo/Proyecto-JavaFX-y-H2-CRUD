package com.owl.Models;

import javafx.beans.property.*;

public class DetallePedido {
    // Propiedades existentes...
    private final IntegerProperty idDetalle;
    private final IntegerProperty idPedido;
    private final IntegerProperty idConexion;
    private final IntegerProperty cantidad;
    private final DoubleProperty precioUnitario;
    private final DoubleProperty total;
    private final StringProperty nombreConexion;  // Nueva propiedad

    // Modificar el constructor existente
    public DetallePedido(int idDetalle, int idPedido, int idConexion, int cantidad, double precioUnitario) {
        this.idDetalle = new SimpleIntegerProperty(idDetalle);
        this.idPedido = new SimpleIntegerProperty(idPedido);
        this.idConexion = new SimpleIntegerProperty(idConexion);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.precioUnitario = new SimpleDoubleProperty(precioUnitario);
        this.total = new SimpleDoubleProperty(0);
        this.nombreConexion = new SimpleStringProperty("");  // Inicializamos con un valor vacío
    }

    // Getter y setter para nombreConexion
    public String getNombreConexion() {
        return nombreConexion.get();
    }

    public void setNombreConexion(String nombre) {
        this.nombreConexion.set(nombre);
    }

    public StringProperty nombreConexionProperty() {
        return nombreConexion;
    }

    // Métodos para las propiedades existentes
    public int getIdDetalle() {
        return idDetalle.get();
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle.set(idDetalle);
    }

    public IntegerProperty idDetalleProperty() {
        return idDetalle;
    }

    public int getIdPedido() {
        return idPedido.get();
    }

    public void setIdPedido(int idPedido) {
        this.idPedido.set(idPedido);
    }

    public IntegerProperty idPedidoProperty() {
        return idPedido;
    }

    public int getIdConexion() {
        return idConexion.get();
    }

    public void setIdConexion(int idConexion) {
        this.idConexion.set(idConexion);
    }

    public IntegerProperty idConexionProperty() {
        return idConexion;
    }

    public int getCantidad() {
        return cantidad.get();
    }

    public void setCantidad(int cantidad) {
        this.cantidad.set(cantidad);
    }

    public IntegerProperty cantidadProperty() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario.get();
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario.set(precioUnitario);
    }

    public DoubleProperty precioUnitarioProperty() {
        return precioUnitario;
    }

    public double getTotal() {
        return total.get();
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    // Método para calcular el total
    public void calcularTotal() {
        setTotal(getCantidad() * getPrecioUnitario());
    }
}
