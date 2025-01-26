package com.owl.Controllers;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;

import com.owl.Models.Conexiones;
import com.owl.Utils.ConexionesUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConexionSelectionDialog {

    public static Conexiones mostrarDialogoDeSeleccion() {
        // Crear la ventana emergente (Dialog)
        Dialog<Conexiones> dialog = new Dialog<>();
        dialog.setTitle("Seleccionar Conexión");
        dialog.setHeaderText("Seleccione una conexión para agregar al pedido:");

        // Crear la lista de conexiones
        ObservableList<Conexiones> conexiones = ConexionesUtils.cargarConexiones("SELECT * FROM conexiones");
        ListView<Conexiones> listaConexiones = new ListView<>(conexiones);
        listaConexiones.setCellFactory(param -> new ListCell<Conexiones>() {
            @Override
            protected void updateItem(Conexiones item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getNombreConexion());
            }
        });

        // Crear el contenedor principal para el diálogo
        VBox vbox = new VBox(listaConexiones);
        vbox.setPrefSize(300, 200);
        dialog.getDialogPane().setContent(vbox);

        // Botones del diálogo
        ButtonType okButtonType = new ButtonType("Seleccionar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

        // Manejar el evento del botón OK
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return listaConexiones.getSelectionModel().getSelectedItem();
            }
            return null;
        });

        // Mostrar el diálogo
        dialog.showAndWait();

        // Retornar la conexión seleccionada o null si no se seleccionó ninguna
        return dialog.getResult();
    }
}
