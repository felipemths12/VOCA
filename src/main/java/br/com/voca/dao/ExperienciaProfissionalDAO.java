package br.com.voca.dao;

import br.com.voca.modelos.ExperienciaProfissional;

public class ExperienciaProfissionalDAO extends GenericDAO<ExperienciaProfissional> {
    public ExperienciaProfissionalDAO() {
        super(ExperienciaProfissional.class);
    }
    /**
     * Busca uma experiência profissional pelo ID do candidato.
     * @param candidatoId O ID do candidato.
     * @return A Experiência Profissional correspondente ou null.
     */
}