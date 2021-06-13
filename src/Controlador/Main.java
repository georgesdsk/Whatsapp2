package Controlador;

import Modelo.Clases.Chat;
import Modelo.Clases.InicioSesion;
import Modelo.Clases.Usuario;
import Vista.Menu;

import java.io.IOException;
import java.sql.SQLException;

public class Main {


    public static void main(String[] args) throws SQLException, IOException {


        int eleccionMenuInicio = 2, eleccionMenuChat = 0;
        Usuario usuarioIniciado = null;
        GestoraBbdd bbdd = new GestoraBbdd();
        InicioSesion inicioSesion = new InicioSesion(bbdd);
        Menu menu = new Menu();
        Chat chat = new Chat(bbdd);


        do {// flujo de toda la aplicacion
            while (eleccionMenuInicio == 2) {
                eleccionMenuInicio = menu.menuInicio();
                if (eleccionMenuInicio == 1) {
                    usuarioIniciado = inicioSesion.iniciarSesion(menu.introducirLogin(), menu.introducirContrasenia());

                    if (usuarioIniciado == null) { // si no se ha podido iniciar la sesion
                        eleccionMenuInicio = 2; // se pone al dos para que se vuelva a repetir el proceso
                    }
                } else if (eleccionMenuInicio == 2) {
                    inicioSesion.registrarUsuario(menu.introducirLogin());

                }

            }
            ;// si es 3 se tiene que salir

            if (eleccionMenuInicio == 1) {

               eleccionMenuInicio = accionesMenuChat(menu, usuarioIniciado, chat);

            }


        } while (eleccionMenuInicio < 3);

        //cerrra las conexiones

    }
        public static int accionesMenuChat( Menu menu, Usuario usuarioIniciado, Chat chat){

            int eleccionMenuChat = menu.menuChat(usuarioIniciado.getLogin());// le pasamos el login para que se pueda comunicar bien con el usuario
            int comunicacionMain = 1; // si es 1

            switch (eleccionMenuChat){
                case 1 -> {
                    try {
                        chat.verChatsUsuario(usuarioIniciado);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace(); // ver si tiene que enviar algun mensaje
                    }
                }
                case 2 -> {
                    try {
                        chat.crearNuevoChat(usuarioIniciado);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                case 3->chat.enviarSolicitud(usuarioIniciado, menu.introducirLogin()); // todo mirar el control de excepciones
                case 4 -> {
                    try {
                        chat.gestionarSolicitudes(usuarioIniciado);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                case 5-> comunicacionMain = 2; // salir del chat
                case 6-> comunicacionMain = 3;// salir de la aplicacion
            }

            return comunicacionMain;

        }



}
