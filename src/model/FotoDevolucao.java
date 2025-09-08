package model;

public class FotoDevolucao {
    private int foto_id, devolucao_id;

    public FotoDevolucao(int foto_id, int devolucao_id) {
        this.foto_id = foto_id;
        this.devolucao_id = devolucao_id;
    }

    public int getFoto_id() {
        return foto_id;
    }

    public void setFoto_id(int foto_id) {
        this.foto_id = foto_id;
    }

    public int getDevolucao_id() {
        return devolucao_id;
    }

    public void setDevolucao_id(int devolucao_id) {
        this.devolucao_id = devolucao_id;
    }
}
