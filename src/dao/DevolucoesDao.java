package dao;

import util.ConexaoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class DevolucoesDao {
    private ConexaoDB conexao = new ConexaoDB();

    public boolean inserirDevolucao(Date dataDevolucao, String autenticadorAssinatura, int destinatarioId, int objetoId) {
        try {
            Connection conndb = conexao.conectar();
            PreparedStatement stmt = conndb.prepareStatement("INSERT INTO devolucoes (dataDevolucao, autenticadorAssinatura, destinatario_id, objeto_id) VALUES (?, ?, ?, ?)");
            stmt.setDate(1, dataDevolucao);
            stmt.setString(2, autenticadorAssinatura);
            stmt.setInt(3, destinatarioId);
            stmt.setInt(4, objetoId);
            int linhaAfetada = stmt.executeUpdate();
            if (linhaAfetada > 0) {
                System.out.println("Devolução inserida com sucesso! " + linhaAfetada + " Linha Afetada com sucesso!");
            }
            return true;

        } catch (Exception erro) {
            System.out.println("Erro ao inserir Devolução: " + erro);
            return false;
        }
    }

    public boolean deletarDevolucao(int id) {
        try {
            Connection conndb = conexao.conectar();
            PreparedStatement stmt = conndb.prepareStatement("DELETE FROM devolucoes WHERE id = ?");
            stmt.setInt(1, id);
            int linhaAfetada = stmt.executeUpdate();

            if (linhaAfetada > 0) {
                System.out.println("Devolução deletada com sucesso! " + linhaAfetada + " Linha Afetada com sucesso!");
            }
            return true;

        } catch (Exception erro) {
            System.out.println("Erro ao deletar Devolução: " + erro);
            return false;
        }
    }


}