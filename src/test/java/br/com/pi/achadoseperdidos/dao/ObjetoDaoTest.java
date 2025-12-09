package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Objeto;
import br.com.pi.achadoseperdidos.model.Local;
import br.com.pi.achadoseperdidos.model.Status;
import br.com.pi.achadoseperdidos.model.Funcionario;
import br.com.pi.achadoseperdidos.model.Categoria;
import java.util.Date;
import java.util.List;

public class ObjetoDaoTest {

    // O método main é a porta de entrada para a execução
    public static void main(String[] args) {

        System.out.println("--- INICIANDO TESTES CRUD OBJETO ---");

        // Instancia os DAOs necessários
        ObjetoDao objetoDao = new ObjetoDao();
        LocalDao localDao = new LocalDao();
        StatusDao statusDao = new StatusDao();
        FuncionarioDao funcionarioDao = new FuncionarioDao();
        CategoriaDao categoriaDao = new CategoriaDao();

        // -----------------------------------------------------------
        // 🚨 CONFIGURAÇÃO DE IDS
        // Os IDs devem ser válidos no banco de dados para o CREATE e UPDATE funcionarem.
        // -----------------------------------------------------------

        // -----------------------------------------------------------
        // 🚨 COMENTE/DESCOMENTE O BLOCO QUE VOCÊ QUER EXECUTAR
        // -----------------------------------------------------------

        // 1. TESTE CREATE
         testeCriarObjeto(objetoDao, localDao, statusDao, funcionarioDao, categoriaDao,
                          1, 2, 3, 15);

        // 2. TESTE READ (listarTodos - Apenas Ativos)
        // testeListarTodos(objetoDao);

        // 2.5 TESTE READ (buscarPorId)
        // testeBuscarPorId(objetoDao, 45);

        // 3. TESTE UPDATE (atualizar)
        // testeAtualizar(objetoDao, statusDao, 62, 3);

        // 4. TESTE DELETE LÓGICO (desativar)
        // testeDesativar(objetoDao, 65);

        System.out.println("--- TESTES CRUD FINALIZADOS ---");
    }

    // =================================================================
    // MÉTODOS DE TESTE (FORA DO MAIN)
    // =================================================================

    // Função auxiliar para gerar um número único (usando timestamp) para o Lacre
    private static Integer gerarLacreUnico() {
        String base = String.valueOf(System.currentTimeMillis());
        // Pega os últimos 5 dígitos e garante que seja um inteiro curto
        return Integer.parseInt(base.substring(base.length() - 5));
    }

    //----------------------- 1. CREATE -----------------------

