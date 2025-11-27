package br.com.pi.achadoseperdidos.dao;
//Importação da DAO para organização das classes no projeto

import br.com.pi.achadoseperdidos.model.Funcionario;
import br.com.pi.achadoseperdidos.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
//Essas são as classes da biblioteca JDBC (java Database Conncetiviity).
//Connection -> Representa uma conexão ativa com o banco
//PreparedStatement -> usado para escrever os comando SQL com parâmetros
//ResultSet -> usado para armazenar e percorrer os resultados de um SELECT.

public class FuncionariosDao {
    public void salvar(Funcionario funcionario){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(funcionario);
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    public Funcionario atualizar(Funcionario funcionario) {
        EntityManager em = JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            Funcionario merged = em.merge(funcionario);
            em.getTransaction().commit();
            return merged;
        }catch (Exception e){
            em.getTransaction().rollback();
            throw e;
        }finally {
            em.close();
        }
    }

    public void remover(Funcionario funcionario) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Funcionario funcionarioRemover = em.find(Funcionario.class, funcionario.getId()); //Busca o objeto para coloca-lo na persistencia
            em.remove(funcionarioRemover); //Removemos o objeto que esta monitorado
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Funcionario> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Funcionario> q = em.createQuery("SELECT f FROM Funcionarios f", Funcionario.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Funcionario buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Funcionario.class, id);
        } finally {
            em.close();
        }
    }
}