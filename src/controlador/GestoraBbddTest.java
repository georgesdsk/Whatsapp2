package controlador;

import modelo.Solicitud;
import modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


class GestoraBbddTest {
/*
    GestoraBbdd bbdd =  new GestoraBbdd();
    Usuario usuario1 = new Usuario("raul",102);
    Usuario usuario2 = new Usuario("1",104);
    //Usuario usuario3 = new Usuario("hola3",5);
    String NOMBRE1 = "'raul'";
    String NOMBRE2 = "'1'";
    String NOMBRE_INVENTADO = "'raul3'";
    String CONTRASENIA_INVENTADO = "'raul3'";
    String CONTRASENIA1 = "'raul'";
    String CONTRASENIA2 = "'1'";

    GestoraBbddTest() throws IOException, SQLException {
    }

    //  GestoraBbdd bbdd;
    /*
    @BeforeEach
    void setUp() throws IOException, SQLException {

            GestoraBbdd bbdd = new GestoraBbdd();
    }
    NO SE PORQUE NO ME RECONOCE EL @BEFOREEACH
     */

/*
    @Test
    void iniciarSesionBuena() throws SQLException {


            assertEquals(bbdd.iniciarSesion(NOMBRE1, CONTRASENIA1), usuario1);
            assertEquals(bbdd.iniciarSesion(NOMBRE2, CONTRASENIA2), usuario2);
    }

    @Test
    void iniciarSesionMala() throws SQLException {

        assertNull(bbdd.iniciarSesion(NOMBRE2, "'adsff'")); // da null porque el usuario no existe
        assertNull(bbdd.iniciarSesion(NOMBRE1, "'DSAFDSF'")); // da null porque el usuario no existe
    }

    @Test
    void usuarioDelLogin() throws SQLException {
       assertEquals(bbdd.usuarioDelLogin(NOMBRE1), usuario1); // si no da null es que ha pasado el test
        assertEquals(bbdd.usuarioDelLogin(NOMBRE2),  usuario2); // da null porque el usuario no existe

    }


    @Test
    void enviarSolicitud() throws SQLException {

        assertTrue(bbdd.enviarSolicitud(usuario1, usuario2));
        bbdd.denegarSolicitud(new Solicitud(usuario1.getId(), usuario2.getId(), usuario1.getLogin()));

    }

    @Test
    void verSolicitudes() {


    }

    @Test
    void aceptarSolicitud() {
    }

    @Test
    void denegarSolicitud() {
    }

    @Test
    void getAmigos() {
    }

    @Test
    void crearNuevoChat() {
    }

    @Test
    void hablarAlChat() {
    }

    @Test
    void getMensajesChat() {
    }

    @Test
    void verChatsUsuario() {
    }

    @Test
    void cerrarConexion() {
    }

    @Test
    void cerrarResultSet() {
    }
}
/*
 */
}