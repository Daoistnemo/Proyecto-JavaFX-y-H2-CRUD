package com.owl.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class OpPedidosController {

    @FXML
    private Button AgregarButton;
    @FXML
    private Button CancelarButton;
    @FXML
    private Button RevisarButton;
    @FXML
    private Button HistorialButton;

    /**
     * Método genérico para abrir una nueva ventana basada en un archivo FXML.
     *
     * @param fxmlFile Ruta relativa del archivo FXML a cargar.
     * @param title    Título de la ventana.
     * @param event    Evento que activa el cambio.
     */
    private void openNewWindow(String fxmlFile, String title, ActionEvent event) {
        try {
            // Cargar el archivo FXML desde la ruta relativa
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane root = loader.load();

            // Crear y configurar la nueva escena y ventana
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();

            // Cerrar la ventana actual
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar la ventana: " + e.getMessage());

            // Mostrar un mensaje de error al usuario
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo abrir la ventana");
            alert.setContentText("Hubo un problema al intentar cargar la ventana: " + title);
            alert.showAndWait();
        }
    }

    @FXML
    void onAgregarPedidoClick(ActionEvent event) {
        openNewWindow("/fxml/AgregarPedido.fxml", "Agregar Pedido", event);
    }

    @FXML
    void onCancelarButtonClick(ActionEvent event) {
        openNewWindow("/fxml/CancelarPedido.fxml", "Cancelar Pedido", event);
    }

    @FXML
    void onRevisarPedidosClick(ActionEvent event) {
        openNewWindow("/fxml/RevisarPedidos.fxml", "Revisar Pedidos", event);
    }

    @FXML
    void onHistorialdePedidosClick(ActionEvent event) {
        openNewWindow("/fxml/HistorialPedidos.fxml", "Historial de Pedidos", event);
    }
}
