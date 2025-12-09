package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.DevolucaoFoto;
import br.com.pi.achadoseperdidos.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class DevolucaoFotoDao {

    public void salvar(DevolucaoFoto devolucaoFoto) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // Usamos merge para anexar as entidades referenciadas (Foto e Devolucao)
            em.merge(devolucaoFoto);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public DevolucaoFoto buscarPorIdComposto(Integer fotoId, Integer devolucaoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // JPQL para buscar por IDs compostos
            return em.createQuery(
                            "SELECT df FROM DevolucaoFoto df WHERE df.id.fotoId = :fId AND df.id.devolucaoId = :dId", DevolucaoFoto.class)
                    .setParameter("fId", fotoId)
                    .setParameter("dId", devolucaoId)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null; // Caso não encontre
        } finally {
            em.close();
        }
    }
}