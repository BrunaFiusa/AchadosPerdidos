package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Destinatario;
import java.util.List;

public class DestinatarioDaoTest {

    // O método main é a porta de entrada para a execução
    public static void main(String[] args) {

        System.out.println("--- INICIANDO TESTES CRUD DESTINATARIO ---");

        // Instancia o DAO
        DestinatarioDao destinatarioDao = new DestinatarioDao();

        // Variável para armazenar o objeto criado, se o teste CREATE for rodado primeiro
        Destinatario destinatarioCriado = null;

        // -----------------------------------------------------------
        // 🚨 COMENTE/DESCOMENTE O BLOCO QUE VOCÊ QUER EXECUTAR
        // -----------------------------------------------------------

        // 1. TESTE CREATE
        //destinatarioCriado = testeCriarDestinatario(destinatarioDao);

        // 2. TESTE READ (listarTodos)
        // testeListarTodos(destinatarioDao);

        // 2.5 TESTE READ (buscarPorId)
        // testeBuscarPorId(destinatarioDao, 32 );

        // 3. TESTE UPDATE (atualizar)
        // testeAtualizar(destinatarioDao, 32);

        // 4. TESTE DELETE (remover)
        // testeDeletar(destinatarioDao, 31);

        System.out.println("--- TESTES CRUD FINALIZADOS ---");
    }

    // =================================================================
    // MÉTODOS DE TESTE (FORA DO MAIN)
    // =================================================================

    //----------------------- 1. CREATE -----------------------

