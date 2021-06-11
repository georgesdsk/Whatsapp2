package Modelo.Clases;


import Controlador.GestoraBbdd;
import Vista.Menu;

import java.sql.SQLException;

/**
 * Esta clase va a responsabilizarse de todo lo que se trata de la entrada del usuario en el sistema
 *
 */
public class InicioSesion {


    private GestoraBbdd bbdd;
    private Menu menu;

    public InicioSesion(GestoraBbdd bbdd) {
        this.bbdd = bbdd; // la bbdd se va a pasar por parametros
        this.menu = new Menu();
    }


    /**
     * Entrada: nada
     * Salida: usuario con el que se haya entrado o null si no se ha entrado
     * Precondiciones: la conexion con base de datos debe de estar activa
     * Postcondiciones: se repetira 3 veces hasta que el usuario introduzca una contraseña y usuario correcto
     */
    public Usuario iniciarSesion() throws SQLException {

        Usuario usuario = null;

        String login = menu.introducirLogin();
        String contrasenhia = menu.introducirContrasenia();

        int contadorFallos = 0;

        try {

            usuario = bbdd.iniciarSesion(login, contrasenhia);

            while (contadorFallos < 3 && usuario == null) {// //todo implementar la forma de salir
                menu.sesionIncorrecta();
                login = menu.introducirLogin();
                contrasenhia = menu.introducirContrasenia();
                contadorFallos++;
            }
        } catch (SQLException exc) {
            menu.errorDeInicio();// todo
        }

        if (contadorFallos < 3) { // si falla 3 veces le da la posibilidad de crear un nuevo usuario
            if (menu.registrarse()) {//todo
                registrarUsuario();
            }
            // si no ha fallado 3 veces es que ha introducido bien el usuario y no va a estar en null


        }
        return usuario;
    }


    /**
     * Entradas:
     * Salidas:
     * PRecondiciones:
     * Postcondiciones:
     */

    public void registrarUsuario(){


        String login = menu.introducirLogin();
        String contrasenia;

        boolean loginCogido = false; // si existe otro usuario, se repite la peticion
        try {
            loginCogido = bbdd.usuarioDelLogin(login) !=null;
            while (loginCogido) {  // se va a repetir mientras existan usuario con el mismo nombre, es decir nuilo
                login = menu.introducirLogin();
                menu.loginExiste();
                loginCogido = bbdd.usuarioDelLogin(login) !=null; //
            }
        } catch (SQLException throwables) {
            menu.errorAlMirarLogins();
        }

        contrasenia = menu.validarConstrasenia();

        try {
            bbdd.nuevoUsuario(login, contrasenia);
        } catch (SQLException throwables) {
            menu.errorDeRegistro();
        }
    }





}
