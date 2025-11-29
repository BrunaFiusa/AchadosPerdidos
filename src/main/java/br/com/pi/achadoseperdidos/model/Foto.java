package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "fotos")
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "foto")
    private List<RegistroFoto> registroFotos;

    @OneToMany(mappedBy = "foto")
    private List<DevolucaoFoto> devolucaoFotos;

    @Column(name = "caminho")
    private String caminho;

    public Foto() {
    }

    public Foto(List<RegistroFoto> registroFotos, List<DevolucaoFoto> devolucaoFotos, String caminho) {
        this.registroFotos = registroFotos;
        this.devolucaoFotos = devolucaoFotos;
        this.caminho = caminho;
    }

    public Integer getId() {
        return id;
    }


    public List<RegistroFoto> getRegistroFotos() {
        return registroFotos;
    }
    public void setRegistroFotos(List<RegistroFoto> registroFotos) {
        this.registroFotos = registroFotos;
    }

    public List<DevolucaoFoto> getDevolucaoFotos() {
        return devolucaoFotos;
    }
    public void setDevolucaoFotos(List<DevolucaoFoto> devolucaoFotos) {
        this.devolucaoFotos = devolucaoFotos;
    }

    public String getCaminho() {
        return caminho;
    }
    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}