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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombreConexion"));
        tipoCol.setCellValueFactory(new PropertyValueFactory<>("tipoConexion"));
        medidasCorteCol.setCellValueFactory(new PropertyValueFactory<>("medidasCorte"));
        medidasCampanasCol.setCellValueFactory(new PropertyValueFactory<>("medidasCampanas"));
        medidasSalidasCol.setCellValueFactory(new PropertyValueFactory<>("medidasCorteSalidas"));
        medidasCampanasSalidaCol.setCellValueFactory(new PropertyValueFactory<>("medidasCampanasSalidas"));
        usoCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        precioCol.setCellValueFactory(new PropertyValueFactory<>("precio"));

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filtrarConexiones(newValue));

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

            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

            cargarConexiones();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar la ventana de conexiones: " + e.getMessage());
        }
    }

    public void cargarConexiones() {
        listaConexiones.clear();

        // Usa DBconexion en lugar de BDconexion
        try (Connection connection = DBconexion.getConnection()) {
            System.out.println("Cargando conexiones desde la base de datos...");

            if (connection == null) {
                System.out.println("Error: La conexión es nula");
                return;
            }

            String query = "SELECT * FROM conexiones";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre_conexion");
                String tipoConexion = resultSet.getString("tipo_conexion");
                String medidasCorte = resultSet.getString("medidas_corte");
                String medidasCampanas = resultSet.getString("medidas_campanas");
                String medidasCorteSalidas = resultSet.getString("medidas_de_corte_de_salidas");
                String medidasCampanasSalidas = resultSet.getString("medidas_de_campanas_de_salidas");
                String tipo = resultSet.getString("tipo_uso");
                double precio = resultSet.getDouble("precio");

                Conexiones conexion = new Conexiones(id, nombre, tipoConexion, medidasCorte,
                        medidasCampanas, medidasCorteSalidas, medidasCampanasSalidas, tipo, precio);
                listaConexiones.add(conexion);
            }

            conexionTable.setItems(listaConexiones);

        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void filtrarConexiones(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            conexionTable.setItems(listaConexiones);
        } else {
            ObservableList<Conexiones> filtrada = FXCollections.observableArrayList();
            for (Conexiones conexion : listaConexiones) {
                if (conexion.getNombreConexion().toLowerCase().contains(filtro.toLowerCase()) ||
                        conexion.getTipoConexion().toLowerCase().contains(filtro.toLowerCase())) {
                    filtrada.add(conexion);
                }
            }
            conexionTable.setItems(filtrada);
        }
    }
}
