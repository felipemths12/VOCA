package br.com.voca.dao;

import br.com.voca.modelos.Endereco;

/**
 * DAO para a entidade Endereco. Fornece operações CRUD básicas.
 */
public class EnderecoDAO extends GenericDAO<Endereco> {

    public EnderecoDAO() {
        super(Endereco.class);
    }
    /**
     * Busca um endereço pelo CEP.
     * @param cep O CEP do endereço.
     * @return O Endereço correspondente ou null se não encontrado.
     */
}