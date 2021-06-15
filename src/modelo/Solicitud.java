package modelo;


import java.util.Objects;

/**
 * Esta clase va a guardar la info de las solicitudes
 *
 */
public class Solicitud {

    private int idEmisor, idReceptor;
    private String loginEmisor;

    public Solicitud(int idEmisor, int idReceptor, String loginEmisor) {
        this.idEmisor = idEmisor;
        this.idReceptor = idReceptor;
        this.loginEmisor = loginEmisor;
    }

    public String getLoginEmisor() {
        return loginEmisor;
    }

    public void setLoginEmisor(String loginEmisor) {
        this.loginEmisor = loginEmisor;
    }

    public int getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(int idEmisor) {
        this.idEmisor = idEmisor;
    }

    public int getIdReceptor() {
        return idReceptor;
    }

    public void setIdReceptor(int idReceptor) {
        this.idReceptor = idReceptor;
    }


    @Override
    public String toString() {
        return loginEmisor;
    }
/*
    @Override
    public boolean equals(Object o) { // ya que va a estar al 100% controlado por el programa no compuebo que sea de la misma clase

        Solicitud solicitud = (Solicitud) o;
        return idEmisor == solicitud.idEmisor && idReceptor == solicitud.idReceptor;
    }
*/

}
