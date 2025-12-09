package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "objetos")
@Inheritance(strategy = InheritanceType.JOINED)
public class Objeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    @OneToMany(mappedBy = "objeto", cascade = CascadeType.ALL)
    private List<ObjetoEncaminhado> objetosEncaminhados;

    @OneToMany(mappedBy = "objeto", cascade = CascadeType.ALL)
    private List<RegistroFoto> registrosFotos;

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
    @Column(name = "ativado")
    private Boolean ativado;
    @Column(name = "data_registro")
// Define o formato de data/hora no SQL. TIMESTAMP salva a data e a hora exata do registro (o momento do registro).
    private Date dataRegistro;

    public Objeto() {
    }

    public Objeto(Local local, Status status, Funcionario funcionario, Categoria categoria, List<Devolucao> devolucoes,
                  List<ObjetoEncaminhado> objetosEncaminhados, List<RegistroFoto> registrosFotos, Integer lacre,
                  String nome, String observacao, String marca, String condicao, Boolean ativado, Date dataRegistro) {
        this.local = local;
        this.status = status;
        this.funcionario = funcionario;
        this.categoria = categoria;
        this.devolucoes = devolucoes;
        this.objetosEncaminhados = objetosEncaminhados;
        this.registrosFotos = registrosFotos;
        this.lacre = lacre;
        this.nome = nome;
        this.observacao = observacao;
        this.marca = marca;
        this.condicao = condicao;
        this.ativado = ativado;
        this.dataRegistro = dataRegistro;
    }

    public Integer getId() {
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

    public List<Devolucao> getDevolucoes() {
        return devolucoes;
    }
    public void setDevolucoes(List<Devolucao> devolucoes) {
        this.devolucoes = devolucoes;
    }

    public List<ObjetoEncaminhado> getObjetosEncaminhados() {
        return objetosEncaminhados;
    }
    public void setObjetosEncaminhados(List<ObjetoEncaminhado> objetosEncaminhados) {
        this.objetosEncaminhados = objetosEncaminhados;
    }

    public List<RegistroFoto> getRegistrosFotos() {
        return registrosFotos;
    }
    public void setRegistrosFotos(List<RegistroFoto> registrosFotos) {
        this.registrosFotos = registrosFotos;
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

    public Boolean getAtivado() {
        return ativado;
    }
    public void setAtivado(Boolean ativado) {
        this.ativado = ativado;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }
    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}