package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Devolucao;
import br.com.pi.achadoseperdidos.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DevolucaoDao {

    public void salvar(Devolucao devolucao){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(devolucao);
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    public Devolucao atualizar(Devolucao devolucao) {
        EntityManager em = JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            Devolucao merged = em.merge(devolucao);
            em.getTransaction().commit();
            return merged;
        }catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    public void remover(Devolucao devolucao) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Devolucao devolucaoRemover = em.find(Devolucao.class, devolucao.getId()); //Busca o objeto para coloca-lo na persistencia
            em.remove(devolucaoRemover); //Removemos o objeto que esta monitorado
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Devolucao> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Devolucao> q = em.createQuery("SELECT d FROM Devolucao d", Devolucao.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Devolucao buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Devolucao.class, id);
        } finally {
            em.close();
        }
    }


}