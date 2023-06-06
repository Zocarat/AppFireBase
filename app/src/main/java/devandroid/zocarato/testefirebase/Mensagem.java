package devandroid.zocarato.testefirebase;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Mensagem {
    private String conteudo;
    private String dataHora;
    private String destinatario;

    private String remetente;

    public Mensagem() {
        // Construtor vazio necessário para o Firebase Realtime Database
    }

    public Mensagem(String conteudo, String destinatario, String remetente) {
        this.conteudo = conteudo;
        this.destinatario = destinatario;
        this.remetente = remetente;
        this.dataHora = getCurrentDateTime();
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

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

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(new Date());
    }
}