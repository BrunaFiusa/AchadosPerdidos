package br.com.pi.achadoseperdidos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DestinosDao {
    private ConexaoDB conexao = new ConexaoDB();

    public boolean inserirDestino(String nome) {
        try {
            Connection conndb = conexao.conectar();
            PreparedStatement stmt = conndb.prepareStatement("INSERT INTO destinos (nome) VALUES (?)");
            stmt.setString(1, nome);
            int linhaAfetada = stmt.executeUpdate();
            if (linhaAfetada > 0) {
                System.out.println("Destino inserido com sucesso! " + linhaAfetada + " Linha Afetada com sucesso!");
            }
            return true;

        } catch (Exception erro) {
            System.out.println("Erro ao inserir Destino: " + erro);
            return false;
        }
    }
}