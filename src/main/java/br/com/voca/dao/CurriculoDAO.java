package br.com.voca.dao;

import br.com.voca.modelos.Curriculo;

/**
 * DAO para a entidade Curriculo. Herda de GenericDAO.
 */
public class CurriculoDAO extends GenericDAO<Curriculo> {

    public CurriculoDAO() {
        super(Curriculo.class);
    }

    /**
     * Busca um currículo pelo ID do candidato.
     * @param candidatoId O ID do candidato.
     * @return O Currículo correspondente ou null.
     */
    public Curriculo buscarPorCandidatoId(Long candidatoId) {
        try {
            return em.createQuery("SELECT c FROM Curriculo c WHERE c.candidato.id = :candidatoId", Curriculo.class)
                    .setParameter("candidatoId", candidatoId)
                    .getSingleResult();
        } catch (Exception e) {
            // Retorna nulo se não encontrar resultados.
            return null;
        }
    }
}