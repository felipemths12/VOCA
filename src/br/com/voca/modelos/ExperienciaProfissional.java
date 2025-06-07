package br.com.voca.modelos;

import java.time.LocalDate; //biblioteca utilizada para representar datas
import java.time.format.DateTimeFormatter; //biblioteca para formatar e analisar datas
import java.time.format.DateTimeParseException; //classe para tratar erros de conversão
import java.util.ArrayList; //biblioteca para criar array redimensionáveis
import java.util.List; //interface para definir uma lista ordenada de elementos

public class ExperienciaProfissional {
    private String nomeEmpresa; //atributo para armazenar o nome da empresa em que o usuário trabalhou
    private String cargoOcupado; //atributo para armazenar o cargo que o usuário trabalhou
    private LocalDate inicio; //atributo para armazenar a data de início do trabalho
    private LocalDate fim; //atributo para armazenar a data de finalização do trabalho
    private String palavraChave; //atributo para armazenar uma palavra-chave
    private List<String> palavras; //lista para armazenar um conjunto de palavras-chave

    //construtor vazio para o JavaFx
    public ExperienciaProfissional () {
    }

    //construtor completo para instanciar um objeto do tipo Experiencia profissional
    public ExperienciaProfissional (String nomeEmpresa, String cargoOcupado, String inicio,
                                    String fim, String palavraChave) {
        setNomeEmpresa(nomeEmpresa);
        setCargoOcupado(cargoOcupado);
        setInicio(inicio);
        setFim(fim);
        setPalavraChave(palavraChave);
        this.palavras = new ArrayList<>();
    }

    //setter do nome da empresa com validação contra string nula ou vazia
    public void setNomeEmpresa(String nomeEmpresa) {
        if (nomeEmpresa != null && !nomeEmpresa.isBlank()) {
            this.nomeEmpresa = nomeEmpresa;
        }
    }

    //setter do cargo ocupado com validação contra string nula ou vazia
    public void setCargoOcupado(String cargoOcupado) {
        if (cargoOcupado != null && !cargoOcupado.isBlank()) {
            this.cargoOcupado = cargoOcupado;
        }
    }

    /*setter da data de início do trabalho que utiliza um formatador para especificar o formato e converte a String em um
    atributo do tipo LocalDate seguindo o padrão mm/aaaa*/
    public void setInicio(String inicio) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("MM/yyyy");
        try {
            this.inicio = LocalDate.parse(inicio,formatador);
        } catch (DateTimeParseException e) {
            //tratar exceção baseado no front-end
        }
    }

    /*setter da data de encerramento do trabalho que utiliza um formatador para especificar o formato e converte a String em um
    atributo do tipo LocalDate seguindo o padrão mm/aaaa*/
    public void setFim(String fim) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("MM/yyyy");
        try {
            this.fim = LocalDate.parse(fim, formatador);
        } catch (DateTimeParseException e) {
            //tratar exceção baseada no front-end
        }
    }

    //setter da palavra-chave com validação contra string nula ou vazia
    public void setPalavraChave (String palavraChave) {
        if (palavraChave != null && !palavraChave.isBlank()) {
            this.palavraChave = palavraChave;
        }
    }

    /*metodo para adicionar uma palavra-chave à lista, com verificações contra String nula e vazia, impedindo
    também que tenham palavras duplicadas*/
    public void adicionarPalavras() {
        if (palavraChave != null && !palavraChave.isBlank() && !palavras.contains(palavraChave)) {
            palavras.add(palavraChave);
        }
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public String getCargoOcupado() {
        return cargoOcupado;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public List<String> getPalavras() {
        return palavras;
    }
}