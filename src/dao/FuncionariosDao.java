package dao;

import util.ConexaoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionariosDao {
    private ConexaoDB conexao = new ConexaoDB();

    public boolean inserirFuncionario(String nome, String email, String senha, int acesso_id) {
        try {
            Connection conndb = conexao.conectar();
            PreparedStatement stmt = conndb.prepareStatement("INSERT INTO funcionarios (nome, email, senha, acesso_id) VALUES (?, ?, ?, ?)");
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setInt(4, acesso_id);
            int linhaAfetada = stmt.executeUpdate();
            if (linhaAfetada > 0) {
                System.out.println("Funcionário inserido com sucesso! " + linhaAfetada + " Linha Afetada com sucesso!");
            }
            return true;

        } catch (Exception erro) {
            System.out.println("Erro ao inserir Funcionário: " + erro);
            return false;
        }
    }

    public boolean atualizarFuncionario(int id, String nome, String email, String senha, int acesso_id) {
        try {
            Connection conndb = conexao.conectar();
            PreparedStatement stmt = conndb.prepareStatement("UPDATE funcionarios SET nome = ?, email = ?, senha = ?, acesso_id = ? WHERE id = ?");
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setInt(4, acesso_id);
            stmt.setInt(5, id);
            int linhaAfetada = stmt.executeUpdate();

            if (linhaAfetada > 0) {
                System.out.println("Funcionário atualizado com sucesso! " + linhaAfetada + " Linha Afetada com sucesso!");
            }
            return true;

        } catch (Exception erro) {
            System.out.println("Erro ao atualizar Funcionário: " + erro);
            return false;
        }
    }

    public boolean deletarFuncionario(int id) {
        try {
            Connection conndb = conexao.conectar();
            PreparedStatement stmt = conndb.prepareStatement("DELETE FROM funcionarios WHERE id = ?");
            stmt.setInt(1, id);
            int linhaAfetada = stmt.executeUpdate();

            if (linhaAfetada > 0) {
                System.out.println("Funcionário deletado com sucesso! " + linhaAfetada + " Linha Afetada com sucesso!");
            }
            return true;

        } catch (Exception erro) {
            System.out.println("Erro ao deletar Funcionário: " + erro);
            return false;
        }
    }
}