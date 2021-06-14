package vista;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {


    private static final String MENSAJE_AMIGOS ="   \tElige al amigo para chatear:" ;
    private static final String MENSAJE_ELIGIR_CHAT = " \n\tElige el chat en el que quiere meterse:";
    private static final String NO_TIENE_CHATS = "  No tienes chats abiertos, crea alguno para chatear";
    private static final String NO_TIENE_AMIGOS = " No tiene amigos, anhade laguno para chatear con el";
    private static final String ESCRIBIR = "   \t Escribe(F para salir):";
    private static final String MENSAJE_SOLICITUDES = " \tElige una solicitud para aceptarla/cancelarla como amigo:";
    private static final String MENSAJE_BIENVENIDA = "╔═══════════════════════════════════════════════════════════════════════════════════════╗" +
                                                    " \n║ Bienvenido a Whatsapp2, un chat gratuito, mejor que la competencia, que desea hacer?  ║\n" +
                                                    "╚═══════════════════════════════════════════════════════════════════════════════════════╝";
    private static final String INICIO_O_REGISTRO = " \t 1) Iniciar sesion \n\t 2) Registarse \n\t 3) Salir \n ";
    private static final String SESION_INCORRECTA = "   Login o la contrasenia incorrectos";
    private static final String MENSAJE_INICIO_CHAT = "\n ╔══════════════════════════════════════════════════════════════════════════════════════╗" +
            "\n     SESION INICIADA POR: ";
    private static final String MENSAJE_MENU_CHAT = "\n\tQue desea hacer? \n\t 1) Ver chats activos \n\t 2) Nuevo chat \n\t 3) Buscar amigos \n\t 4) Ver solicitudes de amistad \n\t 5) Salir al menu principal \n\t 6) Salir de la aplicacion\n" +
            "╚═══════════════════════════════════════════════════════════════════════════════════════╝\n";
    private static final String USUARIO_NOT_FOUND = "   No se ha encontrado usuario con el siguiente login, deseas repetir la busqueda?";
    private static final String AMISTAD_EXISTENTE = "   Ya sois amigos";
    private static final String ACCION_REALIZADA = "    ACCION REALIZADA";
    private static final String ERROR_ENVIANDO_SOLICITUD = "    Error, Ya sois amigos o ya hay una solicitud pendiente";
    private static final String ERROR_AMIGOS = "    ERROR VIENDO AMIGOS";
    private static final String CHAT_SIN_MENSAJES = "   El chat no tiene mensajes, escribe el primero";
    private static final String NO_TIENE_SOLICITUDES = "    No tiene solicitudes de amistad, algo esta haciendo mal";
    private static final String INTRODUCIR_NOMBRE_CHAT = "  Escriba un nombre unico para el Chat";
    private static final String SALIENDO_MENU_CHAT = "  Saliendo al menu del CHAT";
    private static final String SALIENDO_MENU_PRINCIPAL = " Saliendo al menu PRINCIPAL";
    private static final String ERROR = "   Ha habido un error con la base de datos, disculpa las molestias";
    private String MENSAJE_REGISTRO_LOGIN = "   Este es el menu de registro, tiene que introducir un login unico para tu usuario";
    private String MENSAJE_INICIO_SESION ="\n\tIntroduce su Login:";
    private String MENSAJE_INTRODUCIR_CONTRASENIA = "\tIntroduce su contrasenhia: ";
    private String LOGIN_EXISTE = "Este login ya esta en uso";
    private String INTRODUCIR_CONTRASENIA = "Introduce una contrasenia";
    private String CONTRASENIA_DIFERENTE = "Las contrasenias son diferentes, vuelve a introducirlas";
    private String REPETIR_CONTRASENIA = "Introduce la contrasenia de nuevo";
    private String LOGIN = "login";
    private String NOMBRE = "nombre";
    private String SOLICITUD = "solicitud";

    private Scanner sc;


    public  Menu() {
        sc = new Scanner(System.in);
    }

    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion:mostrara el menu y Devolvera un entero con la eleccion del menu
     * @return int eleccion
     */

    public int menuInicio(){
        System.out.println(MENSAJE_BIENVENIDA);
        System.out.println(INICIO_O_REGISTRO);
        return validarNumeroEntre(1,3);
    }

    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion:mostrara el menu y Devolvera un entero con la eleccion del menu
     * @return int eleccion
     */

    public int menuChat(String nombre){
        System.out.println(MENSAJE_INICIO_CHAT +nombre);
        System.out.println(MENSAJE_MENU_CHAT);
        return validarNumeroEntre(1,6);

    }

    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Devolvera un String  entre comillas simples
     * @return texto String
     */

    private String introducirTexto(){

        String texto;

        do {
            texto = "'"+sc.nextLine()+"'" ;
        }while(texto.length() == 0);

        return texto;
    }

    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Devolvera un String entre comillas simples
     * @return texto String
     */


    public String registrarLogin(){

        System.out.println(MENSAJE_REGISTRO_LOGIN);
        return introducirTexto();

    }

    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Devolvera un String  entre comillas simples
     * @return texto String
     */


    public String introducirLogin(){
        System.out.println(MENSAJE_INICIO_SESION);
        return introducirTexto();
    }

    /**
     * Entradas:
     * Precondiciones: Scanner abierto
     * Postcondicion: Devolvera un String de la contrasenia entre comillas simples. La entrada se va a efectuar dos veces
     * @return texto String
     */

    public String validarConstrasenia(){

        String contrasenia;
        String contrasenia2;
        boolean correcto = true;
        
          do {
                  System.out.println(INTRODUCIR_CONTRASENIA);
                  contrasenia = introducirTexto();

                  System.out.println(REPETIR_CONTRASENIA);
                  contrasenia2 = introducirTexto();

              correcto = true;
              if (!contrasenia.equals(contrasenia2)){
                  correcto = false;
                  System.out.println(CONTRASENIA_DIFERENTE);
              }
          }while(!correcto);

        return contrasenia;
    }

    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Devolvera un String  entre comillas simples
     * @return texto String
     */


    public String introducirContrasenia(){
        System.out.println(MENSAJE_INTRODUCIR_CONTRASENIA);
        return introducirTexto();
    }

    /**
     *
     * Entradas: resultSet
     * Precondiciones: rs no puede ser nulo
     * Postcondicion:
     * @param rs
     * @return int que indica la posicion del RESULTADO necesario en el resulset;
     * @throws SQLException
     */

    public int mostrarYEligirResultset(ResultSet rs,String columna, String mensaje) throws SQLException {

        String nombre = null;
        int contResultados = 0;
        int resultado = 0; // el numero que indica el resultado en el que se quiere meter el usuario
        System.out.println(mensaje);

        if(rs.next()){
            rs.first();
            nombre = rs.getString(columna);
            contResultados++;
            System.out.println(contResultados+") "+nombre);

            while(rs.next()){
                nombre = rs.getString(columna);
                contResultados++;
                System.out.println(contResultados+") "+nombre);
            }

            resultado = validarNumeroEntre(1,contResultados);
        }

        return resultado ;// si devuelve 0 es que no tiene todavia chats

    }

    /*** Entradas: resultSet
     * Precondiciones: rs no puede ser nulo
     * Postcondicion:
     * @param rs
     * @return int posicion del resultado necesario
     * @throws SQLException
     */
    public int mostrarYEligirAmigo(ResultSet rs) throws SQLException {
        return mostrarYEligirResultset(rs,LOGIN,MENSAJE_AMIGOS);
    }

    /*** Entradas: resultSet
     * Precondiciones: rs no puede ser nulo
     * Postcondicion:
     * @param solicitudes
     * @return int posicion del resultado necesario
     * @throws SQLException
     */

    public int mostraYEligirSolicitud(ResultSet solicitudes) throws SQLException {
        return mostrarYEligirResultset(solicitudes, LOGIN, MENSAJE_SOLICITUDES);
    }

    /*** Entradas: resultSet
     * Precondiciones: rs no puede ser nulo
     * Postcondicion:
     * @param resultSet
     * @return int posicion del resultado necesario
     * @throws SQLException
     */

    public int mostrarYEligirChat(ResultSet resultSet) throws SQLException {
        return  mostrarYEligirResultset(resultSet,NOMBRE, MENSAJE_ELIGIR_CHAT);
    }
    public void escribirMensajesChat(ResultSet mensajesChat) throws SQLException {

        if(mensajesChat.next()){
            mensajesChat.first();
            String mensaje = mensajesChat.getString("Mensaje"); // todo escribir los nombres de los usuarios al lado
            System.out.println(mensaje);
        }

        while(mensajesChat.next()){
            String mensaje = mensajesChat.getString("Mensaje");
            System.out.println(mensaje);
        }
    }


    /**
     * Entradas: int numero minimo posible, int max maximo posible
     * Precondiciones: ninguna
     * Postcondicion: devolvera un int en el rango de min y max
     * @return int eleccion
     */
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

        System.out.println("1 SI / 2 NO");

        if(validarNumeroEntre(1,2) == 1){
            afirmativo = true;

        }

        return afirmativo;

    }



    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */

    public void loginExiste(){
        System.out.println(LOGIN_EXISTE);
    }



    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */


    public void errorDeInicio() {
        System.out.println(ERROR);
    }
    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */


    public void sesionIncorrecta() {
        System.out.println(SESION_INCORRECTA);
    }


    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */


    public void errorDeRegistro() {
        System.out.println(ERROR);
    }


    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */

    public boolean seguirBusqueda() {
        System.out.println(USUARIO_NOT_FOUND);
        return afirmativo();
    }

    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */

    public void errorEnviandoSolicitud() {
        System.out.println(ERROR_ENVIANDO_SOLICITUD);
    }
    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */
    public void solicitudAmistadExistente() {
        System.out.println(AMISTAD_EXISTENTE);
    }
    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */
    public void accionRealizada() {
        System.out.println(ACCION_REALIZADA);
    }
    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */
    public void errorVerSolicitudes() {
        System.out.println(ERROR);
    }

    public boolean seguir() {
        return true;
    }

    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Boolean que afirma o niega  una solicitud
     *
     */

    public boolean aceptarOcancelarSolicitud(String login) {

        System.out.println("Desea anhadir o denegar la solicitud de "+login+'?');
        return afirmativo();

    }

    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */
    public void noHaySolicitudes() {

        System.out.println(NO_TIENE_SOLICITUDES);
    }

    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */
    public void errorViendoAmigos() {
        System.out.println(ERROR_AMIGOS);
    }
    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */
    public void noTienesAmigos() {
        System.out.println(NO_TIENE_AMIGOS);
    }
    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */
    public String introducirNombreChat() {
        System.out.println(INTRODUCIR_NOMBRE_CHAT);
        return introducirTexto();
    }
    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */
    public String introducirMensaje() {
        System.out.println(ESCRIBIR);
        return introducirTexto();
    }
    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */
    public void errorAlMirarLogins() {
        System.out.println(ERROR);
    }
    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */
    public void noTieneChats() {
        System.out.println(NO_TIENE_CHATS);
    }
    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */
    public void chatNoTieneMensajes() {
        System.out.println(CHAT_SIN_MENSAJES);
    }
    /**
     * Entradas: nada
     * Precondiciones: ninguna
     * Postcondicion: Escribe en pantalla la info necesaria
     *
     */
    public void saliendoMenuChat() { System.out.println(SALIENDO_MENU_CHAT); }


}
