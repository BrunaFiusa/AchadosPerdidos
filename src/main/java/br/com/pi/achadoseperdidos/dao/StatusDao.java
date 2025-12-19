package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Status;
import br.com.pi.achadoseperdidos.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class StatusDao {

    //Métodos de Criação, Desativação e Atualização
    public void inserirStatus(Status status){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(status);
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    public Status alterarStatus(Status status) {
        EntityManager em = JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            Status merged = em.merge(status);
            em.getTransaction().commit();
            return merged;
        }catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    public void desativarStatus(Status status) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Status statusRemover = em.find(Status.class, status.getId()); //Busca o objeto para coloca-lo na persistencia
            em.remove(statusRemover); //Removemos o objeto que esta monitorado
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //Métodos de consulta
    public List<Status> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Status> q = em.createQuery("SELECT s FROM Status s", Status.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Status buscarPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Status.class, id);
        } finally {
            em.close();
        }
    }
}