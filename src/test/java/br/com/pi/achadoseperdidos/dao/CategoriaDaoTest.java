package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Categoria;
import java.util.List;

public class CategoriaDaoTest {

    // O método main é a porta de entrada para a execução
    public static void main(String[] args) {

        System.out.println("--- INICIANDO TESTES CRUD CATEGORIA ---");

        // Instancia o DAO
        CategoriaDao categoriaDao = new CategoriaDao();

        // Variável para armazenar o objeto criado, se o teste CREATE for rodado primeiro
        Categoria categoriaCriada = null;

        // -----------------------------------------------------------
        // 🚨 COMENTE/DESCOMENTE O BLOCO QUE VOCÊ QUER EXECUTAR
        // -----------------------------------------------------------

        // 1. TESTE CREATE
         //testeCriarCategoria(categoriaDao);

        // 2. TESTE READ (listarTodos)
         //testeListarTodos(categoriaDao);

        // 2.5 TESTE READ (buscarPorId)
        // Usamos o ID fixo para buscar um registro conhecido.
         //testeBuscarPorId(categoriaDao, 10);

        // 3. TESTE UPDATE (atualizar)
        // Usamos o ID fixo para testar a atualização.
        //testeAtualizar(categoriaDao, 18);

        // 4. TESTE DELETE (remover)
        // Usamos o ID fixo para testar a remoção.
        //testeDeletar(categoriaDao, 16);

        System.out.println("--- TESTES CRUD FINALIZADOS ---");
    }

    // =================================================================
    // MÉTODOS DE TESTE (FORA DO MAIN)
    // =================================================================

    //----------------------- 1. CREATE -----------------------

    private static Categoria testeCriarCategoria(CategoriaDao dao) {
        System.out.println("\n[1. TESTE CREATE]");
        try {
            // 1. CRIAÇÃO DO OBJETO (nome e prazo)
            Categoria novaCategoria = new Categoria(
                    "Documentos",
                    30 // Prazo de 30 dias para testes
            );

            // 2. EXECUÇÃO DO DAO
            dao.salvar(novaCategoria);

            // Exibe o sucesso
            System.out.println("✅ CREATE: Categoria salva com sucesso! ID: " + novaCategoria.getId());
            System.out.printf("   -> Nome: %s, Prazo: %d dias\n", novaCategoria.getNome(), novaCategoria.getPrazo());
            return novaCategoria;

        } catch (Exception e) {
            System.out.println("❌ ERRO NO CREATE: Falha ao criar Categoria (Verifique restrições UNIQUE).");
            System.out.println("Detalhe: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //----------------------- 2. READ = ALL -----------------------

    private static void testeListarTodos(CategoriaDao dao) {
        System.out.println("\n[2. TESTE READ - Listar Todos]");
        try {
            List<Categoria> categorias = dao.listarTodos();

            if (categorias != null && !categorias.isEmpty()) {
                System.out.println("✅ READ: Total de categorias encontradas: " + categorias.size());
                for (Categoria c : categorias) {
                    System.out.printf("   - ID: %d --- Nome: %s --- Prazo: %d dias\n",
                            c.getId(), c.getNome(), c.getPrazo());
                }
            } else {
                System.out.println("⚠️ READ: Nenhuma categoria encontrada.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 2.5 READ = ID -----------------------

    private static void testeBuscarPorId(CategoriaDao dao, Integer idParaBusca) {
        System.out.println("\n[2.5. TESTE READ - Buscar Por ID]");

        try {
            // 1. Executa o método DAO para buscar.
            Categoria categoriaEncontrada = dao.buscarPorId(idParaBusca);

            // 2. Validação.
            if (categoriaEncontrada != null) {
                System.out.println("✅ READ (ID): Categoria ID " + idParaBusca + " encontrada com sucesso!");
                System.out.printf("   -> Nome: %s --- Prazo: %d dias\n",
                        categoriaEncontrada.getNome(),
                        categoriaEncontrada.getPrazo());
            } else {
                System.out.println("❌ READ (ID): Categoria ID " + idParaBusca + " não foi encontrada no banco de dados.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ (ID): " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 3. UPDATE -----------------------

    private static void testeAtualizar(CategoriaDao dao, Integer idParaAtualizar) {
        System.out.println("\n[3. TESTE UPDATE]");

        try {
            Categoria categoriaParaAtualizar = dao.buscarPorId(idParaAtualizar);

            if (categoriaParaAtualizar != null) {

                // --- NOVOS DADOS PARA ATUALIZAÇÃO (Fixos e Claros) ---
                String novoNome = "Categoria Atualizada - Fixo";
                Integer novoPrazo = 60; // Novo prazo de 60 dias

                // --- VALIDAÇÃO DE IGUALDADE (Evitar Update Desnecessário) ---
                boolean jaAtualizado =
                        categoriaParaAtualizar.getNome().equals(novoNome) &&
                        categoriaParaAtualizar.getPrazo().equals(novoPrazo);

                if (jaAtualizado) {
                    System.out.println("⚠️ UPDATE: Categoria ID " + idParaAtualizar + " JÁ ESTÁ ATUALIZADA com essas informações.");
                    return;
                }

                // --- EXECUÇÃO DO UPDATE ---
                categoriaParaAtualizar.setNome(novoNome);
                categoriaParaAtualizar.setPrazo(novoPrazo);

                dao.atualizar(categoriaParaAtualizar);

                // --- VALIDAÇÃO DE SUCESSO ---
                Categoria categoriaAtualizada = dao.buscarPorId(idParaAtualizar);

                if (novoNome.equals(categoriaAtualizada.getNome())) {
                    System.out.println("✅ UPDATE: Categoria ID " + idParaAtualizar + " ATUALIZADA com sucesso.");
                    System.out.printf("   -> Novo Nome: %s, Novo Prazo: %d dias\n", categoriaAtualizada.getNome(), categoriaAtualizada.getPrazo());
                } else {
                    System.out.println("❌ UPDATE: Falha ao confirmar atualização de dados.");
                }
            } else {
                System.out.println("⚠️ UPDATE: Categoria ID " + idParaAtualizar + " não encontrada para atualização.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO UPDATE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 4. DELETE -----------------------

    private static void testeDeletar(CategoriaDao dao, Integer idParaDeletar) {
        System.out.println("\n[4. TESTE DELETE]");

        try {
            // 1. Busca o objeto no banco. (Usando Long no DAO)
            Categoria categoriaParaRemover = dao.buscarPorId(idParaDeletar);

            if (categoriaParaRemover == null) {
                System.out.println("⚠️ DELETE: Categoria ID " + idParaDeletar + " JÁ ESTÁ DELETADA ou nunca existiu.");
                return;
            }

            // 2. Remove o objeto encontrado.
            dao.remover(categoriaParaRemover);

            // --- VALIDAÇÃO (Confirmação da Deleção) ---
            Categoria categoriaDeletada = dao.buscarPorId(idParaDeletar);

            if (categoriaDeletada == null) {
                System.out.println("✅ DELETE: Categoria ID " + idParaDeletar + " DELETADA com sucesso.");
            } else {
                System.out.println("❌ DELETE: Falha ao deletar Categoria ID " + idParaDeletar + ".");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO DELETE: " + e.getMessage());
            e.printStackTrace();
        }
    }
}