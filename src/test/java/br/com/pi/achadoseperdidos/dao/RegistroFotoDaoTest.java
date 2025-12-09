package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Foto;
import br.com.pi.achadoseperdidos.model.Objeto;
import br.com.pi.achadoseperdidos.model.RegistroFoto;
import br.com.pi.achadoseperdidos.model.RegistroeDevolucaoFotoId;

public class RegistroFotoDaoTest {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO TESTES DE ASSOCIAÇÃO REGISTRO FOTO ---");

        // DAOs necessários
        RegistroFotoDao registroFotoDao = new RegistroFotoDao();
        FotoDao fotoDao = new FotoDao();
        ObjetoDao objetoDao = new ObjetoDao(); // Usamos o ObjetoDao para buscar o Objeto Pai


        // -----------------------------------------------------------

        // 1. OBRIGATÓRIO: Salvar as Fotos se não existirem
        // CUIDADO: Rodar este bloco mais de uma vez sem limpar o banco pode dar erro
        // testeCriarFotos(fotoDao);

        // 2. TESTE CREATE (Cria a associação com o atributo extra 'dataRegistro')
        // testeCriarRegistroFoto(registroFotoDao, objetoDao, fotoDao,
        //                        67, 19);

        // 3. TESTE READ (Buscar a associação pela chave composta)
        // testeBuscarRegistroFoto(registroFotoDao, 19, 67);

        System.out.println("--- TESTES DE ASSOCIAÇÃO FINALIZADOS ---");
    }

    // =================================================================
    // MÉTODOS DE TESTE
    // =================================================================

    // --- 1. CRIAÇÃO DE FOTOS BÁSICAS (Pré-requisito) ---
    private static void testeCriarFotos(FotoDao dao) {
        System.out.println("\n[1. TESTE CRIAÇÃO DE FOTOS]");
        try {
            Foto foto1 = new Foto();
            foto1.setCaminho("/uploads/lacre_0001_registro.jpg");
            dao.salvar(foto1);
            System.out.println("✅ Foto1 salva. ID: " + foto1.getId());

        } catch (Exception e) {
            System.out.println("❌ ERRO AO CRIAR FOTOS: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- 2. CRIAÇÃO DA ASSOCIAÇÃO (CREATE RegistroFoto) ---
    private static void testeCriarRegistroFoto(RegistroFotoDao dao, ObjetoDao objetoDao, FotoDao fotoDao,
                                               Integer objetoId, Integer fotoId) {
        System.out.println("\n[2. TESTE CREATE - RegistroFoto (Associação)]");
        try {
            // 1. Buscar as entidades que formarão a FK
            Objeto objeto = objetoDao.buscarPorId(objetoId);
            Foto foto = fotoDao.buscarPorId(fotoId);

            if (objeto == null || foto == null) {
                System.out.println("❌ ERRO: Objeto ID " + objetoId + " ou Foto ID " + fotoId + " não encontrado.");
                return;
            }

            // 2. Criar a chave composta
            RegistroeDevolucaoFotoId.RegistroFotoId chave = new RegistroeDevolucaoFotoId.RegistroFotoId(foto.getId(), objeto.getId());

            // 3. Criar a Entidade de Associação
            RegistroFoto registro = new RegistroFoto();
            registro.setId(chave);
            registro.setObjeto(objeto);
            registro.setFoto(foto);


            // 4. Salvar
            dao.salvar(registro);

            System.out.println("✅ CREATE SUCESSO! Associação (Objeto: " + objetoId + ", Foto: " + fotoId + ") salva.");

        } catch (Exception e) {
            System.out.println("❌ ERRO NO CREATE: Falha ao criar RegistroFoto.");
            System.out.println("Detalhe: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- 3. BUSCAR A ASSOCIAÇÃO (READ RegistroFoto) ---
    private static void testeBuscarRegistroFoto(RegistroFotoDao dao, int fotoId, int objetoId) {
        System.out.println("\n[3. TESTE READ - Buscar RegistroFoto]");
        try {
            RegistroFoto registro = dao.buscarPorIdComposto(fotoId, objetoId);

            if (registro != null) {
                System.out.println("✅ READ SUCESSO! RegistroFoto encontrado:");
                System.out.printf("   -> Chave Composta: (Foto ID: %d, Objeto ID: %d)\n",
                        registro.getId().getFotoId(), registro.getId().getObjetoId());
                System.out.printf("   -> URL da Foto: %s\n", registro.getFoto().getCaminho());
                System.out.printf("   -> Nome do Objeto: %s\n", registro.getObjeto().getNome());
            } else {
                System.out.println("❌ READ FALHOU: Associação não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ: " + e.getMessage());
            e.printStackTrace();
        }
    }
}