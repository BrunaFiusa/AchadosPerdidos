package model;
import java.util.Date;

public class ObjetoEncaminhado {
    private int objeto_id, destino_id;
    private Date dataEnvio;

    public ObjetoEncaminhado(int objeto_id, int destino_id, Date dataEnvio) {
        this.objeto_id = objeto_id;
        this.destino_id = destino_id;
        this.dataEnvio = dataEnvio;
    }

    public int getObjeto_id() {
        return objeto_id;
    }

    public void setObjeto_id(int objeto_id) {
        this.objeto_id = objeto_id;
    }

    public int getDestino_id() {
        return destino_id;
    }

    public void setDestino_id(int destino_id) {
        this.destino_id = destino_id;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
}
