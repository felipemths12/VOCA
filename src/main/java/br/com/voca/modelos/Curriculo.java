package br.com.voca.modelos;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

// Mapeia a classe para a tabela "curriculo".
@Entity
@Table(name = "curriculo")
public class Curriculo {
    // Define o ID e a estratégia de geração.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidato_id", referencedColumnName = "id")
    private Candidato candidato;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExperienciaProfissional>  experienciaProfissional = new HashSet<>();

    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FormacaoAcademica> formacaoAcademica = new HashSet<>();

    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Habilidades> habilidades = new HashSet<>();

    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Idioma> idioma = new HashSet<>();

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Set<ExperienciaProfissional> getExperienciaProfissional() {
        return experienciaProfissional;
    }

    public void setExperienciaProfissional(Set<ExperienciaProfissional> experienciaProfissional) {
        this.experienciaProfissional = experienciaProfissional;
    }

    public Set<FormacaoAcademica> getFormacaoAcademica() {
        return formacaoAcademica;
    }

    public void setFormacaoAcademica(Set<FormacaoAcademica> formacaoAcademica) {
        this.formacaoAcademica = formacaoAcademica;
    }

    public Set<Habilidades> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(Set<Habilidades> habilidades) {
        this.habilidades = habilidades;
    }

    public Set<Idioma> getIdioma() {
        return idioma;
    }

    public void setIdioma(Set<Idioma> idioma) {
        this.idioma = idioma;
    }
}