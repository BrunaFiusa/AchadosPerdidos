package dao;

import model.Eletronicos;
import util.ConexaoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ObjetosEletronicosDao {

    private ConexaoDB conexao = new ConexaoDB();
    private ObjetosDao objetosDao = new ObjetosDao();

    //MODIFICAR - ALTERAR PARA NOVO MODELO COM MARCA
    public boolean inserirEletronicos(Eletronicos eletronicos) {

        String sqlEletronico = "INSERT INTO eletronicos (modelo, objeto_id) VALUES (?, ?)";

        Connection connDb = null;
        boolean sucesso = false;

        try {
            connDb = conexao.conectar();
            connDb.setAutoCommit(false);

            int objetoIdGerado = objetosDao.inserirObjetos(eletronicos, connDb);

            if (objetoIdGerado <= 0) {
                throw new Exception("Falha crítica ao inserir o objeto base. ID inválido.");
            }

            try (PreparedStatement stmtEle = connDb.prepareStatement(sqlEletronico)) {

                stmtEle.setString(1, eletronicos.getModelo());
                stmtEle.setInt(2, objetoIdGerado);

                stmtEle.executeUpdate();
            }

            connDb.commit();
            sucesso = true;

        }  catch (Exception erro) {
            System.out.println("Erro ao inserir eletronico completo. Detalhes: " + erro.getMessage());

            if (connDb != null) {
                try {
                    System.out.println("A transação falhou! Realizando Rollback...");
                    connDb.rollback();
                } catch (Exception rollbackErr) {
                    System.out.println("Erro ao fazer rollback: " + rollbackErr.getMessage());
                }
            }
            sucesso = false;

        } finally {
            if (connDb != null) {
                try {
                    connDb.setAutoCommit(true);
                    connDb.close();
                } catch (Exception e) { }
            }
        }
        return sucesso;
    }

    //CRIAÇÃO DOS OUTROS MÉTODOS
}