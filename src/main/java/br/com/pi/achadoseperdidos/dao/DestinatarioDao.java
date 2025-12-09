package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Destinatario;
import br.com.pi.achadoseperdidos.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DestinatarioDao {

    public void salvar(Destinatario destinatario){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(destinatario);
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    public Destinatario atualizar(Destinatario destinatario) {
        EntityManager em = JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            Destinatario merged = em.merge(destinatario);
            em.getTransaction().commit();
            return merged;
        }catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    public void remover(Destinatario destinatario) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Destinatario destinatarioRemover = em.find(Destinatario.class, destinatario.getId()); //Busca o objeto para coloca-lo na persistencia
            em.remove(destinatarioRemover); //Removemos o objeto que esta monitorado
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Destinatario> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Destinatario> q = em.createQuery("SELECT d FROM Destinatario d", Destinatario.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Destinatario buscarPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Destinatario.class, id);
        } finally {
            em.close();
        }
    }
    public Destinatario desativar(Destinatario destinatario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // 1. Busca o destinatario na base de dados para garantir que está no contexto
            Destinatario destinatarioParaDesativar = em.find(Destinatario.class, destinatario.getId());

            // 2. Define a ativação como false (Deleção Lógica)
            if (destinatarioParaDesativar != null) {
                // metodo setAtivacao(Boolean) na Model
                destinatarioParaDesativar.setAtivacao(false);
            }
            // 3. Atualiza no banco
            Destinatario merged = em.merge(destinatarioParaDesativar);
            em.getTransaction().commit();
            return merged;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}