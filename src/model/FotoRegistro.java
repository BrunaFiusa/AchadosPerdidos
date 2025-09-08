package model;

public class FotoRegistro {
    private int foto_id, objeto_id;

    public FotoRegistro(int foto_id, int objeto_id) {
        this.foto_id = foto_id;
        this.objeto_id = objeto_id;
    }

    public int getFoto_id() {
        return foto_id;
    }

    public void setFoto_id(int foto_id) {
        this.foto_id = foto_id;
    }

    public int getObjeto_id() {
        return objeto_id;
    }

    public void setObjeto_id(int objeto_id) {
        this.objeto_id = objeto_id;
    }
}
