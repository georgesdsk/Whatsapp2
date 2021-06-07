import java.util.Scanner;

public class Menu {



    private String MENSAJE_REGISTRO = "Introduce un login unico para tu usuario";
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
    º
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


    public void escribir(String dato){
        System.out.println(dato);
    }

    public void loginExiste(){
        System.out.println(LOGIN_EXISTE);
    }




}
