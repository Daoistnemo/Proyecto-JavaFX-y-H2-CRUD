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

    // Getter para id (corregido)
    public int getId() {
        return id.get();
    }

    // Getter para nombre
    public String getNombre() {
        return nombre.get();
    }

    // Getter para direccion
    public String getDireccion() {
        return direccion.get();
    }

    // Getter para telefono
    public String getTelefono() {
        return telefono.get();
    }

    // Getter para email
    public String getEmail() {
        return email.get();
    }

    // Getter para fechaRegistro
    public String getFechaRegistro() {
        return fechaRegistro.get();
    }

    // Getters y Setters con Properties
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public StringProperty direccionProperty() {
        return direccion;
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty fechaRegistroProperty() {
        return fechaRegistro;
    }

    // Setter para id
    public void setId(int id) {
        this.id.set(id);
    }

    // Setter para nombre
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    // Setter para direccion
    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }

    // Setter para telefono
    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }

    // Setter para email
    public void setEmail(String email) {
        this.email.set(email);
    }

    // Setter para fechaRegistro
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro.set(fechaRegistro);
    }

    // Mejorado el toString para que solo devuelva el nombre del cliente (útil en ComboBox, etc.)
    @Override
    public String toString() {
        return nombre.get();  // Devuelve solo el nombre del cliente en lugar de todo el objeto
    }
}
