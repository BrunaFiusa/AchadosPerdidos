package br.com.pi.achadoseperdidos.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "acessos")
public class Acesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "acesso", fetch = FetchType.LAZY)
    private List<Funcionario> funcionarios = new ArrayList<>();

    @Column(name = "nome")
    private String nome;

    public Acesso(){
    }

    // 🔑 CORREÇÃO: Removendo o Funcionario do construtor
    public Acesso(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }
    // Não precisa de setId() se for IDENTITY

    // 🔑 CORREÇÃO: Mudando para List<Funcionario>
    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }
    // 🔑 CORREÇÃO: Mudando o setter
    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}