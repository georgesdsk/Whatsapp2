package Controlador.Conexion.basico;

import Controlador.Clases.Usuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestoraBbdd {


    private String CONSULTA_LOGIN = " Select * from Usuario where login=";
    private String CONSULTA_CONTRASENIA = " and contrasenhia=";
    private String INSERTAR_NUEVO_USUARIO = "INSERT INTO USUARIO VALUES(";
    private String NUEVA_PETICION = "EXEC FN_EnviarPeticionAmistad(";
    private  String VER_SOLICITUDES =
            "SELECT U.Login, U.ID from USUARIO AS U"+
            "INNER JOIN SOLICITUDES as S"+
            "ON U.ID = S.IDEmisor" +
            "  WHERE S.IDReceptor=";

    public GestoraBbdd() {
    }

    private ResultSet hacerConsulta(String consulta) throws SQLException { //dentro de cada metodo especificare el tipo de excepcion

        Connection conexionBaseDatos = Conexion.getConexion(); // utilizo mi clase Controlador.Conexion, donde esta inicializada la bbdd, tan solo da el acceso
        Statement sentencia = conexionBaseDatos.createStatement(); // mirar los prepared statement
        ResultSet resultado = sentencia.executeQuery(consulta); // resultset.deleteRow() borra

        return resultado;

    }



    /**
     * PRecondiciones:
     * Postcondiciones: devolvera el usuario o null si no se ha encontrado
     * @param  login,  (String)
     * @return el usuario o null si no se ha encontrado
     */



    public Usuario iniciarSesion(String login, String contrasenhia) throws SQLException {// ha habido un error con el inicio de sesion

        ResultSet resultado = null;
        Usuario usuario = null;


        resultado = hacerConsulta(CONSULTA_LOGIN+ login + CONSULTA_CONTRASENIA + contrasenhia);

        if (resultado.next()) { //si tiene contenido, mirar si mira solo si existe el siguiente o el actual

            usuario = new Usuario(resultado.getString("Login"), resultado.getInt("ID"));

        }
        return usuario;
    }


    /**
     * PRecondiciones: el login no puede ser nulo
     * Postcondiciones: devolvera el usuario o null si no se ha encontrado
     *
     * @param login, (String)
     * @return el usuario del login  o null si no se ha encontrado
     */


    public Usuario usuariodelLogin(String login) {// se va a utilizar para ver si existe el mismo nombre, hablar a un amigo, borrar amigo
        //hanbria que hacer otro metodo para ver si existe otro usuario para ahorrar recursos, pero este tampoco carga tanto el sistema devolviendo un objeto de dos variables

        ResultSet resultado = null;
        Usuario usuario = null;


        try {
            resultado = hacerConsulta(CONSULTA_LOGIN+login );

            if (resultado.next()){ // si hay resultado, construimos el objeto
                usuario = new Usuario(login, resultado.getInt("ID") );
            }

        } catch (SQLException throwables) {//todo no se encuentra el siguiente login
            throwables.printStackTrace();
        }

        return usuario;

    }

    public void nuevoUsuario(String login, String contrasenia) throws SQLException { //todo controlar la excepcion

        hacerConsulta(INSERTAR_NUEVO_USUARIO+login+','+contrasenia+')');

    }


    public boolean enviarSolicitud(Usuario emisor, Usuario receptor) throws SQLException {

        ResultSet resultado =  hacerConsulta(NUEVA_PETICION+emisor.getId()+','+receptor.getId()+')');// todo ver que devuelve esta funcion, si es una tabla o un numero

        return  resultado.getInt(1) ==1; //si el resultado es 1 devuelve true

    }

    public ResultSet verSolicitudes(Usuario usuario) throws SQLException {

       return hacerConsulta(VER_SOLICITUDES + usuario.getId());// te devolvera el login y el id del solicitante ya que en el programa ya tendremos al receptor

    }
}
