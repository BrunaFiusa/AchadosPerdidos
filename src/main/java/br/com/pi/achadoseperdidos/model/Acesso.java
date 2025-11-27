package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "acessos")
public class Acesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "acesso")
    private Funcionario funcionario;

    @Column(name = "nome")
    private String nome;

    public Acesso(){}

    public Acesso(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }
}