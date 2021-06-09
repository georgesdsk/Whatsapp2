/*

package Controlador.Conexion.basico;
import conexionBD.*;
import java.sql.*;

public class EjecutandoSentencias {
	  public static void main(String[] args) {

		    try {

                        // Create a connection through the DriverManager
                        Connection conexion = ConexionConProperties.getConexion((byte)1);

			Statement sentencia = conexion.createStatement();
                //Usamos un Execute simple
			sentencia.execute("Use Ejemplos");
                 // Ahora un executeQuery, que devuelve un ResulSet
			ResultSet empleados = sentencia.executeQuery ("SELECT ID, Nombre FROM empleados");
		 // Show data from ResultSet
			  while (empleados.next())
				  System.out.println(empleados.getInt("ID")+" "+empleados.getString("Nombre"));
		      // cerrar todo
                      MiConexion.cerrar(empleados);
                      MiConexion.cerrar(sentencia);
		      MiConexion.cerrar(conexion);
		    }
		      catch (SQLException sqle) {
		      System.err.println(sqle);
		    } 
		  }
}
*/