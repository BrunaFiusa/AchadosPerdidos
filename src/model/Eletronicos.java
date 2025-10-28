package model;

import java.util.Date;

public class Eletronicos extends Objetos {
    private String modelo;

    public Eletronicos() {
        super();
    }

    public Eletronicos(int id, int local_id, int lacre, int status_id, int funcionario_id, int categoria_id, int destino_id, String nome, String condicao, String observacao, String marca, Date dataRegistro, String modelo) {
        super(id, local_id, lacre, status_id, funcionario_id, categoria_id, destino_id, nome, condicao, observacao, marca, dataRegistro);
        this.modelo = modelo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}