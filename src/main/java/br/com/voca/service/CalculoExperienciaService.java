package br.com.voca.service;

import br.com.voca.dao.CandidatoDAO;
import br.com.voca.modelos.Candidato;
import br.com.voca.modelos.ExperienciaProfissional;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public class CalculoExperienciaService {

    private final CandidatoDAO candidatoDAO = new CandidatoDAO();

    /**
     * Calcula a experiência total de um candidato em anos.
     * @param candidatoId O ID do candidato.
     * @return Uma string formatada com o total de anos de experiência.
     */
    public String calcularTotalExperiencia(Long candidatoId) {
        try {
            Candidato candidato = candidatoDAO.buscarPorId(candidatoId);
            if (candidato == null || candidato.getCurriculo() == null || candidato.getCurriculo().getExperienciaProfissional() == null) {
                return "0 anos";
            }

            Set<ExperienciaProfissional> experiencias = candidato.getCurriculo().getExperienciaProfissional();
            if (experiencias.isEmpty()) {
                return "0 anos";
            }

            long totalMeses = 0;
            for (ExperienciaProfissional exp : experiencias) {
                if (exp.getInicio() != null && exp.getFim() != null) {
                    totalMeses += ChronoUnit.MONTHS.between(exp.getInicio(), exp.getFim());
                }
            }

            long anos = totalMeses / 12;
            return String.format("%d anos", anos);

        } finally {
            candidatoDAO.fechar();
        }
    }

    /**
     * Sobrecarga do método para calcular a experiência a partir de um objeto Candidato.
     * Útil para evitar múltiplas buscas no banco de dados.
     * @param candidato O objeto Candidato.
     * @return Uma string formatada com o total de anos de experiência.
     */
    public String calcularTotalExperiencia(Candidato candidato) {
        if (candidato == null || candidato.getCurriculo() == null || candidato.getCurriculo().getExperienciaProfissional() == null) {
            return "0 anos";
        }
        Set<ExperienciaProfissional> experiencias = candidato.getCurriculo().getExperienciaProfissional();
        long totalMeses = 0;
        for (ExperienciaProfissional exp : experiencias) {
            if (exp.getInicio() != null && exp.getFim() != null) {
                totalMeses += ChronoUnit.MONTHS.between(exp.getInicio(), exp.getFim());
            }
        }
        long anos = totalMeses / 12;
        return String.format("%d anos", anos);
    }
}