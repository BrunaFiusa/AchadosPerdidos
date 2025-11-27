package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "acesso_id", nullable = false)
    private Acesso acesso;


    @Column(name = "nome")
    private String nome;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "senha", unique = true)
    private String senha;

    public Funcionario() {
    }

    public Funcionario(Acesso acesso, String nome, String email, String senha) {
        this.acesso = acesso;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }


    public Acesso getAcesso() {
        return acesso;
    }
    public void setAcesso(Acesso acesso) {
        this.acesso = acesso;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}