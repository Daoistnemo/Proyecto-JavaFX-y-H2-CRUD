package com.owl.Utils;

import com.owl.Models.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;

import java.sql.*;

public class ClientesUtils {

    // Método para obtener la conexión a la base de datos
    private static Connection obtenerConexion() throws SQLException {
        return DBconexion.getConnection(); // Asegúrate de que DBconexion esté configurado correctamente
    }

    // Método genérico para ejecutar una consulta SQL sin esperar resultados
    private static void ejecutarConsulta(String query, Object... parametros) throws SQLException {
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Establecer los parámetros de la consulta
            for (int i = 0; i < parametros.length; i++) {
                statement.setObject(i + 1, parametros[i]);
            }

            // Ejecutar la actualización (INSERT, UPDATE, DELETE)
            statement.executeUpdate();
        }
    }

    // Método para obtener un cliente por su ID
    public static Cliente obtenerClientePorId(int id) {
        Cliente cliente = null;
        String query = "SELECT * FROM clientes WHERE id = ?";
        try (Connection conn = DBconexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);  // Establece el parámetro ID en la consulta
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("fecha_registro")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // En un proyecto real, manejar adecuadamente la excepción
        }
        return cliente;
    }


    // Método para cargar los clientes desde la base de datos y devolverlos como lista
   // Método para cargar los clientes desde la base de datos y devolverlos como lista
public static ObservableList<Cliente> cargarClientes(String sql) {
    ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    try (Connection conn = obtenerConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("fecha_registro")
            );
            listaClientes.add(cliente);
        }

    } catch (SQLException e) {
        System.out.println("Error al cargar los clientes desde la base de datos: " + e.getMessage());
        e.printStackTrace();
    }

    return listaClientes;
}

    
    // Método para filtrar clientes por nombre, dirección o teléfono
    public static ObservableList<Cliente> filtrarClientes(String textoBusqueda, ObservableList<Cliente> listaClientes) {
        ObservableList<Cliente> listaFiltrada = FXCollections.observableArrayList();

        for (Cliente cliente : listaClientes) {
            if (cliente.getNombre().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                cliente.getDireccion().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                cliente.getTelefono().toLowerCase().contains(textoBusqueda.toLowerCase())) {
                listaFiltrada.add(cliente);
            }
        }

        return listaFiltrada;
    }

    // Método para cargar los clientes en el ComboBox
public void cargarClientesEnComboBox(ComboBox<Cliente> comboBoxClientes) {
    ObservableList<Cliente> listaClientes = ClientesUtils.cargarClientes("SELECT * FROM clientes"); // Cargar los clientes
    comboBoxClientes.setItems(listaClientes); // Asignar la lista al ComboBox

    // Usar un Custom Cell Factory para mostrar el nombre del cliente en el ComboBox
    comboBoxClientes.setCellFactory(param -> new ListCell<Cliente>() {
        @Override
        protected void updateItem(Cliente cliente, boolean empty) {
            super.updateItem(cliente, empty);
            if (cliente != null) {
                setText(cliente.getNombre()); // Mostrar el nombre del cliente en el ComboBox
            } else {
                setText(null);
            }
        }
    });

    // Configurar cómo se muestra el cliente seleccionado en el ComboBox
    comboBoxClientes.setButtonCell(new ListCell<Cliente>() {
        @Override
        protected void updateItem(Cliente cliente, boolean empty) {
            super.updateItem(cliente, empty);
            if (cliente != null) {
                setText(cliente.getNombre()); // Mostrar el nombre del cliente seleccionado
            } else {
                setText(null);
            }
        }
    });
}


    // Método para agregar un cliente a la base de datos
    public static int agregarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, direccion, telefono, email, fecha_registro) VALUES (?, ?, ?, ?, ?)";
        int idGenerado = -1;

        try (Connection conn = obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getDireccion());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setString(4, cliente.getEmail());
            pstmt.setString(5, cliente.getFechaRegistro());

            // Ejecutar la consulta de inserción
            pstmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idGenerado = generatedKeys.getInt(1); // Obtener el ID generado
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al agregar el cliente: " + e.getMessage());
            e.printStackTrace();
        }

        return idGenerado;
    }

    // Método para actualizar un cliente en la base de datos
    public static void actualizarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ?, direccion = ?, telefono = ?, email = ?, fecha_registro = ? WHERE id = ?";

        try (Connection conn = obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getDireccion());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setString(4, cliente.getEmail());
            pstmt.setString(5, cliente.getFechaRegistro());
            pstmt.setInt(6, cliente.getId());

            pstmt.executeUpdate(); // Ejecutar la actualización

        } catch (SQLException e) {
            System.out.println("Error al actualizar el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para eliminar un cliente de la base de datos
    public static void eliminarCliente(int idCliente) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCliente);
            pstmt.executeUpdate(); // Ejecutar la eliminación

        } catch (SQLException e) {
            System.out.println("Error al eliminar el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para mostrar alertas
    public static void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
