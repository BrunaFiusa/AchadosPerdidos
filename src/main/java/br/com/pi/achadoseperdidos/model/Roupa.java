package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "roupas")
@PrimaryKeyJoinColumn(name = "objeto_id")
public class Roupa extends Objeto {

    @Column(name = "tamanho")
    private String tamanho;
    @Column(name = "cor")
    private String cor;

    public Roupa() {
    }

    public Roupa(String tamanho, String cor) {
        this.tamanho = tamanho;
        this.cor = cor;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
