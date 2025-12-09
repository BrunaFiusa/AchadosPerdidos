package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Acesso;
import java.util.List;

public class AcessoDaoTest {

    // O método main é a porta de entrada para a execução
    public static void main(String[] args) {

        System.out.println("--- INICIANDO TESTES CRUD ACESSO ---");

        // Instancia o DAO
        AcessoDao acessoDao = new AcessoDao();

        // Variável para armazenar o objeto criado
        Acesso acessoCriado = null;

        // -----------------------------------------------------------
        // 🚨 COMENTE/DESCOMENTE O BLOCO QUE VOCÊ QUER EXECUTAR
        // -----------------------------------------------------------


        // 1. TESTE CREATE
        // acessoCriado = testeCriarAcesso(acessoDao);

        // 2. TESTE READ (listarTodos)
        // testeListarTodos(acessoDao);

        // 2.5 TESTE READ (buscarPorId)
        // testeBuscarPorId(acessoDao, 1);

        // 3. TESTE UPDATE (atualizar)
        // testeAtualizar(acessoDao, 9);

        // 4. TESTE DELETE (remover)
        // testeDeletar(acessoDao, 9);

        System.out.println("--- TESTES CRUD FINALIZADOS ---");
    }

    // =================================================================
    // MÉTODOS DE TESTE (FORA DO MAIN)
    // =================================================================

    //----------------------- 1. CREATE -----------------------

    private static Acesso testeCriarAcesso(AcessoDao dao) {
        System.out.println("\n[1. TESTE CREATE]");
        try {
            // Gerando um nome único usando o timestamp atual
            String nomeUnico = "Acesso Teste - ";

            // 1. CRIAÇÃO DO OBJETO
            Acesso novoAcesso = new Acesso(nomeUnico);
            // Note: Não precisamos popular a lista de funcionários aqui, pois é LAZY e mappedBy.

            // 2. EXECUÇÃO DO DAO
            dao.salvar(novoAcesso);

            // Exibe o sucesso
            System.out.println("✅ CREATE: Acesso salvo com sucesso! ID: " + novoAcesso.getId());
            System.out.printf("   -> Nome: %s\n", novoAcesso.getNome());
            return novoAcesso;

        } catch (Exception e) {
            System.out.println("❌ ERRO NO CREATE: Falha ao criar Acesso (Verifique restrições UNIQUE).");
            System.out.println("Detalhe: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //----------------------- 2. READ = ALL -----------------------

    private static void testeListarTodos(AcessoDao dao) {
        System.out.println("\n[2. TESTE READ - Listar Todos]");
        try {
            List<Acesso> acessos = dao.listarTodos();

            if (acessos != null && !acessos.isEmpty()) {
                System.out.println("✅ READ: Total de acessos encontrados: " + acessos.size());
                for (Acesso a : acessos) {
                    // Nota: A lista de funcionários não é carregada (LAZY) nesta listagem simples.
                    System.out.printf("   - ID: %d --- Nome: %s\n",
                            a.getId(), a.getNome());
                }
            } else {
                System.out.println("⚠️ READ: Nenhum acesso encontrado.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 2.5 READ = ID -----------------------

    private static void testeBuscarPorId(AcessoDao dao, Integer idParaBusca) {
        System.out.println("\n[2.5. TESTE READ - Buscar Por ID]");

        try {
            Acesso acessoEncontrado = dao.buscarPorId(idParaBusca);

            if (acessoEncontrado != null) {
                System.out.println("✅ READ (ID): Acesso ID " + idParaBusca + " encontrado com sucesso!");
                System.out.printf("   -> Nome: %s\n", acessoEncontrado.getNome());

                // Se quiser testar o carregamento da lista (FetchType.LAZY), você precisaria estar
                // dentro da mesma transação ou ter configurado o Open Session In View.
                // Aqui, faremos apenas a busca básica.

            } else {
                System.out.println("❌ READ (ID): Acesso ID " + idParaBusca + " não foi encontrado no banco de dados.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ (ID): " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 3. UPDATE -----------------------

    private static void testeAtualizar(AcessoDao dao, Integer idParaAtualizar) {
        System.out.println("\n[3. TESTE UPDATE]");

        try {
            // 1. Busca o Acesso no banco
            Acesso acessoParaAtualizar = dao.buscarPorId(idParaAtualizar);

            if (acessoParaAtualizar != null) {

                // --- NOVOS DADOS PARA ATUALIZAÇÃO ---
                String novoNome = "Acesso - NOME ADMINISTRADOR ATUALIZADO";

                // --- VALIDAÇÃO DE IGUALDADE (Evitar Update Desnecessário) ---
                boolean jaAtualizado = acessoParaAtualizar.getNome().equals(novoNome);

                if (jaAtualizado) {
                    System.out.println("⚠️ UPDATE: Acesso ID " + idParaAtualizar + " JÁ ESTÁ ATUALIZADO com esse nome.");
                    return;
                }

                // 2. Define os novos valores no objeto
                acessoParaAtualizar.setNome(novoNome);

                // 3. EXECUÇÃO DO UPDATE
                dao.atualizar(acessoParaAtualizar);

                // 4. VALIDAÇÃO DE SUCESSO (Busca novamente)
                Acesso acessoAtualizado = dao.buscarPorId(idParaAtualizar);

                if (novoNome.equals(acessoAtualizado.getNome())) {
                    System.out.println("✅ UPDATE: Acesso ID " + idParaAtualizar + " ATUALIZADO com sucesso.");
                    System.out.printf("   -> Novo Nome: %s\n", acessoAtualizado.getNome());
                } else {
                    System.out.println("❌ UPDATE: Falha ao confirmar atualização de dados.");
                }
            } else {
                System.out.println("⚠️ UPDATE: Acesso ID " + idParaAtualizar + " não encontrado para atualização.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO UPDATE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 4. DELETE -----------------------

    private static void testeDeletar(AcessoDao dao, Integer idParaDeletar) {
        System.out.println("\n[4. TESTE DELETE]");

        try {
            // 1. Busca o objeto no banco.
            Acesso acessoParaRemover = dao.buscarPorId(idParaDeletar);

            if (acessoParaRemover == null) {
                System.out.println("⚠️ DELETE: Acesso ID " + idParaDeletar + " JÁ ESTÁ DELETADO ou nunca existiu.");
                return;
            }

            // ⚠️ ATENÇÃO: Se houver funcionários associados (Foreign Key Constraint),
            // a deleção física pode falhar com erro de integridade referencial.

            // 2. Remove o objeto encontrado.
            dao.remover(acessoParaRemover);

            // 3. VALIDAÇÃO (Confirmação da Deleção)
            Acesso acessoDeletado = dao.buscarPorId(idParaDeletar);

            if (acessoDeletado == null) {
                System.out.println("✅ DELETE: Acesso ID " + idParaDeletar + " DELETADO com sucesso.");
            } else {
                System.out.println("❌ DELETE: Falha ao deletar Acesso ID " + idParaDeletar + ".");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO DELETE: " + e.getMessage());
            // Se o erro for de Foreign Key Constraint, a mensagem aparecerá aqui.
            e.printStackTrace();
        }
    }
}