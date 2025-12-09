package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Destino;
import br.com.pi.achadoseperdidos.model.Objeto;
import br.com.pi.achadoseperdidos.model.ObjetoEncaminhado;
import br.com.pi.achadoseperdidos.model.ObjetoEncaminhadoId;
import java.util.Date;
import java.util.List;

public class ObjetoEncaminhadoDaoTest {

    // ⚠️ IDS FIXOS PARA TESTES INDEPENDENTES
    // Defina uma associação Objeto-Destino que você sabe que existe no banco.
    private static final Integer ID_OBJETO_FIXO = 21; // ID de um Objeto existente
    private static final Integer ID_DESTINO_FIXO = 8; // ID de um Destino existente

    public static void main(String[] args) {
        System.out.println("--- INICIANDO TESTES CRUD ObjetoEncaminhado ---");

        ObjetoEncaminhadoDao assocDao = new ObjetoEncaminhadoDao();
        ObjetoDao objetoDao = new ObjetoDao();
        DestinoDao destinoDao = new DestinoDao();


        // -----------------------------------------------------------
        // 🚨 COMENTE/DESCOMENTE O BLOCO QUE VOCÊ QUER EXECUTAR
        // -----------------------------------------------------------

        // Chave composta fixa para READ, UPDATE, DELETE
        ObjetoEncaminhadoId idFixo = new ObjetoEncaminhadoId(ID_OBJETO_FIXO, ID_DESTINO_FIXO);

        // -----------------------------------------------------------
        // 1. TESTE CREATE (Cria uma nova associação)
        // testeCriar(assocDao, objetoDao, destinoDao);

        // 2. TESTE READ BY ID (Usa ID Fixo)
        // testeBuscarPorId(assocDao, idFixo);

        // 3. TESTE READ ALL (Lista todas e formata a saída)
        // testeListarTodos(assocDao);

        // 4. TESTE DELETE (Usa ID Fixo)
        // testeRemover(assocDao, idFixo);

        System.out.println("--- TESTES CRUD ObjetoEncaminhado FINALIZADOS ---");
    }

    // =================================================================
    // 1. CREATE
    // =================================================================
    // (Mantido, mas usando IDs fixos para criar uma nova associação)
    private static ObjetoEncaminhadoId testeCriar(ObjetoEncaminhadoDao assocDao, ObjetoDao objetoDao, DestinoDao destinoDao) {
        System.out.println("\n[1. TESTE CREATE - ObjetoEncaminhado]");
        try {
            // Usamos outros IDs ou checamos se a combinação já existe antes de tentar salvar
            Objeto objeto = objetoDao.buscarPorId(2); // Tenta um novo objeto
            Destino destino = destinoDao.buscarPorId(8); // Tenta um novo destino

            if (objeto == null || destino == null) {
                System.out.println("❌ ERRO NO CREATE: Entidades mestre (Objeto/Destino) com IDs incrementados não encontradas. Crie-as primeiro.");
                return null;
            }

            ObjetoEncaminhadoId novaChave = new ObjetoEncaminhadoId(objeto.getId(), destino.getId());

            // Verifica se a associação já existe para evitar ConstraintViolation
            if (assocDao.buscarPorId(novaChave) != null) {
                System.out.println("⚠️ CREATE: Associação Objeto " + objeto.getId() + " e Destino " + destino.getId() + " já existe. Pulando criação.");
                return null;
            }

            ObjetoEncaminhado novaAssoc = new ObjetoEncaminhado();
            novaAssoc.setId(novaChave);
            novaAssoc.setObjeto(objeto);
            novaAssoc.setDestino(destino);
            novaAssoc.setDataEnvio(new Date());

            assocDao.salvar(novaAssoc);

            System.out.println("✅ CREATE SUCESSO! Associação Objeto " + novaAssoc.getObjeto().getId() + " para Destino " + novaAssoc.getDestino().getId() + " criada.");
            return novaChave;

        } catch (Exception e) {
            System.out.println("❌ ERRO NO CREATE: Falha ao criar ObjetoEncaminhado.");
            e.printStackTrace();
            return null;
        }
    }

