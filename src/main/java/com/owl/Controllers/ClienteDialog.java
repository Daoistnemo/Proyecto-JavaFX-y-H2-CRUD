package com.owl.Controllers;

import com.owl.Models.Cliente;
import com.owl.Utils.ClientesUtils;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

public class ClienteDialog extends Dialog<Cliente> {

    public ClienteDialog(Cliente clienteExistente) {
        setTitle(clienteExistente == null ? "Agregar Cliente" : "Editar Cliente");
        setHeaderText(clienteExistente == null ? "Ingrese los datos del nuevo cliente" : "Modifique los datos del cliente");

        // Configurar botones
        ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

        // Crear el formulario
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Campos de texto
        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre");
        TextField direccionField = new TextField();
        direccionField.setPromptText("Dirección");
        TextField telefonoField = new TextField();
        telefonoField.setPromptText("Teléfono");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        // Llenar campos si es un cliente existente
        if (clienteExistente != null) {
            nombreField.setText(clienteExistente.getNombre());
            direccionField.setText(clienteExistente.getDireccion());
            telefonoField.setText(clienteExistente.getTelefono());
            emailField.setText(clienteExistente.getEmail());
        }

        // Añadir campos al grid
        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(new Label("Dirección:"), 0, 1);
        grid.add(direccionField, 1, 1);
        grid.add(new Label("Teléfono:"), 0, 2);
        grid.add(telefonoField, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);

        getDialogPane().setContent(grid);

        // Validación y conversión de resultado
        setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {
                // Validar campos obligatorios
                if (nombreField.getText().isEmpty() || emailField.getText().isEmpty()) {
                    mostrarError("Nombre y Email son campos obligatorios");
                    return null;
                }

                // Crear o actualizar cliente
                return new Cliente(
                    clienteExistente != null ? clienteExistente.getId() : 0,
                    nombreField.getText().trim(),
                    direccionField.getText().trim(),
                    telefonoField.getText().trim(),
                    emailField.getText().trim(),
                    clienteExistente != null ? 
                        clienteExistente.getFechaRegistro() : 
                        java.time.LocalDate.now().toString()
                );
            }
            return null;
        });

        // Configuraciones adicionales
        initModality(Modality.APPLICATION_MODAL);
    }

    // Método de utilidad para mostrar errores
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método estático para mostrar el diálogo de agregar
    public static Cliente mostrarDialogoAgregar() {
        ClienteDialog dialog = new ClienteDialog(null);
        return dialog.showAndWait().orElse(null);
    }

    // Método estático para mostrar el diálogo de edición
    public static Cliente mostrarDialogoEditar(Cliente cliente) {
        ClienteDialog dialog = new ClienteDialog(cliente);
        return dialog.showAndWait().orElse(null);
    }
}