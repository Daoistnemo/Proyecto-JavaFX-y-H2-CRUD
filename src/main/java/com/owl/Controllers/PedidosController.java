package com.owl.Controllers;

import com.owl.Models.Cliente;
import com.owl.Models.DetallePedido;
import com.owl.Models.Pedidos;
import com.owl.Models.Conexiones;
import com.owl.Utils.PedidosUtils;
import com.owl.Utils.ClientesUtils;
import com.owl.Utils.ConexionesUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.sql.Timestamp;
import java.util.List;

public class PedidosController {

    @FXML
    private ComboBox<String> estadoComboBox;  // ComboBox para el estado del pedido
    @FXML
    private ComboBox<Cliente> clienteComboBox;

    @FXML
    private TableView<Pedidos> pedidosTableView;
    @FXML
    private TableColumn<Pedidos, Integer> idPedidoColumn;
    @FXML
    private TableColumn<Pedidos, String> estadoPedidoColumn;
    @FXML
    private TableColumn<Pedidos, String> observacionesColumn;
    @FXML
    private TableColumn<Pedidos, Double> precioTotalColumn;
    @FXML
    private TableColumn<Pedidos, Cliente> clienteColumn;
    @FXML
    private TableColumn<Pedidos, Timestamp> fechaPedidoColumn;

    @FXML
    private DatePicker fechaInicioDatePicker;
    @FXML
    private DatePicker fechaFinDatePicker;

    @FXML
    private TableView<DetallePedido> detallesPedidoTableView;
    @FXML
    private TableColumn<DetallePedido, String> conexionColumn;
    @FXML
    private TableColumn<DetallePedido, Integer> cantidadColumn;
    @FXML
    private TableColumn<DetallePedido, Double> precioUnitarioColumn;
    @FXML
    private TableColumn<DetallePedido, Double> subtotalColumn;

    @FXML
    private TextField totalPedidoField;

    private Pedidos pedidoActual;