    // =================================================================
    // 2. READ BY ID (usando a chave composta fixa)
    // =================================================================
    private static void testeBuscarPorId(ObjetoEncaminhadoDao dao, ObjetoEncaminhadoId id) {
        System.out.println("\n[2. TESTE READ BY ID - ObjetoEncaminhado]");
        try {
            ObjetoEncaminhado encontrado = dao.buscarPorId(id);

            if (encontrado != null) {
                System.out.println("✅ READ (ID) SUCESSO! Associação encontrada.");
                System.out.println("------------------------------------------");
                System.out.printf("   🔑 Chave Composta: Objeto ID %d | Destino ID %d\n", id.getObjetoId(), id.getDestinoId());
                System.out.printf("   📦 Objeto Nome (Lazy): %s\n", (encontrado.getObjeto() != null ? "Carregado" : "Não Carregado"));
                System.out.printf("   📍 Destino Nome (Lazy): %s\n", (encontrado.getDestino() != null ? "Carregado" : "Não Carregado"));
                System.out.printf("   📅 Data Envio: %s\n", encontrado.getDataEnvio());
                System.out.println("------------------------------------------");
            } else {
                System.out.println("❌ READ (ID): Associação com Objeto ID " + id.getObjetoId() + " e Destino ID " + id.getDestinoId() + " não foi encontrada.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ (ID): " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =================================================================
    // 3. READ ALL (Listar e Formatar)
    // =================================================================
    private static void testeListarTodos(ObjetoEncaminhadoDao dao) {
        System.out.println("\n[3. TESTE READ ALL - ObjetoEncaminhado]");
        try {
            List<ObjetoEncaminhado> lista = dao.listarTodos();

            if (lista != null && !lista.isEmpty()) {
                System.out.println("✅ READ ALL: Total de associações encontradas: " + lista.size());

                System.out.println("\n--------------------------------------------------------------");
                System.out.printf("| %-8s | %-8s | %-30s |\n", "OBJETO ID", "DESTINO ID", "DATA ENVIO");
                System.out.println("--------------------------------------------------------------");

                for (ObjetoEncaminhado assoc : lista) {
                    // Nota: Aqui estamos acessando as IDs da chave composta (que são EAGER)
                    // e a DataEnvio. Se você tentar acessar assoc.getObjeto().getNome(), pode ter LazyInitializationException.

                    System.out.printf("| %-8d | %-8d | %-30s |\n",
                            assoc.getId().getObjetoId(),
                            assoc.getId().getDestinoId(),
                            assoc.getDataEnvio());
                }
                System.out.println("--------------------------------------------------------------");

            } else {
                System.out.println("⚠️ READ ALL: Nenhuma associação encontrada.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO READ ALL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =================================================================
    // 4. DELETE (Usando a chave composta fixa)
    // =================================================================
    private static void testeRemover(ObjetoEncaminhadoDao dao, ObjetoEncaminhadoId id) {
        System.out.println("\n[4. TESTE DELETE - ObjetoEncaminhado]");
        try {
            // Busca a entidade usando o ID fixo
            ObjetoEncaminhado assoc = dao.buscarPorId(id);

            if (assoc != null) {
                dao.remover(assoc);

                // Confirma a remoção
                ObjetoEncaminhado removido = dao.buscarPorId(id);

                if (removido == null) {
                    System.out.println("✅ DELETE SUCESSO! Associação Objeto ID " + id.getObjetoId() + " / Destino ID " + id.getDestinoId() + " removida.");
                } else {
                    System.out.println("❌ DELETE FALHOU: A associação ainda existe no banco.");
                }
            } else {
                System.out.println("❌ DELETE: Associação com Objeto ID " + id.getObjetoId() + " / Destino ID " + id.getDestinoId() + " não encontrada para remoção.");
            }
        } catch (Exception e) {
            System.out.println("❌ ERRO NO DELETE: " + e.getMessage());
            e.printStackTrace();
        }
    }
}