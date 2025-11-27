package br.com.pi.achadoseperdidos.model;

import java.util.Date;

public class Eletronico extends Objeto {
    private String modelo;

    public Eletronico() {
        super();
    }

    public Eletronico(int id, int local_id, int lacre, int status_id, int funcionario_id, int categoria_id, int destino_id, String nome, String condicao, String observacao, String marca, Date dataRegistro, boolean ativado, String modelo) {
        super(id, local_id, lacre, status_id, funcionario_id, categoria_id, destino_id, nome, condicao, observacao, marca, dataRegistro, ativado);
        this.modelo = modelo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}