package br.com.voca.dao;

import br.com.voca.modelos.Endereco;

/**
 * DAO para a entidade Endereco.
 * Fornece as operações CRUD básicas através da herança de GenericDAO.
 */
public class EnderecoDAO extends GenericDAO<Endereco> {

    public EnderecoDAO() {
        super(Endereco.class);
    }

    // Métodos de busca específicos para Endereco (ex: por CEP, por UF)
    // poderiam ser implementados aqui, se necessário.
}