package com.owl.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUtils {

    // Método público estático para eliminar duplicados con más especificidad (por nombre_conexion, tipo_conexion y precio)
    public static void eliminarDuplicados() {
        try (Connection connection = DBconexion.getConnection()) {
            // Query para eliminar duplicados basado en la combinación de nombre_conexion, tipo_conexion y precio
            String query = "DELETE FROM conexiones WHERE id NOT IN (" +
                           "  SELECT MIN(id) " +
                           "  FROM conexiones " +
                           "  GROUP BY nombre_conexion, tipo_conexion, precio" +
                           ")";

            PreparedStatement statement = connection.prepareStatement(query);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Duplicados eliminados correctamente.");
            } else {
                System.out.println("No se encontraron duplicados para eliminar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al eliminar duplicados: " + e.getMessage());
        }
    }
}
