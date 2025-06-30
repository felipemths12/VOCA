package br.com.voca.dao;

import br.com.voca.modelos.Candidato;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class CandidatoDAO {

    private final EntityManagerFactory emf;
    private final EntityManager em;

    public CandidatoDAO() {
        // Usa o nome da persistence-unit do arquivo persistence.xml
        this.emf = Persistence.createEntityManagerFactory("voca-mysql");
        this.em = emf.createEntityManager();
    }

    // CREATE: Salva um novo candidato
    public void salvar(Candidato candidato) {
        em.getTransaction().begin();
        em.persist(candidato);
        em.getTransaction().commit();
    }

    // READ: Busca um candidato pelo ID
    public Candidato buscarPorId(Long id) {
        return em.find(Candidato.class, id);
    }

    // READ: Lista todos os candidatos
    public List<Candidato> buscarTodos() {
        String jpql = "SELECT c FROM Candidato c";
        return em.createQuery(jpql, Candidato.class).getResultList();
    }

    // UPDATE: Atualiza um candidato existente
    public void atualizar(Candidato candidato) {
        em.getTransaction().begin();
        em.merge(candidato); // O merge serve tanto para inserir quanto para atualizar
        em.getTransaction().commit();
    }

    // DELETE: Remove um candidato
    public void remover(Long id) {
        Candidato candidato = buscarPorId(id);
        if (candidato != null) {
            em.getTransaction().begin();
            em.remove(candidato);
            em.getTransaction().commit();
        }
    }

    // Método para fechar a conexão
    public void fechar() {
        em.close();
        emf.close();
    }
}