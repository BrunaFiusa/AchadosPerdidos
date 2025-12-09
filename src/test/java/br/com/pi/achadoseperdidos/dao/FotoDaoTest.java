package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Foto;
import java.util.List;

public class FotoDaoTest {

    // O método main é a porta de entrada para a execução
    public static void main(String[] args) {

        System.out.println("--- INICIANDO TESTES CRUD FOTO ---");

        // Instancia o DAO
        FotoDao fotoDao = new FotoDao();

        // Variável para armazenar o objeto criado
        Foto fotoCriada = null;

        // -----------------------------------------------------------
        // 🚨 COMENTE/DESCOMENTE O BLOCO QUE VOCÊ QUER EXECUTAR
        // -----------------------------------------------------------


        // 1. TESTE CREATE
         fotoCriada = testeCriarFoto(fotoDao);

        // 2. TESTE READ (listarTodos)
        // testeListarTodos(fotoDao);

        // 2.5 TESTE READ (buscarPorId)
        // testeBuscarPorId(fotoDao, 2);

        // 3. TESTE UPDATE (atualizar)
        // testeAtualizar(fotoDao, 16);

        // 4. TESTE DELETE (remover)
        // testeDeletar(fotoDao, 16);

        System.out.println("--- TESTES CRUD FINALIZADOS ---");
    }

    // =================================================================
    // MÉTODOS DE TESTE (FORA DO MAIN)
    // =================================================================

    //----------------------- 1. CREATE -----------------------

    private static Foto testeCriarFoto(FotoDao dao) {
        System.out.println("\n[1. TESTE CREATE]");
        try {
            // Gerando um caminho único para a foto (simula um arquivo real)
            String caminhoUnico = "/imagens/item_" + System.currentTimeMillis() + ".jpg";

            // 1. CRIAÇÃO DO OBJETO
            // Usamos o construtor vazio e setamos apenas o campo obrigatório (caminho),
            // pois os Listas de relacionamento são populadas posteriormente pelo sistema.
            Foto novaFoto = new Foto();
            novaFoto.setCaminho(caminhoUnico);

            // 2. EXECUÇÃO DO DAO
            dao.salvar(novaFoto);

            // Exibe o sucesso
            System.out.println("✅ CREATE: Foto salva com sucesso! ID: " + novaFoto.getId());
            System.out.printf("   -> Caminho: %s\n", novaFoto.getCaminho());
            return novaFoto;

        } catch (Exception e) {
            System.out.println("❌ ERRO NO CREATE: Falha ao criar Foto.");
            System.out.println("Detalhe: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //----------------------- 2. READ = ALL -----------------------

    private static void testeListarTodos(FotoDao dao) {
        System.out.println("\n[2. TESTE READ - Listar Todos]");
        try {
            List<Foto> fotos = dao.listarTodos();

            if (fotos != null && !fotos.isEmpty()) {
                System.out.println("✅ READ: Total de fotos encontradas: " + fotos.size());
                for (Foto f : fotos) {
                    System.out.printf("   - ID: %d --- Caminho: %s\n",
                            f.getId(), f.getCaminho());
                }
            } else {
                System.out.println("⚠️ READ: Nenhuma foto encontrada.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 2.5 READ = ID -----------------------

    private static void testeBuscarPorId(FotoDao dao, Integer idParaBusca) {
        System.out.println("\n[2.5. TESTE READ - Buscar Por ID]");

        try {
            Foto fotoEncontrada = dao.buscarPorId(idParaBusca);

            if (fotoEncontrada != null) {
                System.out.println("✅ READ (ID): Foto ID " + idParaBusca + " encontrado com sucesso!");
                System.out.printf("   -> Caminho: %s\n", fotoEncontrada.getCaminho());
            } else {
                System.out.println("❌ READ (ID): Foto ID " + idParaBusca + " não foi encontrada no banco de dados.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ (ID): " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 3. UPDATE -----------------------

    private static void testeAtualizar(FotoDao dao, Integer idParaAtualizar) {
        System.out.println("\n[3. TESTE UPDATE]");

        try {
            // 1. Busca a Foto no banco
            Foto fotoParaAtualizar = dao.buscarPorId(idParaAtualizar);

            if (fotoParaAtualizar != null) {

                // --- NOVOS DADOS PARA ATUALIZAÇÃO ---
                String novoCaminho = "/imagens/item_ATUALIZADO_" + idParaAtualizar + ".png";

                // --- VALIDAÇÃO DE IGUALDADE (Evitar Update Desnecessário) ---
                boolean jaAtualizado = fotoParaAtualizar.getCaminho().equals(novoCaminho);

                if (jaAtualizado) {
                    System.out.println("⚠️ UPDATE: Foto ID " + idParaAtualizar + " JÁ ESTÁ ATUALIZADA com esse caminho.");
                    return;
                }

                // 2. Define os novos valores no objeto
                fotoParaAtualizar.setCaminho(novoCaminho);

                // 3. EXECUÇÃO DO UPDATE
                dao.atualizar(fotoParaAtualizar);

                // 4. VALIDAÇÃO DE SUCESSO (Busca novamente)
                Foto fotoAtualizada = dao.buscarPorId(idParaAtualizar);

                if (novoCaminho.equals(fotoAtualizada.getCaminho())) {
                    System.out.println("✅ UPDATE: Foto ID " + idParaAtualizar + " ATUALIZADA com sucesso.");
                    System.out.printf("   -> Novo Caminho: %s\n", fotoAtualizada.getCaminho());
                } else {
                    System.out.println("❌ UPDATE: Falha ao confirmar atualização de dados.");
                }
            } else {
                System.out.println("⚠️ UPDATE: Foto ID " + idParaAtualizar + " não encontrada para atualização.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO UPDATE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 4. DELETE -----------------------

    private static void testeDeletar(FotoDao dao, Integer idParaDeletar) {
        System.out.println("\n[4. TESTE DELETE]");

        try {
            // 1. Busca o objeto no banco.
            Foto fotoParaRemover = dao.buscarPorId(idParaDeletar);

            if (fotoParaRemover == null) {
                System.out.println("⚠️ DELETE: Foto ID " + idParaDeletar + " JÁ ESTÁ DELETADA ou nunca existiu.");
                return;
            }

            // ⚠️ ATENÇÃO: Se esta foto estiver referenciada em RegistroFoto ou DevolucaoFoto
            // (Foreign Key Constraint), a deleção física falhará.
            // Para deletar, você precisaria primeiro deletar as referências nas tabelas filhas.

            // 2. Remove o objeto encontrado.
            dao.remover(fotoParaRemover);

            // 3. VALIDAÇÃO (Confirmação da Deleção)
            Foto fotoDeletada = dao.buscarPorId(idParaDeletar);

            if (fotoDeletada == null) {
                System.out.println("✅ DELETE: Foto ID " + idParaDeletar + " DELETADA com sucesso.");
            } else {
                System.out.println("❌ DELETE: Falha ao deletar Foto ID " + idParaDeletar + ".");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO DELETE: " + e.getMessage());
            e.printStackTrace();
        }
    }
}