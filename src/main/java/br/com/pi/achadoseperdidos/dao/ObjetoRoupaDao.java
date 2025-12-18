package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Roupa;
import br.com.pi.achadoseperdidos.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ObjetoRoupaDao {

    //Métodos de Criação, Desativação e Atualização
    public void inserirRoupa(Roupa roupa){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // O merge salva o novo registro ou atualiza um existente (em ambas as tabelas)
            em.merge(roupa);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //Métodos de consulta
    public Roupa buscarPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Usa a classe Roupa.class para buscar, o JPA fará o JOIN internamente.
            return em.find(Roupa.class, id);
        } finally {
            em.close();
        }
    }

    public List<Roupa> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // JPQL para listar todas as Roupas
            TypedQuery<Roupa> q = em.createQuery("SELECT r FROM Roupa r", Roupa.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}