package br.com.pi.achadoseperdidos.model;

import java.util.Date;

public class Roupa extends Objeto {
    private String cor, tamanho;

    public Roupa() {
        super();
    }

    public Roupa(int id, int local_id, int lacre, int status_id, int funcionario_id, int categoria_id, int destino_id, String nome, String condicao, String observacao, String marca, Date dataRegistro, boolean ativado, String cor, String tamanho) {
        super(id, local_id, lacre, status_id, funcionario_id, categoria_id, destino_id, nome, condicao, observacao, marca, dataRegistro, ativado);
        this.cor = cor;
        this.tamanho = tamanho;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
}
