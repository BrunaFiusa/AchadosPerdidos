package util;
import controller.UsuariosController;
import dao.*;
import java.sql.Connection;

public class TesteConexao {
    public static void main(String[] args) {
        ConexaoDB conexao = new ConexaoDB();
        UsuariosController usuariosController = new UsuariosController();
        Connection conexaoBanco = conexao.conectar();
        if (conexaoBanco != null) {
            System.out.println("Conexão estabelecida com sucesso!");
            try {

                //Testando a autenticação das classes da DAO

//----- TESTE DE INSERT DAS INFORMAÇÕES DENTRO DO BANCO DE DADOS ------

                ObjetosDao objetoDAO = new ObjetosDao();
                objetoDAO.adicionarObjeto();
                System.out.println("Objeto Inserido com Sucesso!");

                ObjetosEletronicosDao eletronicoDAO = new ObjetosEletronicosDao();
                eletronicoDAO.adicionarEletronico();
                System.out.println("Eletrônico Inserido com Sucesso!");

                ObjetosRoupasDao roupaDAO = new ObjetosRoupasDao();
                roupaDAO.adicionarRoupa();
                System.out.println("Roupa Inserida com Sucesso!");

                DevolucoesDao devolucaoDAO = new DevolucoesDao();
                devolucaoDAO.adicionarUsuario();
                System.out.println("Devolução Inserida com Sucesso!");

                SolicitantesDao solicitanteDAO = new SolicitantesDao();
                solicitanteDAO.adicionarSolicitante();
                System.out.println("Solicitante Inserido com Sucesso!");

                FotosDao fotoDAO = new FotosDao();
                fotoDAO.adicionarFoto();
                System.out.println("Foto Inserida com Sucesso!");

                FuncionariosDao funcionarioDAO = new FuncionariosDao();
                funcionarioDAO.adicionarFuncionario();
                System.out.println("Funcionário Inserido com sucesso!");

                LocalDao localDAO = new LocalDao();
                localDAO.adicionarLocal();
                System.out.println("Local Inserido com Sucesso!");



//----- TESTE DE REMOVER INFORMAÇÕES DENTRO DO BANCO DE DADOS ------


                ObjetosDao objetoDAO = new ObjetosDao();
                objetoDAO.removerObjeto();
                System.out.println("Objeto Removido com Sucesso!");

                ObjetosEletronicosDao eletronicoDAO = new ObjetosEletronicosDao();
                eletronicoDAO.removerEletronico();
                System.out.println("Eletrônico Removido com Sucesso!");

                ObjetosRoupasDao roupaDAO = new ObjetosRoupasDao();
                roupaDAO.removerRoupa();
                System.out.println("Roupa Removida com Sucesso!");

                DevolucoesDao devolucaoDAO = new DevolucoesDao();
                devolucaoDAO.removerDevolucao();
                System.out.println("Devolução Removida com Sucesso!");

                FuncionariosDao funcionarioDAO = new FuncionariosDao();
                funcionarioDAO.removerFuncionario();
                System.out.println("Funcionário Removido com Sucesso!");

                LocalDao localDAO = new LocalDao();
                localDAO.removerLocal();
                System.out.println("Local Removido com Sucesso!");



//----- TESTE DE UPDATE DAS INFORMAÇÕES DENTRO DO BANCO DE DADOS -----


                ObjetosDao objetoDAO = new ObjetosDao();
                objetoDAO.alterarObjeto();
                System.out.println("Objeto Alterado com Sucesso!");

                ObjetosEletronicosDao objetosEletronicosDAO = new ObjetosEletronicosDao();
                objetosEletronicosDAO.alterarEletronico();
                System.out.println("Eletrônico Alterado com Sucesso!");

                ObjetosRoupasDao objetosRoupasDAO = new ObjetosRoupasDao();
                objetosRoupasDAO.alterarRoupa();
                System.out.println("Roupa Alterado com Sucesso!");

                FuncionariosDao funcionariosDAO = new FuncionariosDao();
                funcionariosDAO.alterarPermissao();
                System.out.println("Funcionário Alterado com Sucesso!");

                LocalDao localDAO = new LocalDao();
                localDAO.alterarLocal();
                System.out.println("Local Alterado com Sucesso!");

//----- TESTE DE CONSULTA DAS INFORMAÇÕES DENTRO DO BANCO DE DADOS ------


                ObjetosDao objetoDAO = new ObjetosDao();
                objetoDAO.procurarObjeto();
                System.out.println("Objeto Consultado com Sucesso!");
                
                ObjetosEletronicosDao objetosEletronicosDAO = new ObjetosEletronicosDao();
                objetosEletronicosDAO.procurarEletronico();
                System.out.println("Eletrônico Consultado com Sucesso!");

                ObjetosRoupasDao objetosRoupasDAO = new ObjetosRoupasDao();
                objetosRoupasDAO.procurarRoupa();
                System.out.println("Roupa Consultada com Sucesso!");

                FotosDao fotoDAO = new FotosDao();
                fotoDAO.procurarFoto();
                System.out.println("Foto Consultada com Sucesso!");

                DestinosDao destinoDAO = new DestinosDao();
                destinoDAO.procurarDestino();
                System.out.println("Destino Consultado com Sucesso!");

                StatusDao statusDAO = new StatusDao();
                statusDAO.procurarStatus();
                System.out.println("Status Consultado com Sucesso!");

                CategoriasDao categoriasDAO = new CategoriasDao();
                categoriasDAO.procurarCategoria();
                System.out.println("Categoria Consultada com Sucesso!");


                usuariosController.verificarCredenciais("", "");
                conexaoBanco.close();

                System.out.println("Conexão encerrada");
            } catch (Exception erro) {
                System.out.println("Erro ao encerrar a conexão:" + erro.getMessage());
            }
        } else {
            System.out.println("Falha ao conectar ao banco de dados!");
        }
    }
}
