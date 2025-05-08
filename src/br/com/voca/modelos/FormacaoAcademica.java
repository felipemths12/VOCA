package br.com.voca.modelos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FormacaoAcademica {
    private String curso;
    private String instituicao;
    private String areaAtuacao;
    private LocalDate dataInicio;
    private LocalDate dataConclusao;
    private SituacaoCurso situacaoCurso; //atributo utilizando Enum para o atributo receber valores fixos pré-determinados

    //enumeração de valores fixos (constantes) para a situação do curso utilizando a classe Enum
    public enum SituacaoCurso {
        CONCLUIDO,
        CURSANDO,
        TRANCADO,
        DESISTENTE
    }

    //setter do nome do curso com validação contra string nula ou vazia
    public void setCurso(String curso) {
        if (curso != null && !curso.isBlank()) {
            this.curso = curso;
        }
    }

    //setter do nome da instituição com validação contra string nula ou vazia
    public void setInstituicao(String instituicao) {
        if (instituicao != null && !instituicao.isBlank()) {
            this.instituicao = instituicao;
        }
    }

    //setter do nome do curso com validação contra string nula ou vazia
    public void setAreaAtuacao(String areaAtuacao) {
        if (areaAtuacao != null && !areaAtuacao.isBlank()) {
            this.areaAtuacao = areaAtuacao;
        }
    }

    /*setter da data de inicio do curso que utiliza um formatador para especificar o formato e converte a String em um
    atributo do tipo LocalDate seguindo o padrão mm/aaaa*/
    public void setDataInicio (String dataInicio) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("MM/yyyy");
        try {
            this.dataInicio = LocalDate.parse(dataInicio, formatador);
        } catch (DateTimeParseException e) {
            //implementar o tratamento da exceção baseado no front-end
        }
    }

    /*setter da data de conclusão do curso que utiliza um formatador para especificar o formato e converte a String em um
    atributo do tipo LocalDate seguindo o padrão mm/aaaa*/
    public void setDataConclusao (String dataConclusao) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("MM/yyyy");
        try {
            this.dataConclusao = LocalDate.parse(dataConclusao, formatador);
        } catch (DateTimeParseException e) {
            //implementar o tratamento da exceção baseado no front-end
        }
    }

}
