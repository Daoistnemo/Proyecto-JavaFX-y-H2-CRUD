package com.owl.Controllers;

import com.owl.Models.Conexiones;
import com.owl.Utils.DBconexion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class ConexionesModificarController {

    @FXML
    private TableView<Conexiones> conexionTable;
    @FXML
    private TableColumn<Conexiones, String> nombreCol;
    @FXML
    private TableColumn<Conexiones, String> tipoCol;
    @FXML
    private TableColumn<Conexiones, String> medidasCorteCol;
    @FXML
    private TableColumn<Conexiones, String> medidasCampanasCol;
    @FXML
    private TableColumn<Conexiones, String> medidasSalidasCol;
    @FXML
    private TableColumn<Conexiones, String> medidasCampanasSalidaCol;
    @FXML
    private TableColumn<Conexiones, String> usoCol;
    @FXML
    private TableColumn<Conexiones, Double> precioCol;
    @FXML
    private TextField searchField;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField tipoTextField;
    @FXML
    private TextField medidasCorteTextField;
    @FXML
    private TextField medidasCampanasTextField;
    @FXML
    private TextField medidasSalidasTextField;
    @FXML
    private TextField medidasCampanasSalidasTextField;
    @FXML
    private TextField usoTextField;
    @FXML
    private TextField precioTextField;

    private final ObservableList<Conexiones> listaConexiones = FXCollections.observableArrayList(); // Lista observable

    @FXML
    public void initialize() {
        // Configurar las columnas de la tabla
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombreConexion"));
        tipoCol.setCellValueFactory(new PropertyValueFactory<>("tipoConexion"));
        medidasCorteCol.setCellValueFactory(new PropertyValueFactory<>("medidasCorte"));
        medidasCampanasCol.setCellValueFactory(new PropertyValueFactory<>("medidasCampanas"));
        medidasSalidasCol.setCellValueFactory(new PropertyValueFactory<>("medidasCorteSalidas"));
        medidasCampanasSalidaCol.setCellValueFactory(new PropertyValueFactory<>("medidasCampanasSalidas"));
        usoCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        precioCol.setCellValueFactory(new PropertyValueFactory<>("precio"));

        // Llamar a la función para cargar datos desde la base de datos
        cargarConexiones();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filtrarConexiones(newValue));

        // Asociar los datos con la tabla
        conexionTable.setItems(listaConexiones);
    
        // Añadir un listener para que los campos se llenen al seleccionar una fila
        conexionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Si hay un elemento seleccionado, llenar los campos de texto
                nombreTextField.setText(newValue.getNombreConexion());
                tipoTextField.setText(newValue.getTipoConexion());
                medidasCorteTextField.setText(newValue.getMedidasCorte());
                medidasCampanasTextField.setText(newValue.getMedidasCampanas());
                medidasSalidasTextField.setText(newValue.getMedidasCorteSalidas());
                medidasCampanasSalidasTextField.setText(newValue.getMedidasCampanasSalidas());
                usoTextField.setText(newValue.getTipo());
                precioTextField.setText(String.valueOf(newValue.getPrecio()));
            }
        });
    }

    // Método para cargar conexiones desde la base de datos
    public void cargarConexiones() {
        listaConexiones.clear();  // Limpiar la lista antes de cargar nuevos datos

        try (Connection connection = DBconexion.getConnection()) {  // Usa DBconexion para obtener la conexión
            System.out.println("Cargando conexiones desde la base de datos...");

            if (connection == null) {
                System.out.println("Error: La conexión es nula");
                return;
            }

            String query = "SELECT * FROM conexiones";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Obtener los datos de cada fila de la tabla de la base de datos
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre_conexion");
                String tipoConexion = resultSet.getString("tipo_conexion");
                String medidasCorte = resultSet.getString("medidas_corte");
                String medidasCampanas = resultSet.getString("medidas_campanas");
                String medidasCorteSalidas = resultSet.getString("medidas_de_corte_de_salidas");
                String medidasCampanasSalidas = resultSet.getString("medidas_de_campanas_de_salidas");
                String tipo = resultSet.getString("tipo_uso");
                double precio = resultSet.getDouble("precio");

                // Crear un objeto Conexiones con los datos obtenidos
                Conexiones conexion = new Conexiones(id, nombre, tipoConexion, medidasCorte,
                        medidasCampanas, medidasCorteSalidas, medidasCampanasSalidas, tipo, precio);

                // Agregar el objeto conexión a la lista observable
                listaConexiones.add(conexion);
            }

            // Configurar la tabla para usar la lista de conexiones
            conexionTable.setItems(listaConexiones);

        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    public void onModificarClick() {
        Conexiones selectedConexion = conexionTable.getSelectionModel().getSelectedItem();
        if (selectedConexion == null) {
            mostrarAlerta("Error", "Seleccione una conexión para modificar", Alert.AlertType.ERROR);
            return;
        }
    
        // Obtener los valores de los campos de texto
        String nombre = nombreTextField.getText();
        String tipo = tipoTextField.getText();
        String medidasCorte = medidasCorteTextField.getText();
        String medidasCampanas = medidasCampanasTextField.getText();
        String medidasSalidas = medidasSalidasTextField.getText();
        String medidasCampanasSalidas = medidasCampanasSalidasTextField.getText();
        String uso = usoTextField.getText();
        double precio = Double.parseDouble(precioTextField.getText());
    
        // Actualizar el objeto Conexiones seleccionado
        selectedConexion.setNombreConexion(nombre);
        selectedConexion.setTipoConexion(tipo);
        selectedConexion.setMedidasCorte(medidasCorte);
        selectedConexion.setMedidasCampanas(medidasCampanas);
        selectedConexion.setMedidasCorteSalidas(medidasSalidas);
        selectedConexion.setMedidasCampanasSalidas(medidasCampanasSalidas);
        selectedConexion.setTipo(uso);
        selectedConexion.setPrecio(precio);
    
        // Actualizar la base de datos con los nuevos valores
        try (Connection connection = DBconexion.getConnection()) {
            String updateQuery = "UPDATE conexiones SET nombre_conexion = ?, tipo_conexion = ?, medidas_corte = ?, " +
                    "medidas_campanas = ?, medidas_de_corte_de_salidas = ?, medidas_de_campanas_de_salidas = ?, tipo_uso = ?, precio = ? " +
                    "WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setString(1, nombre);
            statement.setString(2, tipo);
            statement.setString(3, medidasCorte);
            statement.setString(4, medidasCampanas);
            statement.setString(5, medidasSalidas);
            statement.setString(6, medidasCampanasSalidas);
            statement.setString(7, uso);
            statement.setDouble(8, precio);
            statement.setInt(9, selectedConexion.getId());
    
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                mostrarAlerta("Éxito", "Conexión modificada correctamente", Alert.AlertType.INFORMATION);
    
                // Encontrar el índice del objeto actualizado en la lista
                int index = listaConexiones.indexOf(selectedConexion);
                if (index >= 0) {
                    // Reemplazar el objeto en la lista con el actualizado
                    listaConexiones.set(index, selectedConexion);
                }
    
                // Refrescar la tabla
                conexionTable.refresh();
    
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo modificar la conexión", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            mostrarAlerta("Error SQL", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        // Limpiar los campos de texto
        nombreTextField.clear();
        tipoTextField.clear();
        medidasCorteTextField.clear();
        medidasCampanasTextField.clear();
        medidasSalidasTextField.clear();
        medidasCampanasSalidasTextField.clear();
        usoTextField.clear();
        precioTextField.clear();
    }

    private void filtrarConexiones(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            conexionTable.setItems(listaConexiones);
        } else {
            ObservableList<Conexiones> filtrada = FXCollections.observableArrayList();
            for (Conexiones conexion : listaConexiones) {
                if (conexion.getNombreConexion().toLowerCase().contains(filtro.toLowerCase())) {
                    filtrada.add(conexion);
                }
            }
            conexionTable.setItems(filtrada);
        }
    }
}
