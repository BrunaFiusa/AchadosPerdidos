package model;

import java.util.Date;

public class Destino {
    private int id;
    private String nome;
    private Date dataEnvio;

    public Destino(int id, String nome, Date dataEnvio) {
        this.id = id;
        this.nome = nome;
        this.dataEnvio = dataEnvio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
}
