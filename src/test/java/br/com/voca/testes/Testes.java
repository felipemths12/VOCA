package br.com.voca.testes;

import br.com.voca.dao.CandidatoDAO;
import br.com.voca.modelos.Candidato;

import java.util.List;

public class Testes {
    public static void main(String[] args) {
        CandidatoDAO candidatoDAO = new CandidatoDAO();

        // --- CREATE ---
        System.out.println("--- Criando um novo candidato ---");
        Candidato novoCandidato = new Candidato("João da Silva", "15/05/1990", "joao.silva@email.com", "(11) 91234-5678", "Brasileira");
        candidatoDAO.salvar(novoCandidato);
        System.out.println("Candidato salvo com sucesso!");

        // --- READ (TODOS) ---
        System.out.println("\n--- Listando todos os candidatos ---");
        List<Candidato> todos = candidatoDAO.buscarTodos();
        for (Candidato c : todos) {
            System.out.println("Nome: " + c.getNome() + ", Email: " + c.getEmail());
        }

        // --- READ (POR ID) ---
        System.out.println("\n--- Buscando o candidato com ID 1 ---");
        Candidato candidatoEncontrado = candidatoDAO.buscarPorId(1L); // Assumindo que o ID é 1
        if (candidatoEncontrado != null) {
            System.out.println("Encontrado: " + candidatoEncontrado.getNome());
        }

        // --- UPDATE ---
        System.out.println("\n--- Atualizando o candidato ---");
        if (candidatoEncontrado != null) {
            candidatoEncontrado.setTelefone("(11) 98765-4321");
            candidatoDAO.atualizar(candidatoEncontrado);
            System.out.println("Candidato atualizado: " + candidatoEncontrado.getNome() + " - Novo Telefone: " + candidatoEncontrado.getTelefone());
        }

        // --- DELETE ---
        // Cuidado ao executar, isso removerá o registro do banco!
        // System.out.println("\n--- Removendo o candidato com ID 1 ---");
        // candidatoDAO.remover(1L);
        // System.out.println("Candidato removido.");

        // Fecha a conexão ao final
        candidatoDAO.fechar();
    }
}