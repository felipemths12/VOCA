package br.com.voca.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "idioma")
public class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String idioma;
    private String nivelStr;
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
}
