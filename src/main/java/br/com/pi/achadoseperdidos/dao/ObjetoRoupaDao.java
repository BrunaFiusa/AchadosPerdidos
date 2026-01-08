package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Roupa;
import br.com.pi.achadoseperdidos.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ObjetoRoupaDao {

    //Métodos de consulta
    public List<Roupa> listarRoupas() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // JPQL para listar todas as Roupas
            TypedQuery<Roupa> q = em.createQuery("SELECT r FROM Roupa r", Roupa.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Roupa listarRoupaPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Usa a classe Roupa.class para buscar, o JPA fará o JOIN internamente.
            return em.find(Roupa.class, id);
        } finally {
            em.close();
        }
    }
}