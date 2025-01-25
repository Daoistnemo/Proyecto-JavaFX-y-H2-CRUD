package com.owl.Utils;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class iconUtils {
    private static Image sharedIcon;

    public static void loadAndSetStageIcon(Stage stage, String iconPath) {
        try {
            Image icon = new Image(iconUtils.class.getResourceAsStream(iconPath));
            
            if (icon.isError()) {
                System.err.println("Error al cargar el ícono: " + icon.getException());
                return;
            }

            // Almacenar el ícono compartido
            sharedIcon = icon;

            // Configurar el ícono en el stage
            stage.getIcons().add(icon);

        } catch (Exception e) {
            System.err.println("Excepción al cargar el ícono: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Image getSharedIcon() {
        return sharedIcon;
    }
}