    private static void testeCriarObjeto(ObjetoDao dao, LocalDao localDao, StatusDao statusDao,
                                         FuncionarioDao funcionarioDao, CategoriaDao categoriaDao,
                                         Integer idStatus, Integer idFuncionario, Integer idLocal, Integer idCategoria){
        System.out.println("\n[1. TESTE CREATE]");
        try {
            // 0. PRÉ-REQUISITOS (Objetos FKs)
            Local localBase = localDao.buscarPorId(idLocal);
            Status statusBase = statusDao.buscarPorId(idStatus);
            Funcionario funcionarioBase = funcionarioDao.buscarPorId(idFuncionario);
            Categoria categoriaBase = categoriaDao.buscarPorId(idCategoria);

            if (statusBase == null || funcionarioBase == null) {
                System.out.println("❌ ERRO NO CREATE: Status ID " + idStatus + " ou Funcionário ID " + idFuncionario + " não encontrado. Crie os registros FKs primeiro.");
                return;
            }

            // 1. CRIAÇÃO DO OBJETO
            Objeto novoObjeto = new Objeto();
            novoObjeto.setLocal(localBase);
            novoObjeto.setStatus(statusBase);
            novoObjeto.setFuncionario(funcionarioBase);
            novoObjeto.setCategoria(categoriaBase);

            novoObjeto.setLacre(111111);
            novoObjeto.setNome(" TesteEletronico ");
            novoObjeto.setObservacao("Encontrado próximo ao caixa da loja X.");
            novoObjeto.setMarca("Tommy Hilfiger");
            novoObjeto.setCondicao("Ótima, poucas marcas de uso.");
            novoObjeto.setAtivado(true); // Sempre ativo ao criar
            novoObjeto.setDataRegistro(new Date());

            // 2. EXECUÇÃO DO DAO
            dao.salvar(novoObjeto);

            // Exibe o sucesso
            System.out.println("✅ CREATE: Objeto salvo com sucesso! ID: " + novoObjeto.getId());
            System.out.printf("   -> Lacre: %d | Nome: %s | Status: %s\n",
                    novoObjeto.getLacre(), novoObjeto.getNome(), novoObjeto.getStatus().getNome());

        } catch (Exception e) {
            System.out.println("❌ ERRO NO CREATE: Falha ao criar Objeto (Verifique o Lacre UNIQUE).");
            System.out.println("Detalhe: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 2. READ = ALL (APENAS ATIVOS) -----------------------

    private static void testeListarTodos(ObjetoDao dao) {
        System.out.println("\n[2. TESTE READ - Listar Todos (Ativos)]");
        try {
            List<Objeto> objetos = dao.listarTodos();

            if (objetos != null && !objetos.isEmpty()) {
                System.out.println("✅ READ: Total de objetos ATIVOS encontrados: " + objetos.size());

                // --- Cabeçalho para melhor organização (Mostra TODAS as colunas) ---
                System.out.println("   ----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("   | ID | Lacre | Nome do Objeto   | Marca     | Condição   | Data Reg.  | Status | Local | Categoria | Func. ID | Ativo | Observação");
                System.out.println("   ----------------------------------------------------------------------------------------------------------------------------------------------------------------------");

                for (Objeto o : objetos) {

                    // Acessando propriedades das FKs com verificação de nulo
                    String statusNome = o.getStatus() != null ? o.getStatus().getNome() : "N/A";
                    String localNome = o.getLocal() != null ? o.getLocal().getNome() : "N/A";
                    String catNome = o.getCategoria() != null ? o.getCategoria().getNome() : "N/A";
                    Integer funcId = o.getFuncionario() != null ? o.getFuncionario().getId() : null;

                    // Formatação da Data (Apenas AAAA-MM-DD)
                    String dataFormatada = o.getDataRegistro() != null ? o.getDataRegistro().toString().substring(0, 10) : "N/A";

                    // Saída formatada
                    System.out.printf("   | %-2d | %-5d | %-15s | %-9s | %-10s | %-10s | %-6s | %-5s | %-9s | %-9d | %-5b | %s\n",
                            o.getId(),
                            o.getLacre(),
                            o.getNome() != null ? o.getNome() : "N/A",
                            o.getMarca() != null ? o.getMarca() : "N/A",
                            o.getCondicao() != null ? o.getCondicao() : "N/A",
                            dataFormatada,
                            statusNome,
                            localNome,
                            catNome,
                            funcId,
                            o.getAtivado(),
                            o.getObservacao() != null ? o.getObservacao() : ""); // Observação no final
                }
                System.out.println("   ----------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            } else {
                System.out.println("⚠️ READ: Nenhum objeto ATIVO encontrado.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 2.5 READ = ID -----------------------

    private static void testeBuscarPorId(ObjetoDao dao, Integer idParaBusca) {
        System.out.println("\n[2.5. TESTE READ - Buscar Por ID]");

        try {
            Objeto objetoEncontrado = dao.buscarPorId(idParaBusca);

            if (objetoEncontrado != null) {
                System.out.println("✅ READ (ID): Objeto ID " + idParaBusca + " encontrado com sucesso!");

                // Acessando propriedades das FKs com verificação de nulo
                String statusNome = objetoEncontrado.getStatus() != null ? objetoEncontrado.getStatus().getNome() : "N/A";
                String localNome = objetoEncontrado.getLocal() != null ? objetoEncontrado.getLocal().getNome() : "N/A";
                String catNome = objetoEncontrado.getCategoria() != null ? objetoEncontrado.getCategoria().getNome() : "N/A";
                Integer funcId = objetoEncontrado.getFuncionario() != null ? objetoEncontrado.getFuncionario().getId() : null;

                // Formata a data (string com data e hora)
                String dataFormatadaDetalhada = objetoEncontrado.getDataRegistro() != null ? objetoEncontrado.getDataRegistro().toString() : "N/A";

                // --- Exibe todos os campos em formato detalhado ---
                System.out.println("---------------------------------------------------------");
                System.out.printf("   -> ID: %d | Lacre: %d | Ativo: %b\n",
                        objetoEncontrado.getId(), objetoEncontrado.getLacre(), objetoEncontrado.getAtivado());

                System.out.printf("   -> Nome: %s | Marca: %s | Condição: %s\n",
                        objetoEncontrado.getNome(), objetoEncontrado.getMarca(), objetoEncontrado.getCondicao());

                System.out.printf("   -> Status: %s (ID: %d) | Local: %s (ID: %d) | Categoria: %s (ID: %d)\n",
                        statusNome, objetoEncontrado.getStatus().getId(),
                        localNome, objetoEncontrado.getLocal() != null ? objetoEncontrado.getLocal().getId() : null,
                        catNome, objetoEncontrado.getCategoria() != null ? objetoEncontrado.getCategoria().getId() : null);

                System.out.printf("   -> Registrado por Func. ID: %d | Data Registro: %s\n",
                        funcId, dataFormatadaDetalhada);

                System.out.printf("   -> Observação: %s\n",
                        objetoEncontrado.getObservacao());
                System.out.println("---------------------------------------------------------");

            } else {
                System.out.println("❌ READ (ID): Objeto ID " + idParaBusca + " não foi encontrado no banco de dados.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ (ID): " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 3. UPDATE / ATUALIZAR STATUS -----------------------

    private static void testeAtualizar(ObjetoDao dao, StatusDao statusDao, Integer idParaAtualizar, Integer novoIdStatus) {
        System.out.println("\n[3. TESTE UPDATE]");

        try {
            // 1. Busca o Objeto no banco
            Objeto objetoParaAtualizar = dao.buscarPorId(idParaAtualizar);

            if (objetoParaAtualizar != null) {

                // --- NOVOS DADOS PARA ATUALIZAÇÃO ---
                Status novoStatus = statusDao.buscarPorId(novoIdStatus);
                String novaObservacao = "Status ATUALIZADO (ID " + novoIdStatus + ") em " + new Date();

                if (novoStatus == null) {
                    System.out.println("❌ ERRO NO UPDATE: Novo Status ID " + novoIdStatus + " não encontrado. Abortando atualização.");
                    return;
                }

                // 2. Define os novos valores no objeto
                objetoParaAtualizar.setStatus(novoStatus);
                objetoParaAtualizar.setObservacao(novaObservacao);

                // 3. EXECUÇÃO DO UPDATE
                dao.atualizar(objetoParaAtualizar);

                // 4. VALIDAÇÃO DE SUCESSO (Busca novamente)
                Objeto objetoAtualizado = dao.buscarPorId(idParaAtualizar);

                if (novoStatus.getId().equals(objetoAtualizado.getStatus().getId())) {
                    System.out.println("✅ UPDATE: Objeto ID " + idParaAtualizar + " ATUALIZADO com sucesso.");
                    System.out.printf("   -> Novo Status: %s (ID: %d)\n",
                            objetoAtualizado.getStatus().getNome(), objetoAtualizado.getStatus().getId());
                    System.out.printf("   -> Nova Observação: %s\n", objetoAtualizado.getObservacao());
                } else {
                    System.out.println("❌ UPDATE: Falha ao confirmar atualização de dados.");
                }
            } else {
                System.out.println("⚠️ UPDATE: Objeto ID " + idParaAtualizar + " não encontrado para atualização.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO UPDATE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 4. DELETE LÓGICO (DESATIVAR) -----------------------

    private static void testeDesativar(ObjetoDao dao, Integer idParaDesativar) {
        System.out.println("\n[4. TESTE DELETE LÓGICO - Desativar]");

        try {
            // 1. Desativa o objeto usando o novo método
            dao.desativar(idParaDesativar);

            // 2. VALIDAÇÃO (Busca novamente e verifica o status 'ativado')
            Objeto objetoDesativado = dao.buscarPorId(idParaDesativar);

            if (objetoDesativado != null && Boolean.FALSE.equals(objetoDesativado.getAtivado())) {
                System.out.println("✅ DESATIVAR: Objeto ID " + idParaDesativar + " DESATIVADO com sucesso.");
                System.out.printf("   -> Status 'ativado': %b\n", objetoDesativado.getAtivado());
            } else if (objetoDesativado == null) {
                System.out.println("⚠️ DESATIVAR: Objeto ID " + idParaDesativar + " não encontrado ou já deletado fisicamente.");
            } else {
                System.out.println("❌ DESATIVAR: Falha ao desativar Objeto ID " + idParaDesativar + ". Status 'ativado' ainda é TRUE.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO DESATIVAR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}