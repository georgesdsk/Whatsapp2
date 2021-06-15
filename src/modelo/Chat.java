package modelo;

public class Chat {


    private int idChat;
    private String nombreChat;

    public Chat(int idChat, String nombreChat) {
        this.idChat = idChat;
        this.nombreChat = nombreChat;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public String getNombreChat() {
        return nombreChat;
    }

    public void setNombreChat(String nombreChat) {
        this.nombreChat = nombreChat;
    }
}