    private static Destinatario testeCriarDestinatario(DestinatarioDao dao) {
        System.out.println("\n[1. TESTE CREATE]");
        try {

            // 1. CRIAÇÃO DO OBJETO (com dados únicos)
            Destinatario novoDestinatario = new Destinatario(
                    "Novo Destinatario Teste",
                    "000.000.000-",
                    "00.000.000-",
                    "(99) 9",
                    true
            );

            // 2. EXECUÇÃO DO DAO
            dao.salvar(novoDestinatario);

            // Exibe o sucesso
            System.out.println("✅ CREATE: Destinatario salvo com sucesso! ID: " + novoDestinatario.getId());
            System.out.printf("   -> CPF: %s, RG: %s, Telefone: %s\n",
                    novoDestinatario.getCpf(), novoDestinatario.getRg(), novoDestinatario.getTelefone());
            return novoDestinatario;

        } catch (Exception e) {
            System.out.println("❌ ERRO NO CREATE: Falha ao criar Destinatario (Verifique se as restrições UNIQUE estão sendo violadas).");
            System.out.println("Detalhe: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //----------------------- 2. READ = ALL -----------------------

    private static void testeListarTodos(DestinatarioDao dao) {
        System.out.println("\n[2. TESTE READ - Listar Todos]");
        try {
            List<Destinatario> destinatarios = dao.listarTodos();

            if (destinatarios != null && !destinatarios.isEmpty()) {
                System.out.println("✅ READ: Total de destinatarios encontrados: " + destinatarios.size());
                for (Destinatario d : destinatarios) {
                    System.out.printf("   - ID: %d --- Nome: %s --- CPF: %s --- RG: %s --- ATIVAÇÃO: %b\n",
                            d.getId(), d.getNome(), d.getCpf(), d.getRg(), d.isAtivacao());
                }
            } else {
                System.out.println("⚠️ READ: Nenhum destinatario encontrado.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 2.5 READ = ID -----------------------

    private static void testeBuscarPorId(DestinatarioDao dao, Integer idParaBusca) {
        System.out.println("\n[2.5. TESTE READ - Buscar Por ID]");

        try {
            Destinatario destinatarioEncontrado = dao.buscarPorId(idParaBusca);

            if (destinatarioEncontrado != null) {
                System.out.println("✅ READ (ID): Destinatario ID " + idParaBusca + " encontrado com sucesso!");
                System.out.printf("   -> Nome: %s --- Telefone: %s --- CPF: %s --- RG: %s --- ATIVAÇÃO: %b\n",
                        destinatarioEncontrado.getNome(),
                        destinatarioEncontrado.getTelefone(),
                        destinatarioEncontrado.getCpf(),
                        destinatarioEncontrado.getRg(),
                        destinatarioEncontrado.isAtivacao());
            } else {
                System.out.println("❌ READ (ID): Destinatario ID " + idParaBusca + " não foi encontrado no banco de dados.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ (ID): " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 3. UPDATE -----------------------

    private static void testeAtualizar(DestinatarioDao dao, Integer idParaAtualizar) {
        System.out.println("\n[3. TESTE UPDATE]");

        try {
            // 1. Busca o Destinatario no banco
            Destinatario destinatarioParaAtualizar = dao.buscarPorId(idParaAtualizar);

            if (destinatarioParaAtualizar != null) {

                // --- NOVOS DADOS PARA ATUALIZAÇÃO (Fixos e Claros) ---
                String novoNome = "NOME ATUA3LIZADO";
                String novoTelefone = "Destinatario - TELEFO3NE ATUALIZADO";
                String novoCPF = "Destinatario - CPF ATUALI3ZADO";
                String novoRG = "Destinatario - RG ATUALIZ3ADO";
                Boolean novaAtivacao = null;

                // É CRUCIAL que o novo Telefone, RG e CPF sejam diferentes dos existentes NO BANCO,
                // OU que sejam iguais aos do próprio registro, para não violar a UNIQUE KEY.
                // Como não podemos saber os valores atuais do banco sem mais uma busca,
                // e para o teste funcionar, usaremos valores que assumimos não colidir (ou reutilizamos os existentes).

                // Opção segura para testes manuais: Atualizar apenas o Nome (se o CPF/RG/Tel original já for único)
                // Usaremos um valor único baseado no ID para garantir que, ao rodar a primeira vez,
                // ele mude, e na segunda, ele possa ser reconhecido como 'já atualizado'.
                //String novoTelefoneTeste = "(83) 99876-" + String.format("%04d", idParaAtualizar);

                // 2. Define os novos valores no objeto

                // ATENÇÃO: Se o novo Telefone for igual a outro registro no banco, o UPDATE falhará.
                // Usaremos o telefone atual para evitar erros UNIQUE, mudando apenas o nome:
                // destinatarioParaAtualizar.setTelefone(novoTelefoneTeste);

                // --- VALIDAÇÃO DE IGUALDADE (Evitar Update Desnecessário) ---
                boolean jaAtualizado = destinatarioParaAtualizar.getNome().equals(novoNome) &&
                destinatarioParaAtualizar.getTelefone().equals(novoTelefone) &&
                destinatarioParaAtualizar.getCpf().equals(novoCPF) &&
                destinatarioParaAtualizar.getRg().equals(novoRG) &&
                destinatarioParaAtualizar.isAtivacao().equals(novaAtivacao);


                if (jaAtualizado) {
                    System.out.println("⚠️ UPDATE: Destinatario ID " + idParaAtualizar + " JÁ ESTÁ ATUALIZADO com esse nome.");
                    return;
                }

                // 3. EXECUÇÃO DO UPDATE
                destinatarioParaAtualizar.setNome(novoNome);
                destinatarioParaAtualizar.setTelefone(novoTelefone);
                destinatarioParaAtualizar.setCpf(novoCPF);
                destinatarioParaAtualizar.setRg(novoRG);
                destinatarioParaAtualizar.setAtivacao(novaAtivacao);

                dao.atualizar(destinatarioParaAtualizar);

                // 4. VALIDAÇÃO DE SUCESSO (Busca novamente)
                Destinatario destinatarioAtualizado = dao.buscarPorId(idParaAtualizar);

                if (novoNome.equals(destinatarioAtualizado.getNome())) {
                    System.out.println("✅ UPDATE: Destinatario ID " + idParaAtualizar + " ATUALIZADO com sucesso.");
                    System.out.printf("   -> Novo Nome: %s\n", destinatarioAtualizado.getNome());
                } else {
                    System.out.println("❌ UPDATE: Falha ao confirmar atualização de dados.");
                }
            } else {
                System.out.println("⚠️ UPDATE: Destinatario ID " + idParaAtualizar + " não encontrado para atualização.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO UPDATE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 4. DELETE -----------------------

    private static void testeDeletar(DestinatarioDao dao, Integer idParaDeletar) {
        System.out.println("\n[4. TESTE DELETE]");

        try {
            // 1. Busca o objeto no banco.
            Destinatario destinatarioParaRemover = dao.buscarPorId(idParaDeletar);

            if (destinatarioParaRemover == null) {
                System.out.println("⚠️ DELETE: Destinatario ID " + idParaDeletar + " JÁ ESTÁ DELETADO ou nunca existiu.");
                return;
            }

            // 2. Remove o objeto encontrado.
            dao.remover(destinatarioParaRemover);

            // 3. VALIDAÇÃO (Confirmação da Deleção)
            Destinatario destinatarioDeletado = dao.buscarPorId(idParaDeletar);

            if (destinatarioDeletado == null) {
                System.out.println("✅ DELETE: Destinatario ID " + idParaDeletar + " DELETADO com sucesso.");
            } else {
                System.out.println("❌ DELETE: Falha ao deletar Destinatario ID " + idParaDeletar + ".");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO DELETE: " + e.getMessage());
            e.printStackTrace();
        }
    }
}