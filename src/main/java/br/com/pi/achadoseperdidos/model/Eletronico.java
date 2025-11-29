package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "eletronicos")
@PrimaryKeyJoinColumn(name = "objeto_id")
public class Eletronico extends Objeto {

    @Column(name = "modelo")
    private String modelo;

    public Eletronico() {
    }

    public Eletronico(String modelo) {
        this.modelo = modelo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}