package br.com.voca.modelos;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

// Mapeia a classe para a tabela "experiencias_profissionais".
@Entity
@Table(name = "experiencias_profissionais")
public class ExperienciaProfissional {
    // Define o ID e a estratégia de geração.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeEmpresa; // Nome da empresa.
    private String cargoOcupado; // Cargo ocupado.
    private LocalDate inicio; // Data de início.
    private LocalDate fim; // Data de término.
    private String palavraChave; // Palavra-chave.
    @ElementCollection
    @CollectionTable(name = "experiencia_palavras_chave", joinColumns = @JoinColumn(name = "experiencia_id"))
    @Column(name = "palavra_chave")
    private Set<String> palavras; // Lista de palavras-chave.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id")
    private Curriculo curriculo;

    public ExperienciaProfissional () {
        this.palavras = new HashSet<>();
    }

    public ExperienciaProfissional (String nomeEmpresa, String cargoOcupado, String inicio,
                                    String fim, String palavraChave) {
        setNomeEmpresa(nomeEmpresa);
        setCargoOcupado(cargoOcupado);
        setInicio(inicio);
        setFim(fim);
        setPalavraChave(palavraChave);
        this.palavras = new HashSet<>();
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        if (nomeEmpresa != null && !nomeEmpresa.isBlank()) {
            this.nomeEmpresa = nomeEmpresa;
        }
    }

    public void setCargoOcupado(String cargoOcupado) {
        if (cargoOcupado != null && !cargoOcupado.isBlank()) {
            this.cargoOcupado = cargoOcupado;
        }
    }

    // Converte a String de data de início para LocalDate.
    public void setInicio(String inicio) {
        if (inicio == null || inicio.isBlank()) {
            this.inicio = null;
            return;
        }
        try {
            // Adiciona o dia "01" para criar uma data completa e segura para o parse
            String dataCompleta = "01/" + inicio;
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.inicio = LocalDate.parse(dataCompleta, formatador);
        } catch (DateTimeParseException e) {
            System.err.println("Formato de data de início inválido: " + inicio);
            e.printStackTrace(); // Imprime o erro no console para depuração
            this.inicio = null; // Garante que o campo fique nulo se a data for inválida
        }
    }

    // Converte a String de data de fim para LocalDate.
    public void setFim(String fim) {
        if (fim == null || fim.isBlank()) {
            this.fim = null;
            return;
        }
        try {
            // Adiciona o dia "01" para criar uma data completa e segura para o parse
            String dataCompleta = "01/" + fim;
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.fim = LocalDate.parse(dataCompleta, formatador);
        } catch (DateTimeParseException e) {
            System.err.println("Formato de data de fim inválido: " + fim);
            e.printStackTrace(); // Imprime o erro no console para depuração
            this.fim = null; // Garante que o campo fique nulo se a data for inválida
        }
    }

    public void setPalavraChave (String palavraChave) {
        if (palavraChave != null && !palavraChave.isBlank()) {
            this.palavraChave = palavraChave;
        }
    }

    // Adiciona uma palavra-chave à lista.
    public void adicionarPalavras() {
        if (palavraChave != null && !palavraChave.isBlank()) {
            palavras.add(palavraChave);
        }
    }

    // Getters
    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public String getCargoOcupado() {
        return cargoOcupado;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public Set<String> getPalavras() {
        return palavras;
    }

    public Long getId() {
        return id;
    }

    public Curriculo getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(Curriculo curriculo) {
        this.curriculo = curriculo;
    }
}