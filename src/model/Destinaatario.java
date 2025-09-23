package model;

public class Destinaatario {
    private int id;
    private String nome, telefone, documento;

    public Destinaatario(int id, String nome, String telefone, String documento) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.documento = documento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
