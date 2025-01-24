package com.owl.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconexion {

    private static final String URL = "jdbc:h2:file:C:/Users/User/Documents/QA testing/Proyecto - My little Bag/owl/merlin/src/main/java/com/owl/Utils/midb";
    private static final String USER = "sa"; // Usuario
    private static final String PASSWORD = ""; // Contraseña

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
   
}
