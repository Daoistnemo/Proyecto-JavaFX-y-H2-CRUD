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
    private Button conexionesButton;  // Botón que abrirá la ventana de conexiones

    @FXML
    private Button pedidosButton;  // Botón que abrirá la ventana de pedidos

    @FXML
    private Button clientesButton;  // Botón que abrirá la ventana de clientes

    // Método común para cargar ventanas
    private void loadWindow(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar la ventana: " + e.getMessage());
        }
    }

    @FXML
    void onConexionesButtonClick(ActionEvent event) {
        // Abrir la ventana de conexiones
        loadWindow("/fxml/OpConexiones.fxml", "Seleccione una Opción");
    }

    @FXML
    void onPedidosButtonClick(ActionEvent event) {
        // Abrir la ventana de pedidos
        loadWindow("/fxml/OpPedidos.fxml", "Seleccione una Opción");
    }

    @FXML
    void onClientesButtonClick(ActionEvent event) {
        // Abrir la ventana de clientes
        loadWindow("/fxml/Clientes.fxml", "Seleccione una Opción");
    }
}
