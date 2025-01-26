package com.owl.Utils;

import com.owl.Models.DetallePedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoUtils {

    /**
     * Obtiene los detalles de un pedido específico desde la base de datos.
     * 
     * @param idPedido ID del pedido para el que se buscan los detalles.
     * @return Lista de objetos DetallePedido con los datos correspondientes.
     */
    public static List<DetallePedido> obtenerDetallesPorPedido(int idPedido) {
        List<DetallePedido> detalles = new ArrayList<>();
        String sql = "SELECT ID_DETALLE, ID_PEDIDO, ID_CONEXION, CANTIDAD, PRECIO_UNITARIO, TOTAL " +
                     "FROM DETALLE_PEDIDO WHERE ID_PEDIDO = ?";

        // Manejo de recursos con try-with-resources para evitar fugas de memoria
        try (Connection conn = DBconexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Configurar el parámetro de la consulta
            pstmt.setInt(1, idPedido);

            // Ejecutar la consulta
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Crear un objeto DetallePedido con los datos de la fila actual
                    DetallePedido detalle = new DetallePedido(
                        rs.getInt("ID_DETALLE"),
                        rs.getInt("ID_PEDIDO"),
                        rs.getInt("ID_CONEXION"),
                        rs.getInt("CANTIDAD"),
                        rs.getDouble("PRECIO_UNITARIO")
                    );
                    // Calcular el total usando el precio unitario y la cantidad
                    detalle.setTotal(detalle.getPrecioUnitario() * detalle.getCantidad());
                    
                    detalles.add(detalle);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener detalles del pedido: " + e.getMessage());
            e.printStackTrace();
        }

        return detalles;
    }
}
