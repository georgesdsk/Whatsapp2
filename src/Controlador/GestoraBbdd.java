package Controlador;


import Modelo.Clases.Usuario;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**     Hara la conexion con la base de datos y la gestora del programa, es decir este objeto solo trabaja como intermediario o interfaz del programa, sin tener ninguna parte logica en su interior
 *      Al tener la posibilidad de tener solo una sesion abierta en el momento, la conexion con la bbdd se abrirá al abrir el programa y se cerrara al finalizar. Esto significa que en la practica,
 *      en el mundo real, no deberia de funcionar asi
 *
 */


//todo Ver metodo mensajes Pendientes
// todo EliminarUsuario(ver si petan los mensajes de los chats) actualizarUsuario

public class GestoraBbdd {

    private String UBICACION_PROPERTIES = "./src/bbdd/PropiedadesConexion";
    private Properties p = null; // las propiedades con las que se va conectar a la base de datos
    private Connection conexionBaseDatos;


            ///////////////////////////////CONSULTAS

    private String CONSULTA_LOGIN = " Select * from Usuario as U where U.Login = ";
    private String CONSULTA_CONTRASENIA = " and contrasenia=";
    private String INSERTAR_NUEVO_USUARIO = " INSERT INTO Usuario (Login, Contrasenia) VALUES(";
    private String NUEVA_PETICION = "EXEC FN_EnviarPeticionAmistad(";
    private  String VER_SOLICITUDES =
            "SELECT U.Login as Login , U.ID as ID from USUARIO AS U"+
            "INNER JOIN SOLICITUDES as S"+
            "ON U.ID = S.IDEmisor" +
            "  WHERE S.IDReceptor=";
    private String ACEPTAR_SOLICITUD = "EXEC AceptarSolicitud(";
    private String DENEGAR_SOLICITUD = "EXEC DenegarSolicitud(";
    private String GET_AMIGOS = "  SELECT UA.IDEmisor as ID, U.Login as Login FROM UsuarioAmigo" +
                                    "INNER JOIN USUARIO AS U" +
                                    "ON U.ID = UA.IDEMISOR" +
                                    " where IDReceptor= "; // se podria solucionas con preparedStatment

    private String GET_AMIGOS_JOIN = " JOIN SELECT UA.IDReceptor as ID, U.Login as Login FROM UsuarioAmigo" +
                                        "INNER JOIN USUARIO AS U" +
                                        "ON U.ID = UA.IDReceptor" +
                                        " where IDEmisor= ";

    private String NUEVO_CHAT = "exec PR_CrearNuevoChat(";
    private String HABLAR_CHAT = "exec PR_EnivarMensaje(";
    private String VER_MENSAJES_CHAT = "Select U.Login, M.Mensaje, M.ID FROM Usuario as U" +
                                        "INNER JOIN Mensaje as M" +
                                        "ON M.IDUsuario = U.ID" +
                                        "WHERE M.IDChat = ";
    private String PONER_ULTIMO_MENSAJE= "UPDATE ChatUsuario set IDUltimoMensaje = ";
    private String CONDICION_ULTIMO_MENSAJE = " WHERE IDUsuario=";
    private String CONDICION_ULTIMO_MENSAJE2 = " AND IDChat =";

    public GestoraBbdd() throws IOException, SQLException {
        p = new Properties();
        p.load(new FileReader((UBICACION_PROPERTIES))); // PROP RUTA DE DONDE ESTA EL ARCHIVO
        conexionBaseDatos = DriverManager.getConnection(p.getProperty("sourceUrl"), p.getProperty("nombre"), p.getProperty("contrasenia"));

    }



    private ResultSet hacerConsulta(String consulta) throws SQLException { //dentro de cada metodo especificare el tipo de excepcion
        Statement sentencia = conexionBaseDatos.createStatement(); // mirar los prepared statement
        ResultSet resultado = sentencia.executeQuery(consulta); // resultset.deleteRow() borra
        //sentencia.close();//todo cerrar al finalizar el programa
        //resultado.close();
        return resultado;
    }

    private void actualizar(String actualizacion) throws SQLException {
        Statement sentencia = conexionBaseDatos.createStatement();
        sentencia.executeUpdate(actualizacion);
    }

    private void ejecutar(String accion) throws SQLException {
        Statement sentencia = conexionBaseDatos.createStatement();
        sentencia.execute(accion);
    }

    private ResultSet ejecutarConResultado(String accion) throws SQLException {
        Statement sentencia = conexionBaseDatos.createStatement();
         return sentencia.executeQuery(accion);
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

        resultado = hacerConsulta(CONSULTA_LOGIN+ login + CONSULTA_CONTRASENIA + contrasenhia  );

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


    public Usuario usuarioDelLogin(String login) throws SQLException {// se va a utilizar para ver si existe el mismo nombre, hablar a un amigo, borrar amigo
        //hanbria que hacer otro metodo para ver si existe otro usuario para ahorrar recursos, pero este tampoco carga tanto el sistema devolviendo un objeto de dos variables

        ResultSet resultado = null;
        Usuario usuario = null;

            resultado = hacerConsulta(CONSULTA_LOGIN+login +')' );

            if (resultado.next()){ // si hay resultado, construimos el objeto
                usuario = new Usuario(login, resultado.getInt("ID") );
            }

        return usuario;

    }

    public void nuevoUsuario(String login, String contrasenia) throws SQLException { //todo controlar la excepcion

        ejecutar(INSERTAR_NUEVO_USUARIO+login+','+contrasenia+')');

    }

//todo _____________________________________________________________
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

    /**
     *
     * Precondiciones: ni usuario ni idchat, pueden ser nulos
     * Postcondiciones: se devolveran todos los mensajes de un chat ademas actualizandose el ultimoMensaje leido de ese chat del usuario
     * @param idChat
     * @param usuario
     * @return Resultset con todos los mensajes del chat
     * @throws SQLException
     */

    public ResultSet getMensajesChat( int idChat, Usuario usuario) throws SQLException {

        int idUltimoMensaje;

        ResultSet resultSet = hacerConsulta(VER_MENSAJES_CHAT +idChat);
        resultSet.last(); // ponemos el cursor sobre el utlimo
        idUltimoMensaje = resultSet.getInt("M.ID");
        actualizar(PONER_ULTIMO_MENSAJE +idUltimoMensaje +CONDICION_ULTIMO_MENSAJE +usuario.getId() +CONDICION_ULTIMO_MENSAJE2 + idChat);
        return  resultSet;
    }
/*
    private int getNumeroMensajesNoLeidos(Usuario usuario, int idChat){

        ejecutar("Fn_MensajesPendientes");

    }
*/

    //mostrara el nombre de todos los chat abiertos del usuario, todo numero de mensajes pendientes

    public ResultSet verChatsUsuario(Usuario usuario) throws SQLException { //ConMensajesPendientes

        int idChat = 0;
        String nombreChat = null;
        ResultSet resultSet =  hacerConsulta("SELECT CH.Nombre, CH.ID from Chat AS CH" +
                    "INNER JOIN ChatUsuario as CU " +
                    "ON CU.IDChat = CH.ID");
        /*
        while(resultSet.next()){
            idChat = resultSet.getInt("CH.ID");
            nombreChat = resultSet.getString("CH.Nombre");
        }
        */
        return resultSet;
    }
}



