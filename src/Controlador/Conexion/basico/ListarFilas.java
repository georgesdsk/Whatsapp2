package Controlador.Conexion.basico;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;



public class ListarFilas {
  public static void main(String[] args) {


    try {
      String miSelect = "select * from Usuario ;";

      Connection conexionBaseDatos = Conexion.getConexion(); // utilizo mi clase Controlador.Conexion, donde esta inicializada la bbdd, tan solo da el acceso
      Statement sentencia = conexionBaseDatos.createStatement();

      ResultSet nombresProductos = sentencia.executeQuery(miSelect);
      
      // Mostrar los datos del ResultSet
      System.out.println("Productos                          ->  PVP");
      System.out.println("------------------------------------------");
      while (nombresProductos.next())
    	  System.out.println(nombresProductos.getString("Nombre")+ " -> " + 
    		nombresProductos.getFloat("PVP") + " euros");

      // Cerrar ResultSet
      Conexion.cerrar(nombresProductos);
      // Cerrar sentencia
      Conexion.cerrar(sentencia);
      // Cerrar conexion
      Conexion.cerrar(conexionBaseDatos);
    }
      catch (SQLException sqle) {
      System.err.println(sqle);
    } 
  }
}
