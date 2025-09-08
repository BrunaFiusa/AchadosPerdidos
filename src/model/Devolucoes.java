package model;

import java.util.Date;

public class Devolucoes {
    private int id, destinatario_id, objeto_id;
    private Date dataDevoluçao;
    private String autenticadorAssinatura;

    public Devolucoes(int id, Date dataDevoluçao, String autenticadorAssinatura, int destinatario_id, int objeto_id) {
        this.id = id;
        this.dataDevoluçao = dataDevoluçao;
        this.autenticadorAssinatura = autenticadorAssinatura;
        this.destinatario_id = destinatario_id;
        this.objeto_id = objeto_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDestinatario_id() {
        return destinatario_id;
    }

    public void setDestinatario_id(int destinatario_id) {
        this.destinatario_id = destinatario_id;
    }

    public int getObjeto_id() {
        return objeto_id;
    }

    public void setObjeto_id(int objeto_id) {
        this.objeto_id = objeto_id;
    }

    public Date getDataDevoluçao() {
        return dataDevoluçao;
    }

    public void setDataDevoluçao(Date dataDevoluçao) {
        this.dataDevoluçao = dataDevoluçao;
    }

    public String getAutenticadorAssinatura() {
        return autenticadorAssinatura;
    }

    public void setAutenticadorAssinatura(String autenticadorAssinatura) {
        this.autenticadorAssinatura = autenticadorAssinatura;
    }
}
