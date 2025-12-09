package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Devolucao;
import br.com.pi.achadoseperdidos.model.Foto;
import br.com.pi.achadoseperdidos.model.DevolucaoFoto;
import br.com.pi.achadoseperdidos.model.RegistroeDevolucaoFotoId;

public class DevolucaoFotoDaoTest {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO TESTES DE ASSOCIAÇÃO DEVOLUÇÃO FOTO ---");

        // DAOs necessários
        DevolucaoFotoDao devolucaoFotoDao = new DevolucaoFotoDao();
        FotoDao fotoDao = new FotoDao();
        DevolucaoDao devolucaoDao = new DevolucaoDao();

        // -----------------------------------------------------------

        // 1. TESTE CREATE (Cria a associação Devolucao-Foto)
        // testeCriarDevolucaoFoto(devolucaoFotoDao, devolucaoDao, fotoDao,
        //        21, 19);

        // 2. TESTE READ (Buscar a associação pela chave composta)
        // testeBuscarDevolucaoFoto(devolucaoFotoDao, 19, 21);

        System.out.println("--- TESTES DE ASSOCIAÇÃO FINALIZADOS ---");
    }

    // =================================================================
    // MÉTODOS DE TESTE
    // =================================================================

    // --- 1. CRIAÇÃO DA ASSOCIAÇÃO (CREATE DevolucaoFoto) ---
    private static void testeCriarDevolucaoFoto(DevolucaoFotoDao dao, DevolucaoDao devolucaoDao, FotoDao fotoDao,
                                                Integer devolucaoId, Integer fotoId) {
        System.out.println("\n[1. TESTE CREATE - DevolucaoFoto (Associação)]");
        try {
            // 1. Buscar as entidades que formarão a FK (Chaves compostas)
            Devolucao devolucao = devolucaoDao.buscarPorId(devolucaoId);
            Foto foto = fotoDao.buscarPorId(fotoId);

            if (devolucao == null || foto == null) {
                System.out.println("❌ ERRO: Devolucao ID " + devolucaoId + " ou Foto ID " + fotoId + " não encontrado.");
                return;
            }

            // 2. Criar a chave composta
            RegistroeDevolucaoFotoId.DevolucaoFotoId chave = new RegistroeDevolucaoFotoId.DevolucaoFotoId(foto.getId(), devolucao.getId());

            // 3. Criar a Entidade de Associação
            DevolucaoFoto registro = new DevolucaoFoto();
            registro.setId(chave);
            registro.setDevolucao(devolucao);
            registro.setFoto(foto);

            // 4. Salvar (O merge no DAO resolve o problema de detached)
            dao.salvar(registro);

            System.out.println("✅ CREATE SUCESSO! Associação (Devolucao: " + devolucaoId + ", Foto: " + fotoId + ") salva.");
            System.out.println("   -> **VERIFICAÇÃO DE CHAVE:** O registro deve existir na tabela 'fotodevolucoes'.");

        } catch (Exception e) {
            System.out.println("❌ ERRO NO CREATE: Falha ao criar DevolucaoFoto.");
            System.out.println("Detalhe: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- 2. BUSCAR A ASSOCIAÇÃO (READ DevolucaoFoto) ---
    private static void testeBuscarDevolucaoFoto(DevolucaoFotoDao dao, int fotoId, int devolucaoId) {
        System.out.println("\n[2. TESTE READ - Buscar DevolucaoFoto]");
        try {
            DevolucaoFoto registro = dao.buscarPorIdComposto(fotoId, devolucaoId);

            if (registro != null) {
                System.out.println("✅ READ SUCESSO! DevolucaoFoto encontrado:");
                System.out.printf("   -> Chave Composta: (Foto ID: %d, Devolucao ID: %d)\n",
                        registro.getId().getFotoId(), registro.getId().getDevolucaoId());
                System.out.printf("   -> URL da Foto: %s\n", registro.getFoto().getCaminho());
            } else {
                System.out.println("❌ READ FALHOU: Associação não encontrada para FOTO ID %d e DEVOLUCAO ID %d." + fotoId + devolucaoId);
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ: " + e.getMessage());
            e.printStackTrace();
        }
    }
}