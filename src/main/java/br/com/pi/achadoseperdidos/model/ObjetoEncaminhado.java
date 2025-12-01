package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "objetosencaminhados")
public class ObjetoEncaminhado {

    // ID Composto (classe auxiliar ObjetoEncaminhadoId)
    @EmbeddedId
    private ObjetoEncaminhadoId id;

    // Relação com Objeto (FK)
    @ManyToOne
    @MapsId("objetoId") //O @MapsId relaciona o campo 'objetoId' da minha chave composta (id) que está mapeando a Chave Estrangeira para a Entidade Objeto
    @JoinColumn(name = "objeto_id") // Coluna FK no banco
    private Objeto objeto;

    // Relação com Destinos (FK)
    @ManyToOne
    @MapsId("destinoId")
    @JoinColumn(name = "destino_id")
    private Destino destino;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEnvio;
}
