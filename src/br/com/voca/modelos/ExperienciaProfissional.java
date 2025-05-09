package br.com.voca.modelos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ExperienciaProfissional {
    private String nomeEmpresa;
    private String cargoOcupado;
    private LocalDate inicio;
    private LocalDate fim;
    private String palavraChave;
    private List<String> palavras;

    //construtor vazio para o JavaFx
    public ExperienciaProfissional () {
    }

    public ExperienciaProfissional (String nomeEmpresa, String cargoOcupado, String inicio,
                                    String fim, String palavraChave) {
        setNomeEmpresa(nomeEmpresa);
        setCargoOcupado(cargoOcupado);
        setInicio(inicio);
        setFim(fim);
        setPalavraChave(palavraChave);
        this.palavras = new ArrayList<>();
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        if (nomeEmpresa != null && !nomeEmpresa.isBlank()) {
            this.nomeEmpresa = nomeEmpresa;
        }
    }

    public void setCargoOcupado(String cargoOcupado) {
        if (cargoOcupado != null && !cargoOcupado.isBlank()) {
            this.cargoOcupado = cargoOcupado;
        }
    }

    public void setInicio(String inicio) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("MM/yyyy");
        try {
            this.inicio = LocalDate.parse(inicio,formatador);
        } catch (DateTimeParseException e) {
            //tratar exceção baseado no front-end
        }
    }

    public void setFim(String fim) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("MM/yyyy");
        try {
            this.fim = LocalDate.parse(fim, formatador);
        } catch (DateTimeParseException e) {
            //tratar exceção baseada no front-end
        }
    }

    public void setPalavraChave (String palavraChave) {
        if (palavraChave != null && !palavraChave.isBlank()) {
            this.palavraChave = palavraChave;
        }
    }

    public void adicionarPalavras() {
        if (palavraChave != null && !palavraChave.isBlank() && !palavras.contains(palavraChave)) {
            palavras.add(palavraChave);
        }
    }
}