    @FXML
    public void initialize() {
        // Configurar las columnas de la tabla de pedidos
        idPedidoColumn.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        estadoPedidoColumn.setCellValueFactory(new PropertyValueFactory<>("estadoPedido"));
        observacionesColumn.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        precioTotalColumn.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        clienteColumn.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        fechaPedidoColumn.setCellValueFactory(new PropertyValueFactory<>("fechaPedido"));

        // Configurar las columnas de la tabla de detalles de pedido
        conexionColumn.setCellValueFactory(cellData -> cellData.getValue().nombreConexionProperty());
        cantidadColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        precioUnitarioColumn.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        subtotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Cargar los pedidos al inicializar
        cargarPedidos();

        // Cargar los estados al ComboBox de Estado
        cargarEstados();

        // Cargar los clientes al ComboBox de Cliente
        cargarClientes();

        // Listener para mostrar los detalles del pedido seleccionado
        pedidosTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldPedido, newPedido) -> {
            if (newPedido != null) {
                mostrarDetallesPedido(newPedido);
            }
        });

        // Inicializar un pedido vacío
        pedidoActual = new Pedidos();
    }

    // Método para cargar los estados
    private void cargarEstados() {
        List<String> estados = List.of("Pendiente", "En Proceso", "Completado");
        estadoComboBox.setItems(FXCollections.observableArrayList(estados));
        estadoComboBox.getSelectionModel().selectFirst();  // Selecciona el primer estado
    }

    // Método para cargar los clientes
    private void cargarClientes() {
        try {
            List<Cliente> clientes = ClientesUtils.cargarClientes("SELECT * FROM clientes");
            clienteComboBox.setItems(FXCollections.observableArrayList(clientes));
            clienteComboBox.getSelectionModel().selectFirst();  // Selecciona el primer cliente
        } catch (Exception e) {
            mostrarError("Error al cargar los clientes: " + e.getMessage());
        }
    }

    // Método para cargar los pedidos
    private void cargarPedidos() {
        try {
            List<Pedidos> pedidos = PedidosUtils.cargarPedidos("SELECT * FROM pedidos");
            pedidosTableView.setItems(FXCollections.observableArrayList(pedidos));
        } catch (Exception e) {
            mostrarError("Error al cargar los pedidos: " + e.getMessage());
        }
    }

    // Método para mostrar los detalles de un pedido
    private void mostrarDetallesPedido(Pedidos pedido) {
        try {
            pedidoActual = pedido;
            List<DetallePedido> detalles = PedidosUtils.cargarDetallesPedido(pedido.getIdPedido());

            for (DetallePedido detalle : detalles) {
                Conexiones conexion = ConexionesUtils.obtenerConexionPorId(detalle.getIdConexion());
                if (conexion != null) {
                    detalle.setNombreConexion(conexion.getNombreConexion());
                }
            }

            detallesPedidoTableView.setItems(FXCollections.observableArrayList(detalles));
            recalcularTotalPedido();
        } catch (Exception e) {
            mostrarError("Error al cargar los detalles del pedido: " + e.getMessage());
        }
    }

    // Método para recalcular el total del pedido
    private void recalcularTotalPedido() {
        double total = detallesPedidoTableView.getItems().stream()
                .mapToDouble(DetallePedido::getTotal)
                .sum();
        totalPedidoField.setText(String.format("%.2f", total));
    }

    // Método para agregar una conexión al pedido
    @FXML
    public void agregarConexion() {
        try {
            // Crear el diálogo para seleccionar una conexión
            Dialog<DetallePedido> dialog = new Dialog<>();
            dialog.setTitle("Seleccionar Conexión");
            dialog.setHeaderText("Seleccione una conexión y agregue la cantidad.");

            // Crear el contenedor principal del diálogo
            VBox dialogVbox = new VBox(10);

            // Cargar las conexiones desde la base de datos
            List<Conexiones> listaConexiones = ConexionesUtils.cargarConexiones("SELECT * FROM conexiones");

            // Crear un TableView para mostrar las conexiones
            TableView<Conexiones> conexionTableView = new TableView<>();
            TableColumn<Conexiones, String> nombreColumn = new TableColumn<>("Nombre de la Conexión");
            nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreConexion"));
            conexionTableView.getColumns().add(nombreColumn);
            conexionTableView.setItems(FXCollections.observableArrayList(listaConexiones));

            // Campo para la cantidad
            TextField cantidadField = new TextField();
            cantidadField.setPromptText("Cantidad");

            // Añadir la tabla de conexiones y el campo de cantidad al VBox
            dialogVbox.getChildren().addAll(conexionTableView, cantidadField);

            // Botones del diálogo
            ButtonType okButtonType = new ButtonType("Agregar", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButtonType = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);
            dialog.getDialogPane().setContent(dialogVbox);

            // Convertir el resultado del diálogo en un detalle de pedido
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButtonType) {
                    Conexiones conexionSeleccionada = conexionTableView.getSelectionModel().getSelectedItem();
                    String cantidadTexto = cantidadField.getText();

                    if (conexionSeleccionada != null && !cantidadTexto.isEmpty()) {
                        try {
                            int cantidad = Integer.parseInt(cantidadTexto);

                            // Crear un nuevo detalle de pedido
                            DetallePedido detalle = new DetallePedido(
                                    0, // ID temporal
                                    pedidoActual.getIdPedido(),
                                    conexionSeleccionada.getId(),
                                    cantidad,
                                    conexionSeleccionada.getPrecio()
                            );
                            detalle.setNombreConexion(conexionSeleccionada.getNombreConexion());
                            detalle.calcularTotal();

                            return detalle;
                        } catch (NumberFormatException e) {
                            mostrarError("Por favor ingresa una cantidad válida.");
                        }
                    } else {
                        mostrarError("Debe seleccionar una conexión y una cantidad.");
                    }
                }
                return null;
            });

            // Mostrar el diálogo y esperar por el resultado
            dialog.showAndWait().ifPresent(detallePedido -> {
                if (detallePedido != null) {
                    detallesPedidoTableView.getItems().add(detallePedido);
                    recalcularTotalPedido();
                }
            });
        } catch (Exception e) {
            mostrarError("Error al agregar la conexión: " + e.getMessage());
        }
    }

    // Método para guardar un pedido
    @FXML
    public void guardarPedido() {
        try {
            if (detallesPedidoTableView.getItems().isEmpty()) {
                mostrarError("El pedido no tiene detalles.");
                return;
            }
    
            // Calculate total from details
            double totalPedido = detallesPedidoTableView.getItems().stream()
                .mapToDouble(detalle -> detalle.getCantidad() * detalle.getPrecioUnitario())
                .sum();
    
            // Set current timestamp
            Timestamp fechaActual = new Timestamp(System.currentTimeMillis());
    
            // Crear una copia de los detalles para el pedido
            pedidoActual.setDetalles(detallesPedidoTableView.getItems());
    
            // Asignar el cliente, estado, total y fecha al pedido
            pedidoActual.setCliente(clienteComboBox.getValue());
            pedidoActual.setEstadoPedido(estadoComboBox.getValue());
            pedidoActual.setPrecioTotal(totalPedido);
            pedidoActual.setFechaPedido(fechaActual);
    
            // Guardar el pedido y los detalles
            PedidosUtils.guardarPedido(pedidoActual);
            PedidosUtils.guardarDetallesPedido(pedidoActual);
            detallesPedidoTableView.getItems().clear();
            totalPedidoField.clear();
    
            mostrarExito("Pedido guardado con éxito.");
            cargarPedidos();  // Recargar la lista de pedidos
        } catch (Exception e) {
            mostrarError("Error al guardar el pedido: " + e.getMessage());
        }
    }

    // Método para eliminar un pedido
    @FXML
    public void eliminarPedido() {
        try {
            Pedidos pedidoSeleccionado = pedidosTableView.getSelectionModel().getSelectedItem();
            if (pedidoSeleccionado != null) {
                PedidosUtils.eliminarPedido(pedidoSeleccionado.getIdPedido());
                mostrarExito("Pedido eliminado con éxito.");
                cargarPedidos();  // Recargar la lista de pedidos
            } else {
                mostrarError("Debe seleccionar un pedido para eliminar.");
            }
        } catch (Exception e) {
            mostrarError("Error al eliminar el pedido: " + e.getMessage());
        }
    }

    // Método para mostrar un mensaje de error
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para mostrar un mensaje de éxito
    private void mostrarExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}