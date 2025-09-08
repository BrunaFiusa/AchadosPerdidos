package model;

public class Pereciveis {
    private int objeto_id;
    private String marca;

    public Pereciveis(int objeto_id, String marca) {
        this.objeto_id = objeto_id;
        this.marca = marca;
    }

    public int getObjeto_id() {
        return objeto_id;
    }

    public void setObjeto_id(int objeto_id) {
        this.objeto_id = objeto_id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
