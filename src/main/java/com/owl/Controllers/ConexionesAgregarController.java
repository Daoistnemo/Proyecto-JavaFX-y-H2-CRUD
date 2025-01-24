package com.owl.Controllers;
import com.owl.Utils.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexionesAgregarController {

    // Campos del formulario
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
            // Mostrar alerta si algún campo está vacío
            showAlert("Error", "Todos los campos deben ser completados.", AlertType.ERROR);
            return;
        }

        // Validar el valor de tipo_uso
        if (!uso.equals("agua") && !uso.equals("alcantarillado") && !uso.equals("desagüe") && !uso.equals("eléctrica")) {
            showAlert("Error", "El campo 'Uso' debe ser uno de los valores válidos: agua, alcantarillado, desagüe, eléctrica.", AlertType.ERROR);
            return;
        }

        // Si todo está bien, intentar agregar los datos a la base de datos
        try {
            insertConexion(nombre, tipo, medidasCorte, medidasCampanas, medidasSalidas, medidasCampanasSalidas, uso, precio);
            // Mostrar alerta de éxito
            showAlert("Éxito", "Conexión agregada correctamente.", AlertType.INFORMATION);
        } catch (SQLException e) {
            // Si ocurre un error en la base de datos, mostrar alerta de error
            showAlert("Error", "Hubo un error al agregar la conexión: " + e.getMessage(), AlertType.ERROR);
        }
        limpiarCampos();
    }

    // Método para insertar la conexión en la base de datos
    private void insertConexion(String nombre, String tipo, String medidasCorte, String medidasCampanas, 
                                 String medidasSalidas, String medidasCampanasSalidas, String uso, String precio) throws SQLException {
        // Usar la conexión de BDconexion
        try (Connection conn = DBconexion.getConnection()) {
            String sql = "INSERT INTO conexiones (nombre_conexion, tipo_conexion, medidas_corte, medidas_campanas, " +
                         "medidas_de_corte_de_salidas, medidas_de_campanas_de_salidas, tipo_uso, precio) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nombre);
                stmt.setString(2, tipo);
                stmt.setString(3, medidasCorte);
                stmt.setString(4, medidasCampanas);
                stmt.setString(5, medidasSalidas);
                stmt.setString(6, medidasCampanasSalidas);
                stmt.setString(7, uso);
                stmt.setString(8, precio);

                stmt.executeUpdate();
            }
        }
    }
    private void limpiarCampos() {
        // Limpiar los campos de texto
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
