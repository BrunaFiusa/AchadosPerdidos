package model;

public class Categorias {
    private int id;
    private String nome;
    private  int prazo;

    public Categorias(int id, String nome, int prazo) {
        this.id = id;
        this.nome = nome;
        this.prazo = prazo;
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

    public int getPrazo() {
        return prazo;
    }

    public void setPrazo(int prazo) {
        this.prazo = prazo;
    }
}
