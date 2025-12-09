package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Acesso;
import br.com.pi.achadoseperdidos.model.Funcionario;
import java.util.List;

public class FuncionarioDaoTest {

    // O método main é a porta de entrada para a execução
    public static void main(String[] args) {

        System.out.println("--- INICIANDO TESTES CRUD FUNCIONARIO ---");

        // Instancia o DAO do Funcionario
        FuncionarioDao funcionarioDao = new FuncionarioDao();

        // Instancia o DAO do Acesso (necessário para criar um funcionário)
        AcessoDao acessoDao = new AcessoDao();

        // Variável para armazenar o objeto criado
        Funcionario funcionarioCriado = null;

        // -----------------------------------------------------------
        // 🚨 COMENTE/DESCOMENTE O BLOCO QUE VOCÊ QUER EXECUTAR
        // -----------------------------------------------------------

        // 1. TESTE CREATE
        // funcionarioCriado = testeCriarFuncionario(funcionarioDao, acessoDao, 1);

        // 2. TESTE READ (listarTodos)
        // testeListarTodos(funcionarioDao);

        // 2.5 TESTE READ (buscarPorId)
        // testeBuscarPorId(funcionarioDao, 6);

        // 3. TESTE UPDATE (atualizar)
        // testeAtualizar(funcionarioDao, acessoDao, 19, 2);

        // 4. TESTE DELETE (remover)
        // testeDeletar(funcionarioDao, 19);

        System.out.println("--- TESTES CRUD FINALIZADOS ---");
    }

    // =================================================================
    // MÉTODOS DE TESTE (FORA DO MAIN)
    // =================================================================


    //----------------------- 1. CREATE -----------------------

