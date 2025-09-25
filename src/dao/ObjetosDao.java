package dao;
//Importação da DAO para organização das classes no projeto

import util.ConexaoDB;
//Aqui importamos a classe ConexaoDB" criada para estabelecer a conecxão com o banco
//Essa classe deve/tem um metodo para abriur a conexao com o banco de dados. EX: conexao.conectar

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//Essas são as classes da biblioteca JDBC (java Database Conncetiviity).
//Connection -> Representa uma conexão ativa com o banco
//PreparedStatement -> usado para escrever os comando SQL com parâmetros
//ResultSet -> usado para armazenar e percorrer os resultados de um SELECT.

public class ObjetosDao {
    private Connection conexao = new ConexaoDB();
    //Aqui é criado o objeto "conexao" que vai ser usado
    //em todos os metodos abaixo para se conectar ao Banco de dados.

    public boolean inserirObjeto() {
        try {
            Connection conexaoBanco = conexao.conectar();
            //Abre uma conecxao com o banco atraves do metodo conectar da classe ConexaoDB
            //Sendo assim, temos acesso ao banco de dados atravésdo obeto conexaoBanco

            PreparedStatement adicionarObjeto = conexaoBanco.prepareStatement("INSERT INTO objetos" + "(nome, observacao, condicao, lacre, data_registro " +
                    "categoria_fk, funcionario_fk, status_fk ,local_fk ) + VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ;");
            //Preparamos um comando SQL do tipo INSERT para inserir informações na tabela "objetos"
            //Os "?" são "placeholders",ou seja, espaços reservados para colocarmos valores

            adicionarObjeto.setInt(); // Substitui o 1º "?" pelo número.
            adicionarObjeto.setInt(); // Substitui o 2º "?" pelo número.
            adicionarObjeto.setInt();
            adicionarObjeto.setInt();
            adicionarObjeto.setInt();
            adicionarObjeto.setTimestamp();
            adicionarObjeto.setInt();
            adicionarObjeto.setCharacterStream();
            adicionarObjeto.setString();
            adicionarObjeto.setString();
            // Aqui atribuimos valores reais para cada "placeholder" ->  "?" do comando SQL.

            int linhaAfetadaSQL = adicionarObjeto.executeUpdate();
            //Executa o comando SQL ao banco
            //O retorno é um inteiro para mostrar quantas linhas do banco foram inseridas/afetadas.

            conexaoBanco.close();
            //Fecha a conexao com o banco de dados (boa pratica para liberar recursos e não consumir memoria)

            return linhaAfetadaSQL > 0;
            //Se pelo menos ' linha for afetada (inserida), retorna "true", indicando que teve sucesso ao inserir informações

        } catch (Exception erro) {
            System.out.println("Erro ao inserir objetos " + erro);
            //Caso de algum erro, imprime a mensagem + o erro no console

            return false;
            //E retorna false, indicando que a operação não de certo
        }
    }

    public boolean deletarObjetos() {
        try {
            Connection conexaoBanco = conexao.conectar();
            // Abre a conexão com o banco.

            PreparedStatement removerObjeto = conexaoBanco.prepareStatement(
                    "DELETE FROM objetos WHERE id = ? ;"
            );
            // Preparamos um comando SQL de DELETE.
            // Ele vai remover um registro da tabela "objetos", mas precisa de um "id" específico.

            removerObjeto.setInt(1, 1);
            // Aqui estamos definindo que o "id" será 1.
            // Ou seja, vai apagar o registro com id = 1 da tabela.

            int linhaAfetada = removerObjeto.executeUpdate();
            // Executa o DELETE no banco.
            // O retorno indica quantas linhas foram apagadas.

            conexaoBanco.close();
            // Fecha a conexão.

            return linhaAfetada > 0;
            // Retorna true se conseguiu apagar pelo menos 1 registro.

        } catch (Exception erro) {
            System.out.println("Erro ao deletar Objetos" + erro);
            return false;
        }
    }

    public boolean atualizarObjetos() {
        try {
            Connection conexaoBanco = conexao.conectar();
            // Abre a conexão com o banco.

            PreparedStatement alterarObjeto = conexaoBanco.prepareStatement(
                    "UPDATE objetos SET nome = ?, observacao = ?, condicao = ?, lacre = ?, data_registro = ?, " +
                            "categoria_fk = ?, funcionario_fk = ?, status_fk = ?, local_fk = ? WHERE id = ? ;"
            );
            // Cria o comando SQL para atualizar dados de um registro.
            // Ele vai trocar as informações de um objeto que tenha o "id" informado.

            alterarObjeto.setInt(); //Substitui o 1º "?" pelo número.
            alterarObjeto.setInt(); //Substitui o 2º "?" pelo número.
            alterarObjeto.setInt();
            alterarObjeto.setInt();
            alterarObjeto.setInt();
            alterarObjeto.setTimestamp();
            alterarObjeto.setInt();
            alterarObjeto.setCharacterStream();
            alterarObjeto.setString();
            alterarObjeto.setString();

            int linhaAfetada = alterarObjeto.executeUpdate();
            // Executa o UPDATE e retorna quantas linhas foram modificadas.

            conexaoBanco.close();
            return linhaAfetada > 0;
            // Se pelo menos 1 linha foi alterada, significa que funcionou.

        } catch (Exception erro) {
            System.out.println("Erro ao atualizar Objetos!" + erro);
            return false;
        }
    }

    public void pesquisarObjetos() {
        try {
            Connection conexaoBanco = conexao.conectar();
            // Abre a conexão.

            PreparedStatement procurarObjeto = conexaoBanco.prepareStatement(
                    "SELECT nome, observacao, condicao, lacre , data_registro" +
                            "categoria_fk, funcionario_fk, status_fk, local_fk WHERE id = ? ;"
            );
            // Cria o comando SQL de SELECT para buscar o nome e o preço de um objeto específico.

            procurarObjeto.setInt(1, 1);
            // Define o id que será pesquisado (aqui fixo no número 1).

            ResultSet resultado = procurarObjeto.executeQuery();
            // Executa o SELECT e armazena os resultados dentro do objeto "resultado".

            while (resultado.next()) {
                //O "while" percorre os resultados.
                //Mesmo sendo apenas 1 registro, é usado while porque o ResultSet pode ter várias linhas.

                String nome = resultado.getString("nome");
                String observacao = resultado.getString("observacao");
                String condicao = resultado.getString("condicao");
                String lacre = resultado.getString("lacre");
                String data_registro = resultado.getString("data_registro");
                String categoria_fk = resultado.getString("categoria_fk");
                String funcionario_fk = resultado.getString("funcionario_fk");
                String status_fk = resultado.getString("status_fk");
                String local_fk = resultado.getString("local_fk");
                // Pega o valor da coluna "nome/observacao etc".


                System.out.println(nome + " nome " + observacao + " observacao" + condicao + "condicao" + lacre + "lacre" +
                        data_registro + "data_registro" + categoria_fk + "categoria_fk" + funcionario_fk + "funcionario_fk" +
                        status_fk + "status_fk" + local_fk + "local_fk");
                // Imprime no console os valores encontrados.
            }

            conexaoBanco.close();
            // Fecha a conexão.

        } catch (Exception erro) {
            System.out.println("Erro ao pesquisar por Objetos!" + erro);
        }
    }
}
