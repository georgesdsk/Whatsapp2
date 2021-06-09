package Controlador;

import Controlador.Clases.Usuario;
import Controlador.Conexion.basico.GestoraBbdd;
import Vista.Menu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Esta clase se va a responsabilizar del manejo de datos en java.
 */
/**
 *      Nombre de la clase: Controlador.Gestora
 *      Funcion: Va a controlar toodo el flujo de datos del programa, la administracion de las funcionalidades del programa
 * inicio de sesion, creacion de nuevos usuarios, y el acceso a todas las funcionalidades de los usuarios mediante llamadas
 * a sus metodos
 */

// PODRAS ABRIR LOS CHATS QUE QUIERAS CON QUIEN QUIERAS PARA TENER COSAS PENDIENTE

//Metodo de seleccion de una fila de una tabla, controlada por contador. A que chat desea hablar? con que amigo desea abrir conversacion?
                                                                        //Que solicitud desea aceptar/denegar?
//PARA REGISTRAR UN USUARIO PRIMERO VA A MIRAR SI YA EXISTE UNO



public class Gestora {


    private Menu menu;
    private GestoraBbdd bbdd;


    public Gestora() {
        menu = new Menu();
        GestoraBbdd bbdd= new GestoraBbdd();

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

            return usuario;
        }
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

        boolean loginCogido = bbdd.usuariodelLogin(login) !=null; // si existe otro usuario, se repite la peticion

        while (loginCogido) {  // se va a repetir mientras existan usuario con el mismo nombre, es decir nuilo
            login = menu.introducirLogin();
            menu.loginExiste();
            loginCogido = bbdd.usuariodelLogin(login) !=null; //
        }

        contrasenia = menu.validarConstrasenia();

        try {
            bbdd.nuevoUsuario(login, contrasenia);
        } catch (SQLException throwables) {
            menu.errorDeRegistro();
        }
    }



    public void enviarSolicitud(Usuario emisor, Usuario receptor){

        boolean seguir = true;
        String login = menu.introducirLoginSolicitud();
        Usuario usuarioSolicitud = bbdd.usuariodelLogin(login); //el usuario al que va la solicitud

        while (seguir && usuarioSolicitud== null){ // se repite hasta que el usuario no encuentre a otro usuario o diga que no quire seguir

            if (menu.busquedaSinEfecto()){

                login = menu.introducirLoginSolicitud();
                usuarioSolicitud = bbdd.usuariodelLogin(login);

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

        try {
            solicitudes = bbdd.verSolicitudes(usuario);
        } catch (SQLException throwables) {
            menu.errorVerSolicitudes();
        }

        if (hayResultados = solicitudes.next()){// si ha dado resultado la consulta

            do {

                menu.imprimirYEligirDeResultSet(solicitudes);
            }while(menu.seguir() && );



        }



    }









}


