package com.owl.Controllers;

import com.owl.Models.Conexiones;
import com.owl.Utils.DBconexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EliminarConexionesController {

    @FXML
    private Button eliminarButton;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Conexiones> conexionTable;

    @FXML
    private TableColumn<Conexiones, String> nombreCol;
    @FXML
    private TableColumn<Conexiones, String> tipoCol;
    @FXML
    private TableColumn<Conexiones, String> medidasCorteCol;
    @FXML
    private TableColumn<Conexiones, String> medidasCampanasCol;
    @FXML
    private TableColumn<Conexiones, String> medidasSalidasCol;
    @FXML
    private TableColumn<Conexiones, String> medidasCampanasSalidaCol;
    @FXML
    private TableColumn<Conexiones, String> usoCol;
    @FXML
    private TableColumn<Conexiones, Double> precioCol;

    private ObservableList<Conexiones> listaConexiones = FXCollections.observableArrayList();
    private ObservableList<Conexiones> listaFiltrada = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar columnas de datos
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombreConexion"));
        tipoCol.setCellValueFactory(new PropertyValueFactory<>("tipoConexion"));
        medidasCorteCol.setCellValueFactory(new PropertyValueFactory<>("medidasCorte"));
        medidasCampanasCol.setCellValueFactory(new PropertyValueFactory<>("medidasCampanas"));
        medidasSalidasCol.setCellValueFactory(new PropertyValueFactory<>("medidasCorteSalidas"));
        medidasCampanasSalidaCol.setCellValueFactory(new PropertyValueFactory<>("medidasCampanasSalidas"));
        usoCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        precioCol.setCellValueFactory(new PropertyValueFactory<>("precio"));

        // Configurar filtro de búsqueda
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filtrarConexiones(newValue));

        // Cargar conexiones iniciales
        cargarConexiones();
    }

    public void cargarConexiones() {
        listaConexiones.clear();
        listaFiltrada.clear();

        try (Connection connection = DBconexion.getConnection()) {
            String query = "SELECT * FROM conexiones";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Conexiones conexion = new Conexiones(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre_conexion"),
                        resultSet.getString("tipo_conexion"),
                        resultSet.getString("medidas_corte"),
                        resultSet.getString("medidas_campanas"),
                        resultSet.getString("medidas_de_corte_de_salidas"),
                        resultSet.getString("medidas_de_campanas_de_salidas"),
                        resultSet.getString("tipo_uso"),
                        resultSet.getDouble("precio")
                    );
                    listaConexiones.add(conexion);
                }
            }

            conexionTable.setItems(listaConexiones);
            listaFiltrada.addAll(listaConexiones);
        } catch (SQLException e) {
            mostrarAlerta("Error al cargar conexiones", e.getMessage());
        }
    }

    private void filtrarConexiones(String filtro) {
        listaFiltrada.clear();
        if (filtro == null || filtro.isEmpty()) {
            listaFiltrada.addAll(listaConexiones);
        } else {
            String filtroLower = filtro.toLowerCase();
            listaConexiones.forEach(conexion -> {
                if (conexion.getNombreConexion().toLowerCase().contains(filtroLower) ||
                    conexion.getTipoConexion().toLowerCase().contains(filtroLower)) {
                    listaFiltrada.add(conexion);
                }
            });
        }
        conexionTable.setItems(listaFiltrada);
    }

    @FXML
    void onEliminarButtonClick(ActionEvent event) {
        // Obtener la fila seleccionada
        Conexiones conexionSeleccionada = conexionTable.getSelectionModel().getSelectedItem();
    
        if (conexionSeleccionada == null) {
            mostrarAlerta("Eliminación", "No hay ninguna conexión seleccionada para eliminar.");
            return;
        }
    
        // Confirmar eliminación
        if (!confirmarEliminacion(1)) {
            return;
        }
    
        // Eliminar conexión de la base de datos
        try (Connection connection = DBconexion.getConnection()) {
            String query = "DELETE FROM conexiones WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, conexionSeleccionada.getId());
                statement.executeUpdate();
            }
    
            // Eliminar la conexión de la lista y actualizar la tabla
            listaConexiones.remove(conexionSeleccionada);
            conexionTable.setItems(listaConexiones);
    
            mostrarAlerta("Éxito", "Conexión eliminada.");
        } catch (SQLException e) {
            mostrarAlerta("Error al eliminar", e.getMessage());
        }
    }
    

    private boolean confirmarEliminacion(int cantidad) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminación");
        alert.setHeaderText("¿Está seguro de eliminar " + cantidad + " conexión?");
        alert.setContentText("Esta acción no se puede deshacer.");

        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
