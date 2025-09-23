package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoDB {
    private String driver;
    private String url;
    private String usuario;
    private String senha;


// Esse metodo é chamado automaticamente quando criamos um objeto Conexao.
// Aqui, chamamos o metodo carregarConfiguracoes() para inicializar
// os valores de driver, url, usuario e senha.

    public ConexaoDB() {
        carregarConfiguracoes();
    }


    private void carregarConfiguracoes() {
        Properties propriedades = new Properties();
        //Cria um objeto Properties, que vai armazenar os dados lidos dos arquivos config.properties.

        try (InputStream configPropEntrada = getClass()
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            // Tenta abrir o arquivo "config.properties" que deve estar na pasta resources do projeto.
            // O getClassLoader().getResourceAsStream busca esse arquivo dentro do classpath.
            // Se o arquivo não for encontrado, inputPropsConfig será null.

            propriedades.load(configPropEntrada);
            //Lê o conteudo do arquivo e guarda dentrp do obejeto props.

            driver = propriedades.getProperty("driver");
            url = propriedades.getProperty("url");
            usuario = propriedades.getProperty("usuario");
            senha = propriedades.getProperty("senha");
            // Agora, pegamos cada propriedade do arquivo e atribuímos às variáveis da classe.
            // Exemplo:
            // driver = "com.mysql.cj.jdbc.Driver"
            // url = "jdbc:mysql://127.0.0.1:3306/dbhotelaria"
            // usuario = "dbahotel"
            // senha = "dbaHotel123"

        } catch (IOException erro) {
            System.out.println("Erro ao carregar configurações: " + erro.getMessage());

        }
    }

    public Connection conectar() {
        Connection conexaoBanco = null;
        // Criamos a variável que vai guardar a conexão com o banco.
        // Inicialmente ela é null (ainda não tem conexão aberta).

        try {
            // Aqui carregamos a classe do driver do JDBC.
            // Isso é necessário para o DriverManager reconhecer qual banco você quer usar.
            Class.forName(driver);

            //Agora abrimos a conexao com o banco
            //Usamos o DriverManager.getConnection, passando
            //URL do banco, usuário e senha
            conexaoBanco = DriverManager.getConnection(url, usuario, senha);

            return conexaoBanco;
            //Retorna a conexao para quem chamou o metodo

        } catch (SQLException | ClassNotFoundException erro) {
            //SQLException -> erro de banco (senha errada, banco fora do ar, etc
            //ClassNotFoundException -> erro se o driver informado não existir
            System.out.println("Erro conectar ao Banco de Dados" + erro);
            return null;
            //Se a conexão não for recusada, retorna null

        }
    }
}
