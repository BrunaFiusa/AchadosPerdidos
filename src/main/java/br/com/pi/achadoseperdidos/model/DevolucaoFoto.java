package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

public class DevolucaoFoto {

    @EmbeddedId
    private RegistroeDevolucaoFotoId.DevolucaoFotoId id;

    @ManyToOne
    @MapsId("fotoId")
    @JoinColumn(name = "foto_id")
    private Foto foto;

    @ManyToOne
    @MapsId("devolucaoId")
    @JoinColumn(name = "devolucao_id")
    private Devolucao devolucao;

    public DevolucaoFoto() {
    }

    public DevolucaoFoto(RegistroeDevolucaoFotoId.DevolucaoFotoId id, Foto foto, Devolucao devolucao) {
        this.id = id;
        this.foto = foto;
        this.devolucao = devolucao;
    }

    public RegistroeDevolucaoFotoId.DevolucaoFotoId getId() {
        return id;
    }

    public void setId(RegistroeDevolucaoFotoId.DevolucaoFotoId id) {
        this.id = id;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public Devolucao getDevolucao() {
        return devolucao;
    }

    public void setDevolucao(Devolucao devolucao) {
        this.devolucao = devolucao;
    }
}