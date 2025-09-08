package model;

public class Eletronicos {
    private int objeto_id;
    private String marca, modelo;

    public Eletronicos(int objeto_id, String marca, String modelo) {
        this.objeto_id = objeto_id;
        this.marca = marca;
        this.modelo = modelo;
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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
