package br.com.voca.modelos;

import jakarta.persistence.*;

//anotação da JPA para mapear uma classe como tabela
@Entity
//anotação da JPA para definir um nome para a tabela
@Table(name = "habilidade")
public class Habilidades {
    //anotação da JPA para definir um id para cada objeto Habilidades
    @Id
    //anotação da JPA para definir como o id vai ser gerado, nesse caso, será incremental
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String habilidade; //atributo para armazenar a habilidade do usuário

    @Enumerated(EnumType.STRING) // CORREÇÃO: Salva o Enum como texto
    private Nivel nivel; //atributo utilizando Enum para o atributo receber valores fixos pré-determinados

    @Transient // CORREÇÃO: Ignora este campo no banco de dados
    private String nivelStr; //String para armazenar a situação e ser convertida para o Enum

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id")
    private Curriculo curriculo;

    //construtor vazio para o JavaFX
    public Habilidades () {
    }

    //construtor completo para instanciar objetos do tipo Idioma
    public Habilidades (String habilidade, String nivelStr) {
        setHabilidade(habilidade);
        setNivel(nivelStr);
    }

    //enumeração de valores fixos (constantes) para a situação do curso utilizando a classe Enum
    public enum Nivel {
        BASICO,
        INTERMEDIARIO,
        AVANCADO
    }

    //setter da habilidade com validação contra string nula ou vazia
    public void setHabilidade(String habilidade) {
        if (habilidade != null && !habilidade.isBlank()) {
            this.habilidade = habilidade;
        }
    }

    /*setter do nivel de habilidade. recebe uma string, deixa-a em letras maiúsculas e converte para o tipo Enum e atribui
    ao atributo Enum nivel*/
    public void setNivel(String nivelStr) {
        try {
            this.nivel = Nivel.valueOf(nivelStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            //implementar tratamento de exceção no front-end
        }
    }

    //getters para retornar os atributos da classe

    public String getHabilidade() {
        return habilidade;
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