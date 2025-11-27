package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "devolucoes")
public class Devolucao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destinatario_id", nullable = false)
    //Nao pode existir devolucao sem um Destinatario - nullable = false. Verificar o NULL no banco, nullable = true
    private Destinatario destinatario;

    @ManyToOne
    @JoinColumn(name = "objeto_id")
    private Objeto objeto;

    @OneToMany(mappedBy = "devolucao")
    private List<DevolucaoFoto> devolucaoFotos;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dataDevolucao")
    private Date dataDevolucao;
    @Column(name = "autenticadorAssinatura")
    private String autenticadorAssinatura;

    public Devolucao() {
    }

    public Devolucao(Destinatario destinatario, Objeto objeto, List<DevolucaoFoto> devolucaoFotos, Date dataDevolucao, String autenticadorAssinatura) {
        this.destinatario = destinatario;
        this.objeto = objeto;
        this.devolucaoFotos = devolucaoFotos;
        this.dataDevolucao = dataDevolucao;
        this.autenticadorAssinatura = autenticadorAssinatura;
    }

    public Long getId() {
        return id;
    }

    public Destinatario getDestinatario() {
        return destinatario;
    }
    public void setDestinatario(Destinatario destinatario) {
        this.destinatario = destinatario;
    }

    public Objeto getObjeto() {
        return objeto;
    }
    public void setObjeto(Objeto objeto) {
        this.objeto = objeto;
    }

    public List<DevolucaoFoto> getDevolucaoFotos() {
        return devolucaoFotos;
    }
    public void setDevolucaoFotos(List<DevolucaoFoto> devolucaoFotos) {
        this.devolucaoFotos = devolucaoFotos;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }
    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getAutenticadorAssinatura() {
        return autenticadorAssinatura;
    }
    public void setAutenticadorAssinatura(String autenticadorAssinatura) {
        this.autenticadorAssinatura = autenticadorAssinatura;
    }
}