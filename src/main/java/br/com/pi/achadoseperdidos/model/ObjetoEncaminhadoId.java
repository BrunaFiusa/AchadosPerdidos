package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;


//@Embeddable: Marca esta classe como um COMPONENTE EMBUTÍVEL.
//Ela define a estrutura da CHAVE PRIMÁRIA COMPOSTA (formada por objetoId + destinoId),
//que será inserida diretamente na tabela ObjetosEncaminhados.
@Embeddable
public class ObjetoEncaminhadoId {//Esta classe não vai virar uma tabela no banco.
                                  // Ela é apenas um grupo de atributos que eu posso injetar em outras tabelas


    @Column(name = "objeto_id")
    private int objetoId;

    @Column(name = "destino_id")
    private int destinoId;

    public ObjetoEncaminhadoId() {}

    public ObjetoEncaminhadoId(int objetoId, int destinoId) {
        this.objetoId = objetoId;
        this.destinoId = destinoId;
    }

    public int getObjetoId() {
        return objetoId;
    }

    public void setObjetoId(int objetoId) {
        this.objetoId = objetoId;
    }

    public int getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(int destinoId) {
        this.destinoId = destinoId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ObjetoEncaminhadoId that)) return false;
        return objetoId == that.objetoId && destinoId == that.destinoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(objetoId, destinoId);
    }
}