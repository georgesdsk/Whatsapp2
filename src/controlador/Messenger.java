package controlador;

import controlador.GestoraBbdd;
import modelo.Chat;
import modelo.Mensaje;
import modelo.Solicitud;
import modelo.Usuario;
import vista.Menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase se va a responsabilizar del manejo de datos de todos los chats en java.
 */
/**
 *      Nombre de la clase: Modelo.Clases.Messenger
 *      Funcion: Va a controlar toodo el flujo de datos del programa, la administracion de las funcionalidades del programa
 * inicio de sesion, creacion de nuevos usuarios, y el acceso a todas las funcionalidades de los usuarios mediante llamadas
 * a sus metodos
 */

// PODRAS ABRIR LOS CHATS QUE QUIERAS CON QUIEN QUIERAS PARA TENER COSAS PENDIENTE


public class Messenger {

    private Menu menu;
    private GestoraBbdd bbdd;

    public Messenger(GestoraBbdd bbdd) {
        menu = new Menu();
        this.bbdd =bbdd ; //la instancia para comunicarme con ella

    }


    /**
     *   /**
     * Entradas: Usuario emisor, String loginReceptor de la solicitud
     * Precondiciones: el usuario no puede ser nulo
     * Postcondicion: se enviara la solicitud al receptor si no son la misma persona, y si ya no son amigos o hay una solicitud pendiente entre ellos
     * @param emisor
     * @param loginReceptor
     */


    public boolean enviarSolicitud(Usuario emisor, String loginReceptor){

        boolean seguir = true;
        Usuario receptor = null; //el usuario al que va la solicitud

        try {
            receptor = bbdd.usuarioDelLogin(loginReceptor);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
           menu.errorAlMirarLogins();
        }

        while (seguir && receptor== null){ // se repite hasta que el usuario no encuentre a otro usuario o diga que no quire seguir

            if (menu.seguirBusqueda()){

                loginReceptor = menu.introducirLogin();
                try {
                    receptor = bbdd.usuarioDelLogin(loginReceptor);
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
                throwables.printStackTrace();
                menu.errorEnviandoSolicitud();

            }
        }else{
           menu.saliendoMenuChat();
        }
        return seguir;// si todo se ha hecho correctamente devolvera true
    }


    /**
     *  Entradas: Usuario del que quiera saber la info
     * Precondiciones: el usuario tiene que existir, si no devolvera un null
     * Postcondiciones: mostrara el listado de solicitudes del usuario dejandole seleccionar la que quiera para cancelarla o aceptarla
     * @param usuario
     */

    public void gestionarSolicitudes(Usuario usuario) throws SQLException {

        List<Solicitud> solicitudes = null;
        boolean hayResultados = true;
        Solicitud solicitudEligida;
        int idEmisor;
        String loginEmisor = null;

        solicitudes = bbdd.verSolicitudes(usuario); // el receptor de las solicitudes

        while (solicitudes.size()>0 && menu.seguir()){
            solicitudEligida = menu.mostraYEligirSolicitud(solicitudes); // entero de donde esta la solicitud entre todas

            if (menu.aceptarOcancelarSolicitud(solicitudEligida.getLoginEmisor())){ //si acepta la solicitud
                bbdd.aceptarSolicitud(solicitudEligida);
            }else{
                bbdd.denegarSolicitud(solicitudEligida);
            }
            solicitudes = bbdd.verSolicitudes(usuario); // volvemos a ver las solicitudes actulizadas
        }


        if(solicitudes.size()==0){ // lo he hecho de esta forma para que sea recursivo hasta que el usuario se canse o no haya solicitudes
            menu.noHaySolicitudes();
        }
    }

    /**
     *  Entradas: Usuario al que quiere crear un hcat
     * Precondiciones: el usuario tiene que existir, si no devolvera un null
     * Postcondiciones: si el usuario tiene amigos, eligira uno de ellos para crear el Chat
     * @param usuario
     */

    public void crearNuevoChat(Usuario usuario) throws SQLException {
        //primero con quien desea hacer el Chat
        //como se va a llamar el Chat

        List<Usuario> amigos = new ArrayList<Usuario>();
        boolean hayResultados = false;
        Usuario usuarioElegido;
        String nombreChat;
        int idChat;

        try {
            amigos = bbdd.getAmigos(usuario);
        } catch (SQLException throwables) {
            menu.errorViendoAmigos();
        }

        if (amigos.size()>0) {// si ha dado resultado la consulta
            usuarioElegido =  menu.mostrarYEligirAmigo(amigos);

            nombreChat = menu.introducirNombreChat(); // todo controlar que el nombre del Chat sea unico
            try{
                idChat = bbdd.crearNuevoChat(usuario.getId(), usuarioElegido.getId(), nombreChat);
              hablarAlChat(usuario, idChat);
            }catch(SQLException exception){
                menu.errorCreandoChat();

            }

        }else{
            menu.noTienesAmigos();
        }
    }


    /**
     * Postcondiciones: Se hablara a el Chat pasado por paramtros por parte del usuario
     * Precondiciones :Escribe en un Chat hasta que pulse F para salir
     * @param usuario
     * @param idChat
     * @throws SQLException
     */

    private void hablarAlChat(Usuario usuario, int idChat) throws SQLException {

        boolean salir = false;
        String mensaje;

        while(!salir){

            mensaje = menu.introducirMensaje();

            if (mensaje.equals("'F'")){
                salir = true;
            }else{
                bbdd.hablarAlChat(mensaje, usuario.getId(), idChat);
            }
        }
    }

    /**
     * enviara a menu todos los mensajes con su respectivo nombre de usuario de todo el Chat
      cuando un usuario se meta en un Chat se le mostrara esto
     * Precondiciones: id usuario ni idchat, pueden ser nulos
     * @param idChat
     * @param usuario
     * @throws SQLException
     */
    //
    private void  verMensajesChat(int idChat, Usuario usuario) throws SQLException {// le paso el usuario por si en el futuro implemento la funcion de ver los mensajes pendientes

        List<Mensaje> mensajes =  bbdd.getMensajesChat(idChat, usuario);

        if (mensajes.size() >0){
            menu.escribirMensajesChat(mensajes);
        }else{
            menu.chatNoTieneMensajes();
        }



    }

    //esto se mostrara siempre que un usuario inicie la sesion
    /**
     *  Entradas: Usuario del que quiera saber los chats
     * Precondiciones: el usuario tiene que existir
     * Postcondiciones: mostrara el listado de chats del usuario dejandole seleccionar la que quiera para entrar adentro y mirar los mensajes y escribir
     * @param usuario
     */

    public void verChatsUsuario(Usuario usuario) throws SQLException {


        Chat chat;
        int idChat;
        List<Chat> chats = bbdd.verChatsUsuario(usuario);

          if (chats.size()>0){
              chat = menu.mostrarYEligirChat(chats);

             verMensajesChat(chat.getIdChat(), usuario);
             hablarAlChat(usuario,chat.getIdChat());
              
          }else{
              menu.noTieneChats();
          }
    }
}


