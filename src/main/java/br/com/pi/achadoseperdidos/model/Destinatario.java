package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "destinatarios")
public class Destinatario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String nome;
    @Column(name = "telefone", unique = true)
    private String telefone;
    @Column(name = "rg", unique = true)
    private String rg;
    @Column(name = "cpf", unique = true)
    private String cpf;
    @Column(name = "ativação")
    private Boolean ativacao;

    public Destinatario() {}

    public Destinatario(String nome, String telefone, String rg, String cpf, Boolean ativacao) {
        this.nome = nome;
        this.telefone = telefone;
        this.rg = rg;
        this.cpf = cpf;
        this.ativacao = ativacao;
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

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getRg() {
        return rg;
    }
    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Boolean isAtivacao() {
        return ativacao;
    }
    public void setAtivacao(Boolean ativacao) {
        this.ativacao = ativacao;
    }
}