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

public class FuncionariosDao {
    private Connection conexao = new ConexaoDB();
        //Aqui é criado o objeto "conexao" que vai ser usado
        //em todos os metodos abaixo para se conectar ao Banco de dados.

        public boolean inserirFuncionario() {
            try {
                Connection conexaoBanco = conexao.conectar();
                //Abre uma conecxao com o banco atraves do metodo conectar da classe ConexaoDB
                //Sendo assim, temos acesso ao banco de dados atravésdo obeto conexaoBanco

                PreparedStatement adicionarFuncionario = conexaoBanco.prepareStatement("INSERT INTO funcionarios" + "(nome, email, senha, acesso_fk ) +" +
                        "VALUES (?, ?, ?, ?) ;");
                //Preparamos um comando SQL do tipo INSERT para inserir informações na tabela "funcionarios"
                //Os "?" são "placeholders",ou seja, espaços reservados para colocarmos valores

                adicionarFuncionario.setString(); // Substitui o 1º "?" pela String.
                adicionarFuncionario.setString(); // Substitui o 2º "?" pela String.
                adicionarFuncionario.setString();
                adicionarFuncionario.setInt();
                // Aqui atribuimos valores reais para cada "placeholder" ->  "?" do comando SQL.

                int linhaAfetadaSQL = adicionarFuncionario.executeUpdate();
                //Executa o comando SQL ao banco
                //O retorno é um inteiro para mostrar quantas linhas do banco foram inseridas/afetadas.

                conexaoBanco.close();
                //Fecha a conexao com o banco de dados (boa pratica para liberar recursos e não consumir memoria)

                return linhaAfetadaSQL > 0;
                //Se pelo menos ' linha for afetada (inserida), retorna "true", indicando que teve sucesso ao inserir informações

            } catch (Exception erro) {
                System.out.println("Erro ao inserir funcionários " + erro);
                //Caso de algum erro, imprime a mensagem + o erro no console

                return false;
                //E retorna false, indicando que a operação não de certo
            }
        }

        public boolean deletarFuncionario() {
            try {
                Connection conexaoBanco = conexao.conectar();
                // Abre a conexão com o banco.

                PreparedStatement removerFuncionario = conexaoBanco.prepareStatement(
                        "DELETE FROM funcionarios WHERE id = ? ;");
                // Preparamos um comando SQL de DELETE.
                // Ele vai remover um registro da tabela "funcionario", mas precisa de um "id" específico.

                removerFuncionario.setInt(1, 1);
                // Aqui estamos definindo que o "id" será 1.
                // Ou seja, vai apagar o registro com id = 1 da tabela.

                int linhaAfetadaSQL = removerFuncionario.executeUpdate();
                // Executa o DELETE no banco.
                // O retorno indica quantas linhas foram apagadas.

                conexaoBanco.close();
                // Fecha a conexão.

                return linhaAfetadaSQL > 0;
                // Retorna true se conseguiu apagar pelo menos 1 registro.

            } catch (Exception erro) {
                System.out.println("Erro ao deletar Funcionário " + erro);
                return false;
            }
        }

        public boolean atualizarFuncionario() {
            try {
                Connection conexaoBanco = conexao.conectar();
                // Abre a conexão com o banco.

                PreparedStatement alterarFuncionario = conexaoBanco.prepareStatement(
                        "UPDATE objetos SET nome = ?, email = ?, senha = ?, acesso_fk = ? WHERE id = ? ;"
                );
                // Cria o comando SQL para atualizar dados de um registro.
                // Ele vai trocar as informações do funcionario que tenha o "id" informado.

                alterarFuncionario.setString(); // Substitui o 1º "?" pela String.
                alterarFuncionario.setString(); // Substitui o 2º "?" pela String.
                alterarFuncionario.setString();
                alterarFuncionario.setInt();


                int linhaAfetadaSQL = alterarFuncionario.executeUpdate();
                // Executa o UPDATE e retorna quantas linhas foram modificadas.

                conexaoBanco.close();
                return linhaAfetadaSQL > 0;
                // Se pelo menos 1 linha foi alterada, significa que funcionou.

            } catch (Exception erro) {
                System.out.println("Erro ao atualizar Funcionário!" + erro);
                return false;

            }
        }
    }