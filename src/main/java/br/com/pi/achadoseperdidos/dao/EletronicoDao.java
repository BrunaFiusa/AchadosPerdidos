package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Eletronico;
import br.com.pi.achadoseperdidos.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class EletronicoDao {


    public void salvar(Eletronico eletronico){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // O merge é usado para salvar novos ou atualizar existentes.
            em.merge(eletronico);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Eletronico buscarPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Usa a classe Eletronico.class para buscar, o JPA fará o JOIN internamente.
            return em.find(Eletronico.class, id);
        } finally {
            em.close();
        }
    }

    public List<Eletronico> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // JPQL para listar todos os Eletronicos
            TypedQuery<Eletronico> q = em.createQuery("SELECT e FROM Eletronico e", Eletronico.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}