    private static Funcionario testeCriarFuncionario(FuncionarioDao dao, AcessoDao acessoDao, Integer idAcesso) {
        System.out.println("\n[1. TESTE CREATE]");
        try {
            // 0. Pré-requisito: Busca o objeto Acesso obrigatório
            Acesso acessoBase = acessoDao.buscarPorId(idAcesso);

            if (acessoBase == null) {
                System.out.println("❌ ERRO NO CREATE: Acesso ID " + idAcesso + " não encontrado. Crie um registro na tabela 'acessos' primeiro.");
                return null;
            }

            // Gerando email único
            String emailUnico = "funcionario.teste@iguatemi.com";

            // 1. CRIAÇÃO DO OBJETO
            Funcionario novoFuncionario = new Funcionario(
                    acessoBase,
                    "Funcionário Novo Teste",
                    emailUnico,
                    "senha123"
            );

            // 2. EXECUÇÃO DO DAO
            dao.salvar(novoFuncionario);

            // Exibe o sucesso
            System.out.println("✅ CREATE: Funcionário salvo com sucesso! ID: " + novoFuncionario.getId());
            System.out.printf("   -> Nome: %s, Email: %s, Acesso ID: %d\n",
                    novoFuncionario.getNome(), novoFuncionario.getEmail(), novoFuncionario.getAcesso().getId());
            return novoFuncionario;

        } catch (Exception e) {
            System.out.println("❌ ERRO NO CREATE: Falha ao criar Funcionário.");
            System.out.println("Detalhe: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //----------------------- 2. READ = ALL -----------------------

    private static void testeListarTodos(FuncionarioDao dao) {
        System.out.println("\n[2. TESTE READ - Listar Todos]");
        try {
            List<Funcionario> funcionarios = dao.listarTodos();

            if (funcionarios != null && !funcionarios.isEmpty()) {
                System.out.println("✅ READ: Total de funcionários encontrados: " + funcionarios.size());
                for (Funcionario f : funcionarios) {
                    System.out.printf("   - ID: %d --- Nome: %s --- Email: %s --- Acesso ID: %d\n",
                            f.getId(), f.getNome(), f.getEmail(), f.getAcesso().getId());
                }
            } else {
                System.out.println("⚠️ READ: Nenhum funcionário encontrado.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 2.5 READ = ID -----------------------

    private static void testeBuscarPorId(FuncionarioDao dao, Integer idParaBusca) {
        System.out.println("\n[2.5. TESTE READ - Buscar Por ID]");

        try {
            Funcionario funcionarioEncontrado = dao.buscarPorId(idParaBusca);

            if (funcionarioEncontrado != null) {
                System.out.println("✅ READ (ID): Funcionário ID " + idParaBusca + " encontrado com sucesso!");
                System.out.printf("   -> Nome: %s, Email: %s, Acesso: %s (ID: %d)\n",
                        funcionarioEncontrado.getNome(),
                        funcionarioEncontrado.getEmail(),
                        funcionarioEncontrado.getAcesso().getNome(),
                        funcionarioEncontrado.getAcesso().getId());
            } else {
                System.out.println("❌ READ (ID): Funcionário ID " + idParaBusca + " não foi encontrado no banco de dados.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ (ID): " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 3. UPDATE -----------------------

    private static void testeAtualizar(FuncionarioDao dao, AcessoDao acessoDao, Integer idParaAtualizar, Integer novoIdAcesso) {
        System.out.println("\n[3. TESTE UPDATE]");

        try {
            // 1. Busca o Funcionario no banco
            Funcionario funcionarioParaAtualizar = dao.buscarPorId(idParaAtualizar);

            if (funcionarioParaAtualizar != null) {

                // --- NOVOS DADOS PARA ATUALIZAÇÃO ---
                String novoNome = "Funcionario - NOME ATUALIZADO";
                String novaSenha = "senhaNova123";

                // Tenta buscar o NOVO ACESSO (Opcional: se for mudar o nível de permissão)
                Acesso novoAcesso = acessoDao.buscarPorId(novoIdAcesso);

                if (novoAcesso == null) {
                    System.out.println("❌ ERRO NO UPDATE: Novo Acesso ID " + novoIdAcesso + " não encontrado. Manterá o acesso antigo.");
                    novoAcesso = funcionarioParaAtualizar.getAcesso(); // Mantém o antigo
                }

                // --- VALIDAÇÃO DE IGUALDADE (Evitar Update Desnecessário) ---
                boolean jaAtualizado = funcionarioParaAtualizar.getNome().equals(novoNome) &&
                        funcionarioParaAtualizar.getAcesso().getId().equals(novoAcesso.getId());

                if (jaAtualizado) {
                    System.out.println("⚠️ UPDATE: Funcionário ID " + idParaAtualizar + " JÁ ESTÁ ATUALIZADO com esses dados.");
                    return;
                }

                // 2. Define os novos valores no objeto
                funcionarioParaAtualizar.setNome(novoNome);
                funcionarioParaAtualizar.setSenha(novaSenha);
                funcionarioParaAtualizar.setAcesso(novoAcesso); // Atualiza o nível de acesso

                // 3. EXECUÇÃO DO UPDATE
                dao.atualizar(funcionarioParaAtualizar);

                // 4. VALIDAÇÃO DE SUCESSO (Busca novamente)
                Funcionario funcionarioAtualizado = dao.buscarPorId(idParaAtualizar);

                if (novoNome.equals(funcionarioAtualizado.getNome()) && novaSenha.equals(funcionarioAtualizado.getSenha())) {
                    System.out.println("✅ UPDATE: Funcionário ID " + idParaAtualizar + " ATUALIZADO com sucesso.");
                    System.out.printf("   -> Novo Nome: %s, Novo Acesso ID: %d\n",
                            funcionarioAtualizado.getNome(), funcionarioAtualizado.getAcesso().getId());
                } else {
                    System.out.println("❌ UPDATE: Falha ao confirmar atualização de dados.");
                }
            } else {
                System.out.println("⚠️ UPDATE: Funcionário ID " + idParaAtualizar + " não encontrado para atualização.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO UPDATE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 4. DELETE -----------------------

    private static void testeDeletar(FuncionarioDao dao, Integer idParaDeletar) {
        System.out.println("\n[4. TESTE DELETE]");

        try {
            // 1. Busca o objeto no banco.
            Funcionario funcionarioParaRemover = dao.buscarPorId(idParaDeletar);

            if (funcionarioParaRemover == null) {
                System.out.println("⚠️ DELETE: Funcionário ID " + idParaDeletar + " JÁ ESTÁ DELETADO ou nunca existiu.");
                return;
            }

            // 2. Remove o objeto encontrado.
            dao.remover(funcionarioParaRemover);

            // 3. VALIDAÇÃO (Confirmação da Deleção)
            Funcionario funcionarioDeletado = dao.buscarPorId(idParaDeletar);

            if (funcionarioDeletado == null) {
                System.out.println("✅ DELETE: Funcionário ID " + idParaDeletar + " DELETADO com sucesso.");
            } else {
                System.out.println("❌ DELETE: Falha ao deletar Funcionário ID " + idParaDeletar + ".");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO DELETE: " + e.getMessage());
            e.printStackTrace();
        }
    }
}