package dao;

import model.Funcionario;
import util.ConexaoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionariosDao {
    private ConexaoDB conexao = new ConexaoDB();

    public List<Funcionario> listarFuncionariosCadastrados() {
        List<Funcionario> listaDeFuncionarios = new ArrayList<>();
        String sql = "SELECT nome, email FROM funcionarios;";

        try (Connection connDb = conexao.conectar();
             PreparedStatement ps = connDb.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Funcionario funcionario = new Funcionario();

                funcionario.setNome(rs.getString("nome"));
                funcionario.setEmail(rs.getString("email"));

                listaDeFuncionarios.add(funcionario);
        }
    }catch(SQLException erro){
        System.out.println("Erro ao pesquisar fucionários cadastrados" + erro.getMessage());
        erro.printStackTrace();
        }
        return listaDeFuncionarios;
    }
}
