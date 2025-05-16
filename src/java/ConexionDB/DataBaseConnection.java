/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConexionDB;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Chris
 */
public class DataBaseConnection {
    public DataBaseConnection(){
    }
    private static final Logger LOGGER = Logger.getLogger(DataBaseConnection.class.getName());
    private static Connection connection;
    
    static {
        try {
            // Cargar el driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Error al cargar el driver JDBC", ex);
        }
    }
    
    public static Connection getConnection() {
        if (connection == null) {
            try (InputStream input = DataBaseConnection.class.getClassLoader()
                    .getResourceAsStream("config.properties")) {
                
                Properties props = new Properties();
                props.load(input);
                
                connection = DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
                );
                
                LOGGER.info("Conexión establecida exitosamente");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Error al establecer conexión", ex);
            }
        }
        return connection;
    }
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
                LOGGER.info("Conexión cerrada correctamente");
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al cerrar conexión", ex);
        }
    }
}
