package br.com.voca.modelos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FormacaoAcademica {
    private String curso; //atributo para armazenar o nome do curso
    private String instituicao; //atributo para armazenar o nome da instituição de ensino
    private String areaAtuacao; //atributo para armazenar area de atuação do candidato
    private LocalDate dataInicio; //atributo para armazenar a data de inicio do curso
    private LocalDate dataConclusao; //atributo para armazenar a data de conclusão do curso
    private SituacaoCurso situacaoCurso;//atributo utilizando Enum para o atributo receber valores fixos pré-determinados
    private String situacaoCursoStr; //String para armazenar a situação e ser convertida para o Enum

    public FormacaoAcademica () {
    }

    public FormacaoAcademica (String curso, String instituicao, String areaAtuacao,
                              String dataInicio, String dataConclusao, String situacaoCursoStr) {
        setCurso(curso);
        setInstituicao(instituicao);
        setAreaAtuacao(areaAtuacao);
        setDataInicio(dataInicio);
        setDataConclusao(dataConclusao);
        setSituacaoCurso(situacaoCursoStr);
    }
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

    /*setter da data de início do curso que utiliza um formatador para especificar o formato e converte a String em um
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

    public void setSituacaoCurso(String situacaoCurso) {
        try {
            this.situacaoCurso = SituacaoCurso.valueOf(situacaoCurso.toUpperCase());
        } catch (IllegalArgumentException e) {
            //implementar o tratamento da exceção baseado no front-end
        }
    }
}