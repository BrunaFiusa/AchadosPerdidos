package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Local;
import java.util.List;

public class LocalDaoTest {

    // O método main é a porta de entrada para a execução
    public static void main(String[] args) {

        System.out.println("--- INICIANDO TESTES CRUD LOCAL ---");

        // Instancia o DAO
        LocalDao localDao = new LocalDao();

        // Variável para armazenar o objeto criado
        Local localCriado = null;

        // -----------------------------------------------------------
        // 🚨 COMENTE/DESCOMENTE O BLOCO QUE VOCÊ QUER EXECUTAR
        // -----------------------------------------------------------


        // 1. TESTE CREATE
        // localCriado = testeCriarLocal(localDao);

        // 2. TESTE READ (listarTodos)
        // testeListarTodos(localDao);

        // 2.5 TESTE READ (buscarPorId)
        // testeBuscarPorId(localDao, 1);

        // 3. TESTE UPDATE (atualizar)
        // testeAtualizar(localDao, 16);

        // 4. TESTE DELETE (remover)
        // testeDeletar(localDao, 16);

        System.out.println("--- TESTES CRUD FINALIZADOS ---");
    }

    // =================================================================
    // MÉTODOS DE TESTE (FORA DO MAIN)
    // =================================================================

    //----------------------- 1. CREATE -----------------------

    private static Local testeCriarLocal(LocalDao dao) {
        System.out.println("\n[1. TESTE CREATE]");
        try {
            // Gerando um nome único (para evitar duplicidade em testes repetidos)
            String nomeUnico = "Local Teste - ";

            // 1. CRIAÇÃO DO OBJETO
            Local novoLocal = new Local(nomeUnico);

            // 2. EXECUÇÃO DO DAO
            dao.salvar(novoLocal);

            // Exibe o sucesso
            System.out.println("✅ CREATE: Local salvo com sucesso! ID: " + novoLocal.getId());
            System.out.printf("   -> Nome: %s\n", novoLocal.getNome());
            return novoLocal;

        } catch (Exception e) {
            System.out.println("❌ ERRO NO CREATE: Falha ao criar Local (Verifique restrições UNIQUE).");
            System.out.println("Detalhe: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //----------------------- 2. READ = ALL -----------------------

    private static void testeListarTodos(LocalDao dao) {
        System.out.println("\n[2. TESTE READ - Listar Todos]");
        try {
            List<Local> locais = dao.listarTodos();

            if (locais != null && !locais.isEmpty()) {
                System.out.println("✅ READ: Total de locais encontrados: " + locais.size());
                for (Local l : locais) {
                    System.out.printf("   - ID: %d --- Nome: %s\n",
                            l.getId(), l.getNome());
                }
            } else {
                System.out.println("⚠️ READ: Nenhum local encontrado.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 2.5 READ = ID -----------------------

    private static void testeBuscarPorId(LocalDao dao, Integer idParaBusca) {
        System.out.println("\n[2.5. TESTE READ - Buscar Por ID]");

        try {
            Local localEncontrado = dao.buscarPorId(idParaBusca);

            if (localEncontrado != null) {
                System.out.println("✅ READ (ID): Local ID " + idParaBusca + " encontrado com sucesso!");
                System.out.printf("   -> Nome: %s\n", localEncontrado.getNome());
            } else {
                System.out.println("❌ READ (ID): Local ID " + idParaBusca + " não foi encontrado no banco de dados.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ (ID): " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 3. UPDATE -----------------------

    private static void testeAtualizar(LocalDao dao, Integer idParaAtualizar) {
        System.out.println("\n[3. TESTE UPDATE]");

        try {
            // 1. Busca o Local no banco
            Local localParaAtualizar = dao.buscarPorId(idParaAtualizar);

            if (localParaAtualizar != null) {

                // --- NOVOS DADOS PARA ATUALIZAÇÃO ---
                String novoNome = "Local - Teste ATUALIZADO";

                // --- VALIDAÇÃO DE IGUALDADE (Evitar Update Desnecessário) ---
                boolean jaAtualizado = localParaAtualizar.getNome().equals(novoNome);

                if (jaAtualizado) {
                    System.out.println("⚠️ UPDATE: Local ID " + idParaAtualizar + " JÁ ESTÁ ATUALIZADO com esse nome.");
                    return;
                }

                // 2. Define os novos valores no objeto
                localParaAtualizar.setNome(novoNome);

                // 3. EXECUÇÃO DO UPDATE
                dao.atualizar(localParaAtualizar);

                // 4. VALIDAÇÃO DE SUCESSO (Busca novamente)
                Local localAtualizado = dao.buscarPorId(idParaAtualizar);

                if (novoNome.equals(localAtualizado.getNome())) {
                    System.out.println("✅ UPDATE: Local ID " + idParaAtualizar + " ATUALIZADO com sucesso.");
                    System.out.printf("   -> Novo Nome: %s\n", localAtualizado.getNome());
                } else {
                    System.out.println("❌ UPDATE: Falha ao confirmar atualização de dados.");
                }
            } else {
                System.out.println("⚠️ UPDATE: Local ID " + idParaAtualizar + " não encontrado para atualização.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO UPDATE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 4. DELETE -----------------------

    private static void testeDeletar(LocalDao dao, Integer idParaDeletar) {
        System.out.println("\n[4. TESTE DELETE]");

        try {
            // 1. Busca o objeto no banco.
            Local localParaRemover = dao.buscarPorId(idParaDeletar);

            if (localParaRemover == null) {
                System.out.println("⚠️ DELETE: Local ID " + idParaDeletar + " JÁ ESTÁ DELETADO ou nunca existiu.");
                return;
            }

            // ⚠️ ATENÇÃO: Se o Local estiver relacionado a outras tabelas (ex: Item Perdido),
            // a deleção física pode falhar com erro de Foreign Key Constraint.

            // 2. Remove o objeto encontrado.
            dao.remover(localParaRemover);

            // 3. VALIDAÇÃO (Confirmação da Deleção)
            Local localDeletado = dao.buscarPorId(idParaDeletar);

            if (localDeletado == null) {
                System.out.println("✅ DELETE: Local ID " + idParaDeletar + " DELETADO com sucesso.");
            } else {
                System.out.println("❌ DELETE: Falha ao deletar Local ID " + idParaDeletar + ".");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO DELETE: " + e.getMessage());
            e.printStackTrace();
        }
    }
}