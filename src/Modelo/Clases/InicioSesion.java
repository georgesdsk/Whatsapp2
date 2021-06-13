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
    public Usuario iniciarSesion(String login, String contrasenhia ) throws SQLException {

        Usuario usuario = null;

        int contadorFallos = 0;

        try {
            usuario = bbdd.iniciarSesion(login, contrasenhia); // si el usuario da nulo es que no existe

            while (contadorFallos < 2 && usuario == null) {// //todo implementar la forma de salir
                contadorFallos++;
                menu.sesionIncorrecta();
                login = menu.introducirLogin();
                contrasenhia = menu.introducirContrasenia();
                usuario = bbdd.iniciarSesion(login, contrasenhia);
            }
        } catch (SQLException exc) {
            System.out.println( exc.getErrorCode());
            System.out.println( exc.getSQLState());
            System.out.println( exc.toString());
            System.out.println( exc.getMessage());
            menu.errorDeInicio();// todo
        }

        return usuario;
    }


    /**
     * Entradas:
     * Salidas:
     * PRecondiciones:
     * Postcondiciones:
     */

    public void registrarUsuario(String login){



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
