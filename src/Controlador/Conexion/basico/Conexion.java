package Controlador.Conexion.basico;

import java.sql.*;

public class Conexion {
    public static Connection getConexion() throws SQLException {

        // Cadena de conexión

       // Properties propiedadesConexion = new java.util.Properties();
        //propiedadesConexion.put("useUnicode","true");
        //propiedadesConexion.put("characterEncoding","utf8");

        String url="jdbc:sqlserver://localhost;databaseName=ACDAT;user=amazon;password=allavoy;";
        // Connection es una interface, no una clase
        Connection conexionBaseDatos = DriverManager.getConnection(url); //, propiedadesConexion
        
        return conexionBaseDatos; 
    }
    public static void cerrar ( ResultSet rs ) throws SQLException {
        rs.close();
    }
    public static void cerrar ( Statement st ) throws SQLException {
        st.close();
    }
    public static void cerrar (Connection con) throws SQLException {
        con.close();
    }
}
