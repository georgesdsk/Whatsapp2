package modelo;


/**
Clase que guarda la info esencial de cada usuario
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




    @Override
    public String toString() {
        return "Usuario{" +
                "login='" + login + '\'' +
                ", id=" + id +
                '}';
    }


    @Override
    public boolean equals(Object o) {

        boolean resultado = false;

       if ( o instanceof Usuario){
           if (((Usuario) o).getLogin().equals(this.getLogin())){
               resultado = true;
           }
       }

       return resultado;

    }


}
