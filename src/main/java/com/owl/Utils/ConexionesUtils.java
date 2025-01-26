package com.owl.Utils;

import com.owl.Models.Conexiones;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class ConexionesUtils {

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

    // Método para obtener una conexión por su ID
    public static Conexiones obtenerConexionPorId(int idConexion) {
        Conexiones conexion = null;
        String sql = "SELECT * FROM CONEXIONES WHERE ID = ?";

        try (Connection conn = DBconexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idConexion);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                conexion = new Conexiones(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE_CONEXION"),
                        rs.getString("TIPO_CONEXION"),
                        rs.getString("MEDIDAS_CORTE"),
                        rs.getString("MEDIDAS_CAMPANAS"),
                        rs.getString("MEDIDAS_DE_CORTE_DE_SALIDAS"),
                        rs.getString("MEDIDAS_DE_CAMPANAS_DE_SALIDAS"),
                        rs.getString("TIPO_USO"),
                        rs.getDouble("PRECIO")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conexion;
    }

    // Método para cargar las conexiones desde la base de datos
    public static ObservableList<Conexiones> cargarConexiones(String query) {
        ObservableList<Conexiones> listaConexiones = FXCollections.observableArrayList();
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Conexiones conexion = new Conexiones(
                        resultSet.getInt("ID"),
                        resultSet.getString("NOMBRE_CONEXION"),
                        resultSet.getString("TIPO_CONEXION"),
                        resultSet.getString("MEDIDAS_CORTE"),
                        resultSet.getString("MEDIDAS_CAMPANAS"),
                        resultSet.getString("MEDIDAS_DE_CORTE_DE_SALIDAS"),
                        resultSet.getString("MEDIDAS_DE_CAMPANAS_DE_SALIDAS"),
                        resultSet.getString("TIPO_USO"),
                        resultSet.getDouble("PRECIO")
                );
                if (!listaConexiones.contains(conexion)) {
                    listaConexiones.add(conexion);
                }
            }
        } catch (SQLException e) {
            mostrarAlerta("Error al cargar conexiones", e.getMessage(), Alert.AlertType.ERROR);
        }
        return listaConexiones;
    }

    // Método para filtrar las conexiones
    public static ObservableList<Conexiones> filtrarConexiones(String filtro, ObservableList<Conexiones> listaConexiones) {
        ObservableList<Conexiones> listaFiltrada = FXCollections.observableArrayList();
        if (filtro == null || filtro.isEmpty()) {
            listaFiltrada.addAll(listaConexiones);
        } else {
            for (Conexiones conexion : listaConexiones) {
                if (conexion.getNombreConexion().toLowerCase().contains(filtro.toLowerCase()) ||
                        conexion.getTipoConexion().toLowerCase().contains(filtro.toLowerCase())) {
                    listaFiltrada.add(conexion);
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

    // Método para eliminar una conexión
    public static void eliminarConexion(int id) {
        String query = "DELETE FROM CONEXIONES WHERE ID = ?";
        try {
            ejecutarConsulta(query, id);
        } catch (SQLException e) {
            mostrarAlerta("Error al eliminar conexión", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Método para agregar una nueva conexión y obtener el ID generado
    public static int agregarConexion(Conexiones conexion) {
        if (existeConexionPorNombre(conexion.getNombreConexion())) {
            mostrarAlerta("Duplicado", "Ya existe una conexión con este nombre", Alert.AlertType.WARNING);
            return -1;
        }

        String query = "INSERT INTO CONEXIONES (NOMBRE_CONEXION, TIPO_CONEXION, MEDIDAS_CORTE, MEDIDAS_CAMPANAS, " +
                "MEDIDAS_DE_CORTE_DE_SALIDAS, MEDIDAS_DE_CAMPANAS_DE_SALIDAS, TIPO_USO, PRECIO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int generatedId = -1;
        try (Connection conn = DBconexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, conexion.getNombreConexion());
            stmt.setString(2, conexion.getTipoConexion());
            stmt.setString(3, conexion.getMedidasCorte());
            stmt.setString(4, conexion.getMedidasCampanas());
            stmt.setString(5, conexion.getMedidasCorteSalidas());
            stmt.setString(6, conexion.getMedidasCampanasSalidas());
            stmt.setString(7, conexion.getTipo());
            stmt.setDouble(8, conexion.getPrecio());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
                conexion.setId(generatedId);
                System.out.println("Conexión agregada con ID: " + generatedId);
            }

        } catch (SQLException e) {
            mostrarAlerta("Error al agregar conexión", e.getMessage(), Alert.AlertType.ERROR);
        }
        return generatedId;
    }

    // Método para verificar existencia de conexión por nombre
    private static boolean existeConexionPorNombre(String nombreConexion) {
        String query = "SELECT COUNT(*) FROM CONEXIONES WHERE NOMBRE_CONEXION = ?";
        try (Connection conn = DBconexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreConexion);
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

    public static void actualizarConexion(Conexiones conexion) {
        if (conexion.getId() <= 0) {
            mostrarAlerta("Error", "ID de conexión inválido", Alert.AlertType.ERROR);
            return;
        }

        if (!existeConexion(conexion.getId())) {
            mostrarAlerta("Error", "No existe un registro con el ID proporcionado", Alert.AlertType.ERROR);
            return;
        }

        String query = "UPDATE CONEXIONES SET NOMBRE_CONEXION = ?, TIPO_CONEXION = ?, MEDIDAS_CORTE = ?, MEDIDAS_CAMPANAS = ?, " +
                "MEDIDAS_DE_CORTE_DE_SALIDAS = ?, MEDIDAS_DE_CAMPANAS_DE_SALIDAS = ?, TIPO_USO = ?, PRECIO = ? WHERE ID = ?";
        try (Connection conn = obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, conexion.getNombreConexion());
            stmt.setString(2, conexion.getTipoConexion());
            stmt.setString(3, conexion.getMedidasCorte());
            stmt.setString(4, conexion.getMedidasCampanas());
            stmt.setString(5, conexion.getMedidasCorteSalidas());
            stmt.setString(6, conexion.getMedidasCampanasSalidas());
            stmt.setString(7, conexion.getTipo());
            stmt.setDouble(8, conexion.getPrecio());
            stmt.setInt(9, conexion.getId());

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Registro con ID " + conexion.getId() + " actualizado exitosamente.");
            }
        } catch (SQLException e) {
            mostrarAlerta("Error al actualizar conexión", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Método para verificar si una conexión existe por ID
    private static boolean existeConexion(int id) {
        String query = "SELECT COUNT(*) FROM CONEXIONES WHERE ID = ?";
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
}