//package br.com.pi.achadoseperdidos.dao;
//
//public class FuncionarioDaoTest {
//
//    // O método main é a porta de entrada para a execução
//    public static void main(String[] args) {
//
//        System.out.println("--- INICIANDO TESTES CRUD FUNCIONARIO ---");
//
//        // Instancia os DAOs
//        FuncionarioDao funcionarioDao = new FuncionarioDao();
//        AcessoDao acessoDao = new AcessoDao(); // Necessário para a FK
//
//        // 1. TESTE CREATE
////        Funcionario funcionarioCriado = testaCriarFuncionario (funcionarioDao, acessoDao);
//
////        // 2. TESTE READ (Buscar todos)
//        //testeListarTodos(funcionarioDao); // É preciso modificar este para usar o FuncionarioDao.listarTodos()
//
//        // 2.5 TESTE READ (Buscar por id)
//        //testeBuscarPorId(funcionarioDao);
//
////        // 3. TESTE UPDATE (Atualizar)
//        //testeAtualizar(funcionarioDao);
//
//        // 4. TESTE DELETE
//        //testeDeletar(funcionarioDao);
//
//        System.out.println("--- TESTES CRUD FINALIZADOS ---");
//    }
//
//    // =================================================================
//    // MÉTODOS DE TESTE (FORA DO MAIN)
//    // =================================================================
//
//
//    //----------------------- CREATE -----------------------
//
//
////    private static Funcionario testaCriarFuncionario(FuncionarioDao funcionarioDao, AcessoDao acessoDao) {
////        System.out.println("\n[1. TESTE CREATE]");
////        try {
////            // --- BUSCAR O ACESSO EXISTENTE ---
////
////            // Define o ID do Acesso (chave estrangeira) que será associado ao novo Funcionário.
////            Integer idAcessoExistente = 1;
////
////            // Busca a entidade Acesso no banco de dados usando o AcessoDao.
////            // O Acesso deve existir para que o Funcionário possa ser criado (regra de chave estrangeira).
////            Acesso acessoExistente = acessoDao.buscarPorId(idAcessoExistente);
////
////            // Verifica se a busca pelo Acesso falhou (retornou nulo).
////            if (acessoExistente == null) {
////                // Se não encontrou, exibe uma mensagem de erro.
////                System.out.println("❌ ERRO NO CREATE: O ID de Acesso " + idAcessoExistente + " não foi encontrado no banco. Por favor, crie um registro de Acesso com ID 1/2.");
////
////                // Se o Acesso não foi encontrado, o teste de criação do Funcionário não pode continuar.
////                // Aqui deveria ter um tratamento para criar o Acesso, se necessário, mas o teste falha e retorna.
////                // O trecho abaixo foi removido para evitar lógica duplicada, mas mantém a intenção de falha:
////                if (acessoExistente == null) return null; // Retorna nulo se o Acesso não for encontrado.
////            }
////
////            // 2. CRIAÇÃO DO OBJETO PRINCIPAL (Funcionario)
////            // Instancia um novo objeto Funcionario, passando o objeto Acesso existente (acessoExistente)
////            // e os demais dados (nome, email, senha).
////            Funcionario novoFuncionario = new Funcionario(
////                    acessoExistente,
////                    "PAULOTESTEMIGRATION",
////                    "joaPAULO.teste.MV@pi.com.br",
////                    "senha9090"
////            );
////
////            // 3. EXECUÇÃO DO DAO
////            // Salva o novo objeto Funcionario no banco de dados.
////            // O Hibernate/JPA insere o registro e popula o campo 'id' do objeto 'novoFuncionario'.
////            funcionarioDao.salvar(novoFuncionario);
////
////            // Exibe o sucesso da criação, incluindo o ID gerado pelo banco.
////            System.out.println("✅ CREATE: Funcionario salvo com sucesso! ID: " + novoFuncionario.getId());
////            // Retorna o objeto criado (agora com ID) para ser usado nos testes de UPDATE e DELETE.
////            return novoFuncionario;
////
////        } catch (Exception e) {
////            // Captura qualquer exceção durante o processo (ex: erro de conexão, chave duplicada).
////            System.out.println("❌ ERRO NO CREATE: " + e.getMessage());
////            // Imprime o stack trace completo para depuração.
////            e.printStackTrace();
////            // Retorna nulo indicando falha no teste de criação.
////            return null;
////        }
////    }
//
//
//    //----------------------- READ = ALL -----------------------
//
//
////    private static void testeListarTodos(FuncionarioDao dao) {
////        System.out.println("\n[2. TESTE READ - Listar]");
////        try {
////            // Executa o método DAO que busca todos os registros de Funcionario no banco de dados.
////            // Isso executa a query JPQL "SELECT f FROM Funcionario f".
////            List<Funcionario> funcionarios = dao.listarTodos();
////
////            // Verifica se a lista retornada não está vazia ou nula.
////            if (funcionarios != null && !funcionarios.isEmpty()) {
////                System.out.println("✅ READ: Total de funcionários encontrados: " + funcionarios.size()); // Exibe a quantidade total de registros encontrados.
////                // Itera sobre a lista de Funcionários para exibir os dados de cada um.
////                for (Funcionario f : funcionarios) {
////                    // Exibe o ID e Nome do Funcionário.
////                    // Ao chamar f.getAcesso().getNome(), o Hibernate carrega a entidade Acesso
////                    // associada (Lazy Loading) e confirma que o relacionamento está funcionando.
////                    System.out.printf("   - ID: %d --- Nome: %s --- Email: %s --- Acesso: %s\n",
////                            f.getId(), f.getNome(), f.getEmail(), f.getAcesso().getNome());
////                }
////            } else {
////                // Caso a lista esteja vazia.
////                System.out.println("⚠️ READ: Nenhum funcionário encontrado.");
////            }
////        } catch (Exception e) {
////            System.out.println("❌ ERRO NO READ: " + e.getMessage());
////            e.printStackTrace();
////        }
////    }
//
//
//    //----------------------- READ = ID -----------------------
//
//
////    private static void testeBuscarPorId(FuncionarioDao dao) {
////        System.out.println("\n[2.5. TESTE READ - Buscar Por ID]");
////
////        // 🔑 SELEÇÃO MANUAL: Defina o ID do funcionário que você quer buscar.
////        Integer idParaBusca = 1;
////
////        try {
////            // 1. Executa o método DAO para buscar um único funcionário pelo ID.
////            Funcionario funcionarioEncontrado = dao.buscarPorId(idParaBusca);
////
////            // 2. Validação.
////            if (funcionarioEncontrado != null) {
////                // Se encontrado, exibe os detalhes.
////                System.out.println("✅ READ (ID): Funcionário ID " + idParaBusca + " encontrado com sucesso!");
////                System.out.printf("   -> Nome: %s --- Email: %s --- Senha: %s --- Acesso: %s\n",
////                        funcionarioEncontrado.getNome(),
////                        funcionarioEncontrado.getEmail(),
////                        funcionarioEncontrado.getSenha(),
////                        funcionarioEncontrado.getAcesso().getNome());
////            } else {
////                // Se não encontrado.
////                System.out.println("❌ READ (ID): Funcionário ID " + idParaBusca + " não foi encontrado no banco de dados.");
////            }
////        } catch (Exception e) {
////            System.out.println("❌ ERRO NO READ (ID): " + e.getMessage());
////            e.printStackTrace();
////        }
////    }
//
////    private static void testeAtualizar(FuncionarioDao dao) {
////        System.out.println("\n[3. TESTE UPDATE]");
////
////        // 🔑 O ID DO FUNCIONÁRIO A SER ATUALIZADO AGORA É O PARÂMETRO 'idFuncionario'
////
////        // Você pode definir um ID fixo para testes independentes aqui se quiser:
////        // Integer idParaAtualizar = 18;
////        // Ou, se você quer usar o ID passado pelo main (o que é mais flexível):
////        Integer idParaAtualizar = 20;
////
////        try {
////            // 1. Busca o Funcionário no banco
////            Funcionario funcionarioParaAtualizar = dao.buscarPorId(idParaAtualizar);
////
////            if (funcionarioParaAtualizar != null) {
////
////                // --- NOVOS DADOS PARA ATUALIZAÇÃO ---
////
////                // 2. Define o Acesso (Vamos tentar manter o Acesso 2 para o nosso teste)
////                AcessoDao acessoDao = new AcessoDao();
////                Integer novoIdAcesso = 1;
////                Acesso novoAcesso = acessoDao.buscarPorId(novoIdAcesso);
////
////                if (novoAcesso == null) {
////                    System.out.println("❌ ERRO NO UPDATE: Novo Acesso (ID " + novoIdAcesso + ") não encontrado.");
////                    return;
////                }
////
////                // 3. Define os novos valores (Para o primeiro teste, eles serão diferentes do que está no banco.
////                // Na segunda rodada com o mesmo ID, eles devem ser iguais).
////                String novoNome = "NomeTESTE";
////                // Use um email fixo para que na segunda execução a comparação seja verdadeira
////                String novoEmail = "TESTE.email.update.fixo@pi.com.br";
////                String novaSenha = "TESTE_123";
////
////                // --- VALIDAÇÃO DE IGUALDADE (Evitar Update Desnecessário) ---
////
////                boolean jaAtualizado =
////                        funcionarioParaAtualizar.getNome().equals(novoNome) &&
////                                funcionarioParaAtualizar.getEmail().equals(novoEmail) &&
////                                funcionarioParaAtualizar.getSenha().equals(novaSenha) &&
////                                funcionarioParaAtualizar.getAcesso().getId().equals(novoAcesso.getId());
////
////                if (jaAtualizado) {
////                    // Se todos os campos já estiverem com os novos valores, não faz nada.
////                    System.out.println("⚠️ UPDATE: Funcionario ID " + idParaAtualizar + " JÁ ESTÁ ATUALIZADO com essas informações.");
////                    return; // Encerra o método de teste
////                }
////
////                // --- EXECUÇÃO DO UPDATE (Apenas se houver alteração) ---
////
////                // 4. Aplica os novos valores no objeto
////                funcionarioParaAtualizar.setNome(novoNome);
////                funcionarioParaAtualizar.setEmail(novoEmail);
////                funcionarioParaAtualizar.setSenha(novaSenha);
////                funcionarioParaAtualizar.setAcesso(novoAcesso);
////
////                // 5. Executa o Update no banco
////                dao.atualizar(funcionarioParaAtualizar);
////
////                // --- VALIDAÇÃO DE SUCESSO ---
////
////                Funcionario funcionarioAtualizado = dao.buscarPorId(idParaAtualizar);
////
////                if (novoEmail.equals(funcionarioAtualizado.getEmail())) {
////                    System.out.println("✅ UPDATE: Funcionario ID " + idParaAtualizar + " ATUALIZADO com sucesso.");
////                    System.out.println("   -> Nome: " + funcionarioAtualizado.getNome() + ", Acesso: " + funcionarioAtualizado.getAcesso().getNome());
////                } else {
////                    System.out.println("❌ UPDATE: Falha ao confirmar atualização de dados.");
////                }
////            } else {
////                System.out.println("⚠️ UPDATE: Funcionário ID " + idParaAtualizar + " não encontrado para atualização.");
////            }
////        } catch (Exception e) {
////            System.out.println("❌ ERRO NO UPDATE: " + e.getMessage());
////            e.printStackTrace();
////        }
////    }
//
//    //----------------------- DELETE -----------------------
//
//
////    private static void testeDeletar(FuncionarioDao dao) {
////        System.out.println("\n[4. TESTE DELETE]");
////
////        // 🔑 SELEÇÃO MANUAL: Defina aqui o ID do Funcionário que você deseja deletar.
////        // Se você estiver rodando o CRUD completo, pode comentar esta linha
////        // e usar o parâmetro 'idIgnorado' (que será o ID recém-criado).
////        Integer idParaDeletar = 18;
////
////        try {
////            // 1. Busca o objeto no banco.
////            Funcionario funcionarioParaRemover = dao.buscarPorId(idParaDeletar);
////
////            // --- VALIDAÇÃO (Já Deletado) ---
////            if (funcionarioParaRemover == null) {
////                // Se o funcionário não foi encontrado, ele já está deletado ou o ID é inválido.
////                System.out.println("⚠️ DELETE: Funcionario ID " + idParaDeletar + " JÁ ESTÁ DELETADO ou nunca existiu.");
////                return; // Sai do método sem tentar remover novamente.
////            }
////
////            // 2. Remove o objeto encontrado (o DAO deve receber o objeto para o em.remove funcionar).
////            dao.remover(funcionarioParaRemover);
////
////            // --- VALIDAÇÃO (Confirmação da Deleção) ---
////
////            // 3. Tenta buscar o funcionário deletado novamente.
////            Funcionario funcionarioDeletado = dao.buscarPorId(idParaDeletar);
////
////            if (funcionarioDeletado == null) {
////                // Se a busca retornar nulo, a deleção foi um sucesso.
////                System.out.println("✅ DELETE: Funcionario ID " + idParaDeletar + " DELETADO com sucesso.");
////            } else {
////                // Se a busca retornar algo, a deleção falhou.
////                System.out.println("❌ DELETE: Falha ao deletar Funcionario ID " + idParaDeletar + ".");
////            }
////        } catch (Exception e) {
////            System.out.println("❌ ERRO NO DELETE: " + e.getMessage());
////            e.printStackTrace();
////        }
////    }
//}