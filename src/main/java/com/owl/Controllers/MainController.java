package com.owl.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.owl.Utils.iconUtils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML
    private ImageView imageView;

    @FXML
    private Button startbutton;

    @FXML
    private Label welcomeLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Cargar el logo en el ImageView
        loadLogo();

        // Configurar el título e ícono de la ventana principal
    Platform.runLater(this::configurePrimaryStage);
    }

    private void loadLogo() {
        try {
            // Cargar el logo desde los recursos del proyecto
            Image logo = new Image(getClass().getResourceAsStream("/images/logo.png"));
            if (logo.isError()) {
                System.err.println("Error al cargar el logo: " + logo.getException());
                return;
            }

            // Configurar la imagen en el ImageView
            imageView.setImage(logo);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);

        } catch (Exception e) {
            System.err.println("Excepción al cargar el logo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void configurePrimaryStage() {
        try {
            // Obtener el Stage principal de manera diferente
            Stage primaryStage = (Stage) imageView.getScene().getWindow();
    
            // Configurar el título de la ventana principal
            primaryStage.setTitle("Inicio - My little bag");
    
            // Configurar el ícono de la ventana principal
            iconUtils.loadAndSetStageIcon(primaryStage, "/images/icon1.png");
    
        } catch (Exception e) {
            System.err.println("Error al configurar el Stage principal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void click(ActionEvent event) {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Opciones.fxml"));
            VBox root = loader.load();

            // Crear una nueva escena y obtener el Stage actual
            Scene scene = new Scene(root);
            Stage stage = (Stage) startbutton.getScene().getWindow();

            // Configurar el ícono para la nueva escena
            iconUtils.loadAndSetStageIcon(stage, "/images/icon1.png");

            // Cambiar la escena en el Stage actual
            stage.setScene(scene);
            stage.setTitle("Selecciona una opción");
            stage.show();

        } catch (Exception e) {
            System.err.println("Error al cambiar de escena: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
