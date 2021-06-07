/**
 * Esta clase se va a responsabilizar del manejo de datos en java.
 */

//PARA REGISTRAR UN USUARIO PRIMERO VA A MIRAR SI YA EXISTE UNO
public class Gestora {


    private Menu menu;


    public Gestora() {
        menu = new Menu();
    }



    /**
     * Entradas:
     * Salidas:
     * PRecondiciones:
     * Postcondiciones:
     */
    public int registrarUsuario(){

        String login;

        do {
            login = menu.introducirLogin(); // ya que la conexion con la bbdd la tiene que hacer la gestora, hago la comprobacion aqui
            menu.loginExiste();
        }while();//FN_IDdelNombre(@Login VarChar(30) != null




    }



}
