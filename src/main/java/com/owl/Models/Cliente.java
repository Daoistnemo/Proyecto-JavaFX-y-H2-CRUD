package com.owl.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {

    private final IntegerProperty id;
    private final StringProperty nombre;
    private final StringProperty direccion;
    private final StringProperty telefono;
    private final StringProperty email;
    private final StringProperty fechaRegistro;

    // Constructor
    public Cliente(int id, String nombre, String direccion, String telefono, String email, String fechaRegistro) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.direccion = new SimpleStringProperty(direccion);
        this.telefono = new SimpleStringProperty(telefono);
        this.email = new SimpleStringProperty(email);
        this.fechaRegistro = new SimpleStringProperty(fechaRegistro);
    }

    // Getters y Setters con Properties
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getDireccion() {
        return direccion.get();
    }

    public StringProperty direccionProperty() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }

    public String getTelefono() {
        return telefono.get();
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getFechaRegistro() {
        return fechaRegistro.get();
    }

    public StringProperty fechaRegistroProperty() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro.set(fechaRegistro);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id.get() +
                ", nombre='" + nombre.get() + '\'' +
                ", direccion='" + direccion.get() + '\'' +
                ", telefono='" + telefono.get() + '\'' +
                ", email='" + email.get() + '\'' +
                ", fechaRegistro='" + fechaRegistro.get() + '\'' +
                '}';
    }
}