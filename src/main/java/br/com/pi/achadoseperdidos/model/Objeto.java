package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "objetos")
public class Objeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Mapeamento das chaves FK
    @ManyToOne // Indica que Muitos Objetos (N) apontam para Um Local (1)
    @JoinColumn(name = "local_id") // O nome EXATO da coluna FK na tabela Objeto
    private Local local; // O tipo é o Objeto (Local), e não o ID primitivo

    @ManyToOne // Indica que Muitos Objetos (N) apontam para Um Status (1)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToOne // Indica que Muitos Objetos (N) apontam para Um Funcionario (1)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @ManyToOne // Indica que Muitos Objetos (N) apontam para Uma Categoria (1)
    @JoinColumn(name = "categoria_id")
    //verificar se no cad do objeto não é obrigatorio colocar categoria(nullable = true)
    private Categoria categoria;

    //Mapeamento de navegação(boas praticas)
    @OneToMany(mappedBy = "objeto")
    private List<Devolucao> devolucoes;

    // Identificação para o Hibernate não criar uma coluna ObjetosEncaminhados na tabela.
    // Lista de navegação: a chave estrangeira (FK) está na tabela intermediária ObjetoEncaminhados (identificação p Hibernate).
    @OneToMany(mappedBy = "objeto")
    private List<ObjetoEncaminhado> objetosEncaminhados;

    @Column(name = "lacre", nullable = false, unique = true) //verificar se ele é unico
    private Integer lacre;
    @Column(name = "nome")
    private String nome;
    @Column(name = "observacao")
    private String observacao;
    @Column(name = "marca")
    private String marca;
    @Column(name = "condicao")
    private String condicao;

    @Temporal(TemporalType.TIMESTAMP)
    // Define o formato de data/hora no SQL. TIMESTAMP salva a data e a hora exata do registro (o momento do registro).
    private Date dataRegistro;

    private boolean ativado;

    public Objeto() {
    }

    public Long getId() { //somente getter!
        return id;
    }

    public Local getLocal() {
        return local;
    }
    public void setLocal(Local local) {
        this.local = local;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<ObjetoEncaminhado> getObjetoEncaminhado() { //somente getter!
        return objetoEncaminhado;
    }

    public Integer getLacre() {
        return lacre;
    }
    public void setLacre(Integer lacre) {
        this.lacre = lacre;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObservacao() {
        return observacao;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCondicao() {
        return condicao;
    }
    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }
    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public boolean isAtivado() {
        return ativado;
    }
    public void setAtivado(boolean ativado) {
        this.ativado = ativado;
    }
}