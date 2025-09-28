package dao;

import util.ConexaoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FotosDao {
    private ConexaoDB conexao = new ConexaoDB();

    public boolean inserirFoto(String caminho) {
        try {
            Connection conndb = conexao.conectar();
            PreparedStatement stmt = conndb.prepareStatement("INSERT INTO fotos (caminho) VALUES (?)");
            stmt.setString(1, caminho);
            int linhaAfetada = stmt.executeUpdate();
            if (linhaAfetada > 0) {
                System.out.println("Foto inserida com sucesso! " + linhaAfetada + " Linha Afetada com sucesso!");
            }
            return true;

        } catch (Exception erro) {
            System.out.println("Erro ao inserir Foto: " + erro);
            return false;
        }
    }

    public void buscarFotoPorId(int id) {
        try {
            Connection conndb = conexao.conectar();
            PreparedStatement stmt = conndb.prepareStatement("SELECT * FROM fotos WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                System.out.println("=== FOTO ENCONTRADA ===");
                System.out.println("ID: " + resultado.getInt("id"));
                System.out.println("Caminho: " + resultado.getString("caminho"));
                System.out.println("=== FIM ===");
            } else {
                System.out.println("Foto não encontrada para o ID: " + id);
            }

        } catch (Exception erro) {
            System.out.println("Erro ao buscar Foto: " + erro);
        }
    }





}