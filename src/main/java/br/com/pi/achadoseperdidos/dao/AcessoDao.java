package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Acesso;
import br.com.pi.achadoseperdidos.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class AcessoDao {

    public void salvar(Acesso acesso){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(acesso);
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    public Acesso atualizar(Acesso acesso) {
        EntityManager em = JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            Acesso merged = em.merge(acesso);
            em.getTransaction().commit();
            return merged;
        }catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    public void remover(Acesso acesso) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Acesso acessoRemover = em.find(Acesso.class, acesso.getId()); //Busca o objeto para coloca-lo na persistencia
            em.remove(acessoRemover); //Removemos o objeto que esta monitorado
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Acesso> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Acesso> q = em.createQuery("SELECT a FROM Acesso a", Acesso.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Acesso buscarPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Acesso.class, id);
        } finally {
            em.close();
        }
    }
}