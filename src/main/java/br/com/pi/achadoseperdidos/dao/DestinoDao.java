package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Destino;
import br.com.pi.achadoseperdidos.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DestinoDao {

    //Métodos de Criação, Desativação e Atualização
    public void inserirDestino(Destino destino){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(destino);
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    public Destino alterarDestino(Destino destino) {
        EntityManager em = JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            Destino merged = em.merge(destino);
            em.getTransaction().commit();
            return merged;
        }catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    public void desativarDestino(Destino destino) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Destino destinoRemover = em.find(Destino.class, destino.getId()); //Busca o objeto para coloca-lo na persistencia
            em.remove(destinoRemover); //Removemos o objeto que esta monitorado
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //Métodos de consulta
    public List<Destino> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Destino> q = em.createQuery("SELECT d FROM Destino d", Destino.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Destino buscarPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Destino.class, id);
        } finally {
            em.close();
        }
    }

}