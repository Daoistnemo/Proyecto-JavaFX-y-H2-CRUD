package com.owl.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;  // Cambiar a VBox
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
        try {
            // Opción 1: Usando la ruta absoluta
            String imagePath = "file:///C:/Users/User/Documents/QA testing/Proyecto - My little Bag/dog/dog/src/main/resources/logo.png";
            Image image = new Image(imagePath);

            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar la imagen: " + e.getMessage());
        }
    }

    @FXML
    void click(ActionEvent event) {
        try {
            // Cargar la nueva escena con el archivo FXML usando la ruta relativa
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Opciones.fxml"));
            VBox root = loader.load();  // Cambiar a VBox

            // Crear una nueva escena con el archivo FXML cargado
            Scene scene = new Scene(root);

            // Obtener la ventana (stage) actual
            Stage stage = (Stage) startbutton.getScene().getWindow();

            // Cambiar la escena en la ventana
            stage.setScene(scene);
            stage.setTitle("Selecciona una opción");

     

            // Mostrar la nueva escena
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cambiar de escena: " + e.getMessage());
        }
    }
}
