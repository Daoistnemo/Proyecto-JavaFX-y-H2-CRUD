package com.owl.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Dump {
    public static void main(String[] args) {
        // Database connection details
        String dbUrl = "jdbc:h2:file:C:/Users/User/Documents/QA testing/Proyecto - My little Bag/owl/merlin/src/main/java/com/owl/Utils/midb";
        String usuario = "sa";
        String contrasena = "";

        // Dump file path
        String dumpFile = "C:/Users/User/Documents/QA testing/Proyecto - My little Bag/owl/merlin/src/main/java/com/owl/Utils/dump.sql";

        try (Connection conn = DriverManager.getConnection(dbUrl, usuario, contrasena);
             Statement stmt = conn.createStatement()) {
            
            // Generate dump using SCRIPT command
            stmt.execute("SCRIPT TO '" + dumpFile + "'");
            
            System.out.println("Dump generado exitosamente en: " + dumpFile);
        } catch (SQLException e) {
            System.err.println("Error al generar el dump: " + e.getMessage());
            e.printStackTrace();
        }
    }
}