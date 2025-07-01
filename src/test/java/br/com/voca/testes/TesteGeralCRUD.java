package br.com.voca.testes;

import br.com.voca.dao.CandidatoDAO;
import br.com.voca.dao.GenericDAO;
import br.com.voca.modelos.*;
import br.com.voca.service.ExportacaoService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TesteGeralCRUD {

    public static void main(String[] args) {
        // SETUP: Garante um banco de dados limpo a cada execução.
        System.out.println("[SETUP] O modo 'create-drop' garante um banco de dados limpo a cada execução.");

        CandidatoDAO candidatoDAO = new CandidatoDAO();

        System.out.println("\n--- INÍCIO DO TESTE INTEGRADO CRUD ---");

        // CREATE: Cria um candidato completo.
        System.out.println("\n[CREATE] Criando um candidato completo com todas as informações...");

        // Dados
        Endereco endereco = new Endereco("12345-678", "Rua dos Desenvolvedores", "Centro", "Código Fonte", "JV", "1024");
        FormacaoAcademica formacao1 = new FormacaoAcademica("Ciência da Computação", "Universidade Federal de Java", "Tecnologia", "01/2018", "12/2022", "CONCLUIDO");
        ExperienciaProfissional exp1 = new ExperienciaProfissional("Tech Solutions S.A.", "Desenvolvedor Java Pleno", "01/2022", "06/2024", "Desenvolvimento de APIs REST");
        Habilidades hab1 = new Habilidades("Java", "AVANCADO");
        Idioma idioma1 = new Idioma("Inglês", "AVANCADO");
        Idioma idioma2 = new Idioma("Espanhol", "BASICO");

        // Montagem do currículo
        Curriculo curriculo = new Curriculo();
        curriculo.setEndereco(endereco);
        curriculo.getFormacaoAcademica().add(formacao1);
        curriculo.getExperienciaProfissional().add(exp1);
        curriculo.getHabilidades().add(hab1);
        curriculo.getIdioma().add(idioma1);
        curriculo.getIdioma().add(idioma2);

        // Associações bidirecionais
        formacao1.setCurriculo(curriculo);
        exp1.setCurriculo(curriculo);
        hab1.setCurriculo(curriculo);
        idioma1.setCurriculo(curriculo);
        idioma2.setCurriculo(curriculo);

        // Criação e associação do candidato
        Candidato novoCandidato = new Candidato("Ana Coder", "10/10/2000", "ana.coder@dev.com", "(11) 91111-2222", "Brasileira");
        novoCandidato.setCurriculo(curriculo);
        curriculo.setCandidato(novoCandidato);

        // Salva o candidato
        candidatoDAO.salvar(novoCandidato);
        System.out.println("Candidato salvo com sucesso! ID: " + novoCandidato.getId());

        // EXPORTAÇÃO: Exporta os dados para CSV e PDF.
        System.out.println("\n[EXPORT] Exportando dados para CSV e PDF...");
        ExportacaoService exportacaoService = new ExportacaoService();
        exportacaoService.exportarCandidatosParaCSV("candidatos_exportados.csv");
        exportacaoService.exportarCandidatosParaPDF("candidatos_exportados.pdf");
        System.out.println("[EXPORT] Arquivos devem ter sido gerados na raiz do projeto (pasta VOCA).");


        // DELETE: Remove o candidato do banco de dados.
        System.out.println("\n[DELETE] Removendo o candidato do banco de dados...");
        candidatoDAO.remover(novoCandidato.getId());
        System.out.println("Candidato com ID " + novoCandidato.getId() + " removido.");

        System.out.println("\n--- FIM DO TESTE INTEGRADO CRUD ---");

        GenericDAO.fecharFactory();
    }
}