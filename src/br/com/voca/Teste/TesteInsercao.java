package br.com.voca.Teste;

import br.com.voca.DAO.CandidatoDAO;
import br.com.voca.modelos.Candidato;

import java.time.LocalDate;

public class TesteInsercao {
    private static String[] args;

    public static void main(String[] args) {
        TesteInsercao.args = args;
        Candidato candidato = new Candidato();
        candidato.setNome("João da Silva");
        candidato.setDataNascimento("15/05/1990");
        candidato.setEmail("joao@email.com");
        candidato.setTelefone("(11) 98765-4321");
        candidato.setNacionalidade("Brasileiro");

        CandidatoDAO dao = new CandidatoDAO();
        dao.inserir(candidato);

        // Supondo que o ID é 1, ou use dao.buscarUltimoIdInserido() se tiver
        Candidato candidatoBuscado = dao.buscarPorId(1);

        if (candidatoBuscado != null) {
            System.out.println("Candidato encontrado:");
            System.out.println("Nome: " + candidatoBuscado.getNome());
            System.out.println("Email: " + candidatoBuscado.getEmail());
            System.out.println("Nascimento: " + candidatoBuscado.getDataNascimento());
        } else {
            System.out.println("Candidato não encontrado.");
        }
    }

}
