package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "destinos")
public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "destino")
    private List<ObjetoEncaminhado> objetosEncaminhados;

    @Column(name = "nome")
    private String nome;

    public Destino(String nome) {
        this.nome = nome;
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

}
