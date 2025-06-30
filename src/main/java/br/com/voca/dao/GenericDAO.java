package br.com.voca.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;

public class GenericDAO<T> {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("voca-mysql");
    protected final EntityManager em;
    private final Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.em = emf.createEntityManager();
        this.entityClass = entityClass;
    }

    // CREATE: Salva uma nova entidade
    public void salvar(T entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    // READ: Busca uma entidade pelo ID
    public T buscarPorId(Object id) {
        return em.find(entityClass, id);
    }

    // READ: Lista todas as entidades
    public List<T> buscarTodos() {
        CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

    // UPDATE: Atualiza uma entidade existente
    public void atualizar(T entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    // DELETE: Remove uma entidade
    public void remover(Object id) {
        T entity = buscarPorId(id);
        if (entity != null) {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        }
    }

    // Método para fechar a conexão
    public void fechar() {
        if (em.isOpen()) {
            em.close();
        }
    }

    // Método estático para fechar a factory, chame no final da aplicação
    public static void fecharFactory() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}