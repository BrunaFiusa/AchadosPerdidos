package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Destinatario;

public class DestinatarioDaoTest {

    // 🔑 ID FIXO PARA TESTES MANUAIS: Defina um ID que você quer testar (se já existir no banco).
    private static final Integer ID_DESTINATARIO_TESTE = 1;

    // O método main é a porta de entrada para a execução
    public static void main(String[] args) {

        System.out.println("--- INICIANDO TESTES CRUD DESTINATARIO ---");

        // Instancia o DAO
        DestinatarioDao destinatarioDao = new DestinatarioDao();

        // -----------------------------------------------------------
        // 🚨 COMENTE/DESCOMENTE O BLOCO QUE VOCÊ QUER EXECUTAR
        // -----------------------------------------------------------

        // 1. TESTE CREATE
        // Para testar CREATE, use o método abaixo. Ele gera dados ÚNICOS.
        //Destinatario destinatarioCriado = testeCriarDestinatario(destinatarioDao);

        // 2. TESTE READ (Buscar todos)
        //testeListarTodos(destinatarioDao);

        // 2.5 TESTE READ (Buscar por id)
         //testeBuscarPorId(destinatarioDao);

        // 3. TESTE UPDATE (Atualizar)
        //Para usar um ID fixo, use
        testeAtualizar(destinatarioDao);
//         if (destinatarioCriado != null) {
//             testaAtualizar(destinatarioDao, destinatarioCriado.getId());
//         }

        // 4. TESTE DELETE
        // Para usar um ID fixo, use testeDeletar(destinatarioDao, ID_DESTINATARIO_TESTE);
        // if (destinatarioCriado != null) {
        //     testaDeletar(destinatarioDao, destinatarioCriado.getId());
        // }

        System.out.println("--- TESTES CRUD FINALIZADOS ---");
    }

    // =================================================================
    // MÉTODOS DE TESTE (FORA DO MAIN)
    // =================================================================

    //----------------------- CREATE -----------------------

    // Este método gera dados únicos para CPF, RG e Telefone para evitar
    // a exceção de violação de UNIQUE do banco na segunda execução.
//    private static Destinatario testeCriarDestinatario(DestinatarioDao dao) {
//        System.out.println("\n[1. TESTE CREATE]");
//        try {
//
//
//
//            // 1. CRIAÇÃO DO OBJETO PRINCIPAL (Destinatario)
//            Destinatario novoDestinatario = new Destinatario(
//                    "Novo Destinatario Teste ",
//                    // Garante unicidade para o campo telefone
//                    "(83) 9941-5243",
//                    // Garante unicidade para o campo RG
//                    "87.488.758-0",
//                    // Garante unicidade para o campo CPF
//                    "746.531.795-0"
//            );
//
//            // 2. EXECUÇÃO DO DAO
//            dao.salvar(novoDestinatario);
//
//            // Exibe o sucesso da criação, incluindo o ID gerado pelo banco.
//            System.out.println("✅ CREATE: Destinatario salvo com sucesso! ID: " + novoDestinatario.getId());
//            System.out.println("   -> CPF gerado: " + novoDestinatario.getCpf());
//            // Retorna o objeto criado (agora com ID) para ser usado em testes sequenciais.
//            return novoDestinatario;
//
//        } catch (Exception e) {
//            System.out.println("❌ ERRO NO CREATE: Falha ao criar Destinatário (Verifique se as restrições UNIQUE estão sendo violadas).");
//            System.out.println("Detalhe: " + e.getMessage());
//            e.printStackTrace();
//            return null;
//        }
//    }

