package br.com.voca.dao;

import br.com.voca.modelos.Idioma;

/**
 * DAO para a entidade Idioma.
 * Herda as operações CRUD da classe GenericDAO.
 */
public class IdiomaDAO extends GenericDAO<Idioma> {

    public IdiomaDAO() {
        super(Idioma.class);
    }

    // Métodos específicos para a entidade Idioma podem ser adicionados aqui.
}