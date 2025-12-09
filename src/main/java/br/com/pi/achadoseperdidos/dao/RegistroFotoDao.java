package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.RegistroFoto;
import br.com.pi.achadoseperdidos.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class RegistroFotoDao {

    public void salvar(RegistroFoto registroFoto) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(registroFoto);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }


    public RegistroFoto buscarPorIdComposto(int fotoId, int objetoId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // JPQL para buscar por IDs compostos
            return em.createQuery(
                            "SELECT rf FROM RegistroFoto rf WHERE rf.id.fotoId = :fId AND rf.id.objetoId = :oId", RegistroFoto.class)
                    .setParameter("fId", fotoId)
                    .setParameter("oId", objetoId)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null; // Caso não encontre
        } finally {
            em.close();
        }
    }
}