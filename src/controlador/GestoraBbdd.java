package controlador;


import modelo.Chat;
import modelo.Mensaje;
import modelo.Solicitud;
import modelo.Usuario;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**     Hara la conexion con la base de datos y la gestora del programa, es decir este objeto solo trabaja como intermediario o interfaz del programa, sin tener ninguna parte logica en su interior
 *      Al tener la posibilidad de tener solo una sesion abierta en el momento, la conexion con la bbdd se abrirá al abrir el programa y se cerrara al finalizar. Esto significa que en la practica,
 *      en el mundo real, no deberia de funcionar asi
 *       PD: me he dado cuenta a ultima hora que lo mas optimizado posible seria que en vez
 */


//todo Ver metodo mensajes Pendientes


public class GestoraBbdd {

    private static final String BORRAR_USUARIO = "DELETE FROM USUARIO WHERE LOGIN = ";
    private String UBICACION_PROPERTIES = "./src/bbdd/PropiedadesConexion";
    private Properties p = null; // las propiedades con las que se va conectar a la base de datos
    private Connection conexionBaseDatos;


            ///////////////////////////////CONSULTAS

    private String CONSULTA_LOGIN = " Select ID,Login from Usuario where Login = ";
    private String CONSULTA_CONTRASENIA = " and contrasenia= ";
    private String INSERTAR_NUEVO_USUARIO = " INSERT INTO Usuario (Login, Contrasenia) VALUES(";
    private String NUEVA_PETICION = "SELECT IDEmisor FROM UsuarioAmigo WHERE ( ? = IDReceptor AND ? = IDEmisor) " +
            "OR (? = IDEmisor AND ? = IDReceptor)  ";

   private String amigoExiste1 = "SELECT IDEmisor FROM UsuarioAmigo WHERE IDEmisor = ";
    String amigoExiste2= " AND IDReceptor= ";
    String amigoExiste3= " OR IDEmisor = ";
    String amigoExiste5= " AND IDReceptor= ";

    private String INSERTAR_AMISTAD = "INSERT INTO UsuarioAmigo(IDReceptor, IDEmisor) values(";
    private String INSERTAR_SOLICITUD = "INSERT INTO Solicitud(IDEmisor,IDReceptor) values( ";

    private  String VER_SOLICITUDES =
            " SELECT U.Login as Login , U.ID as ID from USUARIO AS U "+
            " INNER JOIN SOLICITUD as S "+
            " ON U.ID = S.IDEmisor " +
            "  WHERE S.IDReceptor= ";
    private String ACEPTAR_SOLICITUD = "EXEC PR_AceptarSolicitud ";
    private String DENEGAR_SOLICITUD = "EXEC PR_DenegarSolicitud ";
    private String GET_AMIGOS = " Select * from FN_AMIGOS (";


    private String VER_CHATS =
            " SELECT CH.ID, CH.Nombre from Chat AS CH " +
            " INNER JOIN ChatUsuario as CU " +
            " ON CU.IDChat = CH.ID " +
            " WHERE CU.IDUsuario = ";

    private String NUEVO_CHAT = " {call PR_CrearNuevoChat(?,?,?,?) }";
    private String HABLAR_CHAT = " exec PR_EnviarMensaje ";
    private String VER_MENSAJES_CHAT = " Select U.Login, M.Mensaje, M.ID FROM Usuario as U " +
                                        " INNER JOIN Mensaje as M " +
                                        " ON M.IDUsuario = U.ID " +
                                        " WHERE M.IDChat = ";
    private String PONER_ULTIMO_MENSAJE= "UPDATE ChatUsuario set IDUltimoMensaje = ";
    private String CONDICION_ULTIMO_MENSAJE = " WHERE IDUsuario=";
    private String CONDICION_ULTIMO_MENSAJE2 = " AND IDChat =";

    private Statement sentencia = null;


    public GestoraBbdd() throws IOException, SQLException {
        p = new Properties();
        p.load(new FileReader((UBICACION_PROPERTIES))); // PROP RUTA DE DONDE ESTA EL ARCHIVO
        conexionBaseDatos = DriverManager.getConnection(p.getProperty("sourceUrl"), p.getProperty("nombre"), p.getProperty("contrasenia"));

       this.sentencia = conexionBaseDatos.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

    }



    private ResultSet hacerConsulta(String consulta) throws SQLException { //dentro de cada metodo especificare el tipo de excepcion
         // mirar los prepared statement
        ResultSet resultado = sentencia.executeQuery(consulta); // resultset.deleteRow() borra
        return resultado;
    }

    private void actualizar(String actualizacion) throws SQLException {
        sentencia.executeUpdate(actualizacion);
    }

