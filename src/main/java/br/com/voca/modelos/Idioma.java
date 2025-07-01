package br.com.voca.modelos;

import jakarta.persistence.*;

// Mapeia a classe para a tabela "idioma".
@Entity
@Table(name = "idioma", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"curriculo_id", "idioma"})
})
public class Idioma {
    // Define o ID e a estratégia de geração.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idioma;

    @Transient // Não salva este campo no banco.
    private String nivelStr;

    @Enumerated(EnumType.STRING) // Salva o Enum como texto.
    private Nivel nivel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id")
    private Curriculo curriculo;

    public Idioma () {
    }

    public Idioma (String idioma, String nivelStr) {
        setIdioma(idioma);
        setNivel(nivelStr);
    }

    public enum Nivel {
        BASICO,
        INTERMEDIARIO,
        AVANCADO
    }

    public void setIdioma (String idioma) {
        if (idioma != null && !idioma.isBlank()) {
            this.idioma = idioma;
        }
    }

    // Converte a String de nível para o Enum.
    public void setNivel (String nivelStr) {
        try{
            this.nivel = Nivel.valueOf(nivelStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Tratamento de exceção.
        }
    }

    // Getters
    public String getIdioma() {
        return idioma;
    }

    public Nivel getNivel() {
        return nivel;
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