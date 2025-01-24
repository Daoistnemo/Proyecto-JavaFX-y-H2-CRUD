package com.owl.Controllers;

import com.owl.Models.Conexiones;
import com.owl.Utils.ConexionesUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

        // Filtrar por nombre de conexión
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
        listaConexiones.addAll(ConexionesUtils.cargarConexiones("SELECT * FROM conexiones"));
        conexionTable.setItems(listaConexiones);
    }

    @FXML
    public void onModificarClick() {
        Conexiones selectedConexion = conexionTable.getSelectionModel().getSelectedItem();
        if (selectedConexion == null) {
            ConexionesUtils.mostrarAlerta("Error", "Seleccione una conexión para modificar", Alert.AlertType.ERROR);
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
        ConexionesUtils.actualizarConexion(selectedConexion);
    
        ConexionesUtils.mostrarAlerta("Éxito", "Conexión modificada correctamente", Alert.AlertType.INFORMATION);

        // Refrescar la lista de conexiones
        cargarConexiones();

        limpiarCampos();
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
        ObservableList<Conexiones> conexionesFiltradas = ConexionesUtils.filtrarConexiones(filtro, listaConexiones);
        conexionTable.setItems(conexionesFiltradas);
    }
}
