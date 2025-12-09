package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Destino;
import java.util.Date;
import java.util.List;

public class DestinoDaoTest {

    // ⚠️ ID FIXO PARA TESTES: SUBSTITUA 999 PELO ID DE UM DESTINO QUE VOCÊ DESEJA ATUALIZAR/DELETAR
    private static final Integer ID_FIXO_PARA_TESTES = 999;

    public static void main(String[] args) {

        System.out.println("--- INICIANDO TESTES CRUD DESTINO ---");

        // Instancia o DAO
        DestinoDao destinoDao = new DestinoDao();

        // Variável para armazenar o objeto criado, se o teste CREATE for rodado primeiro
        Destino destinoCriado = null;

        // -----------------------------------------------------------
        // 🚨 COMENTE/DESCOMENTE O BLOCO QUE VOCÊ QUER EXECUTAR
        // -----------------------------------------------------------

        // 1. TESTE CREATE
        // destinoCriado = testeCriarDestino(destinoDao);

        // 2. TESTE READ (listarTodos)
        // testeListarTodos(destinoDao);

        // 2.5 TESTE READ (buscarPorId)
        // testeBuscarPorId(destinoDao, 4);

        // 3. TESTE UPDATE (atualizar)
        // testeAtualizar(destinoDao, 9);

        // 4. TESTE DELETE (remover)
        // testeDeletar(destinoDao, 9);

        System.out.println("--- TESTES CRUD FINALIZADOS ---");
    }

    // =================================================================
    // MÉTODOS DE TESTE (FORA DO MAIN)
    // =================================================================

    //----------------------- 1. CREATE -----------------------

    private static Destino testeCriarDestino(DestinoDao dao) {
        System.out.println("\n[1. TESTE CREATE]");
        try {
            // 1. CRIAÇÃO DO OBJETO
            Destino novoDestino = new Destino();
            // Cria um nome único para evitar problemas de teste
            novoDestino.setNome("Destino de Teste - " + new Date().getTime());

            // 2. EXECUÇÃO DO DAO
            dao.salvar(novoDestino);

            // Exibe o sucesso
            System.out.println("✅ CREATE: Destino salvo com sucesso! ID: " + novoDestino.getId());
            System.out.printf("  -> Nome: %s\n", novoDestino.getNome());
            return novoDestino;

        } catch (Exception e) {
            System.out.println("❌ ERRO NO CREATE: Falha ao criar Destino.");
            System.out.println("Detalhe: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //----------------------- 2. READ = ALL -----------------------

    private static void testeListarTodos(DestinoDao dao) {
        System.out.println("\n[2. TESTE READ - Listar Todos]");
        try {
            List<Destino> destinos = dao.listarTodos();

            if (destinos != null && !destinos.isEmpty()) {
                System.out.println("✅ READ: Total de destinos encontrados: " + destinos.size());

                System.out.println("   --------------------------------------");
                System.out.println("   | ID | Nome ");
                System.out.println("   --------------------------------------");

                for (Destino d : destinos) {
                    // Não é seguro acessar a lista 'objetosEncaminhados' aqui devido ao LAZY loading
                    System.out.printf("   | %-2d | %-30s |\n", d.getId(), d.getNome());
                }
                System.out.println("   --------------------------------------");

            } else {
                System.out.println("⚠️ READ: Nenhum destino encontrado.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 2.5 READ = ID -----------------------

    private static void testeBuscarPorId(DestinoDao dao, Integer idParaBusca) {
        System.out.println("\n[2.5. TESTE READ - Buscar Por ID]");

        try {
            // 1. Executa o método DAO para buscar.
            Destino destinoEncontrado = dao.buscarPorId(idParaBusca);

            // 2. Validação.
            if (destinoEncontrado != null) {
                System.out.println("✅ READ (ID): Destino ID " + idParaBusca + " encontrado com sucesso!");
                System.out.printf("  -> Nome: %s\n", destinoEncontrado.getNome());
            } else {
                System.out.println("❌ READ (ID): Destino ID " + idParaBusca + " não foi encontrado no banco de dados.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ (ID): " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 3. UPDATE -----------------------

    private static void testeAtualizar(DestinoDao dao, Integer idParaAtualizar) {
        System.out.println("\n[3. TESTE UPDATE]");

        try {
            Destino destinoParaAtualizar = dao.buscarPorId(idParaAtualizar);

            if (destinoParaAtualizar != null) {

                // --- NOVOS DADOS PARA ATUALIZAÇÃO (Fixos e Claros) ---
                String novoNome = "Destino Atualizado - Fixo";

                // --- VALIDAÇÃO DE IGUALDADE (Evitar Update Desnecessário) ---
                boolean jaAtualizado = destinoParaAtualizar.getNome().equals(novoNome);

                if (jaAtualizado) {
                    System.out.println("⚠️ UPDATE: Destino ID " + idParaAtualizar + " JÁ ESTÁ ATUALIZADO com essas informações.");
                    return;
                }

                // --- EXECUÇÃO DO UPDATE ---
                destinoParaAtualizar.setNome(novoNome);

                dao.atualizar(destinoParaAtualizar);

                // --- VALIDAÇÃO DE SUCESSO ---
                Destino destinoAtualizado = dao.buscarPorId(idParaAtualizar);

                if (novoNome.equals(destinoAtualizado.getNome())) {
                    System.out.println("✅ UPDATE: Destino ID " + idParaAtualizar + " ATUALIZADO com sucesso.");
                    System.out.printf("  -> Novo Nome: %s\n", destinoAtualizado.getNome());
                } else {
                    System.out.println("❌ UPDATE: Falha ao confirmar atualização de dados.");
                }
            } else {
                System.out.println("⚠️ UPDATE: Destino ID " + idParaAtualizar + " não encontrado para atualização.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO UPDATE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 4. DELETE -----------------------

    private static void testeDeletar(DestinoDao dao, Integer idParaDeletar) {
        System.out.println("\n[4. TESTE DELETE]");

        try {
            // 1. Busca o objeto no banco.
            Destino destinoParaRemover = dao.buscarPorId(idParaDeletar);

            if (destinoParaRemover == null) {
                System.out.println("⚠️ DELETE: Destino ID " + idParaDeletar + " JÁ ESTÁ DELETADO ou nunca existiu.");
                return;
            }

            // 2. Remove o objeto encontrado. Se a cascata estiver OK, as FKs também serão removidas.
            dao.remover(destinoParaRemover);

            // --- VALIDAÇÃO (Confirmação da Deleção) ---
            Destino destinoDeletado = dao.buscarPorId(idParaDeletar);

            if (destinoDeletado == null) {
                System.out.println("✅ DELETE: Destino ID " + idParaDeletar + " DELETADO com sucesso.");
            } else {
                System.out.println("❌ DELETE: Falha ao deletar Destino ID " + idParaDeletar + ".");
            }
        } catch (Exception e) {
            // Se o CASCADE não estiver no Destino Model, um erro de ConstraintViolation será lançado aqui.
            System.out.println("❌ ERRO NO DELETE: Falha ao remover Destino.");
            System.out.println("Detalhe: " + e.getMessage());
            e.printStackTrace();
        }
    }
}