package br.com.voca.dao;

import br.com.voca.modelos.Habilidades;

/**
 * DAO para a entidade Habilidades. Herda métodos CRUD de GenericDAO.
 */
public class HabilidadesDAO extends GenericDAO<Habilidades> {

    public HabilidadesDAO() {
        super(Habilidades.class);
    }

    /**
     * Busca habilidades pelo ID do candidato.
     * @param candidatoId O ID do candidato.
     * @return As Habilidades correspondentes ou null se não encontradas.
     */
}