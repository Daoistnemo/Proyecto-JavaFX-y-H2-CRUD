package com.owl.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SeleccionOpcionesController {

    @FXML
    private Button conexionesButton;  // Botón que abrirá la tabla de conexiones

 
    @FXML
    void onConexionesButtonClick(ActionEvent event) {
        // Abrir la ventana que muestra la tabla de conexiones
        try {
            // Si el archivo Conexiones.fxml está en la misma carpeta que el controlador
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/OpConexiones.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Seleccione un Opción");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar la ventana de conexiones: " + e.getMessage());
        }
    }
}
