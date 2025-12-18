package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Devolucao;
import br.com.pi.achadoseperdidos.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DevolucaoDao {

    //Métodos de Criação, Desativação e Atualização
    public void inserirDevolucao(Devolucao devolucao){
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

    public Devolucao alterarDevolucao(Devolucao devolucao) {
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

    public void desativarDevolucao(Devolucao devolucao) {
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

    //Métodos de consulta
    public List<Devolucao> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Devolucao> q = em.createQuery("SELECT d FROM Devolucao d", Devolucao.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Devolucao buscarPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Devolucao.class, id);
        } finally {
            em.close();
        }
    }

}