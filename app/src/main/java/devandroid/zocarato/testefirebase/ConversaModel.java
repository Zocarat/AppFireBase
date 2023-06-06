package devandroid.zocarato.testefirebase;

public class ConversaModel {

    private  String id;
    private String destinatario;
    private String remetente;
    private String conteudo;
    private String data;
   // private String conteudo;


    public ConversaModel(String destinatario, String remetente, String conteudo, String data) {
        //this.id = id;
        this.destinatario = destinatario;
        this.remetente = remetente;
       // this.conteudo = conteudo;
        this.data = data;
        this.conteudo = conteudo;
    }

    // Getters e Setters

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getUltimaMensagem() {
        return conteudo;
    }

    public void setUltimaMensagem(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getDataHoraUltimaMensagem() {
        return data;
    }

    public void setDataHoraUltimaMensagem(String data) {
        this.data = data;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getNomeDestinatario() {
        if (destinatario != null) {
            return destinatario;
        } else {
            return ""; // Retorna uma string vazia em caso de erro ou valor nulo
        }
    }

}
