package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Roupa;
import br.com.pi.achadoseperdidos.model.Local;
import br.com.pi.achadoseperdidos.model.Status;
import br.com.pi.achadoseperdidos.model.Funcionario;
import br.com.pi.achadoseperdidos.model.Categoria;
import java.util.Date;
import java.util.List;

public class ObjetoRoupaDaoTest {

    // IDs devem ser válidos no seu BD
    private static final Integer ID_STATUS_VALIDO = 1;
    private static final Integer ID_FUNCIONARIO_VALIDO = 1;
    private static final Integer ID_LOCAL_VALIDO = 1;
    private static final Integer ID_CATEGORIA_ROUPA = 2; // ID da sua categoria 'Roupas'

    public static void main(String[] args) {
        System.out.println("--- INICIANDO TESTES CRUD ROUPA (HERANÇA) ---");

        // 1. Instanciação dos DAOs
        RoupaDao roupaDao = new RoupaDao();
        LocalDao localDao = new LocalDao();
        StatusDao statusDao = new StatusDao();
        FuncionarioDao funcionarioDao = new FuncionarioDao();
        CategoriaDao categoriaDao = new CategoriaDao();

        // -----------------------------------------------------------
        // 🚨 COMENTE/DESCOMENTE O BLOCO QUE VOCÊ QUER EXECUTAR
        // -----------------------------------------------------------

        // 1. TESTE CREATE
//        testeCriarRoupa(roupaDao, localDao, statusDao, funcionarioDao, categoriaDao,
//                ID_STATUS_VALIDO, ID_FUNCIONARIO_VALIDO, ID_LOCAL_VALIDO, ID_CATEGORIA_ROUPA);

        // 2. TESTE READ (buscarPorId)
        //     testeBuscarPorId(roupaDao, 19);

        // 3. TESTE READ ALL (listarTodos)
        // testeListarTodos(roupaDao);

        System.out.println("--- TESTES CRUD ROUPA FINALIZADOS ---");
    }


    //----------------------- 1. CREATE -----------------------

    private static void testeCriarRoupa(RoupaDao dao, LocalDao localDao, StatusDao statusDao,
                                        FuncionarioDao funcionarioDao, CategoriaDao categoriaDao,
                                        Integer idStatus, Integer idFuncionario, Integer idLocal, Integer idCategoria){
        System.out.println("\n[1. TESTE CREATE - Roupa]");
        try {
            // 0. PRÉ-REQUISITOS (Objetos FKs)
            Local localBase = localDao.buscarPorId(idLocal);
            Status statusBase = statusDao.buscarPorId(idStatus);
            Funcionario funcionarioBase = funcionarioDao.buscarPorId(idFuncionario);
            Categoria categoriaBase = categoriaDao.buscarPorId(idCategoria);

            if (statusBase == null || funcionarioBase == null) {
                System.out.println("❌ ERRO NO CREATE: Status ou Funcionário obrigatórios não encontrados.");
                return;
            }

            // 1. CRIAÇÃO DO OBJETO FILHO (Roupa)
            Roupa novaRoupa = new Roupa();

            // CAMPOS HERDADOS de Objeto
            novaRoupa.setLocal(localBase);
            novaRoupa.setStatus(statusBase);
            novaRoupa.setFuncionario(funcionarioBase);
            novaRoupa.setCategoria(categoriaBase);

            novaRoupa.setLacre(12121);
            novaRoupa.setNome("Camiseta de Marca");
            novaRoupa.setObservacao("Encontrada dobrada perto do vestiário.");
            novaRoupa.setMarca("Hering");
            novaRoupa.setCondicao("Ótima, limpa.");
            novaRoupa.setAtivado(true);
            novaRoupa.setDataRegistro(new Date());

            // CAMPOS ESPECÍFICOS de Roupa
            novaRoupa.setTamanho("M");
            novaRoupa.setCor("Azul Marinho");

            // 2. EXECUÇÃO DO DAO
            dao.salvar(novaRoupa);

            // Exibe o sucesso
            System.out.println("✅ CREATE SUCESSO! ID Gerado: " + novaRoupa.getId());
            System.out.println("   -> **VERIFICAÇÃO DE HERANÇA:** Este ID deve existir nas tabelas 'objetos' E 'roupas'.");
            System.out.printf("   -> Nome (Pai): %s | Tamanho (Filho): %s | Cor (Filho): %s\n",
                    novaRoupa.getNome(), novaRoupa.getTamanho(), novaRoupa.getCor());

        } catch (Exception e) {
            System.out.println("❌ ERRO NO CREATE: Falha ao criar Roupa.");
            System.out.println("Detalhe: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 2. READ BY ID -----------------------

    private static void testeBuscarPorId(RoupaDao dao, Integer idParaBusca) {
        System.out.println("\n[2. TESTE READ BY ID - Roupa]");

        try {
            Roupa roupaEncontrada = dao.buscarPorId(idParaBusca);

            if (roupaEncontrada != null) {
                System.out.println("✅ READ (ID) SUCESSO! Roupa ID " + idParaBusca + " encontrada.");

                System.out.println("---------------------------------------------------------");
                // CAMPOS HERDADOS DE OBJETO
                System.out.printf("   -> Nome do Objeto: %s | Marca: %s | Lacre: %d | Ativo: %b\n",
                        roupaEncontrada.getNome(),
                        roupaEncontrada.getMarca(),
                        roupaEncontrada.getLacre(),
                        roupaEncontrada.getAtivado());

                // CAMPOS ESPECÍFICOS DE ROUPA
                System.out.printf("   -> **Tamanho**: %s | **Cor**: %s\n",
                        roupaEncontrada.getTamanho(), roupaEncontrada.getCor());

                System.out.printf("   -> Status: %s | Categoria ID: %d\n",
                        roupaEncontrada.getStatus().getNome(), roupaEncontrada.getCategoria().getId());
                System.out.println("---------------------------------------------------------");

            } else {
                System.out.println("❌ READ (ID): Roupa ID " + idParaBusca + " não foi encontrada.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ (ID): " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 3. READ ALL -----------------------

    private static void testeListarTodos(RoupaDao dao) {
        System.out.println("\n[3. TESTE READ ALL - Roupa]");
        try {
            List<Roupa> roupas = dao.listarTodos();

            if (roupas != null && !roupas.isEmpty()) {
                System.out.println("✅ READ ALL: Total de roupas encontradas: " + roupas.size());

                System.out.println("   ---------------------------------------------------------------------------------------------------");
                System.out.println("   | ID | Lacre | Nome do Objeto   | Marca | Tamanho | Cor | Status | Ativo |");
                System.out.println("   ---------------------------------------------------------------------------------------------------");

                for (Roupa r : roupas) {
                    String statusNome = r.getStatus() != null ? r.getStatus().getNome() : "N/A";

                    System.out.printf("   | %-2d | %-5d | %-15s | %-5s | %-7s | %-3s | %-6s | %-5b |\n",
                            r.getId(),
                            r.getLacre(),
                            r.getNome(),
                            r.getMarca(),
                            r.getTamanho(), // Campo específico
                            r.getCor(),     // Campo específico
                            statusNome,
                            r.getAtivado());
                }
                System.out.println("   ---------------------------------------------------------------------------------------------------");

            } else {
                System.out.println("⚠️ READ ALL: Nenhuma roupa encontrada.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ ALL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}