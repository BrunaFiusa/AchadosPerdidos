package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Objeto;
import br.com.pi.achadoseperdidos.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ObjetoDao {

    //Métodos de Criação, Desativação e Atualização
    public void inserirObjeto(Objeto objeto) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (objeto.getId() == null) {
                em.persist(objeto);
            } else {
                em.merge(objeto);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {em.getTransaction().rollback();}
            throw new RuntimeException("Erro ao salvar objeto: " + e.getMessage(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public Objeto alterarObjeto(Objeto objeto) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Objeto resultado = em.merge(objeto);
            em.getTransaction().commit();
            return resultado;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Falha ao atualizar objeto ID: " + objeto.getId(), e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public void desativarObjeto(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Objeto objeto = em.find(Objeto.class, id);
            if (objeto != null) {
                objeto.setAtivado(false);
                em.getTransaction().commit();
            } else {
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao desativar objeto ID: " + id, e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    //Métodos de consulta
    public List<Objeto> listarObjetos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Objeto> q = em.createQuery("SELECT o FROM Objeto o", Objeto.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Objeto listarObjetoPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Objeto.class, id);
        } finally {
            em.close();
        }
    }

}