    private void ejecutar(String accion) throws SQLException {
        sentencia.execute(accion);
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

        if (resultado.first()) { //si tiene contenido, mirar si mira solo si existe el siguiente o el actual
            usuario = new Usuario(resultado.getString("Login"), resultado.getInt("ID"));

        }
        resultado.close();
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

            resultado = hacerConsulta(CONSULTA_LOGIN+login );

            if ( resultado.next()){ // si hay resultado, construimos el objeto
                usuario = new Usuario(login, resultado.getInt("ID") );
            }

            resultado.close();
        return usuario;

    }

    /**
     *     /**
     * Entradas: Login y contrasenia
     * Precondiciones: conexion con la base de datos
     * Postcondiciones: inserta nuevo usuario en la base de datos
     * @param login
     * @param contrasenia
     * @throws SQLException
     */

    public void nuevoUsuario(String login, String contrasenia) throws SQLException {
        ejecutar(INSERTAR_NUEVO_USUARIO+login+','+contrasenia+')');
    }
    /**
     *     /**
     * Entradas: Login
     * Precondiciones: conexion con la base de datos
     * Postcondiciones: BORRA EL USUARIO DE LA BASE DE DATOS
     * @param login

     * @throws SQLException
     */

    public void borrarUsuario(String login) throws SQLException {
        ejecutar(BORRAR_USUARIO +login);
    }

    /**
     *      * Entradas: Usuario emisor y receptor
     *      * Precondiciones: conexion con la base de datos
     *      * Postcondiciones: si ya no son amigos, y los datos estan bien se anhadira una nueva solicitud de amistad
     * @param emisor
     * @param receptor
     * @return
     * @throws SQLException
     */

    public boolean enviarSolicitud(Usuario emisor, Usuario receptor) throws SQLException { // todo pensar alguna forma de automatizar las preparedStatement

        boolean accionRealizada = false;
        int idRecepto = receptor.getId();
        int idEmisor = emisor.getId();
        ResultSet resultado = null;

        //  LUEGO SI ES IGUAL QUE EL MIO, LUEGO SI YA SOMO AMIGOS

        if (!emisor.equals(receptor)) { // si no son la misma persona
             resultado = hacerConsulta(amigoExiste1 + idEmisor + amigoExiste2 + idRecepto + amigoExiste3 + idRecepto + amigoExiste5 + idRecepto);

            if (!resultado.first()) {  //eso significa que todavia no son amigos
                ejecutar(INSERTAR_SOLICITUD + idEmisor + ',' + idRecepto + ')');
                accionRealizada = true;
            }
        }

        cerrarResultSet(resultado);

        return accionRealizada;

    }

    /**
     *  * Entradas: Usuario
     *      * Precondiciones: conexion con la base de datos
     *      * Postcondiciones: devolvera un resultset con todas las solicitudes
     * @param usuario
     * @return
     * @throws SQLException
     */

    public List<Solicitud> verSolicitudes(Usuario usuario) throws SQLException { // voy a crear el objeto solicitud con los dos ides. Y voy a devolver la lista

       ResultSet rs =  hacerConsulta(VER_SOLICITUDES + usuario.getId() ); // te devolvera el login y el id del solicitante ya que en el programa ya tendremos al receptor
        String nombre;
        int idReceptor, idEmisor;
        List<Solicitud> solicitudes= new ArrayList<Solicitud>();

        if(rs.next()){
            rs.first();
           idEmisor = rs.getInt("ID");
           nombre = rs.getString("Login");
            solicitudes.add(new Solicitud(idEmisor, usuario.getId(), nombre));
           while(rs.next()){
               idEmisor = rs.getInt("ID");
               nombre = rs.getString("Login");
               solicitudes.add(new Solicitud(idEmisor, usuario.getId(), nombre));
            }
        }

        cerrarResultSet(rs);
        return solicitudes;
    }

    /**
     *  * Entradas: idEmisor, idReceptor
     *      * Precondiciones: conexion con la base de datos
     *      * Postcondiciones: inserta en la amistad la solicitud
     * @param solicitud

     * @throws SQLException
     */

    public void aceptarSolicitud(Solicitud solicitud) throws SQLException {
        ejecutar(ACEPTAR_SOLICITUD+ solicitud.getIdEmisor()+','+solicitud.getIdReceptor());
    }

    /**
     *  * Entradas: idEmisor, idReceptor
     *      * Precondiciones: conexion con la base de datos
     *      * Postcondiciones: borra la solicitud
     * @param solicitud
     *
     * @throws SQLException
     */

    public void denegarSolicitud(Solicitud solicitud) throws SQLException {
       ejecutar(DENEGAR_SOLICITUD+ solicitud.getIdEmisor()+','+solicitud.getIdReceptor());
    }

    /**
     *       Entradas: usuario
     *      Precondiciones: conexion con la base de datos
     *      Postcondiciones: resultset con los amigos del usuario
     * @param usuario
     * @return
     * @throws SQLException
     */

