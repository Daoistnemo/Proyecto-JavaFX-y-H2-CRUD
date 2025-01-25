package com.owl.Utils;

import com.owl.Models.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class ClientesUtils {

    // Método para obtener la conexión
    private static Connection obtenerConexion() throws SQLException {
        return DBconexion.getConnection();
    }

    // Método genérico para ejecutar una consulta SQL
    private static void ejecutarConsulta(String query, Object... parametros) throws SQLException {
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (int i = 0; i < parametros.length; i++) {
                statement.setObject(i + 1, parametros[i]);
            }
            statement.executeUpdate();
        }
    }

    // Método para cargar los clientes desde la base de datos
    public static ObservableList<Cliente> cargarClientes(String query) {
        ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Cliente cliente = new Cliente(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("direccion"),
                        resultSet.getString("telefono"),
                        resultSet.getString("email"),
                        resultSet.getString("fecha_registro")
                );
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            mostrarAlerta("Error al cargar clientes", e.getMessage(), Alert.AlertType.ERROR);
        }
        return listaClientes;
    }

    // Método para filtrar los clientes
    public static ObservableList<Cliente> filtrarClientes(String filtro, ObservableList<Cliente> listaClientes) {
        ObservableList<Cliente> listaFiltrada = FXCollections.observableArrayList();
        if (filtro == null || filtro.isEmpty()) {
            listaFiltrada.addAll(listaClientes);
        } else {
            for (Cliente cliente : listaClientes) {
                if (cliente.getNombre().toLowerCase().contains(filtro.toLowerCase()) ||
                        cliente.getDireccion().toLowerCase().contains(filtro.toLowerCase()) ||
                        cliente.getEmail().toLowerCase().contains(filtro.toLowerCase())) {
                    listaFiltrada.add(cliente);
                }
            }
        }
        return listaFiltrada;
    }

    // Método para mostrar alertas
    public static void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    // Método para eliminar un cliente
    public static void eliminarCliente(int id) {
        if (id <= 0) {
            mostrarAlerta("Error", "ID de cliente inválido para eliminar.", Alert.AlertType.ERROR);
            return;
        }

        String query = "DELETE FROM clientes WHERE id = ?";
        try {
            ejecutarConsulta(query, id);
            System.out.println("Cliente con ID " + id + " eliminado exitosamente.");
        } catch (SQLException e) {
            mostrarAlerta("Error al eliminar cliente", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static int agregarCliente(Cliente cliente) {
        if (existeClientePorEmail(cliente.getEmail())) {
            mostrarAlerta("Duplicado", "Ya existe un cliente con este correo electrónico", Alert.AlertType.WARNING);
            return -1; // Esto es correcto.
        }
    
        String query = "INSERT INTO clientes (nombre, direccion, telefono, email, fecha_registro) VALUES (?, ?, ?, ?, ?)";
        int generatedId = -1; // Valor inicial.
        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
    
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getDireccion());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getFechaRegistro());
    
            stmt.executeUpdate();
    
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1); // Aquí obtenemos el ID generado.
                cliente.setId(generatedId);
                System.out.println("Cliente agregado con ID: " + generatedId);
            }
    
        } catch (SQLException e) {
            mostrarAlerta("Error al agregar cliente", e.getMessage(), Alert.AlertType.ERROR);
        }
        return generatedId; // Devuelve el ID o -1 si hubo un error.
    }
    

    // Método para actualizar un cliente
    public static void actualizarCliente(Cliente cliente) {
        if (cliente.getId() <= 0) {
            mostrarAlerta("Error", "ID de cliente inválido", Alert.AlertType.ERROR);
            return;
        }

        if (!existeCliente(cliente.getId())) {
            mostrarAlerta("Error", "No existe un registro con el ID proporcionado", Alert.AlertType.ERROR);
            return;
        }

        String query = "UPDATE clientes SET nombre = ?, direccion = ?, telefono = ?, email = ?, fecha_registro = ? WHERE id = ?";
        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getDireccion());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getFechaRegistro());
            stmt.setInt(6, cliente.getId());

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Registro con ID " + cliente.getId() + " actualizado exitosamente.");
            }
        } catch (SQLException e) {
            mostrarAlerta("Error al actualizar cliente", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Método para verificar si un cliente existe por ID
    private static boolean existeCliente(int id) {
        String query = "SELECT COUNT(*) FROM clientes WHERE id = ?";
        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            mostrarAlerta("Error al verificar existencia", e.getMessage(), Alert.AlertType.ERROR);
        }
        return false;
    }

    // Método para verificar existencia de cliente por email
    private static boolean existeClientePorEmail(String email) {
        String query = "SELECT COUNT(*) FROM clientes WHERE email = ?";
        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            mostrarAlerta("Error al verificar existencia", e.getMessage(), Alert.AlertType.ERROR);
        }
        return false;
    }
}
