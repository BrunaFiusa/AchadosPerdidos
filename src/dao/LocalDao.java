package dao;

import util.ConexaoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class LocalDao {
    private ConexaoDB conexao = new ConexaoDB();

    public boolean inserirLocal(String nome) {
        try {
            Connection conndb = conexao.conectar();
            PreparedStatement stmt = conndb.prepareStatement("INSERT INTO locais (nome) VALUES (?)");
            stmt.setString(1, nome);
            int linhaAfetada = stmt.executeUpdate();
            if (linhaAfetada > 0) {
                System.out.println("Local inserido com sucesso! " + linhaAfetada + " Linha Afetada com sucesso!");
            }
            return true;

        } catch (Exception erro) {
            System.out.println("Erro ao inserir Local: " + erro);
            return false;
        }
    }

    public boolean atualizarLocal(int id, String novoNome) {
        try {
            Connection conndb = conexao.conectar();
            PreparedStatement stmt = conndb.prepareStatement("UPDATE locais SET nome = ? WHERE id = ?");
            stmt.setString(1, novoNome);
            stmt.setInt(2, id);
            int linhaAfetada = stmt.executeUpdate();

            if (linhaAfetada > 0) {
                System.out.println("Local atualizado com sucesso! " + linhaAfetada + " Linha Afetada com sucesso!");
            }
            return true;

        } catch (Exception erro) {
            System.out.println("Erro ao atualizar Local: " + erro);
            return false;
        }
    }

    public boolean deletarLocal(int id) {
        try {
            Connection conndb = conexao.conectar();
            PreparedStatement stmt = conndb.prepareStatement("DELETE FROM locais WHERE id = ?");
            stmt.setInt(1, id);
            int linhaAfetada = stmt.executeUpdate();

            if (linhaAfetada > 0) {
                System.out.println("Local deletado com sucesso! " + linhaAfetada + " Linha Afetada com sucesso!");
            }
            return true;

        } catch (Exception erro) {
            System.out.println("Erro ao deletar Local: " + erro);
            return false;
        }
    }
}