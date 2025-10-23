package model;

import java.util.Date;

public class Objetos {
    private int id, lacre, local_id, status_id, funcionario_id, categoria_id, destino_id;
    private String nome, condicao, observacao, marca;
    private Date dataRegistro;

    public Objetos(int id, int local_id, int lacre, int status_id, int funcionario_id, int categoria_id, int destino_id, String nome, String condicao, String observacao, String marca, Date dataRegistro) {
        this.id = id;
        this.local_id = local_id;
        this.lacre = lacre;
        this.status_id = status_id;
        this.funcionario_id = funcionario_id;
        this.categoria_id = categoria_id;
        this.destino_id = destino_id;
        this.nome = nome;
        this.condicao = condicao;
        this.observacao = observacao;
        this.marca = marca;
        this.dataRegistro = dataRegistro;
    }

    public Objetos() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLacre() {
        return lacre;
    }

    public void setLacre(int lacre) {
        this.lacre = lacre;
    }

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getFuncionario_id() {
        return funcionario_id;
    }

    public void setFuncionario_id(int funcionario_id) {
        this.funcionario_id = funcionario_id;
    }

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public int getDestino_id() {
        return destino_id;
    }

    public void setDestino_id(int destino_id) {
        this.destino_id = destino_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}
