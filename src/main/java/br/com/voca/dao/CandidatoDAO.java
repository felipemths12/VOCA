package br.com.voca.dao;

import br.com.voca.modelos.Candidato;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.List;

public class CandidatoDAO extends GenericDAO<Candidato> {
    public CandidatoDAO() {
        super(Candidato.class);
    }
    /**
     * Busca candidatos por área de formação e/ou anos de experiência.
     * Filtros nulos ou vazios são ignorados.
     *
     * @param areaFormacao Área de formação para busca.
     * @param anosExperienciaMinimos Anos de experiência mínimos.
     * @return Lista de candidatos que correspondem aos filtros.
     */
    public List<Candidato> buscarPorFiltros(String areaFormacao, Integer anosExperienciaMinimos) {
        // Converte parâmetros para a consulta.
        String areaParam = (areaFormacao == null || areaFormacao.isBlank()) ? null : areaFormacao;
        Long mesesExperienciaParam = (anosExperienciaMinimos == null || anosExperienciaMinimos <= 0) ? null : (long) anosExperienciaMinimos * 12;

        /*
         * JPQL Dinâmica:
         * 1. LEFT JOIN inclui candidatos sem formação ou experiência.
         * 2. WHERE aplica o filtro de área se não for nulo.
         * 3. HAVING aplica o filtro de experiência se não for nulo.
         * 4. GROUP BY é usado para a função de agregação SUM.
         */
        String jpql = "SELECT c FROM Candidato c " +
                "LEFT JOIN c.curriculo cu " +
                "LEFT JOIN cu.formacaoAcademica fa " +
                "LEFT JOIN cu.experienciaProfissional ep " +
                "WHERE :areaParam IS NULL OR LOWER(fa.areaAtuacao) LIKE LOWER(CONCAT('%', :areaParam, '%')) " +
                "GROUP BY c.id, c.nome, c.email, c.telefone, c.nacionalidade, c.dataNascimento " +
                "HAVING :mesesParam IS NULL OR COALESCE(SUM(TIMESTAMPDIFF(MONTH, ep.inicio, ep.fim)), 0) >= :mesesParam";

        try {
            TypedQuery<Candidato> query = em.createQuery(jpql, Candidato.class);
            query.setParameter("areaParam", areaParam);
            query.setParameter("mesesParam", mesesExperienciaParam);

            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao executar a busca por filtros: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}