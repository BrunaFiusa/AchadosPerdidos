package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Roupa;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ObjetosRoupasDao {

    private ConexaoDB conexao = new ConexaoDB();
    private ObjetosDao objetosDao = new ObjetosDao();

    //Pronto ✅
    public  boolean inserirRoupas(Roupa roupa){
        String sqlRoupa = "INSERT INTO roupas (cor, tamanho, objeto_id) VALUES (?, ?, ?)";

        Connection connDb = null;
        boolean sucesso = false;

        try {
            connDb = conexao.conectar();
            connDb.setAutoCommit(false);

            int objetoIdGerado = objetosDao.inserirObjetos(roupa);

            if (objetoIdGerado <= 0) {
                throw new Exception("Falha crítica ao inserir o objeto base. ID inválido.");
            }

            try (PreparedStatement stmtRou = connDb.prepareStatement(sqlRoupa)) {

                stmtRou.setString(1, roupa.getCor());
                stmtRou.setString(2, roupa.getTamanho());
                stmtRou.setInt(3, objetoIdGerado);

                stmtRou.executeUpdate();
            }

            connDb.commit();
            sucesso = true;

        } catch (Exception erro) {
            System.out.println("Erro ao inserir Roupas completo. Detalhes: " + erro.getMessage());

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
    public boolean alterarRoupas(Roupa roupa) {
        String sqlEletronico = "UPDATE roupas SET cor = ?, tamanho= ? WHERE objeto_id = ?";

        Connection connDb = null;
        boolean sucesso = false;

        try {
            connDb = conexao.conectar();
            connDb.setAutoCommit(false);

            boolean sucessoObjeto = objetosDao.alterarObjetos(roupa, connDb);

            if (!sucessoObjeto) {
                throw new Exception("Falha ao alterar o objeto base.");
            }

            try (PreparedStatement stmtEle = connDb.prepareStatement(sqlEletronico)) {

                stmtEle.setString(1, roupa.getCor());
                stmtEle.setString(2, roupa.getTamanho());
                stmtEle.setInt(3, roupa.getId());

                stmtEle.executeUpdate();
            }

            connDb.commit();
            sucesso = true;

        } catch (Exception erro) {
            System.out.println("Erro ao alterar roupa completo: " + erro.getMessage());

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
