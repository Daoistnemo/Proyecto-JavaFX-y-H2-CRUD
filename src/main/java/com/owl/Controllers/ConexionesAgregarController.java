package com.owl.Controllers;

import com.owl.Models.Conexiones;
import com.owl.Utils.ConexionesUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;

public class ConexionesAgregarController {

    @FXML
    private TextField nombreTextField;

    @FXML
    private TextField tipoTextField;

    @FXML
    private TextField medidasCorteTextField;

    @FXML
    private TextField medidasCampanasTextField;

    @FXML
    private TextField medidasSalidasTextField;

    @FXML
    private TextField medidasCampanasSalidasTextField;

    @FXML
    private TextField usoTextField;

    @FXML
    private TextField precioTextField;

    // Método que se invoca cuando se hace clic en el botón "Agregar"
    @FXML
    private void onAgregarClick() {
        // Recuperar los valores ingresados en los campos de texto
        String nombre = nombreTextField.getText();
        String tipo = tipoTextField.getText();
        String medidasCorte = medidasCorteTextField.getText();
        String medidasCampanas = medidasCampanasTextField.getText();
        String medidasSalidas = medidasSalidasTextField.getText();
        String medidasCampanasSalidas = medidasCampanasSalidasTextField.getText();
        String uso = usoTextField.getText();
        String precio = precioTextField.getText();

        // Validar que todos los campos tengan datos
        if (nombre.isEmpty() || tipo.isEmpty() || medidasCorte.isEmpty() || medidasCampanas.isEmpty() ||
            medidasSalidas.isEmpty() || medidasCampanasSalidas.isEmpty() || uso.isEmpty() || precio.isEmpty()) {
            showAlert("Error", "Todos los campos deben ser completados.", AlertType.ERROR);
            return;
        }

        // Validar el valor de tipo_uso
        if (!uso.equals("agua") && !uso.equals("alcantarillado") && !uso.equals("desagüe") && !uso.equals("eléctrica")) {
            showAlert("Error", "El campo 'Uso' debe ser uno de los valores válidos: agua, alcantarillado, desagüe, eléctrica.", AlertType.ERROR);
            return;
        }

        // Validar el precio
        double precioValue;
        try {
            precioValue = Double.parseDouble(precio);
            if (precioValue <= 0) {
                showAlert("Error", "El precio debe ser mayor que 0.", AlertType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "El precio debe ser un número válido.", AlertType.ERROR);
            return;
        }

        // Crear la conexión
        Conexiones nuevaConexion = new Conexiones(0, nombre, tipo, medidasCorte, medidasCampanas, 
                                                  medidasSalidas, medidasCampanasSalidas, uso, precioValue);

        ConexionesUtils.agregarConexion(nuevaConexion);
        showAlert("Éxito", "Conexión agregada correctamente.", AlertType.INFORMATION);
        
        limpiarCampos();
    }

    // Método para limpiar los campos de texto
    private void limpiarCampos() {
        nombreTextField.clear();
        tipoTextField.clear();
        medidasCorteTextField.clear();
        medidasCampanasTextField.clear();
        medidasSalidasTextField.clear();
        medidasCampanasSalidasTextField.clear();
        usoTextField.clear();
        precioTextField.clear();
    }

    // Método para mostrar una alerta
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
