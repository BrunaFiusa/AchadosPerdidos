package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Objeto;

import java.sql.*;

public class    ObjetosDao {
    private ConexaoDB conexao = new ConexaoDB();

    //Pronto ✅
    public int inserirObjetos(Objeto objeto) {
        String sql = "INSERT INTO objetos (nome, lacre, condicao, observacao, local_id, status_id, funcionario_id, categoria_id, marca) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int idGerado = -1;

        try (Connection connDb = conexao.conectar(); PreparedStatement stmt = connDb.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, objeto.getNome());
            stmt.setInt(2, objeto.getLacre());
            stmt.setString(3, objeto.getCondicao());
            stmt.setString(4, objeto.getObservacao());
            stmt.setInt(5, objeto.getLocal_id());
            stmt.setInt(6, objeto.getStatus_id());
            stmt.setInt(7, objeto.getFuncionario_id());
            stmt.setInt(8, objeto.getCategoria_id());
            stmt.setString(9, objeto.getMarca());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idGerado = rs.getInt(1);
                }
            }
            return idGerado;

        } catch (Exception erro) {
            System.out.println("Erro ao inserir objetos: " + erro);
            return -1;
        }
    }

    //Pronto ✅
    public int inserirObjetos(Objeto objeto, Connection connDb) throws SQLException {
        String sql = "INSERT INTO objetos (nome, lacre, condicao, observacao, local_id, status_id, funcionario_id, categoria_id, marca) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int idGerado = -1;

        try (PreparedStatement stmt = connDb.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, objeto.getNome());
            stmt.setInt(2, objeto.getLacre());
            stmt.setString(3, objeto.getCondicao());
            stmt.setString(4, objeto.getObservacao());
            stmt.setInt(5, objeto.getLocal_id());
            stmt.setInt(6, objeto.getStatus_id());
            stmt.setInt(7, objeto.getFuncionario_id());
            stmt.setInt(8, objeto.getCategoria_id());
            stmt.setString(9, objeto.getMarca());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idGerado = rs.getInt(1);
                }
            }
            return idGerado;
        }
    }

    //Pronto ✅
    public boolean alterarObjetos(Objeto objeto) {
        String sql = "UPDATE objetos SET nome = ?, lacre = ?, condicao = ?, observacao = ?, local_id = ?, status_id = ?, funcionario_id = ?, categoria_id = ?, marca = ? WHERE id = ?";

        try (Connection connDb = conexao.conectar(); PreparedStatement stmt = connDb.prepareStatement(sql)) {

            stmt.setString(1, objeto.getNome());
            stmt.setInt(2, objeto.getLacre());
            stmt.setString(3, objeto.getCondicao());
            stmt.setString(4, objeto.getObservacao());
            stmt.setInt(5, objeto.getLocal_id());
            stmt.setInt(6, objeto.getStatus_id());
            stmt.setInt(7, objeto.getFuncionario_id());
            stmt.setInt(8, objeto.getCategoria_id());
            stmt.setString(9, objeto.getMarca());
            stmt.setInt(10, objeto.getId());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (Exception erro) {
            System.out.println("Erro ao alterar objetos: " + erro);
            return false;
        }
    }

    //Pronto ✅
    public boolean alterarObjetos(Objeto objeto, Connection connDb) throws Exception {
        String sql = "UPDATE objetos SET nome = ?, lacre = ?, condicao = ?, observacao = ?, local_id = ?, status_id = ?, funcionario_id = ?, categoria_id = ?, marca = ? WHERE id = ?";

        try (PreparedStatement stmt = connDb.prepareStatement(sql)) {

            stmt.setString(1, objeto.getNome());
            stmt.setInt(2, objeto.getLacre());
            stmt.setString(3, objeto.getCondicao());
            stmt.setString(4, objeto.getObservacao());
            stmt.setInt(5, objeto.getLocal_id());
            stmt.setInt(6, objeto.getStatus_id());
            stmt.setInt(7, objeto.getFuncionario_id());
            stmt.setInt(8, objeto.getCategoria_id());
            stmt.setString(9, objeto.getMarca());
            stmt.setInt(10, objeto.getId());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
    }

    //Pronto ✅
    public boolean desativarObjetos(Objeto objeto) {
        String sql = "UPDATE objetos SET ativado = 0 WHERE id = ?";

        try (Connection connDb = conexao.conectar(); PreparedStatement stmt = connDb.prepareStatement(sql)) {

            stmt.setInt(1, objeto.getId());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (Exception erro) {
            System.out.println("Erro ao deletar objetos: " + erro);
            return false;
        }
    }

/* A finalizar⚠️
    public List<Objetos> listarTodosObjetos() {
        List<Objetos> listaDeObjetos = new ArrayList<>();
        String sql = "SELECT id, nome, data_registro, lacre, condicao, observacao, local_id, status_id, funcionario_id, categoria_id FROM objetos";

        try (Connection connDb = conexao.conectar();
            PreparedStatement ps = connDb.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Objetos objeto = new Objetos();

                    //usar só para teste || objeto.setId(rs.getInt("id"));
                    objeto.setNome(rs.getString("nome"));
                    objeto.setMarca(rs.getString("marca"));
                    objeto.setDataRegistro(rs.getTimestamp("data_registro"));
                    objeto.setLacre(rs.getInt("lacre"));
                    objeto.setCondicao(rs.getString("condicao"));
                    objeto.setObservacao(rs.getString("observacao"));
                    //usar só para teste || objeto.setLocal_id(rs.getInt("local_id"));
                    //usar só para teste || objeto.setStatus_id(rs.getInt("status_id"));
                    //usar só para teste || objeto.setFuncionario_id(rs.getInt("funcionario_id"));
                    //usar só para teste || objeto.setCategoria_id(rs.getInt("categoria_id"));

                    listaDeObjetos.add(objeto);

                }
            }catch (SQLException erro) {
            // Se qualquer coisa no 'try' der errado (SQL inválido, banco offline),
            // o erro (do tipo SQLException) é capturado aqui.
            System.out.println("Erro ao pesquisar todos os objetos: " + erro.getMessage());
            erro.printStackTrace(); // Imprime o "mapa" do erro no console
        }
        // Retorno do Método.
        // Retorna a 'listaDeObjetos' para o método que chamou 'pesquisarTodosObjetos()'.
        // Se o 'try' falhou ou se o 'while' nunca executou, a lista vazia
        // (criada na linha 1) será retornada.
            return listaDeObjetos;
    }

    public List<Objetos> listarCondicao(){
        List<Objetos> listaDeObjetos = new ArrayList<>();
        String sql = "SELECT nome, condicao FROM objetos";

        try (Connection connDb = conexao.conectar();
            PreparedStatement ps = connDb.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Objetos objeto = new Objetos();

                objeto.setNome(rs.getString("nome"));
                objeto.setCondicao(rs.getString("condicao"));

                listaDeObjetos.add(objeto);
            }
        }catch (SQLException erro) {
            // Se qualquer coisa no 'try' der errado (SQL inválido, banco offline),
            // o erro (do tipo SQLException) é capturado aqui.
            System.out.println("Erro ao pesquisar todos os objetos: " + erro.getMessage());
            erro.printStackTrace(); // Imprime o "mapa" do erro no console
        }
        // Retorno do Método.
        // Retorna a 'listaDeObjetos' para o método que chamou 'pesquisarTodosObjetos()'.
        // Se o 'try' falhou ou se o 'while' nunca executou, a lista vazia
        // (criada na linha 1) será retornada.
        return listaDeObjetos;
    }

    public List<Objetos> listarObjetosPreservados(List<String> condicoes){

        //Caso não tenha filtros, retona tudo ou somente uma lista vazia
        if( condicao == null || condicao.isEmpty()){
            //lista vazia
        return listarTodosObjetos(); //chama o metodo listarTodosObjetos
        }

        String sql = "SELECT nome, marca, condicao FROM objetos WHERE condicao IN = (?, ?,?) ";

        try (Connection connDb = conexao.conectar();
             PreparedStatement ps = connDb.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Objetos objeto = new Objetos();

                objeto.setNome(rs.getString("nome"));
                objeto.setMarca(rs.getString("marca"));
                objeto.setCondicao(rs.getString("condicao"));

                listaDeObjetos.add(objeto);
            }
        }catch (SQLException erro) {
            // Se qualquer coisa no 'try' der errado (SQL inválido, banco offline),
            // o erro (do tipo SQLException) é capturado aqui.
            System.out.println("Erro ao pesquisar todos os objetos: " + erro.getMessage());
            erro.printStackTrace(); // Imprime o "mapa" do erro no console
        }
        // Retorno do Método.
        // Retorna a 'listaDeObjetos' para o método que chamou 'pesquisarTodosObjetos()'.
        // Se o 'try' falhou ou se o 'while' nunca executou, a lista vazia
        // (criada na linha 1) será retornada.
        return listaDeObjetos;

    }
 */

}