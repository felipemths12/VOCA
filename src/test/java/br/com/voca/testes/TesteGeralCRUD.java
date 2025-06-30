package br.com.voca.testes;

import br.com.voca.dao.*;
import br.com.voca.modelos.*;

import java.util.ArrayList;
import java.util.List;

public class TesteGeralCRUD {

    public static void main(String[] args) {
        // Instancia todos os DAOs necessários para o teste
        CandidatoDAO candidatoDAO = new CandidatoDAO();
        CurriculoDAO curriculoDAO = new CurriculoDAO();
        FormacaoAcademicaDAO formacaoDAO = new FormacaoAcademicaDAO();
        ExperienciaProfissionalDAO experienciaDAO = new ExperienciaProfissionalDAO();
        HabilidadesDAO habilidadesDAO = new HabilidadesDAO();
        IdiomaDAO idiomaDAO = new IdiomaDAO();

        System.out.println("--- INÍCIO DO TESTE INTEGRADO CRUD ---");

        // --- 1. CREATE (Criação do grafo de objetos) ---
        System.out.println("\n[CREATE] Criando um candidato completo com todas as informações...");

        // 1.1 Cria as entidades que serão associadas ao currículo
        Endereco endereco = new Endereco("12345-678", "Rua dos Desenvolvedores", "Centro", "Código Fonte", "JV", "1024");

        FormacaoAcademica formacao1 = new FormacaoAcademica("Ciência da Computação", "Universidade Federal de Java", "Tecnologia", "01/2018", "12/2022", "CONCLUIDO");
        FormacaoAcademica formacao2 = new FormacaoAcademica("Curso de Inglês", "Escola de Idiomas SpeakWell", "Idiomas", "03/2021", "12/2021", "CONCLUIDO");

        ExperienciaProfissional exp1 = new ExperienciaProfissional("Tech Solutions S.A.", "Desenvolvedor Java Pleno", "01/2022", "06/2024", "Desenvolvimento de APIs REST");
        exp1.adicionarPalavras(); // Adiciona a palavra-chave à lista

        Habilidades hab1 = new Habilidades("Java", "AVANCADO");
        Habilidades hab2 = new Habilidades("Spring Boot", "INTERMEDIARIO");
        Habilidades hab3 = new Habilidades("SQL", "INTERMEDIARIO");

        Idioma idioma1 = new Idioma("Inglês", "AVANCADO");
        Idioma idioma2 = new Idioma("Espanhol", "BASICO");

        // 1.2 Cria o Currículo e associa todas as informações
        Curriculo curriculo = new Curriculo();
        curriculo.setEndereco(endereco);
        curriculo.setFormacaoAcademica(new ArrayList<>(List.of(formacao1, formacao2)));
        curriculo.setExperienciaProfissional(new ArrayList<>(List.of(exp1)));
        curriculo.setHabilidades(new ArrayList<>(List.of(hab1, hab2, hab3)));
        curriculo.setIdioma(new ArrayList<>(List.of(idioma1, idioma2)));

        // Define a relação bidirecional (lado filho -> pai)
        formacao1.setCurriculo(curriculo);
        formacao2.setCurriculo(curriculo);
        exp1.setCurriculo(curriculo);
        hab1.setCurriculo(curriculo);
        hab2.setCurriculo(curriculo);
        hab3.setCurriculo(curriculo);
        idioma1.setCurriculo(curriculo);
        idioma2.setCurriculo(curriculo);

        // 1.3 Cria o Candidato e associa o currículo
        Candidato novoCandidato = new Candidato("Ana Coder", "10/10/2000", "ana.coder@dev.com", "(11) 91111-2222", "Brasileira");
        novoCandidato.setCurriculo(curriculo);
        curriculo.setCandidato(novoCandidato); // Define a relação bidirecional

        // 1.4 Salva o candidato. Graças ao CascadeType.ALL, tudo será salvo junto.
        candidatoDAO.salvar(novoCandidato);
        System.out.println("Candidato salvo com sucesso! ID: " + novoCandidato.getId());

        // --- 2. READ (Leitura e verificação dos dados) ---
        System.out.println("\n[READ] Buscando o candidato salvo e verificando todos os seus dados...");
        Candidato candidatoSalvo = candidatoDAO.buscarPorId(novoCandidato.getId());

        System.out.println("Nome do candidato encontrado: " + candidatoSalvo.getNome());
        System.out.println("Endereço: " + candidatoSalvo.getCurriculo().getEndereco().getLogradouro());
        System.out.println("Quantidade de formações: " + candidatoSalvo.getCurriculo().getFormacaoAcademica().size());
        System.out.println("Cargo da experiência: " + candidatoSalvo.getCurriculo().getExperienciaProfissional().get(0).getCargoOcupado());
        System.out.println("Quantidade de habilidades: " + candidatoSalvo.getCurriculo().getHabilidades().size());
        System.out.println("Idioma principal: " + candidatoSalvo.getCurriculo().getIdioma().get(0).getIdioma());

        // --- 3. UPDATE (Atualização dos dados) ---
        System.out.println("\n[UPDATE] Atualizando o telefone do candidato e adicionando uma nova habilidade...");

        // 3.1 Modifica um dado simples
        candidatoSalvo.setTelefone("(11) 93333-4444");

        // 3.2 Adiciona um novo item a uma lista
        Habilidades novaHabilidade = new Habilidades("Docker", "BASICO");
        novaHabilidade.setCurriculo(candidatoSalvo.getCurriculo()); // Associa ao currículo existente
        candidatoSalvo.getCurriculo().getHabilidades().add(novaHabilidade);

        candidatoDAO.atualizar(candidatoSalvo);
        System.out.println("Candidato atualizado!");

        // 3.3 Verifica a atualização
        Candidato candidatoAtualizado = candidatoDAO.buscarPorId(novoCandidato.getId());
        System.out.println("Novo telefone: " + candidatoAtualizado.getTelefone());
        System.out.println("Nova quantidade de habilidades: " + candidatoAtualizado.getCurriculo().getHabilidades().size());

        // --- 4. DELETE (Remoção dos dados) ---
        System.out.println("\n[DELETE] Removendo o candidato do banco de dados...");
        long idParaRemover = novoCandidato.getId();
        candidatoDAO.remover(idParaRemover);
        System.out.println("Candidato com ID " + idParaRemover + " removido.");

        // 4.1 Verifica a remoção
        Candidato candidatoRemovido = candidatoDAO.buscarPorId(idParaRemover);
        if (candidatoRemovido == null) {
            System.out.println("Verificação: Candidato não encontrado, remoção bem-sucedida!");
        } else {
            System.out.println("ERRO: O candidato ainda foi encontrado no banco.");
        }

        System.out.println("\n--- FIM DO TESTE INTEGRADO CRUD ---");

        // Fecha a conexão da factory ao final de toda a operação
        GenericDAO.fecharFactory();
    }
}