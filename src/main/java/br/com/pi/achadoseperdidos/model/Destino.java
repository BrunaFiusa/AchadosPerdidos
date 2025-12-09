package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "destinos")
public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "destino", cascade = CascadeType.ALL)
    private List<ObjetoEncaminhado> objetosEncaminhados;

    @Column(name = "nome")
    private String nome;

    public Destino() {
    }

    public Destino(List<ObjetoEncaminhado> objetosEncaminhados, String nome) {
        this.objetosEncaminhados = objetosEncaminhados;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public List<ObjetoEncaminhado> getObjetosEncaminhados() {
        return objetosEncaminhados;
    }

    public void setObjetosEncaminhados(List<ObjetoEncaminhado> objetosEncaminhados) {
        this.objetosEncaminhados = objetosEncaminhados;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
