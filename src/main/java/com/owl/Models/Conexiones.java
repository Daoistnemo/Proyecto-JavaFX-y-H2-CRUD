package com.owl.Models;

import javafx.beans.property.*;

public class Conexiones {

    private final IntegerProperty id;
    private final StringProperty nombreConexion;
    private final StringProperty tipoConexion;
    private final StringProperty medidasCorte;
    private final StringProperty medidasCampanas;
    private final StringProperty medidasCorteSalidas;
    private final StringProperty medidasCampanasSalidas;
    private final StringProperty tipo;
    private final DoubleProperty precio;
    private final BooleanProperty seleccionada;


    // Constructor
    public Conexiones(int id, String nombreConexion, String tipoConexion, String medidasCorte,
                      String medidasCampanas, String medidasCorteSalidas, String medidasCampanasSalidas,
                      String tipo, double precio) {
        this.id = new SimpleIntegerProperty(id);
        this.nombreConexion = new SimpleStringProperty(nombreConexion);
        this.tipoConexion = new SimpleStringProperty(tipoConexion);
        this.medidasCorte = new SimpleStringProperty(medidasCorte);
        this.medidasCampanas = new SimpleStringProperty(medidasCampanas);
        this.medidasCorteSalidas = new SimpleStringProperty(medidasCorteSalidas);
        this.medidasCampanasSalidas = new SimpleStringProperty(medidasCampanasSalidas);
        this.tipo = new SimpleStringProperty(tipo);
        this.precio = new SimpleDoubleProperty(precio);
        this.seleccionada = new SimpleBooleanProperty(false);
    }

    // Getters y Setters
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public String getNombreConexion() { return nombreConexion.get(); }
    public void setNombreConexion(String nombreConexion) { this.nombreConexion.set(nombreConexion); }
    public StringProperty nombreConexionProperty() { return nombreConexion; }

    public String getTipoConexion() { return tipoConexion.get(); }
    public void setTipoConexion(String tipoConexion) { this.tipoConexion.set(tipoConexion); }
    public StringProperty tipoConexionProperty() { return tipoConexion; }

    public String getMedidasCorte() { return medidasCorte.get(); }
    public void setMedidasCorte(String medidasCorte) { this.medidasCorte.set(medidasCorte); }
    public StringProperty medidasCorteProperty() { return medidasCorte; }

    public String getMedidasCampanas() { return medidasCampanas.get(); }
    public void setMedidasCampanas(String medidasCampanas) { this.medidasCampanas.set(medidasCampanas); }
    public StringProperty medidasCampanasProperty() { return medidasCampanas; }

    public String getMedidasCorteSalidas() { return medidasCorteSalidas.get(); }
    public void setMedidasCorteSalidas(String medidasCorteSalidas) { this.medidasCorteSalidas.set(medidasCorteSalidas); }
    public StringProperty medidasCorteSalidasProperty() { return medidasCorteSalidas; }

    public String getMedidasCampanasSalidas() { return medidasCampanasSalidas.get(); }
    public void setMedidasCampanasSalidas(String medidasCampanasSalidas) { this.medidasCampanasSalidas.set(medidasCampanasSalidas); }
    public StringProperty medidasCampanasSalidasProperty() { return medidasCampanasSalidas; }

    public String getTipo() { return tipo.get(); }
    public void setTipo(String tipo) { this.tipo.set(tipo); }
    public StringProperty tipoProperty() { return tipo; }

    public double getPrecio() { return precio.get(); }
    public void setPrecio(double precio) { this.precio.set(precio); }
    public DoubleProperty precioProperty() { return precio; }
     // Métodos para manejar la selección
     public boolean isSeleccionada() { 
        return seleccionada.get(); 
    }

    public void setSeleccionada(boolean seleccionada) { 
        this.seleccionada.set(seleccionada); 
    }

    public BooleanProperty seleccionadaProperty() { 
        return seleccionada; 
    }
}
