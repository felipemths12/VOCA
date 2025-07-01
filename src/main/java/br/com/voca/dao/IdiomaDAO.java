package br.com.voca.dao;

import br.com.voca.modelos.Idioma;

/**
 * DAO para a entidade Idioma. Herda as operações CRUD de GenericDAO.
 */
public class IdiomaDAO extends GenericDAO<Idioma> {

    public IdiomaDAO() {
        super(Idioma.class);
    }

    /**
     * Busca um idioma pelo ID do candidato.
     * @param candidatoId O ID do candidato.
     * @return O Idioma correspondente ou null se não encontrado.
     */
}