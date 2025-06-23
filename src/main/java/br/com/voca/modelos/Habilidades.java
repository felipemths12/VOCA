package br.com.voca.modelos;

public class Habilidades {
    private String habilidade; //atributo para armazenar a habilidade do usuário
    private Nivel nivel; //atributo utilizando Enum para o atributo receber valores fixos pré-determinados
    private String nivelStr; //String para armazenar a situação e ser convertida para o Enum

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
}
