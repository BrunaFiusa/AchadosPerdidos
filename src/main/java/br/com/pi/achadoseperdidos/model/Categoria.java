package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "prazo", nullable = false)
    private Integer prazo;

    public Categoria(){}

    public Categoria(String nome, Integer prazo) {
        this.nome = nome;
        this.prazo = prazo;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPrazo() {
        return prazo;
    }
    public void setPrazo(Integer prazo) {
        this.prazo = prazo;
    }
}
