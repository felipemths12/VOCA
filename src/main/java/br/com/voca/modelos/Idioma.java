package br.com.voca.modelos;

import jakarta.persistence.*;

//anotação da JPA para mapear uma classe como tabela
@Entity
// CORREÇÃO: Define que a combinação de curriculo_id e idioma deve ser única.
@Table(name = "idioma", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"curriculo_id", "idioma"})
})
public class Idioma {
    //anotação da JPA para definir um id para cada objeto Idioma
    @Id
    //anotação da JPA para definir como o id vai ser gerado, nesse caso, será incremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A anotação @Column(unique = true) foi removida daqui.
    private String idioma;

    @Transient // CORREÇÃO: Ignora este campo no banco de dados
    private String nivelStr;

    @Enumerated(EnumType.STRING) // CORREÇÃO: Salva o Enum como texto
    private Nivel nivel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id")
    private Curriculo curriculo;

    //construtor vazio para o JavaFX
    public Idioma () {
    }

    //construtor completo para instanciar objetos do tipo Idioma
    public Idioma (String idioma, String nivelStr) {
        setIdioma(idioma);
        setNivel(nivelStr);
    }

    //enumeração de valores fixos (constantes) para a situação do curso utilizando a classe Enum
    public enum Nivel {
        BASICO,
        INTERMEDIARIO,
        AVANCADO
    }

    //setter do idioma com validação contra string nula ou vazia
    public void setIdioma (String idioma) {
        if (idioma != null && !idioma.isBlank()) {
            this.idioma = idioma;
        }
    }

    /*setter do nivel de habilidade. recebe uma string, deixa-a em letras maiúsculas e converte para o tipo Enum e atribui
    ao atributo Enum nivel*/
    public void setNivel (String nivelStr) {
        try{
            this.nivel = Nivel.valueOf(nivelStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            //implementar o tratamento no front-end
        }
    }

    //getters para retornar os atributos da classe

    public String getIdioma() {
        return idioma;
    }

    public Nivel getNivel() {
        return nivel;
    }

    // CORREÇÃO: Adicionando getters e setters que faltavam
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