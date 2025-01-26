package com.owl.Utils;

import com.owl.Models.Pedidos;
import com.owl.Models.DetallePedido;
import com.owl.Models.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidosUtils {

    // Cargar los pedidos desde la base de datos
    public static List<Pedidos> cargarPedidos(String sql, Object... params) {
        List<Pedidos> listaPedidos = new ArrayList<>();
        try (Connection conn = DBconexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Establecer los parámetros de la consulta si existen
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof String) {
                    stmt.setString(i + 1, (String) params[i]);
                } else if (params[i] instanceof Timestamp) {
                    stmt.setTimestamp(i + 1, (Timestamp) params[i]);
                }
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pedidos pedido = new Pedidos(
                            rs.getInt("id_pedido"),
                            rs.getString("estado_pedido"),
                            rs.getString("observaciones"),
                            rs.getDouble("precio_total"),
                            cargarCliente(rs.getInt("id_cliente")),  // Obtener el cliente con su ID
                            rs.getTimestamp("fecha_pedido")
                    );
                    listaPedidos.add(pedido);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPedidos;
    }

    // Cargar los detalles de un pedido
    public static List<DetallePedido> cargarDetallesPedido(int idPedido) {
        List<DetallePedido> detalles = new ArrayList<>();
        String sql = "SELECT * FROM detalles_pedido WHERE id_pedido = ?";
        try (Connection conn = DBconexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DetallePedido detalle = new DetallePedido(
                            rs.getInt("id_detalle"),
                            rs.getInt("id_pedido"),
                            rs.getInt("id_conexion"),
                            rs.getInt("cantidad"),
                            rs.getDouble("precio_unitario")
                    );
                    detalle.setIdDetalle(rs.getInt("id_detalle"));
                    detalle.calcularTotal();
                    detalles.add(detalle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }

    // Guardar un pedido en la base de datos
    public static void guardarPedido(Pedidos pedido) {
        String sql = "INSERT INTO pedidos (estado_pedido, observaciones, precio_total, cliente, fecha_pedido) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBconexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, pedido.getEstadoPedido());
            stmt.setString(2, pedido.getObservaciones());
            stmt.setDouble(3, pedido.getPrecioTotal());
            stmt.setInt(4, pedido.getCliente().getId()); // Usamos el ID del cliente
            stmt.setTimestamp(5, pedido.getFechaPedido());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idPedido = generatedKeys.getInt(1);
                        pedido.setIdPedido(idPedido); // Establecer el ID generado
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar un pedido de la base de datos
    public static void eliminarPedido(int idPedido) {
        String sql = "DELETE FROM pedidos WHERE id_pedido = ?";
        try (Connection conn = DBconexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Guardar los detalles de un pedido
    public static void guardarDetallesPedido(Pedidos pedido) {
        String sql = "INSERT INTO detalles_pedido (id_pedido, id_conexion, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBconexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (DetallePedido detalle : pedido.getDetalles()) {
                stmt.setInt(1, pedido.getIdPedido());
                stmt.setInt(2, detalle.getIdConexion());
                stmt.setInt(3, detalle.getCantidad());
                stmt.setDouble(4, detalle.getPrecioUnitario());
                stmt.addBatch();
            }

            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar los detalles de un pedido
    public static void eliminarDetallesPedido(int idPedido) {
        String sql = "DELETE FROM detalles_pedido WHERE id_pedido = ?";
        try (Connection conn = DBconexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Cargar cliente con su ID
    private static Cliente cargarCliente(int idCliente) {
        Cliente cliente = null;
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        try (Connection conn = DBconexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente(
                            rs.getInt("id_cliente"),
                            rs.getString("nombre"),
                            rs.getString("direccion"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                            rs.getString("fecha_registro")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }
}
