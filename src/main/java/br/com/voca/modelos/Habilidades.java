package br.com.voca.modelos;

import jakarta.persistence.*;

// Mapeia a classe para a tabela "habilidade".
@Entity
@Table(name = "habilidade", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"curriculo_id", "habilidade"})
})
public class Habilidades {
    // Define o ID e a estratégia de geração.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String habilidade; // Habilidade do usuário.

    @Enumerated(EnumType.STRING) // Salva o Enum como texto.
    private Nivel nivel;

    @Transient // Não salva este campo no banco.
    private String nivelStr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id")
    private Curriculo curriculo;

    public Habilidades () {
    }

    public Habilidades (String habilidade, String nivelStr) {
        setHabilidade(habilidade);
        setNivel(nivelStr);
    }

    public enum Nivel {
        BASICO,
        INTERMEDIARIO,
        AVANCADO
    }

    public void setHabilidade(String habilidade) {
        if (habilidade != null && !habilidade.isBlank()) {
            this.habilidade = habilidade;
        }
    }

    // Converte a String de nível para o Enum.
    public void setNivel(String nivelStr) {
        try {
            this.nivel = Nivel.valueOf(nivelStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Tratamento de exceção.
        }
    }

    // Getters
    public String getHabilidade() {
        return habilidade;
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