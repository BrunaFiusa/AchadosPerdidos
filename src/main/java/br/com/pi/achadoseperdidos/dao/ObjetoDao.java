package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.model.Objeto;
import br.com.pi.achadoseperdidos.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ObjetoDao {

    public void salvar(Objeto objeto){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(objeto);
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    public Objeto atualizar(Objeto objeto) {
        EntityManager em = JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            Objeto merged = em.merge(objeto);
            em.getTransaction().commit();
            return merged;
        }catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }


    // O método listarTodos deve filtrar apenas os objetos onde 'ativo' é TRUE
    public List<Objeto> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // JPQL: Adicionando a cláusula WHERE para filtrar apenas objetos ATIVOS
            TypedQuery<Objeto> q = em.createQuery("SELECT o FROM Objeto o WHERE o.ativo = true", Objeto.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Objeto buscarPorId(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Objeto.class, id);
        } finally {
            em.close();
        }
    }
    // Adicione este método ao final da sua classe ObjetoDao

    public void desativarObjeto(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            // 1. Busca o objeto
            Objeto objeto = em.find(Objeto.class, id);

            if (objeto != null) {
                // 2. Altera o status (Assumindo que sua entidade Objeto tem o método setAtivo(Boolean))
                objeto.setAtivacao(false);
                // 3. Persiste a mudança
                em.merge(objeto);
                em.getTransaction().commit();
            } else {
                // Se não encontrar, faz o rollback e pode lançar uma exceção de que o objeto não existe.
                em.getTransaction().rollback();
                // Opcional: throw new IllegalArgumentException("Objeto ID " + id + " não encontrado.");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao desativar Objeto: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}