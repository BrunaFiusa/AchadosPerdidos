package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Foto;
import br.com.pi.achadoseperdidos.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class FotoDao {

    public void salvar(Foto foto){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(foto);
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    public Foto atualizar(Foto foto) {
        EntityManager em = JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            Foto merged = em.merge(foto);
            em.getTransaction().commit();
            return merged;
        }catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    public void remover(Foto foto) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Foto fotoRemover = em.find(Foto.class, foto.getId()); //Busca o objeto para coloca-lo na persistencia
            em.remove(fotoRemover); //Removemos o objeto que esta monitorado
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Foto> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Foto> q = em.createQuery("SELECT f FROM Foto f", Foto.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Foto buscarPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Foto.class, id);
        } finally {
            em.close();
        }
    }
}