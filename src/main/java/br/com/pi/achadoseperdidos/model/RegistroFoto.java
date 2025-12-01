package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "fotoregistros")
public class RegistroFoto {

    @EmbeddedId
    private RegistroeDevolucaoFotoId.RegistroFotoId id;

    @ManyToOne
    @MapsId("fotoId")
    @JoinColumn(name = "foto_id")
    private Foto foto;

    @ManyToOne
    @MapsId("objetoId")
    @JoinColumn(name = "objeto_id")
    private Objeto objeto;

    public RegistroFoto() {
    }

    public RegistroFoto(RegistroeDevolucaoFotoId.RegistroFotoId id, Foto foto, Objeto objeto) {
        this.id = id;
        this.foto = foto;
        this.objeto = objeto;
    }

    public RegistroeDevolucaoFotoId.RegistroFotoId getId() {
        return id;
    }

    public void setId(RegistroeDevolucaoFotoId.RegistroFotoId id) {
        this.id = id;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public Objeto getObjeto() {
        return objeto;
    }

    public void setObjeto(Objeto objeto) {
        this.objeto = objeto;
    }
}
