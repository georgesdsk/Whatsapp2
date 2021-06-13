package Vista;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {


    private static final String MENSAJE_AMIGOS =" Elige al amigo para chatear:" ;
    private static final String MENSAJE_ELIGIR_CHAT = "Elige el chat en el que quiere meterse:";
    private static final String NO_TIENE_CHATS = "No tienes chats abiertos, crea alguno para chatear";
    private static final String NO_TIENE_AMIGOS = "No tiene amigos, anhade laguno para chatear con el";
    private static final String ESCRIBIR = "Escribe(F para salir):";
    private static final String MENSAJE_SOLICITUDES = "Elige una solicitud para aceptarla/cancelarla como amigo:";
    private static final String MENSAJE_BIENVENIDA = "Bienvenido a Whatsapp2, un chat gratuito, mejor que la competencia, que desea hacer?";
    private static final String INICIO_O_REGISTRO = "1) Iniciar sesion \n 2) Registarse \n 3)para salir ";
    private static final String SESION_INCORRECTA = "Login o la contrasenia incorrectos";
    private static final String MENSAJE_INICIO_CHAT = "Sesion iniciada por:";
    private static final String MENSAJE_MENU_CHAT = "Que desea hacer? \n 1) Ver chats activos \n 2) Nuevo chat \n 3) Buscar amigos \n 4) Ver solicitudes de amistad \n 5) Salir al menu principal \n 6) Salir de la aplicacion";
    private String MENSAJE_REGISTRO_LOGIN = "Este es el menu de registro, tiene que introducir un login unico para tu usuario";
    private String MENSAJE_INICIO_SESION =" Introduce su Login:";
    private String MENSAJE_INTRODUCIR_CONTRASENIA = "Introduce su contrasenhia: ";
    private String LOGIN_EXISTE = "Este login ya esta en uso";
    private String INTRODUCIR_CONTRASENIA = "Introduce una contrasenia";
    private String CONTRASENIA_DIFERENTE = "Las contrasenias son diferentes, vuelve a introducirlas";
    private String REPETIR_CONTRASENIA = "Introduce la contrasenia de nuevo";
    private Scanner sc;


    public  Menu() {
        sc = new Scanner(System.in);
    }


    public int menuInicio(){

        System.out.println(MENSAJE_BIENVENIDA);
        System.out.println(INICIO_O_REGISTRO);
        return validarNumeroEntre(1,3);
    }

    public int menuChat(String nombre){
        System.out.println(MENSAJE_INICIO_CHAT +nombre);
        System.out.println(MENSAJE_MENU_CHAT);
        return validarNumeroEntre(1,6);

    }





    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Poscondicion: Devolvera un String mayor que 0
     * @return login
     */

    private String introducirTexto(){

        String texto;

        do {
            texto = "'"+sc.nextLine()+"'" ;
        }while(texto.length() == 0);

        return texto;
    }

    public String registrarLogin(){

        System.out.println(MENSAJE_REGISTRO_LOGIN);
        return introducirTexto();

    }

    public String introducirLogin(){
        System.out.println(MENSAJE_INICIO_SESION);
        return introducirTexto();
    }

    public String validarConstrasenia(){

        String contrasenia;
        String contrasenia2;
        boolean incorrecto = false;
        
          do {
                  System.out.println(INTRODUCIR_CONTRASENIA);
                  contrasenia = introducirTexto();

                  System.out.println(REPETIR_CONTRASENIA);
                  contrasenia2 = introducirTexto();


              if (!contrasenia.equals(contrasenia2)){
                  incorrecto = true;
                  System.out.println(CONTRASENIA_DIFERENTE);
              }
          }while(incorrecto);

        return contrasenia;
    }


    public String introducirContrasenia(){
        System.out.println(MENSAJE_INTRODUCIR_CONTRASENIA);
        return introducirTexto();
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


    /**
     * 
     * @param rs
     * @return int que indica la posicion del amigo en el resultset
     * @throws SQLException
     */
    public int mostrarYEligirAmigo(ResultSet rs) throws SQLException {


        String nombreAmigo = null;
        int contAmigos = 1;
        int amigo = 0; // el numero que indica el chat en el que se quiere meter el usuario
        System.out.println(MENSAJE_AMIGOS);
        
            while(rs.next()){
                nombreAmigo = rs.getString("Login");
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


    public boolean quiereRegistrarse() {
        return false;
    }

    public void errorDeInicio() {
    }

    public void sesionIncorrecta() {
        System.out.println(SESION_INCORRECTA);
    }

    public void errorDeRegistro() {
    }

    public String introducirLoginSolicitud() {
        return "";
    }

    public boolean seguirBusqueda() {
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
        System.out.println(NO_TIENE_AMIGOS);
    }

    public String introducirNombreChat() {
        return "true";
    }

    public String introducirMensaje() {
        System.out.println(ESCRIBIR);
        return sc.nextLine();
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

    public void noTieneChats() {
        System.out.println(NO_TIENE_CHATS);
    }


    public void chatNoTieneMensajes() {
    }

    public int mostraYEligirSolicitud(ResultSet solicitudes) throws SQLException {

        String nombreAmigo = null;
        int contSolicitudes = 1;
        int solicitud = 0; //
        System.out.println(MENSAJE_SOLICITUDES);

        while(solicitudes.next()){
            nombreAmigo = solicitudes.getString("Login");
            contSolicitudes++;
            System.out.println(contSolicitudes+") "+nombreAmigo);
        }

        solicitud = validarNumeroEntre(0,contSolicitudes);

        return solicitud ;// si devuelve 0 es que no tiene todavia chats


    }
}
