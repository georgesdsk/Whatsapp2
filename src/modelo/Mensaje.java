package modelo;

public class Mensaje {

    private String login, mensaje;

    public Mensaje(String login, String mensaje) {
        this.login = login;
        this.mensaje = mensaje;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return login+": "+mensaje+".";
    }
}
