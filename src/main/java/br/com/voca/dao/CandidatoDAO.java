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
     * Busca candidatos com base na área de formação e/ou anos de experiência mínimos.
     * Os filtros são opcionais. Se um filtro for nulo ou vazio, ele é ignorado.
     *
     * @param areaFormacao A área de formação a ser buscada. Pode ser nula ou vazia.
     * @param anosExperienciaMinimos O número mínimo de anos de experiência. Pode ser nulo ou zero.
     * @return Uma lista de candidatos que atendem aos critérios especificados.
     */
    public List<Candidato> buscarPorFiltros(String areaFormacao, Integer anosExperienciaMinimos) {
        // Converte os parâmetros de entrada para serem usados na query.
        // String vazia se torna nula para que o filtro de área seja ignorado.
        String areaParam = (areaFormacao == null || areaFormacao.isBlank()) ? null : areaFormacao;

        // Anos de experiência nulos ou zero fazem com que o filtro seja ignorado.
        Long mesesExperienciaParam = (anosExperienciaMinimos == null || anosExperienciaMinimos <= 0) ? null : (long) anosExperienciaMinimos * 12;

        /*
         * JPQL Dinâmica com Condicionais:
         * 1. LEFT JOIN é usado para incluir candidatos mesmo que não tenham formação ou experiência.
         * 2. A cláusula WHERE `(:areaParam IS NULL OR ...)` aplica o filtro de área somente se :areaParam não for nulo.
         * 3. A cláusula HAVING `(:mesesParam IS NULL OR ...)` aplica o filtro de experiência somente se :mesesParam não for nulo.
         * 4. GROUP BY é necessário para a função de agregação SUM na cláusula HAVING.
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
