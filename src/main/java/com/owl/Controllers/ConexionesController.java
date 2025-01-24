package com.owl.Controllers;

import com.owl.Models.*;
import com.owl.Utils.*; // Asegúrate de importar correctamente la clase DBconexion
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConexionesController {

    @FXML
    private Button conexionesButton;

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

    @FXML
    public void initialize() {
        // Configuración de las columnas de la tabla
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombreConexion"));
        tipoCol.setCellValueFactory(new PropertyValueFactory<>("tipoConexion"));
        medidasCorteCol.setCellValueFactory(new PropertyValueFactory<>("medidasCorte"));
        medidasCampanasCol.setCellValueFactory(new PropertyValueFactory<>("medidasCampanas"));
        medidasSalidasCol.setCellValueFactory(new PropertyValueFactory<>("medidasCorteSalidas"));
        medidasCampanasSalidaCol.setCellValueFactory(new PropertyValueFactory<>("medidasCampanasSalidas"));
        usoCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        precioCol.setCellValueFactory(new PropertyValueFactory<>("precio"));

        // Filtrado de las conexiones
        searchField.textProperty().addListener((observable, oldValue, newValue) -> 
            conexionTable.setItems(ConexionesUtils.filtrarConexiones(newValue, listaConexiones))
        );

        // Cargar las conexiones
        cargarConexiones();
    }

    @FXML
    void onConexionesButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Conexiones.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Conexiones");
            stage.show();

            // Cerrar la ventana actual
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

            cargarConexiones();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar la ventana de conexiones: " + e.getMessage());
        }
    }

    // Cargar las conexiones desde la base de datos
    public void cargarConexiones() {
        listaConexiones.clear();
        // Usamos el método centralizado de ConexionesUtils para cargar las conexiones
        listaConexiones = ConexionesUtils.cargarConexiones("SELECT * FROM conexiones");
        conexionTable.setItems(listaConexiones);
    }
}
