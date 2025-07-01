package br.com.voca.dao;

import br.com.voca.modelos.FormacaoAcademica;

public class FormacaoAcademicaDAO extends GenericDAO<FormacaoAcademica> {
    public FormacaoAcademicaDAO() {
        super(FormacaoAcademica.class);
    }
    /**
     * Busca uma formação acadêmica pelo ID do candidato.
     * @param candidatoId O ID do candidato.
     * @return A Formação Acadêmica correspondente ou null.
     */
}