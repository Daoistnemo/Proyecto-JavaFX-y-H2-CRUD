package com.owl.Controllers;

import com.owl.Utils.iconUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class OpConexionesController {

    // Método genérico para cargar las vistas
    private void cargarVentana(String fxmlPath, String tituloVentana, ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane root = loader.load();

            // Configurar y mostrar la nueva ventana
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(tituloVentana);
            stage.show();

            // Cerrar la ventana actual
            iconUtils.loadAndSetStageIcon(stage, "/images/icon1.png");

            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar la ventana: " + e.getMessage());

            // Mostrar mensaje de error en la UI
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo abrir la ventana");
            alert.setContentText("Hubo un problema al intentar cargar la ventana.");
            alert.showAndWait();
        }
    }

    @FXML
    void onModificarConexionClick(ActionEvent event) {
        cargarVentana("/fxml/ModificarConexiones.fxml", "Modificar Conexión", event);
    }

    @FXML
    void onBuscarButtonClick(ActionEvent event) {
        cargarVentana("/fxml/BuscarConexiones.fxml", "Tabla de Conexiones", event);
    }

    @FXML
    void onAgregarConexionClick(ActionEvent event) {
        cargarVentana("/fxml/AgregarConexiones.fxml", "Agregar Conexión", event);
    }

    @FXML
    void onEliminarConexionClick(ActionEvent event) {
        cargarVentana("/fxml/EliminarConexiones.fxml", "Eliminar Conexión", event);
    }
}
