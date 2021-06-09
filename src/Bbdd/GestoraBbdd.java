package Bbdd;

import Modelo.Clases.Usuario;

import java.sql.*;
import java.util.Properties;

public class GestoraBbdd {

    private Properties p = null;
    private Connection conexionBaseDatos = null;


    private String CONSULTA_LOGIN = " Select * from Usuario where login=";
    private String CONSULTA_CONTRASENIA = " and contrasenhia=";
    private String INSERTAR_NUEVO_USUARIO = "INSERT INTO USUARIO VALUES(";
    private String NUEVA_PETICION = "EXEC FN_EnviarPeticionAmistad(";
    private  String VER_SOLICITUDES =
            "SELECT U.Login, U.ID from USUARIO AS U"+
            "INNER JOIN SOLICITUDES as S"+
            "ON U.ID = S.IDEmisor" +
            "  WHERE S.IDReceptor=";

    private String ACEPTAR_SOLICITUD = "EXEC AceptarSolicitud(";
    private String DENEGAR_SOLICITUD = "EXEC DenegarSolicitud(";
    private String GET_AMIGOS = "SELECT IDEmisor FROM UsuarioAmigo where IDReceptor= "; // se podria solucionas con preparedStatment
    private String GET_AMIGOS_JOIN = " JOIN SELECT IDReceptor FROM UsuarioAmigo where IDEmisor=";
    private String NUEVO_CHAT = "exec PR_CrearNuevoChat(";
    private String HABLAR_CHAT = "exec PR_EnivarMensaje(";

    public GestoraBbdd() {
        p = new Properties();

       // p.load(new FileReader((PROPERTIES))); // PROP RUTA DE DONDE ESTA EL ARCHIVO
       // conexionBaseDatos = DriverManager.getConnection(p.getProperty("sourceUrl"),p.getProperty("nombre"), p.getProperty("contrasenia") );
    }

    private ResultSet hacerConsulta(String consulta) throws SQLException { //dentro de cada metodo especificare el tipo de excepcion

        Connection conexionBaseDatos = Conexion.getConexion(); // utilizo mi clase Controlador.Conexion, donde esta inicializada la bbdd, tan solo da el acceso
        Statement sentencia = conexionBaseDatos.createStatement(); // mirar los prepared statement
        ResultSet resultado = sentencia.executeQuery(consulta); // resultset.deleteRow() borra
        sentencia.close();//todo que cosas hay que cerrar???
        resultado.close();

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


        resultado = hacerConsulta(CONSULTA_LOGIN+ login + CONSULTA_CONTRASENIA + contrasenhia +')' );

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
            resultado = hacerConsulta(CONSULTA_LOGIN+login +')' );

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

       return hacerConsulta(VER_SOLICITUDES + usuario.getId()+')' );// te devolvera el login y el id del solicitante ya que en el programa ya tendremos al receptor

    }

    public void aceptarSolicitud(int idEmisor, int  idReceptor) throws SQLException {

        hacerConsulta(ACEPTAR_SOLICITUD+ idEmisor+','+idReceptor+')');

    }

    public void denegarSolicitud(int idEmisor, int  idReceptor) throws SQLException {

        hacerConsulta(DENEGAR_SOLICITUD+ idEmisor+','+idReceptor+')');

    }


    public ResultSet getAmigos(Usuario usuario) throws SQLException {

        return hacerConsulta(GET_AMIGOS+usuario.getId()+GET_AMIGOS_JOIN+usuario.getId()+')' );

    }


    public int crearNuevoChat(int id, int idUsuarioElegido, String nombreChat) throws SQLException {

       return hacerConsulta(NUEVO_CHAT+id+','+idUsuarioElegido+','+nombreChat+')').getInt(1) ;//todo SALIDA MALA, tiene que devolver un entero
    }


    public void hablarAlChat(String mensaje, int idUsuario, int idChat) throws SQLException {

        hacerConsulta(HABLAR_CHAT +mensaje+','+idUsuario+ ','+idChat+')' );

    }
}



