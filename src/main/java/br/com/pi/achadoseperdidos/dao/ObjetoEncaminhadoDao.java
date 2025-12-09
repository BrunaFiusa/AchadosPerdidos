package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.ObjetoEncaminhado;
import br.com.pi.achadoseperdidos.model.ObjetoEncaminhadoId;
import br.com.pi.achadoseperdidos.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ObjetoEncaminhadoDao {

    // Método para salvar/atualizar a associação
    public void salvar(ObjetoEncaminhado objetoEncaminhado){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(objetoEncaminhado);
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    // Método para buscar usando a CHAVE COMPOSTA
    public ObjetoEncaminhado buscarPorId(ObjetoEncaminhadoId id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Usa o find() com a classe da Entidade e a classe da Chave Composta
            return em.find(ObjetoEncaminhado.class, id);
        } finally {
            em.close();
        }
    }

    // Método para remover a associação
    public void remover(ObjetoEncaminhado objetoEncaminhado) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // Primeiro, anexa a entidade para que ela possa ser removida
            ObjetoEncaminhado aRemover = em.find(ObjetoEncaminhado.class, objetoEncaminhado.getId());
            if (aRemover != null) {
                em.remove(aRemover);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // Método para listar todas as associações (opcional)
    public List<ObjetoEncaminhado> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT oe FROM ObjetoEncaminhado oe", ObjetoEncaminhado.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}