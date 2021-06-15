package controlador;

import modelo.InicioSesion;
import modelo.Usuario;
import vista.Menu;

import java.io.IOException;
import java.sql.SQLException;

public class Main {


    public static void main(String[] args) throws SQLException, IOException {


        int eleccionMenuInicio = 2, eleccionMenuChat = 0;
        Usuario usuarioIniciado = null;
        GestoraBbdd bbdd = new GestoraBbdd();
        InicioSesion inicioSesion = new InicioSesion(bbdd);
        Menu menu = new Menu();
        Messenger messenger = new Messenger(bbdd);

        bbdd.verSolicitudes(new Usuario("", 2));



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
               eleccionMenuInicio = accionesMenuChat(menu, usuarioIniciado, messenger);
            }
        } while (eleccionMenuInicio < 3);

        bbdd.cerrarConexion();

    }
        public static int accionesMenuChat( Menu menu, Usuario usuarioIniciado, Messenger messenger){

            int eleccionMenuChat = menu.menuChat(usuarioIniciado.getLogin());// le pasamos el login para que se pueda comunicar bien con el usuario
            int comunicacionMain = 1; // si es 1

            switch (eleccionMenuChat){
                case 1 -> {
                    try {
                        messenger.verChatsUsuario(usuarioIniciado);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace(); // ver si tiene que enviar algun mensaje
                    }
                }
                case 2 -> {
                    try {
                        messenger.crearNuevoChat(usuarioIniciado);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace(); //todo quitar
                    }
                }
                case 3-> messenger.enviarSolicitud(usuarioIniciado, menu.introducirLogin()); // todo mirar el control de excepciones
                case 4 -> {
                    try {
                        messenger.gestionarSolicitudes(usuarioIniciado);
                    } catch (SQLException throwables) {
                        menu.errorViendoAmigos();
                    }
                }
                case 5-> comunicacionMain = 2; // salir del messenger
                case 6-> comunicacionMain = 3;// salir de la aplicacion
            }

            return comunicacionMain;
        }
}
