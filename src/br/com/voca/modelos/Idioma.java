package br.com.voca.modelos;

public class Idioma {
    private String idioma;
    private String nivelStr;
    private Nivel nivel;

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

    public void setNivel (String nivelStr) {
        try{
            this.nivel = Nivel.valueOf(nivelStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            //implementar o tratamento no front-end
        }
    }
}
