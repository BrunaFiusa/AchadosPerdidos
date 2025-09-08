package model;

public class Fotos {
    private int id;
    private String caminho;

    public Fotos(int id, String caminho) {
        this.id = id;
        this.caminho = caminho;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
