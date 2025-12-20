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
    @Column(name = "ativado")
    private boolean ativado;

    public Destino() {
    }

    public Destino(List<ObjetoEncaminhado> objetosEncaminhados, String nome, boolean ativado) {
        this.objetosEncaminhados = objetosEncaminhados;
        this.nome = nome;
        this.ativado = ativado;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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

    public boolean isAtivado() {
        return ativado;
    }
    public void setAtivado(boolean ativado) {
        this.ativado = ativado;
    }
}
