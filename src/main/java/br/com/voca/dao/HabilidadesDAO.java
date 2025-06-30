package br.com.voca.dao;

import br.com.voca.modelos.Habilidades;

/**
 * DAO para a entidade Habilidades.
 * Herda todos os métodos CRUD (salvar, buscarPorId, buscarTodos, atualizar, remover)
 * da classe GenericDAO.
 */
public class HabilidadesDAO extends GenericDAO<Habilidades> {

    public HabilidadesDAO() {
        super(Habilidades.class);
    }

    // Se precisar de um método específico para Habilidades no futuro,
    // pode adicioná-lo aqui. Por exemplo:
    // public List<Habilidades> buscarPorNivel(Nivel nivel) { ... }
}