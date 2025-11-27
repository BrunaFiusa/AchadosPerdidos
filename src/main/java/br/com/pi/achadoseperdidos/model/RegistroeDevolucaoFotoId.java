package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.Column;

import jakarta.persistence.Embeddable;

public class RegistroeDevolucaoFotoId {

    @Embeddable
    public static class RegistroFotoId {

        @Column(name = "foto_id")
        private int fotoId;
        @Column(name = "objeto_id")
        private int objetoId;

        public RegistroFotoId() {}

        public RegistroFotoId(int fotoId, int objetoId) {
            this.fotoId = fotoId;
            this.objetoId = objetoId;
        }

        public int getFotoId() {
            return fotoId;
        }

        public void setFotoId(int fotoId) {
            this.fotoId = fotoId;
        }

        public int getObjetoId() {
            return objetoId;
        }

        public void setObjetoId(int objetoId) {
            this.objetoId = objetoId;
        }
    }

    @Embeddable
    public static class DevolucaoFotoId {

        @Column(name = "foto_id")
        private int fotoId;
        @Column(name = "devolucao_id")
        private int devolucaoId;

        public DevolucaoFotoId() {}

        public DevolucaoFotoId(int fotoId, int devolucaoId) {
            this.fotoId = fotoId;
            this.devolucaoId = devolucaoId;
        }

        public int getFotoId() {
            return fotoId;
        }

        public void setFotoId(int fotoId) {
            this.fotoId = fotoId;
        }

        public int getDevolucaoId() {
            return devolucaoId;
        }

        public void setDevolucaoId(int devolucaoId) {
            this.devolucaoId = devolucaoId;
        }

    }

}

