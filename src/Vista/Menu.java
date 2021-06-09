package Vista;

import java.sql.ResultSet;
import java.util.Scanner;

public class Menu {



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



    public int imprimirYEligirDeResultSet(ResultSet rs){



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
    }

    public void errorDeInicio() {
    }

    public void sesionIncorrecta() {
    }

    public void errorDeRegistro() {
    }

    public String introducirLoginSolicitud() {
    }

    public boolean busquedaSinEfecto() {
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
    }



    public boolean aceptarOcancelarSolicitud(String login) {


    
    }

    public void noHaySolicitudes() {
    }

    public void errorViendoAmigos() {
    }

    public void noTienesAmigos() {
    }

    public String introducirNombreChat() {
    }
}
