package br.com.voca.dao;

import br.com.voca.modelos.Curriculo;

/**
 * DAO para a entidade Curriculo.
 * Herda as funcionalidades CRUD de GenericDAO e pode ser estendido
 * com métodos de negócio mais complexos.
 */
public class CurriculoDAO extends GenericDAO<Curriculo> {

    public CurriculoDAO() {
        super(Curriculo.class);
    }

    // Exemplo de um método mais específico que poderia ser útil:
    /**
     * Busca um currículo pelo ID do candidato associado a ele.
     * @param candidatoId O ID do candidato.
     * @return O Currículo correspondente ou null se não for encontrado.
     */
    public Curriculo buscarPorCandidatoId(Long candidatoId) {
        try {
            return em.createQuery("SELECT c FROM Curriculo c WHERE c.candidato.id = :candidatoId", Curriculo.class)
                    .setParameter("candidatoId", candidatoId)
                    .getSingleResult();
        } catch (Exception e) {
            // Trata o caso de não encontrar resultados
            return null;
        }
    }
}