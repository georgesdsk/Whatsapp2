package Controlador;

import Modelo.Clases.Usuario;
import Vista.Menu;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Esta clase se va a responsabilizar del manejo de datos en java.
 */
/**
 *      Nombre de la clase: Controlador.Chat
 *      Funcion: Va a controlar toodo el flujo de datos del programa, la administracion de las funcionalidades del programa
 * inicio de sesion, creacion de nuevos usuarios, y el acceso a todas las funcionalidades de los usuarios mediante llamadas
 * a sus metodos
 */

// PODRAS ABRIR LOS CHATS QUE QUIERAS CON QUIEN QUIERAS PARA TENER COSAS PENDIENTE

//Metodo de seleccion de una fila de una tabla, controlada por contador. A que chat desea hablar? con que amigo desea abrir conversacion?
                                                                        //Que solicitud desea aceptar/denegar?
//PARA REGISTRAR UN USUARIO PRIMERO VA A MIRAR SI YA EXISTE UNO


public class Chat {


    private Menu menu;
    private GestoraBbdd bbdd;


    public Chat() throws IOException, SQLException { //TODO CONTROL EXCEPCION
        menu = new Menu();
        GestoraBbdd bbdd= new GestoraBbdd();

    }



    public void enviarSolicitud(Usuario emisor, Usuario receptor){

        boolean seguir = true;
        String login = menu.introducirLoginSolicitud();
        Usuario usuarioSolicitud = null; //el usuario al que va la solicitud

        try {
            usuarioSolicitud = bbdd.usuarioDelLogin(login);
        } catch (SQLException throwables) {
           menu.errorAlMirarLogins();
        }

        while (seguir && usuarioSolicitud== null){ // se repite hasta que el usuario no encuentre a otro usuario o diga que no quire seguir

            if (menu.busquedaSinEfecto()){

                login = menu.introducirLoginSolicitud();
                try {
                    usuarioSolicitud = bbdd.usuarioDelLogin(login);
                } catch (SQLException throwables) {
                    menu.errorAlMirarLogins();
                }

            }else{
                seguir = false;// el usuario se ha desistido
            }
        }

        if (seguir){ // si se ha encontrado al usuario

            try {
                if(!bbdd.enviarSolicitud(emisor, receptor)) { // mira si son amigos ya
                    menu.solicitudAmistadExistente();
                }else{
                    menu.accionRealizada();
                }
            } catch (SQLException throwables) {
                menu.errorEnviandoSolicitud();
            }
        }
    }


    /**
     * Precondiciones: el usuario tiene que existir, si no devolvera un null
     * Postcondiciones: mostrara el listado de solicitudes del usuario dejandole seleccionar la que quiera para cancelarla o aceptarla
     * @param usuario
     */

    public void gestionarSolicitudes(Usuario usuario) throws SQLException {

        ResultSet solicitudes = null;
        boolean hayResultados;
        int solicitudEligida, idEmisor;
        String loginEmisor = null;


        try {
            solicitudes = bbdd.verSolicitudes(usuario); // el receptor de las solicitudes
        } catch (SQLException throwables) {
            menu.errorVerSolicitudes();
        }

        if (hayResultados = solicitudes.first()){// si ha dado resultado la consulta
            do {
                solicitudEligida = menu.imprimirYEligirDeResultSet(solicitudes); // entero de donde esta la solicitud entre todas
                solicitudes.absolute(solicitudEligida);// ponemos el cursor sobre la solicitud elegida

                idEmisor = solicitudes.getInt("ID"); // podria salir como u.idemisor
                loginEmisor = solicitudes.getString("Login");

                if (menu.aceptarOcancelarSolicitud(loginEmisor)){ //si acepta la solicitud
                    bbdd.aceptarSolicitud(idEmisor, usuario.getId());
                }else{
                    bbdd.denegarSolicitud(idEmisor, usuario.getId());
                }

                hayResultados = solicitudes.next();
            }while(menu.seguir() && hayResultados);
        }
        
        if(!hayResultados){
            menu.noHaySolicitudes();
        }
    }


    public void crearNuevoChat(Usuario usuario) throws SQLException {
        //primero con quien desea hacer el chat
        //como se va a llamar el chat
        
        ResultSet usuarios = null;
        boolean hayResultados = false;
        int usuarioElegido = 0, idUsuarioElegido = 0, idChat = 0;
        String nombreChat;

        try {
            usuarios = bbdd.getAmigos(usuario);
        } catch (SQLException throwables) {
            menu.errorViendoAmigos();
        }

        if (hayResultados = usuarios.first()) {// si ha dado resultado la consulta
            usuarioElegido =  menu.imprimirYEligirDeResultSet(usuarios);
            usuarios.absolute(usuarioElegido);//ponemos le cursor sobre el usuario elegido

            nombreChat = menu.introducirNombreChat(); // todo controlar que le nombre del chat sea unico

             idChat = bbdd.crearNuevoChat(usuario.getId(), idUsuarioElegido, nombreChat);
             hablarAlChat(usuario, idChat);
        }else{
            menu.noTienesAmigos();
        }
    }

    public void hablarAlChat(Usuario usuario, int idChat) throws SQLException {

        String mensaje = menu.introducirMensaje();

        bbdd.hablarAlChat(mensaje, usuario.getId(), idChat);
    }

    /**
     * enviara a menu todos los mensajes con su respectivo nombre de usuario de todo el chat
      cuando un usuario se meta en un chat se le mostrara esto
     * Precondiciones: ni usuario ni idchat, pueden ser nulos
     * @param idChat
     * @param usuario
     * @throws SQLException
     */
    //
    public void  verMensajesChat(int idChat, Usuario usuario) throws SQLException {

        menu.escribirMensajesChat(bbdd.getMensajesChat(idChat, usuario));

    }

    //esto se mostrara siempre que un usuario inicie la sesion
    public void verChatsUsuario(Usuario usuario) throws SQLException {

         bbdd.verChatsUsuario(usuario);

    }

    public static void main(String[] args) throws SQLException, IOException {

        GestoraBbdd bbdd= new GestoraBbdd();
        bbdd.nuevoUsuario("'hola3'", "'hola2'");

    }













}


