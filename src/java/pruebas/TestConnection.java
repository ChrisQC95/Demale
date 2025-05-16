package pruebas;

import ConexionDB.DataBaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestConnection {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            // 1. Obtener conexión
            conn = DataBaseConnection.getConnection();
            System.out.println("¡Conexión exitosa!");
            
            // 2. Crear statement y ejecutar consulta de prueba
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT 1 + 1 AS result");
            
            // 3. Procesar resultados
            if (rs.next()) {
                System.out.println("Resultado de prueba: " + rs.getInt("result"));
            }
            
            // 4. Prueba adicional: mostrar información de la BD
            System.out.println("\nInformación de la Base de Datos:");
            System.out.println("Producto: " + conn.getMetaData().getDatabaseProductName());
            System.out.println("Versión: " + conn.getMetaData().getDatabaseProductVersion());
            
        } catch (Exception ex) {
            System.err.println("Error durante la prueba: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            // 5. Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DataBaseConnection.closeConnection();
            } catch (Exception ex) {
                System.err.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
    }
}