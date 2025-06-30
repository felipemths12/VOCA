package br.com.voca.modelos;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

//anotação da JPA para mapear uma classe como tabela
@Entity
//anotação da JPA para definir um nome para a tabela
@Table(name = "curriculo")
public class Curriculo {
    //anotação da JPA para definir um id para cada objeto Curriculo
    @Id
    //anotação da JPA para definir como o id vai ser gerado, nesse caso, será incremental
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

    // --- GETTERS E SETTERS ADICIONADOS ---

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