package com.owl.Utils;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.RunScript;

public class H2DatabaseMigration {

    // URL de la base de datos H2. 
    // En este caso, 'midb' es el archivo de base de datos H2
    private static final String DB_URL = "jdbc:h2:file:C:/Users/User/Documents/QA testing/Proyecto - My little Bag/owl/merlin/src/main/java/com/owl/Utils/midb"; 
    
    // Usuario y contraseña predeterminados para la conexión
    private static final String USER = "sa"; // Usuario predeterminado para H2
    private static final String PASSWORD = ""; // Contraseña predeterminada (vacía por defecto)
    
    // Archivo donde se guardará el dump (copia) de la base de datos
    // El archivo dump.sql se guardará en esta ubicación
    private static final String dump = "C:/Users/User/Documents/QA testing/Proyecto - My little Bag/owl/merlin/src/main/java/com/owl/Utils/dump.sql"; 

    /**
     * Método para exportar la base de datos a un archivo SQL.
     * Genera un archivo .sql que contiene la estructura y los datos de la base de datos.
     */
    public static void exportDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            // Generar el dump utilizando el comando SCRIPT TO
            // El dump será guardado en la ruta especificada en dump
            stmt.execute("SCRIPT TO '" + dump + "'");
            
            System.out.println("Dump generado exitosamente en: " + dump);
        } catch (SQLException e) {
            System.err.println("Error al generar el dump: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método para importar los datos desde el archivo SQL a la base de datos.
     * Este método ejecuta un script SQL que contiene la estructura y los datos
     * para crear y llenar las tablas en la base de datos.
     */
    public static void importDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            // Usamos RunScript.execute para ejecutar el script SQL que contiene la base de datos
            // El archivo dump.sql se lee y ejecuta en la base de datos
            RunScript.execute(conn, new FileReader(dump));
            System.out.println("Base de datos importada exitosamente.");
        } catch (Exception e) {
            // Si ocurre un error durante la importación, se imprime el error
            e.printStackTrace();
        }
    }

    /**
     * Método principal donde se llama a la exportación o importación según se desee
     * Descomenta una de las líneas a continuación para ejecutar la acción deseada.
     */
    public static void main(String[] args) {
        // Llamamos al método exportDatabase para exportar la base de datos
        // Descomenta la línea para exportar la base de datos a un archivo SQL
        //exportDatabase();
        
        // Si deseas importar la base de datos, descomenta la siguiente línea
        importDatabase();
    }
}
