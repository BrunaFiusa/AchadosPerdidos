package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DevolucaoDaoTest {

    // IDs VÁLIDOS: Devem existir no banco de dados para o teste funcionar
    private static final Integer ID_DESTINATARIO_VALIDO = 1;
    private static final Integer ID_OBJETO_VALIDO = 1;
    private static final Integer ID_FOTO_DEVOLUCAO_VALIDA = 1; // Para criar a DevolucaoFoto

    public static void main(String[] args) {
        System.out.println("--- INICIANDO TESTES CRUD DEVOLUCAO ---");

        // Instanciação dos DAOs
        DevolucaoDao devolucaoDao = new DevolucaoDao();
        // DAOs auxiliares para buscar FKs
        DestinatarioDao destinatarioDao = new DestinatarioDao();
        ObjetoDao objetoDao = new ObjetoDao();
        DevolucaoFotoDao devolucaoFotoDao = new DevolucaoFotoDao(); // Para buscar a associação

        // 1. TESTE CREATE
        // final Integer idCriado = testeCriarDevolucao(devolucaoDao, destinatarioDao, objetoDao, devolucaoFotoDao);

        // 2. TESTE READ (buscarPorId)
        // testeBuscarPorId(devolucaoDao, 1);

        // 3. TESTE UPDATE
        // testeAtualizar(devolucaoDao, 21);

        // 4. TESTE READ ALL (listarTodos)
        // testeListarTodos(devolucaoDao);

        // 5. TESTE DELETE (CUIDADO: Use um ID criado especificamente para exclusão)
         // testeRemover(devolucaoDao, 25);

        System.out.println("--- TESTES CRUD DEVOLUCAO FINALIZADOS ---");
    }

    // =================================================================
    // 1. CREATE
    // =================================================================

    // Código para simular a criação de uma Devolucao com uma Foto Associada

    private static Integer testeCriarDevolucao(DevolucaoDao dao, DestinatarioDao destDao, ObjetoDao objDao, FotoDao fotoDao) {
        System.out.println("\n[1. TESTE CREATE - Devolucao com Cascade]");
        try {
            // IDs VÁLIDOS EXISTENTES no banco (PRE-REQUISITOS)
            final Integer ID_DESTINATARIO_VALIDO = 1;
            final Integer ID_OBJETO_VALIDO = 1;
            final Integer ID_FOTO_VALIDA = 2;

            // 0. PRÉ-REQUISITOS (Entidades FKs que já existem)
            Destinatario destinatario = destDao.buscarPorId(ID_DESTINATARIO_VALIDO);
            Objeto objeto = objDao.buscarPorId(ID_OBJETO_VALIDO);
            Foto fotoExistente = fotoDao.buscarPorId(ID_FOTO_VALIDA); // Entidade 'detached' do banco

            if (destinatario == null || objeto == null || fotoExistente == null) {
                System.out.println("❌ ERRO NO CREATE: Entidades Destinatario, Objeto ou Foto não encontradas.");
                return null;
            }

            // 1. CRIAÇÃO DA ENTIDADE DE ASSOCIAÇÃO (FILHO)
            RegistroeDevolucaoFotoId.DevolucaoFotoId chave =
                    new RegistroeDevolucaoFotoId.DevolucaoFotoId(fotoExistente.getId(), 0); // 0 temporário para devolucaoId

            DevolucaoFoto novaAssocFoto = new DevolucaoFoto();
            novaAssocFoto.setId(chave);
            novaAssocFoto.setFoto(fotoExistente); // Usando a Foto 'detached'

            // 2. CRIAÇÃO DO OBJETO PAI (Devolucao)
            Devolucao novaDevolucao = new Devolucao();
            novaDevolucao.setDestinatario(destinatario);
            novaDevolucao.setObjeto(objeto);
            novaDevolucao.setDataDevolucao(new Date());
            novaDevolucao.setAutenticadorAssinatura("TESTE_CASCADE_ALL");

            // 3. ASSOCIAÇÃO DO FILHO NO PAI (A cascata agirá aqui)
            // Adiciona a DevolucaoFoto na lista
            novaDevolucao.setDevolucaoFotos(Arrays.asList(novaAssocFoto));
            // A chave composta (devolucaoId) em novaAssocFoto será atualizada pelo Hibernate/JPA
            // antes de salvar a DevolucaoFoto no banco.

            // 4. SALVAR A DEVOLUCAO (A cascata tentará persistir o DevolucaoFoto)
            dao.salvar(novaDevolucao);

            System.out.println("✅ CREATE SUCESSO! Devolucao e DevolucaoFoto salvas via CASCADE. ID Gerado: " + novaDevolucao.getId());
            return novaDevolucao.getId();

        } catch (jakarta.persistence.PersistenceException e) {
            System.out.println("❌ ERRO DE PERSISTÊNCIA (Pode ser o Detached Entity): " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.println("❌ ERRO NO CREATE: Falha ao criar Devolucao.");
            e.printStackTrace();
            return null;
        }
    }

    // =================================================================
    // 2. READ BY ID
    // =================================================================

    private static void testeBuscarPorId(DevolucaoDao dao, Integer idParaBusca) {
        System.out.println("\n[2. TESTE READ BY ID - Devolucao]");
        try {
            Devolucao devolucaoEncontrada = dao.buscarPorId(idParaBusca);

            if (devolucaoEncontrada != null) {
                System.out.println("✅ READ (ID) SUCESSO! Devolucao ID " + idParaBusca + " encontrada.");
                System.out.println("---------------------------------------------------------");
                System.out.printf("   -> Objeto Devolvido: %s (ID: %d)\n", devolucaoEncontrada.getObjeto().getNome(), devolucaoEncontrada.getObjeto().getId());
                System.out.printf("   -> Destinatário: %s (ID: %d)\n", devolucaoEncontrada.getDestinatario().getNome(), devolucaoEncontrada.getDestinatario().getId());
                System.out.printf("   -> Data: %s\n", devolucaoEncontrada.getDataDevolucao());
                System.out.printf("   -> Autenticador: %s\n", devolucaoEncontrada.getAutenticadorAssinatura());
                System.out.println("---------------------------------------------------------");
            } else {
                System.out.println("❌ READ (ID): Devolucao ID " + idParaBusca + " não foi encontrada.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ (ID): " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =================================================================
    // 3. UPDATE
    // =================================================================

    private static void testeAtualizar(DevolucaoDao dao, Integer idParaAtualizar) {
        System.out.println("\n[3. TESTE UPDATE - Devolucao]");
        try {
            Devolucao devolucao = dao.buscarPorId(idParaAtualizar);

            if (devolucao != null) {
                // Modifica o campo que será atualizado
                String novoAutenticador = "testeAutenticador";
                devolucao.setAutenticadorAssinatura(novoAutenticador);

                Devolucao atualizada = dao.atualizar(devolucao);

                System.out.println("✅ UPDATE SUCESSO! Devolucao ID " + idParaAtualizar + " atualizada.");
                System.out.println("   -> Novo Autenticador: " + atualizada.getAutenticadorAssinatura());

            } else {
                System.out.println("❌ UPDATE: Devolucao ID " + idParaAtualizar + " não encontrada para atualização.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO UPDATE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =================================================================
    // 4. READ ALL
    // =================================================================

    private static void testeListarTodos(DevolucaoDao dao) {
        System.out.println("\n[4. TESTE READ ALL - Devolucao]");
        try {
            List<Devolucao> devolucoes = dao.listarTodos();

            if (devolucoes != null && !devolucoes.isEmpty()) {
                System.out.println("✅ READ ALL: Total de devoluções encontradas: " + devolucoes.size());

                System.out.println("   -----------------------------------------------------------------------------------");
                System.out.println("   | ID | Objeto ID | Destinatário ID | Data Devolução |");
                System.out.println("   -----------------------------------------------------------------------------------");

                for (Devolucao d : devolucoes) {
                    System.out.printf("   | %-2d | %-9d | %-15d | %-14s |\n",
                            d.getId(),
                            d.getObjeto() != null ? d.getObjeto().getId() : 0,
                            d.getDestinatario() != null ? d.getDestinatario().getId() : 0,
                            d.getDataDevolucao());
                }
                System.out.println("   -----------------------------------------------------------------------------------");

            } else {
                System.out.println("⚠️ READ ALL: Nenhuma devolução encontrada.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ ALL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =================================================================
    // 5. DELETE
    // =================================================================

    private static void testeRemover(DevolucaoDao dao, Integer idParaRemover) {
        System.out.println("\n[5. TESTE DELETE - Devolucao]");
        try {
            Devolucao devolucao = dao.buscarPorId(idParaRemover);

            if (devolucao != null) {
                dao.remover(devolucao);

                // Tenta buscar para confirmar a remoção
                Devolucao removida = dao.buscarPorId(idParaRemover);

                if (removida == null) {
                    System.out.println("✅ DELETE SUCESSO! Devolucao ID " + idParaRemover + " removida.");
                } else {
                    System.out.println("❌ DELETE FALHOU: O objeto ainda existe no banco.");
                }
            } else {
                System.out.println("❌ DELETE: Devolucao ID " + idParaRemover + " não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO DELETE: " + e.getMessage());
            e.printStackTrace();
        }
    }
}