package com.owl.Controllers;

import com.owl.Models.Conexiones;
import com.owl.Utils.ConexionesUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

        listaConexiones = ConexionesUtils.cargarConexiones("SELECT * FROM conexiones");
        conexionTable.setItems(listaConexiones);
        listaFiltrada.addAll(listaConexiones);
    }

    private void filtrarConexiones(String filtro) {
        listaFiltrada = ConexionesUtils.filtrarConexiones(filtro, listaConexiones);
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

        // Confirmación de eliminación
        if (!confirmarEliminacion("¿Está seguro de eliminar esta conexión?")) {
            return;
        }

        // Eliminar la conexión de la base de datos utilizando ConexionesUtils
        ConexionesUtils.eliminarConexion(conexionSeleccionada.getId());

        // Eliminar la conexión de la lista y actualizar la tabla
        listaConexiones.remove(conexionSeleccionada);
        conexionTable.setItems(listaConexiones);

        mostrarAlerta("Éxito", "Conexión eliminada.");
    }

    private boolean confirmarEliminacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

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