    public List<Usuario> getAmigos(Usuario usuario) throws SQLException {

        ResultSet rs =  hacerConsulta( GET_AMIGOS +usuario.getId() +')' );
        List<Usuario> amigos = new ArrayList<Usuario>();
        int id;
        String nombre;

        if(rs.next()){
            rs.first();
            id = rs.getInt("ID");
            nombre = rs.getString("Login");
            amigos.add(new Usuario(nombre, id));
            while(rs.next()){
                id = rs.getInt("ID");
                nombre = rs.getString("Login");
                amigos.add(new Usuario(nombre, id));
            }
        }
        cerrarResultSet(rs);
        return amigos;

    }

    /**
     *      Entradas: idUsuario, idUsuarioElegido, String nombreChat
     *      Precondiciones: conexion con la base de datos
     *      Postcondiciones: inserta en los chats
     *
     * @param idUsuario
     * @param idUsuarioElegido
     * @param nombreChat
     * @return
     * @throws SQLException
     */

    public int crearNuevoChat(int idUsuario, int idUsuarioElegido, String nombreChat) throws SQLException {
        CallableStatement sentencia = conexionBaseDatos.prepareCall(NUEVO_CHAT);
        sentencia.setInt(1,idUsuario);
        sentencia.setInt(2,idUsuarioElegido);
        sentencia.setString(3,nombreChat);
        sentencia.registerOutParameter(4, java.sql.Types.SMALLINT);
        sentencia.execute();

       return sentencia.getInt(4);
    }


    /**
     *      Precondiciones: conexion con la base de datos
     *      Postcondiciones: habla por un Chat dado
     * @param mensaje
     * @param idUsuario
     * @param idChat
     * @throws SQLException
     */
    public void hablarAlChat(String mensaje, int idUsuario, int idChat) throws SQLException {
        ejecutar(HABLAR_CHAT +mensaje+','+idUsuario+ ','+idChat );
    }

    /**
     *
     * Precondiciones: id usuario id idchat, pueden ser nulos
     * Postcondiciones: se devolveran todos los mensajes de un Chat ademas actualizandose el ultimoMensaje leido de ese Chat del usuario
     * @param idChat
     * @param usuario
     * @return Resultset con todos los mensajes del Chat
     * @throws SQLException
     */

    public List<Mensaje> getMensajesChat(int idChat, Usuario usuario) throws SQLException {
        //int idUltimoMensaje;

        List<Mensaje> mensajes = new ArrayList<Mensaje>();
        ResultSet rs = hacerConsulta(VER_MENSAJES_CHAT +idChat);
        String login, mensaje;

        if(rs.next()){
            rs.first();
            login = rs.getString("Login");
            mensaje = rs.getString("Mensaje");
            mensajes.add(new Mensaje(login, mensaje));
            while(rs.next()){
                login = rs.getString("Login");
                mensaje = rs.getString("Mensaje");
                mensajes.add(new Mensaje(login, mensaje));
            }
        }
        cerrarResultSet(rs);

        //resultSet.last(); // ponemos el cursor sobre el utlimo
        //idUltimoMensaje = resultSet.getInt("M.ID");
        //actualizar(PONER_ULTIMO_MENSAJE +idUltimoMensaje +CONDICION_ULTIMO_MENSAJE +usuario.getId() +CONDICION_ULTIMO_MENSAJE2 + idChat);
        return  mensajes;
    }
/*
    private int getNumeroMensajesNoLeidos(Usuario usuario, int idChat){

        ejecutar("Fn_MensajesPendientes");

    }
*/

    //mostrara el nombre de todos los Chat abiertos del usuario, todo numero de mensajes pendientes


    /**
     *       Entradas: Usuario
     *      Precondiciones: conexion con la base de datos
     *      Postcondiciones: lista con todos los chats
     * @param usuario
     * @return
     * @throws SQLException
     */
    public List<Chat> verChatsUsuario(Usuario usuario) throws SQLException { //ConMensajesPendientes
        int idChat = 0;
        String nombreChat = null;
        List<Chat> chats = new ArrayList<Chat>();
        ResultSet rs =  hacerConsulta(VER_CHATS+ usuario.getId());

        if(rs.next()){
            rs.first();
            idChat = rs.getInt("id");
            nombreChat = rs.getString("nombre");
            chats.add(new Chat(idChat, nombreChat));
            while(rs.next()){
                idChat = rs.getInt("id");
                nombreChat = rs.getString("nombre");
                chats.add(new Chat(idChat, nombreChat));
            }
        }
        cerrarResultSet(rs);
        return chats;
    }

/**
 *       Entradas: nada
 *      Precondiciones: conexion con la base de datos
 *      Postcondiciones: la conexion con la base de datos se va a cerrar
        @trows SQLException
 */

    public void cerrarConexion() throws SQLException{

        conexionBaseDatos.close();
    }


    /**
     *       Entradas: ResultSet rs
     *      Precondiciones: conexion con la base de datos
     *      Postcondiciones: Se va a cerrar el resultset
     @trows SQLException
     */

    public void cerrarResultSet(ResultSet rs) throws SQLException {

        if (rs != null){
            rs.close();
        }
    }
}



