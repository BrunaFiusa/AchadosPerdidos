package model;

public class Funcionarios {
    private int id, acesso_id;
    private String nome, email, senha;

    public Funcionarios(int id, int acesso_id, String nome, String email, String senha) {
        this.id = id;
        this.acesso_id = acesso_id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAcesso_id() {
        return acesso_id;
    }

    public void setAcesso_id(int acesso_id) {
        this.acesso_id = acesso_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
