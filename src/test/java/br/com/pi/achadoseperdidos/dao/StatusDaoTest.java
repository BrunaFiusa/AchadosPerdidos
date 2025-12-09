package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Status;
import java.util.List;

public class StatusDaoTest {

    // O método main é a porta de entrada para a execução
    public static void main(String[] args) {

        System.out.println("--- INICIANDO TESTES CRUD STATUS ---");

        // Instancia o DAO
        StatusDao statusDao = new StatusDao();

        // Variável para armazenar o objeto criado, se o teste CREATE for rodado primeiro
        Status statusCriado = null;

        // -----------------------------------------------------------
        // 🚨 COMENTE/DESCOMENTE O BLOCO QUE VOCÊ QUER EXECUTAR
        // -----------------------------------------------------------

        // 1. TESTE CREATE
        //statusCriado = testeCriarStatus(statusDao);

        // 2. TESTE READ (listarTodos)
        //testeListarTodos(statusDao);

        // 2.5 TESTE READ (buscarPorId)
        //testeBuscarPorId(statusDao, 4);

        // 3. TESTE UPDATE (atualizar)
        //testeAtualizar(statusDao, 5);

        // 4. TESTE DELETE (remover)
        //testeDeletar(statusDao, 5);

        System.out.println("--- TESTES CRUD FINALIZADOS ---");
    }

    // =================================================================
    // MÉTODOS DE TESTE (FORA DO MAIN)
    // =================================================================

    //----------------------- 1. CREATE -----------------------

    private static Status testeCriarStatus(StatusDao dao) {
        System.out.println("\n[1. TESTE CREATE]");
        try {
            // 1. CRIAÇÃO DO OBJETO (nome)
            Status novoStatus = new Status("TesteQUEFOIREALIZADO1");

            // 2. EXECUÇÃO DO DAO
            dao.salvar(novoStatus);

            // Exibe o sucesso
            System.out.println("✅ CREATE: Status salvo com sucesso! ID: " + novoStatus.getId());
            System.out.printf("   -> Nome: %s\n", novoStatus.getNome());
            return novoStatus;

        } catch (Exception e) {
            System.out.println("❌ ERRO NO CREATE: Falha ao criar Status (Verifique restrições UNIQUE).");
            System.out.println("Detalhe: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //----------------------- 2. READ = ALL -----------------------

    private static void testeListarTodos(StatusDao dao) {
        System.out.println("\n[2. TESTE READ - Listar Todos]");
        try {
            List<Status> statusList = dao.listarTodos();

            if (statusList != null && !statusList.isEmpty()) {
                System.out.println("✅ READ: Total de status encontrados: " + statusList.size());
                for (Status s : statusList) {
                    System.out.printf("   - ID: %d --- Nome: %s\n",
                            s.getId(), s.getNome());
                }
            } else {
                System.out.println("⚠️ READ: Nenhum status encontrado.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 2.5 READ = ID -----------------------

    private static void testeBuscarPorId(StatusDao dao, Integer idParaBusca) {
        System.out.println("\n[2.5. TESTE READ - Buscar Por ID]");

        try {
            // Chamada direta com Integer
            Status statusEncontrado = dao.buscarPorId(idParaBusca);

            if (statusEncontrado != null) {
                System.out.println("✅ READ (ID): Status ID " + idParaBusca + " encontrado com sucesso!");
                System.out.printf("   -> Nome: %s\n", statusEncontrado.getNome());
            } else {
                System.out.println("❌ READ (ID): Status ID " + idParaBusca + " não foi encontrado no banco de dados.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ (ID): " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 3. UPDATE -----------------------

    private static void testeAtualizar(StatusDao dao, Integer idParaAtualizar) {
        System.out.println("\n[3. TESTE UPDATE]");

        try {
            // Busca o Status no banco
            Status statusParaAtualizar = dao.buscarPorId(idParaAtualizar);

            if (statusParaAtualizar != null) {

                // --- NOVOS DADOS PARA ATUALIZAÇÃO (Fixos e Claros) ---
                String novoNome = "ATUALIZADO - Entregue ao Usuário";

                // --- VALIDAÇÃO DE IGUALDADE (Evitar Update Desnecessário) ---
                boolean jaAtualizado = statusParaAtualizar.getNome().equals(novoNome);

                if (jaAtualizado) {
                    System.out.println("⚠️ UPDATE: Status ID " + idParaAtualizar + " JÁ ESTÁ ATUALIZADO com essa informação.");
                    return;
                }

                // --- EXECUÇÃO DO UPDATE ---
                statusParaAtualizar.setNome(novoNome);

                dao.atualizar(statusParaAtualizar);

                // --- VALIDAÇÃO DE SUCESSO ---
                Status statusAtualizado = dao.buscarPorId(idParaAtualizar);

                if (novoNome.equals(statusAtualizado.getNome())) {
                    System.out.println("✅ UPDATE: Status ID " + idParaAtualizar + " ATUALIZADO com sucesso.");
                    System.out.printf("   -> Novo Nome: %s\n", statusAtualizado.getNome());
                } else {
                    System.out.println("❌ UPDATE: Falha ao confirmar atualização de dados.");
                }
            } else {
                System.out.println("⚠️ UPDATE: Status ID " + idParaAtualizar + " não encontrado para atualização.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO UPDATE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 4. DELETE -----------------------

    private static void testeDeletar(StatusDao dao, Integer idParaDeletar) {
        System.out.println("\n[4. TESTE DELETE]");

        try {
            // 1. Busca o objeto no banco.
            Status statusParaRemover = dao.buscarPorId(idParaDeletar);

            if (statusParaRemover == null) {
                System.out.println("⚠️ DELETE: Status ID " + idParaDeletar + " JÁ ESTÁ DELETADO ou nunca existiu.");
                return;
            }

            // 2. Remove o objeto encontrado.
            dao.remover(statusParaRemover);

            // --- VALIDAÇÃO (Confirmação da Deleção) ---
            Status statusDeletado = dao.buscarPorId(idParaDeletar);

            if (statusDeletado == null) {
                System.out.println("✅ DELETE: Status ID " + idParaDeletar + " DELETADO com sucesso.");
            } else {
                System.out.println("❌ DELETE: Falha ao deletar Status ID " + idParaDeletar + ".");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO DELETE: " + e.getMessage());
            e.printStackTrace();
        }
    }
}