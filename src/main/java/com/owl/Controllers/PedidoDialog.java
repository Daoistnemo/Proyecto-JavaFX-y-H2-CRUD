package com.owl.Controllers;

import com.owl.Models.Cliente;
import com.owl.Models.Pedidos;
import com.owl.Utils.DBconexion;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PedidoDialog extends Dialog<Pedidos> {

    public PedidoDialog(Pedidos pedidoExistente) {
        setTitle(pedidoExistente == null ? "Agregar Pedido" : "Editar Pedido");
        setHeaderText(pedidoExistente == null ? "Ingrese los datos del nuevo pedido" : "Modifique los datos del pedido");

        // Configurar botones
        ButtonType guardarButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(guardarButtonType, ButtonType.CANCEL);

        // Crear el formulario
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Campos de texto
        TextField estadoPedidoField = new TextField();
        estadoPedidoField.setPromptText("Estado del pedido");
        TextField observacionesField = new TextField();
        observacionesField.setPromptText("Observaciones");
        TextField precioTotalField = new TextField();
        precioTotalField.setPromptText("Precio Total");

        // ComboBox para clientes
        ComboBox<Cliente> clienteComboBox = new ComboBox<>();
        clienteComboBox.setPromptText("Seleccionar Cliente");
        cargarClientes(clienteComboBox);

        // Llenar campos si es un pedido existente
        if (pedidoExistente != null) {
            estadoPedidoField.setText(pedidoExistente.getEstadoPedido());
            observacionesField.setText(pedidoExistente.getObservaciones());
            precioTotalField.setText(String.valueOf(pedidoExistente.getPrecioTotal()));
            clienteComboBox.setValue(pedidoExistente.getCliente());
        }

        // Añadir campos al grid
        grid.add(new Label("Estado del Pedido:"), 0, 0);
        grid.add(estadoPedidoField, 1, 0);
        grid.add(new Label("Observaciones:"), 0, 1);
        grid.add(observacionesField, 1, 1);
        grid.add(new Label("Precio Total:"), 0, 2);
        grid.add(precioTotalField, 1, 2);
        grid.add(new Label("Cliente:"), 0, 3);
        grid.add(clienteComboBox, 1, 3);

        getDialogPane().setContent(grid);

        // Validación y conversión de resultado
        setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {
                // Validar campos obligatorios
                if (estadoPedidoField.getText().isEmpty() || precioTotalField.getText().isEmpty() || clienteComboBox.getValue() == null) {
                    mostrarError("El estado del pedido, el precio total y el cliente son campos obligatorios.");
                    return null;
                }

                // Crear o actualizar el pedido
                try {
                    double precioTotal = Double.parseDouble(precioTotalField.getText());

                    return new Pedidos(
                        pedidoExistente != null ? pedidoExistente.getIdPedido() : 0,
                        estadoPedidoField.getText().trim(),
                        observacionesField.getText().trim(),
                        precioTotal,
                        clienteComboBox.getValue(), // Cliente seleccionado
                        java.sql.Timestamp.valueOf(java.time.LocalDateTime.now())
                    );
                } catch (NumberFormatException e) {
                    mostrarError("El precio total debe ser un número válido.");
                    return null;
                }
            }
            return null;
        });

        // Configuraciones adicionales
        initModality(Modality.APPLICATION_MODAL);
    }

    // Método de utilidad para cargar los clientes desde la base de datos en el ComboBox
    private void cargarClientes(ComboBox<Cliente> clienteComboBox) {
        String sql = "SELECT id, nombre, direccion, telefono, email, fecha_registro FROM clientes"; // Ajustar columna según tu tabla

        try (Connection conn = DBconexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Crear un objeto Cliente con los datos de la base de datos
                Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("fecha_registro")
                );

                // Agregar el objeto Cliente al ComboBox
                clienteComboBox.getItems().add(cliente);
            }

            // Establecer cómo se va a mostrar el cliente en el ComboBox
            clienteComboBox.setCellFactory(param -> new ListCell<Cliente>() {
                @Override
                protected void updateItem(Cliente item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(item.getNombre()); // Mostrar el nombre del cliente en el ComboBox
                    } else {
                        setText(null);
                    }
                }
            });

            clienteComboBox.setButtonCell(new ListCell<Cliente>() {
                @Override
                protected void updateItem(Cliente item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(item.getNombre()); // Mostrar el nombre del cliente seleccionado
                    } else {
                        setText(null);
                    }
                }
            });

        } catch (SQLException e) {
            mostrarError("Error al cargar los clientes desde la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
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
    public static Pedidos mostrarDialogoAgregar() {
        PedidoDialog dialog = new PedidoDialog(null);
        return dialog.showAndWait().orElse(null);
    }

    // Método estático para mostrar el diálogo de edición
    public static Pedidos mostrarDialogoEditar(Pedidos pedido) {
        PedidoDialog dialog = new PedidoDialog(pedido);
        return dialog.showAndWait().orElse(null);
    }
}
