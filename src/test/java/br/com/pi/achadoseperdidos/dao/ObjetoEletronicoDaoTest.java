package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Eletronico;
import br.com.pi.achadoseperdidos.model.Local;
import br.com.pi.achadoseperdidos.model.Status;
import br.com.pi.achadoseperdidos.model.Funcionario;
import br.com.pi.achadoseperdidos.model.Categoria;
import java.util.Date;
import java.util.List;

public class ObjetoEletronicoDaoTest {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO TESTES CRUD ELETRONICO (HERANÇA) ---");

        // 1. Instanciação dos DAOs necessários
        EletronicoDao eletronicoDao = new EletronicoDao();
        LocalDao localDao = new LocalDao();
        StatusDao statusDao = new StatusDao();
        FuncionarioDao funcionarioDao = new FuncionarioDao();
        CategoriaDao categoriaDao = new CategoriaDao();

        // 2. CONFIGURAÇÃO DE IDS OBRIGATÓRIOS
        // Estes IDs DEVEM existir no BD para o CREATE funcionar.
        final Integer ID_STATUS_VALIDO = 1;      // Ex: ID de um status "Perdido"
        final Integer ID_FUNCIONARIO_VALIDO = 1; // Ex: ID de um Funcionário Admin
        final Integer ID_LOCAL_VALIDO = 1;       // Ex: ID de um Local "Recepção"
        final Integer ID_CATEGORIA_ELETRONICO = 15; // ID da sua categoria 'Eletrônicos'

        // -----------------------------------------------------------
        // 🚨 COMENTE/DESCOMENTE O BLOCO QUE VOCÊ QUER EXECUTAR
        // -----------------------------------------------------------

        // 1. TESTE CREATE (Persistência em duas tabelas: Objetos e Eletronicos)
//         testeCriarEletronico(eletronicoDao, localDao, statusDao, funcionarioDao, categoriaDao,
//                              ID_STATUS_VALIDO, ID_FUNCIONARIO_VALIDO, ID_LOCAL_VALIDO, ID_CATEGORIA_ELETRONICO);

        // 2. TESTE READ (buscarPorId - Busca a entidade filha completa)
         // testeBuscarPorId(eletronicoDao, 40);

        // 3. TESTE READ ALL (listarTodos)
        // testeListarTodos(eletronicoDao);

        System.out.println("--- TESTES CRUD ELETRONICO FINALIZADOS ---");
    }

    // =================================================================
    // MÉTODOS DE TESTE
    // =================================================================

    private static Integer gerarLacreUnico() {
        String base = String.valueOf(System.currentTimeMillis());
        return Integer.parseInt(base.substring(base.length() - 5));
    }

    //----------------------- 1. CREATE -----------------------

    private static void testeCriarEletronico(EletronicoDao dao, LocalDao localDao, StatusDao statusDao,
                                             FuncionarioDao funcionarioDao, CategoriaDao categoriaDao,
                                             Integer idStatus, Integer idFuncionario, Integer idLocal, Integer idCategoria){
        System.out.println("\n[1. TESTE CREATE - Eletronico]");
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

            // 1. CRIAÇÃO DO OBJETO FILHO (Eletronico)
            Eletronico novoEletronico = new Eletronico();

            // CAMPOS HERDADOS de Objeto
            novoEletronico.setLocal(localBase);
            novoEletronico.setStatus(statusBase);
            novoEletronico.setFuncionario(funcionarioBase);
            novoEletronico.setCategoria(categoriaBase); // Categoria: Eletrônicos (ID 15)

            novoEletronico.setLacre(gerarLacreUnico());
            novoEletronico.setNome("Smartwatch Perdido " + gerarLacreUnico());
            novoEletronico.setObservacao("Encontrado próximo ao elevador.");
            novoEletronico.setMarca("Xiaomi");
            novoEletronico.setCondicao("Funcionando, com pulseira quebrada.");
            novoEletronico.setAtivado(true);
            novoEletronico.setDataRegistro(new Date());

            // CAMPO ESPECÍFICO de Eletronico
            novoEletronico.setModelo("Amazfit Bip 3");

            // 2. EXECUÇÃO DO DAO
            dao.salvar(novoEletronico);

            // Exibe o sucesso
            System.out.println("✅ CREATE SUCESSO! ID Gerado: " + novoEletronico.getId());
            System.out.println("   -> **VERIFICAÇÃO DE HERANÇA:** Este ID deve existir nas tabelas 'objetos' E 'eletronicos'.");
            System.out.printf("   -> Nome (Pai): %s | Modelo (Filho): %s\n",
                    novoEletronico.getNome(), novoEletronico.getModelo());

        } catch (Exception e) {
            System.out.println("❌ ERRO NO CREATE: Falha ao criar Eletrônico.");
            System.out.println("Detalhe: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 2. READ BY ID -----------------------

    private static void testeBuscarPorId(EletronicoDao dao, Integer idParaBusca) {
        System.out.println("\n[2. TESTE READ BY ID - Eletronico]");

        try {
            // Usa o EletronicoDao, que fará o JOIN interno para buscar os dados de 'objetos' e 'eletronicos'.
            Eletronico eletronicoEncontrado = dao.buscarPorId(idParaBusca);

            if (eletronicoEncontrado != null) {
                System.out.println("✅ READ (ID) SUCESSO! Eletronico ID " + idParaBusca + " encontrado.");

                // Exibe campo específico (modelo) e campos herdados (nome, status)
                System.out.println("---------------------------------------------------------");
                System.out.printf("   -> Nome do Objeto: %s | Lacre: %d | Ativo: %b\n",
                        eletronicoEncontrado.getNome(), eletronicoEncontrado.getLacre(), eletronicoEncontrado.getAtivado());

                System.out.printf("   -> **Modelo Específico**: %s | Marca: %s\n",
                        eletronicoEncontrado.getModelo(), eletronicoEncontrado.getMarca());

                System.out.printf("   -> Status: %s | Categoria ID: %d\n",
                        eletronicoEncontrado.getStatus().getNome(), eletronicoEncontrado.getCategoria().getId());
                System.out.println("---------------------------------------------------------");

            } else {
                System.out.println("❌ READ (ID): Eletronico ID " + idParaBusca + " não foi encontrado.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ (ID): " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------------- 3. READ ALL -----------------------

    private static void testeListarTodos(EletronicoDao dao) {
        System.out.println("\n[3. TESTE READ ALL - Eletronico]");
        try {
            List<Eletronico> eletronicos = dao.listarTodos();

            if (eletronicos != null && !eletronicos.isEmpty()) {
                System.out.println("✅ READ ALL: Total de eletrônicos encontrados: " + eletronicos.size());

                System.out.println("   ---------------------------------------------------------------------------------------------------");
                System.out.println("   | ID | Lacre | Nome do Objeto   | Marca | Modelo Específico | Status | Ativo |");
                System.out.println("   ---------------------------------------------------------------------------------------------------");

                for (Eletronico e : eletronicos) {
                    String statusNome = e.getStatus() != null ? e.getStatus().getNome() : "N/A";

                    System.out.printf("   | %-2d | %-5d | %-15s | %-5s | %-17s | %-6s | %-5b |\n",
                            e.getId(),
                            e.getLacre(),
                            e.getNome(),
                            e.getMarca(),
                            e.getModelo(), // Campo específico
                            statusNome,
                            e.getAtivado());
                }
                System.out.println("   ---------------------------------------------------------------------------------------------------");

            } else {
                System.out.println("⚠️ READ ALL: Nenhum eletrônico encontrado.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ ALL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}