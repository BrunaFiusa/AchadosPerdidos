package model;

public class Roupas {
    private int objeti_id;
    private String cor, marca, tamanho;

    public Roupas(int objeti_id, String cor, String marca, String tamanho) {
        this.objeti_id = objeti_id;
        this.cor = cor;
        this.marca = marca;
        this.tamanho = tamanho;
    }

    public int getObjeti_id() {
        return objeti_id;
    }

    public void setObjeti_id(int objeti_id) {
        this.objeti_id = objeti_id;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
}
