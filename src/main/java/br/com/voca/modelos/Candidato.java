package br.com.voca.modelos;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Entity
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID do candidato.
    private String nome; // Nome do candidato.
    private LocalDate dataNascimento; // Data de nascimento.
    private String email; // E-mail do candidato.
    private String telefone; // Telefone do candidato.
    private String nacionalidade; // Nacionalidade do candidato.

    @OneToOne(mappedBy = "candidato", cascade = CascadeType.ALL)
    private Curriculo curriculo;

    public Candidato() {
    }

    public Candidato (String nome, String dataNascimento, String email,
                      String telefone, String nacionalidade) {
        setNome(nome);
        setDataNascimento(dataNascimento);
        setEmail(email);
        setTelefone(telefone);
        setNacionalidade(nacionalidade);
    }

    public void setNome (String nome) {
        if(nome != null && !nome.isBlank()) {
            this.nome = nome;
        }
    }

    // Valida o e-mail com regex.
    public void setEmail(String email) {
        if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("E-mail inválido");
        }
    }

    // Valida o telefone com regex.
    public void setTelefone(String telefone) {
        if (telefone.matches("^\\(\\d{2}\\) 9\\d{4}-\\d{4}$")) {
            this.telefone = telefone;
        } else {
            throw new IllegalArgumentException("Telefone inválido");
        }
    }

    // Converte a String de data de nascimento para LocalDate.
    public void setDataNascimento (String dataNascimento) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            this.dataNascimento = LocalDate.parse(dataNascimento, formatador);
        } catch (DateTimeParseException e) {
            // Tratamento de exceção (a ser implementado no front-end).
        }
    }

    public void setNacionalidade(String nacionalidade) {
        if(nacionalidade != null && !nacionalidade.isBlank()) {
            this.nacionalidade = nacionalidade;
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public Curriculo getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(Curriculo curriculo) {
        this.curriculo = curriculo;
    }
}