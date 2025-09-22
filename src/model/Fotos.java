package model;

public class Fotos {
    private int id, objeto_id;
    private String caminho;

    public Fotos(int id, int objeto_id, String caminho) {
        this.id = id;
        this.objeto_id = objeto_id;
        this.caminho = caminho;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getObjeto_id() {
        return objeto_id;
    }

    public void setObjeto_id(int objeto_id) {
        this.objeto_id = objeto_id;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
