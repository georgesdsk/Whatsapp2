package Vista;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {


    private static final String MENSAJE_AMIGOS =" Elige al amigo para chatear:" ;
    private static final String MENSAJE_ELIGIR_CHAT = "Elige el chat en el que quiere meterse:";
    private static final String NO_TIENE_CHATS = "No tienes chats abiertos, crea alguno para chatear";
    private static final String NO_TIENE_AMIGOS = "No tiene amigos, anhade laguno para chatear con el";
    private String MENSAJE_REGISTRO = "Introduce un login unico para tu usuario";
    private String MENSAJE_INTRODUCIR_CONTRASENIA = "Introduce la contrasenhia de su usuario: ";
    private String LOGIN_EXISTE = "Este login ya esta en uso";
    private String INTRODUCIR_CONTRASENIA = "Introduce una contrasenia";
    private String CONTRASENIA_DIFERENTE = "Las contrasenias son diferentes, vuelve a introducirlas";
    private String REPETIR_CONTRASENIA = "Introduce la contrasenia de nuevo";
    private Scanner sc;


    public Menu() {
        sc = new Scanner(System.in);
    }


    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Poscondicion: Devolvera un String mayor que 0
     * @return login
     */

    public String introducirLogin(){

        String login;

        do {
            System.out.println(MENSAJE_REGISTRO);
            login =  sc.nextLine();
        }while(login.length() == 0);

        return login;
    }

    public String validarConstrasenia(){

        String contrasenia;
        String contrasenia2;
        boolean incorrecto = false;


          do {
              do {
                  System.out.println(INTRODUCIR_CONTRASENIA);
                  contrasenia = sc.nextLine();
              } while (contrasenia.length() == 0);

              do {
                  System.out.println(REPETIR_CONTRASENIA);
                  contrasenia2 = sc.nextLine();
              } while (contrasenia2.length() == 0);

              if (!contrasenia.equals(contrasenia2)){
                  incorrecto = true;
                  System.out.println(CONTRASENIA_DIFERENTE);
              }
          }while(incorrecto);

        return contrasenia;
    }


    public String introducirContrasenia(){
        System.out.println(MENSAJE_INTRODUCIR_CONTRASENIA);
        return sc.nextLine();
    }

    /**
     *
     *
     * @param rs
     * @return int que indica la posicion del chat en el resulset;
     * @throws SQLException
     */


    public int mostrarYEligirChat(ResultSet rs) throws SQLException {

        String nombreChat = null;
        int contChats = 1;
        int chat = 0; // el numero que indica el chat en el que se quiere meter el usuario
        System.out.println(MENSAJE_ELIGIR_CHAT);

            while(rs.next()){
                nombreChat = rs.getString("CH.Nombre");
                contChats++;
                System.out.println(contChats+") "+nombreChat);
            }

            chat = validarNumeroEntre(0,contChats);

        return chat ;// si devuelve 0 es que no tiene todavia chats


    }

    public int mostrarYEligirAmigo(ResultSet rs) throws SQLException {


        String nombreAmigo = null;
        int contAmigos = 1;
        int amigo = 0; // el numero que indica el chat en el que se quiere meter el usuario
        System.out.println(MENSAJE_AMIGOS);



            while(rs.next()){
                nombreAmigo = rs.getString("CH.Nombre");
                contAmigos++;
                System.out.println(contAmigos+") "+nombreAmigo);
            }

            amigo = validarNumeroEntre(0,contAmigos);

        return amigo ;// si devuelve 0 es que no tiene todavia chats
    }


 //rs.absolute(5); // moves the cursor to the fifth row of rs
   //    rs.updateString("NAME", "AINSWORTH"); // updates the

    public void escribir(String dato){
        System.out.println(dato);
    }

    public void loginExiste(){
        System.out.println(LOGIN_EXISTE);
    }


    public boolean registrarse() {
        return false;
    }

    public void errorDeInicio() {
    }

    public void sesionIncorrecta() {
    }

    public void errorDeRegistro() {
    }

    public String introducirLoginSolicitud() {
        return "";
    }

    public boolean busquedaSinEfecto() {
        return true;
    }

    public void errorEnviandoSolicitud() {
    }

    public void solicitudAmistadExistente() {
    }

    public void accionRealizada() {
    }

    public void errorVerSolicitudes() {
    }

    public boolean seguir() {
        return true;
    }



    public boolean aceptarOcancelarSolicitud(String login) {

        return true;
    
    }

    public void noHaySolicitudes() {
    }

    public void errorViendoAmigos() {
    }

    public void noTienesAmigos() {
    }

    public String introducirNombreChat() {
        return "true";
    }

    public String introducirMensaje() {
        return "d";
    }

    public void escribirMensajesChat(ResultSet mensajesChat) {
    }

    public void errorAlMirarLogins() {
    }


    private int validarNumeroEntre(int min, int max){

        int numero = 0;
        boolean fallo = false;

        do{
            System.out.println("Introduce un numero de "+min +" a "+ max );

            try {
                numero = Integer.parseInt(sc.nextLine());
                fallo = false;

            }catch(IllegalArgumentException e){
                fallo = true;
                System.out.println("fallo");
            }

        }while(fallo || numero > max || numero < min);

        return numero;

    }

    private boolean afirmativo(){

        boolean afirmativo = false;

        System.out.println("Si su respuesta es si, pulsa 1, si no, pulsa 2");

        if(validarNumeroEntre(1,2) == 1){
            afirmativo = true;

        }

        return afirmativo;

    }
}
