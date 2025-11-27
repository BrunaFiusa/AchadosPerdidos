package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "destinos")
public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "destino")
    private List<ObjetoEncaminhado> objetosEncaminhados;

    @Column(name = "nome")
    private String nome;
    @Column(name = "dataEnvio", nullable = false)
    private Date dataEnvio;

    public Destino(String nome, Date dataEnvio) {
        this.nome = nome;
        this.dataEnvio = dataEnvio;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
}
