package br.com.voca.service;

import br.com.voca.dao.CandidatoDAO;
import br.com.voca.modelos.Candidato;
import br.com.voca.modelos.ExperienciaProfissional;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public class CalculoExperienciaService {

    private final CandidatoDAO candidatoDAO = new CandidatoDAO();

    public String calcularTotalExperiencia(Long candidatoId) {
        try {
            Candidato candidato = candidatoDAO.buscarPorId(candidatoId);
            if (candidato == null || candidato.getCurriculo() == null) {
                return "Candidato ou currículo não encontrado.";
            }

            Set<ExperienciaProfissional> experiencias = candidato.getCurriculo().getExperienciaProfissional();
            if (experiencias == null || experiencias.isEmpty()) {
                return "0 anos";
            }

            long totalMeses = 0;
            for (ExperienciaProfissional exp : experiencias) {
                if (exp.getInicio() != null && exp.getFim() != null) {
                    totalMeses += ChronoUnit.MONTHS.between(exp.getInicio(), exp.getFim());
                }
            }

            if (totalMeses == 0) {
                return "Nenhuma experiência com período definido foi encontrada para " + candidato.getNome();
            }

            long anos = totalMeses / 12;

            return String.format("%d anos", anos);

        } finally {
            candidatoDAO.fechar();
        }
    }
}