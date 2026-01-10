package br.com.pi.achadoseperdidos.model;


import jakarta.persistence.*;

@Entity
@Table(name = "locais")
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "ativado", nullable = false)
    private boolean ativado;

    public Local(){}

    public Local(String nome, boolean ativado) {
        this.nome = nome;
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

    public boolean isAtivado() {
        return ativado;
    }
    public void setAtivado(boolean ativado) {
        this.ativado = ativado;
    }
}
