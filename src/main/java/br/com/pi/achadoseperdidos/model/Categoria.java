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
    @Column(name = "ativado", nullable = false)
    private boolean ativado;

    public Categoria(){}

    public Categoria(String nome, Integer prazo, boolean ativado) {
        this.nome = nome;
        this.prazo = prazo;
        this.ativado = ativado;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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

    public boolean isAtivado() {
        return ativado;
    }
    public void setAtivado(boolean ativado) {
        this.ativado = ativado;
    }
}
