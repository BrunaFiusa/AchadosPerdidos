package dao;

import model.Objetos;
import util.ConexaoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ObjetosDao {
    private ConexaoDB conexao = new ConexaoDB();
    private Timestamp converterParaSqlTimestamp(Objetos objetos) {
        if (objetos.getDataRegistro() == null) {
            return null;
        }
        return new Timestamp(objetos.getDataRegistro().getTime());
    }

    //** COMPLETO - INSERIR OBJETOS
    public int inserirObjetos(Objetos objetos, Connection connDb) throws Exception {
        String sql = "INSERT INTO objetos (nome, data_registro, lacre, condicao, observacao, local_id, status_id, funcionario_id, categoria_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int idGerado = -1;

        try (PreparedStatement stmt = connDb.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            Timestamp sqlTimestamp = converterParaSqlTimestamp(objetos);

            stmt.setString(1, objetos.getNome());
            stmt.setTimestamp(2, sqlTimestamp);
            stmt.setInt(3, objetos.getLacre());
            stmt.setString(4, objetos.getCondicao());
            stmt.setString(5, objetos.getObservacao());
            stmt.setInt(6, objetos.getLocal_id());
            stmt.setInt(7, objetos.getStatus_id());
            stmt.setInt(8, objetos.getFuncionario_id());
            stmt.setInt(9, objetos.getCategoria_id());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idGerado = rs.getInt(1);
                }
            }
            return idGerado;
        }
    }

    public boolean alterarObjetos(Objetos objetos) {
        try {

            Connection connDb = conexao.conectar();
            Timestamp sqlTimestamp = converterParaSqlTimestamp(objetos);

            PreparedStatement stmt = connDb.prepareStatement("UPDATE objetos SET nome = ?, data_registro = ?, lacre = ?, condicao = ?, observacao = ?, local_id = ?, status_id = ?, funcionario_id = ?, categoria_id = ? WHERE id = ?;");

            stmt.setString(1, objetos.getNome());
            stmt.setTimestamp(2, converterParaSqlTimestamp(objetos));
            stmt.setInt(3, objetos.getLacre());
            stmt.setString(4, objetos.getCondicao());
            stmt.setString(5, objetos.getObservacao());
            stmt.setInt(6, objetos.getLocal_id());
            stmt.setInt(7, objetos.getStatus_id());
            stmt.setInt(8, objetos.getFuncionario_id());
            stmt.setInt(9, objetos.getCategoria_id());
            stmt.setInt(10, objetos.getId());

            int linhaAfetada = stmt.executeUpdate();
            connDb.close();
            return linhaAfetada > 0;

        } catch (Exception erro) {
            System.out.println("Erro ao alterar objetos: " + erro);
            return false;
        }
    }

    public boolean deletarObjetos(Objetos objetos) {
        try {

            Connection connDb = conexao.conectar();

            PreparedStatement stmt = connDb.prepareStatement("DELETE FROM objetos WHERE id = ?;");

            stmt.setInt(1, objetos.getId());

            int linhaAfetada = stmt.executeUpdate();
            connDb.close();
            return linhaAfetada > 0;

        } catch (Exception erro) {
            System.out.println("Erro ao deletar objetos: " + erro);
            return false;
        }
    }

    //** MODIFICAR - NÃO LISTAR CHAVES ESTRANGEIRAS | NÃO LISTAR ID **
    public List<Objetos> listarTodosObjetos() {
        String sql = "SELECT id, nome, data_registro, lacre, condicao, observacao, local_id, status_id, funcionario_id, categoria_id FROM objetos";

        Connection connDb = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Objetos> listaDeObjetos = new ArrayList<>();

        try {
            connDb = conexao.conectar();
            stmt = connDb.prepareStatement(sql);

            rs = stmt.executeQuery();

            while (rs.next()) {

                Objetos objeto = new Objetos();

                objeto.setId(rs.getInt("id"));
                objeto.setNome(rs.getString("nome"));
                objeto.setDataRegistro(rs.getTimestamp("data_registro"));
                objeto.setLacre(rs.getInt("lacre"));
                objeto.setCondicao(rs.getString("condicao"));
                objeto.setObservacao(rs.getString("observacao"));
                objeto.setLocal_id(rs.getInt("local_id"));
                objeto.setStatus_id(rs.getInt("status_id"));
                objeto.setFuncionario_id(rs.getInt("funcionario_id"));
                objeto.setCategoria_id(rs.getInt("categoria_id"));

                listaDeObjetos.add(objeto);

            }

        } catch (Exception erro) {
            System.out.println("Erro ao listar objetos: " + erro);

        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connDb != null) connDb.close();
            } catch (Exception e) {}
        }

        return listaDeObjetos;
    }

    //Outros Metodos de Listagem!
}