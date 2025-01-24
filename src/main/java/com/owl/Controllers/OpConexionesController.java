package com.owl.Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class OpConexionesController {

    @FXML
    private Button conexionesButton;  // Botón que abrirá la tabla de conexiones

    
    @FXML
    private Button modificarButton;  // Botón de modificar conexión

    @FXML
    void onModificarConexionClick(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana de modificación
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModificarConexiones.fxml"));
            AnchorPane root = loader.load();

            // Configurar y mostrar la nueva ventana
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Modificar Conexión");
            stage.show();
    
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar la ventana de modificación: " + e.getMessage());
    
            // Mostrar un mensaje de error al usuario
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo abrir la ventana de modificación");
            alert.setContentText("Hubo un problema al intentar cargar la ventana de modificación.");
            alert.showAndWait();
        }
            // Cerrar la ventana actual
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();
    
    }
    


 
 @FXML
void onBuscarButtonClick(ActionEvent event) {
    try {
        // Cargar la ventana de la tabla de conexiones
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BuscarConexiones.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Tabla de Conexiones");  // Título de la ventana
        stage.show();

        // Cerrar la ventana actual
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error al cargar la ventana de conexiones: " + e.getMessage());

        // Mostrar mensaje de error en la UI
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No se pudo abrir la ventana");
        alert.setContentText("Hubo un problema al intentar cargar la ventana de conexiones.");
        alert.showAndWait();
    }
}

@FXML
void onAgregarConexionClick(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarConexiones.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Agregar Conexión");
        stage.show();
         // Cerrar la ventana actual
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error al cargar la ventana de agregar conexiones: " + e.getMessage());

        // Mostrar mensaje de error en la UI
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No se pudo abrir la ventana de agregar conexión");
        alert.setContentText("Hubo un problema al intentar cargar la ventana para agregar una conexión.");
        alert.showAndWait();
    }
}

@FXML
void onEliminarConexionClick(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EliminarConexiones.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Eliminar Conexión");
        stage.show();
        
        // Cerrar la ventana actual
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error al cargar la ventana de eliminar conexiones: " + e.getMessage());

        // Mostrar mensaje de error en la UI
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No se pudo abrir la ventana de eliminación de conexión");
        alert.setContentText("Hubo un problema al intentar cargar la ventana para eliminar una conexión.");
        alert.showAndWait();
    }
}

}

