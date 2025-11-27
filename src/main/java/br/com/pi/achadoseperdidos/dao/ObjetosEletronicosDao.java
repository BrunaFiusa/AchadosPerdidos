package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Eletronico;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ObjetosEletronicosDao {

    private ConexaoDB conexao = new ConexaoDB();
    private ObjetosDao objetosDao = new ObjetosDao();

    //Pronto ✅
    public boolean inserirEletronicos(Eletronico eletronico) {

        String sqlEletronico = "INSERT INTO eletronicos (modelo, objeto_id) VALUES (?, ?)";

        Connection connDb = null;
        boolean sucesso = false;

        try {
            connDb = conexao.conectar();
            connDb.setAutoCommit(false);

            int objetoIdGerado = objetosDao.inserirObjetos(eletronico, connDb);

            if (objetoIdGerado <= 0) {
                throw new Exception("Falha crítica ao inserir o objeto base. ID inválido.");
            }

            try (PreparedStatement stmtEle = connDb.prepareStatement(sqlEletronico)) {

                stmtEle.setString(1, eletronico.getModelo());
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
                } catch (Exception e) {}
            }
        }
        return sucesso;
    }

    //Pronto ✅
    public boolean alterarEletronicos(Eletronico eletronico) {
        String sqlEletronico = "UPDATE eletronicos SET modelo = ? WHERE objeto_id = ?";

        Connection connDb = null;
        boolean sucesso = false;

        try {
            connDb = conexao.conectar();
            connDb.setAutoCommit(false);

            boolean sucessoObjeto = objetosDao.alterarObjetos(eletronico, connDb);

            if (!sucessoObjeto) {
                throw new Exception("Falha ao alterar o objeto base.");
            }

            try (PreparedStatement stmtEle = connDb.prepareStatement(sqlEletronico)) {

                stmtEle.setString(1, eletronico.getModelo());
                stmtEle.setInt(2, eletronico.getId());

                stmtEle.executeUpdate();
            }

            connDb.commit();
            sucesso = true;

        } catch (Exception erro) {
            System.out.println("Erro ao alterar eletrônico completo: " + erro.getMessage());

            if (connDb != null) {
                try {
                    connDb.rollback();
                } catch (Exception rollbackErr) { }
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
}