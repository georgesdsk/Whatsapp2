package Controlador.Clases;


/**
 *      Nombre de la clase: Usuario
 *      Funcionalidad: objeto que guarda y administra la informacion de otros usuarios seguidos, es decir el envio de solicitud
 *  a otros a usuarios, aceptar o cancelar solicitudes, hablar en un char ya abierto o crear uno nuevo
 *      La contraseñá la va a guardar y gestionar esta clase, aunque lo correcto seria que se gestionara mediante
 *      la BBDD
 */

public class Usuario {

    private String login;// para el acceso al usuario y para que otros usuarios lo encuentren
    private int id;

    public Usuario(String login, int id) {
        this.login = login;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }
}
