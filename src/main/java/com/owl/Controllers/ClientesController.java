package com.owl.Controllers;

import com.owl.Models.Cliente;
import com.owl.Utils.ClientesUtils;
import com.owl.Utils.iconUtils;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ClientesController {

    @FXML private TableView<Cliente> clientesTable;
    @FXML private TableColumn<Cliente, Integer> idColumn;
    @FXML private TableColumn<Cliente, String> nombreColumn;
    @FXML private TableColumn<Cliente, String> direccionColumn;
    @FXML private TableColumn<Cliente, String> telefonoColumn;
    @FXML private TableColumn<Cliente, String> emailColumn;
    @FXML private TableColumn<Cliente, String> fechaRegistroColumn;

    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private Button pedidosButton;
    @FXML private Button agregarButton;
    @FXML private Button modificarButton;
    @FXML private Button eliminarButton;

    private ObservableList<Cliente> listaClientes;

    @FXML
    public void initialize() {
        // Configurar columnas de la tabla
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        direccionColumn.setCellValueFactory(cellData -> cellData.getValue().direccionProperty());
        telefonoColumn.setCellValueFactory(cellData -> cellData.getValue().telefonoProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        fechaRegistroColumn.setCellValueFactory(cellData -> cellData.getValue().fechaRegistroProperty());

        // Cargar clientes iniciales
        cargarClientes();

        // Configurar eventos
        configurarEventos();
                cargarClientes();
        configurarEventos();

        // Configurar Stage con ícono
        Platform.runLater(() -> {
            Stage stage = (Stage) clientesTable.getScene().getWindow();
            stage.setTitle("Clientes");
            iconUtils.loadAndSetStageIcon(stage, "/images/icon1.png");
        });
    }
    

    private void configurarEventos() {
        // Búsqueda de clientes
        searchButton.setOnMouseClicked(this::handleBusqueda);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarClientes(newValue);
        });

        // Eventos de botones
        pedidosButton.setOnMouseClicked(this::handleVerPedidos);
        agregarButton.setOnMouseClicked(this::handleAgregarCliente);
        modificarButton.setOnMouseClicked(this::handleModificarCliente);
        eliminarButton.setOnMouseClicked(this::handleEliminarCliente);
    }

    private void cargarClientes() {
        listaClientes = ClientesUtils.cargarClientes("SELECT * FROM clientes");
        clientesTable.setItems(listaClientes);
    }

    private void filtrarClientes(String textoBusqueda) {
        ObservableList<Cliente> listaFiltrada = ClientesUtils.filtrarClientes(textoBusqueda, listaClientes);
        clientesTable.setItems(listaFiltrada);
    }

    private void handleBusqueda(MouseEvent event) {
        String textoBusqueda = searchField.getText();
        filtrarClientes(textoBusqueda);
    }

    private void handleVerPedidos(MouseEvent event) {
        Cliente clienteSeleccionado = clientesTable.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            ClientesUtils.mostrarAlerta("Error", "Seleccione un cliente para ver sus pedidos", Alert.AlertType.WARNING);
            return;
        }

        // TODO: Implementar ventana de pedidos
        ClientesUtils.mostrarAlerta("Información", 
            "Funcionalidad de pedidos para el cliente: " + clienteSeleccionado.getNombre(), 
            Alert.AlertType.INFORMATION);
    }

    private void handleAgregarCliente(MouseEvent event) {
        try {
            Cliente nuevoCliente = ClienteDialog.mostrarDialogoAgregar();
            if (nuevoCliente != null) {
                int idGenerado = ClientesUtils.agregarCliente(nuevoCliente);
                if (idGenerado != -1) {
                    cargarClientes();
                }
            }
        } catch (Exception e) {
            ClientesUtils.mostrarAlerta("Error", "No se pudo agregar el cliente: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void handleModificarCliente(MouseEvent event) {
        Cliente clienteSeleccionado = clientesTable.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            ClientesUtils.mostrarAlerta("Error", "Seleccione un cliente para modificar", Alert.AlertType.WARNING);
            return;
        }

        try {
            Cliente clienteModificado = ClienteDialog.mostrarDialogoEditar(clienteSeleccionado);
            if (clienteModificado != null) {
                ClientesUtils.actualizarCliente(clienteModificado);
                cargarClientes();
            }
        } catch (Exception e) {
            ClientesUtils.mostrarAlerta("Error", "No se pudo modificar el cliente: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void handleEliminarCliente(MouseEvent event) {
        Cliente clienteSeleccionado = clientesTable.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            ClientesUtils.mostrarAlerta("Error", "Seleccione un cliente para eliminar", Alert.AlertType.WARNING);
            return;
        }

        // Mostrar confirmación antes de eliminar
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Está seguro que desea eliminar este cliente?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");

        if (confirmacion.showAndWait().orElse(null) == ButtonType.OK) {
            ClientesUtils.eliminarCliente(clienteSeleccionado.getId());
            cargarClientes();
        }
    }
}