    //----------------------- READ = ALL -----------------------

//    private static void testeListarTodos(DestinatarioDao dao) {
//        System.out.println("\n[2. TESTE READ - Listar Todos]");
//        try {
//            // Executa o método DAO que busca todos os registros.
//            List<Destinatario> destinatarios = dao.listarTodos();
//
//            if (destinatarios != null && !destinatarios.isEmpty()) {
//                System.out.println("✅ READ: Total de destinatários encontrados: " + destinatarios.size());
//                // Itera sobre a lista para exibir os dados.
//                for (Destinatario d : destinatarios) {
//                    System.out.printf("   - ID: %d --- Nome: %s --- Telefone: %s --- CPF: %s --- RG: %s---\n",
//                            d.getId(), d.getNome(), d.getTelefone(), d.getCpf(), d.getRg());
//                }
//            } else {
//                // Caso a lista esteja vazia.
//                System.out.println("⚠️ READ: Nenhum destinatário encontrado.");
//            }
//        } catch (Exception e) {
//            System.out.println("❌ ERRO NO READ: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    //----------------------- READ = ID -----------------------
//
//    private static void testeBuscarPorId(DestinatarioDao dao) {
//        System.out.println("\n[2.5. TESTE READ - Buscar Por ID]");
//
//        // 🔑 SELEÇÃO MANUAL: Usa a constante definida no topo da classe.
//        Integer idParaBusca = 30;
//
//        try {
//            // 1. Executa o método DAO para buscar um único destinatário pelo ID.
//            Destinatario destinatarioEncontrado = dao.buscarPorId(idParaBusca);
//
//            // 2. Validação.
//            if (destinatarioEncontrado != null) {
//                // Se encontrado, exibe os detalhes.
//                System.out.println("✅ READ (ID): Destinatário ID " + idParaBusca + " encontrado com sucesso!");
//                System.out.printf("   -> Nome: %s --- Telefone: %s --- RG: %s --- CPF: %s\n",
//                        destinatarioEncontrado.getNome(),
//                        destinatarioEncontrado.getTelefone(),
//                        destinatarioEncontrado.getRg(),
//                        destinatarioEncontrado.getCpf());
//            } else {
//                // Se não encontrado.
//                System.out.println("❌ READ (ID): Destinatário ID " + idParaBusca + " não foi encontrado no banco de dados.");
//            }
//        } catch (Exception e) {
//            System.out.println("❌ ERRO NO READ (ID): " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    //----------------------- UPDATE -----------------------
//
    private static void testeAtualizar(DestinatarioDao dao) {
        System.out.println("\n[3. TESTE UPDATE]");

        // 🔑 O ID DO DESTINATÁRIO A SER ATUALIZADO VEM DO PARÂMETRO
        Integer idParaAtualizar = 20;

        try {
            // 1. Busca o Destinatário no banco
            Destinatario destinatarioParaAtualizar = dao.buscarPorId(idParaAtualizar);

            if (destinatarioParaAtualizar != null) {

                // --- NOVOS DADOS PARA ATUALIZAÇÃO ---
                String novoNome = "Destinatario - Nome Atualizado";

                // Geramos um telefone novo único (necessário para o teste)
                String novoTelefone = "998" + idParaAtualizar;
                String novoCpf = "558" + idParaAtualizar;
                String novoRg = "778" + idParaAtualizar;

                // --- VALIDAÇÃO DE IGUALDADE (Evitar Update Desnecessário) ---

                boolean jaAtualizado =
                        destinatarioParaAtualizar.getNome().equals(novoNome) &&
                        destinatarioParaAtualizar.getTelefone().equals(novoTelefone); // Simplificado para os campos alterados
                    destinatarioParaAtualizar.getCpf().equals(novoCpf);
                    destinatarioParaAtualizar.getRg().equals(novoRg);

                if (jaAtualizado) {
                    System.out.println("⚠️ UPDATE: Destinatário ID " + idParaAtualizar + " JÁ ESTÁ ATUALIZADO com essas informações.");
                    return; // Encerra o método de teste
                }


                // --- VALIDAÇÃO DE SUCESSO ---

                Destinatario destinatarioAtualizado = dao.buscarPorId(idParaAtualizar);

                if (novoNome.equals(destinatarioAtualizado.getNome())) {
                    System.out.println("✅ UPDATE: Destinatário ID " + idParaAtualizar + " ATUALIZADO com sucesso.");
                    System.out.println("   -> Novo Nome: " + destinatarioAtualizado.getNome() + destinatarioAtualizado.getTelefone() + destinatarioAtualizado.getCpf()
                    + destinatarioAtualizado.getRg());
                } else {
                    System.out.println("❌ UPDATE: Falha ao confirmar atualização de dados.");
                }
            } else {
                System.out.println("⚠️ UPDATE: Destinatário ID " + idParaAtualizar + " não encontrado para atualização.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO UPDATE: " + e.getMessage());
            e.printStackTrace();
        }
    }
//
//    //----------------------- DELETE -----------------------
//
//    private static void testaDeletar(DestinatarioDao dao, Integer idDestinatario) {
//        System.out.println("\n[4. TESTE DELETE]");
//
//        // 🔑 O ID DO DESTINATÁRIO A SER DELETADO VEM DO PARÂMETRO
//        Integer idParaDeletar = idDestinatario;
//
//        try {
//            // 1. Busca o objeto no banco.
//            Destinatario destinatarioParaRemover = dao.buscarPorId(idParaDeletar);
//
//            // --- VALIDAÇÃO (Já Deletado) ---
//            if(destinatarioParaRemover == null) {
//                System.out.println("⚠️ DELETE: Destinatário ID " + idParaDeletar + " JÁ ESTÁ DELETADO ou não existe.");
//                return;
//            }
//
//            // 2. Remove o objeto encontrado.
//            dao.remover(destinatarioParaRemover);
//
//            // --- VALIDAÇÃO (Confirmação da Deleção) ---
//
//            // 3. Tenta buscar o destinatário deletado novamente.
//            Destinatario destinatarioDeletado = dao.buscarPorId(idParaDeletar);
//
//            if (destinatarioDeletado == null) {
//                System.out.println("✅ DELETE: Destinatário ID " + idParaDeletar + " DELETADO com sucesso.");
//            } else {
//                System.out.println("❌ DELETE: Falha ao deletar Destinatário ID " + idParaDeletar + ".");
//            }
//        } catch (Exception e) {
//            System.out.println("❌ ERRO NO DELETE: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
}