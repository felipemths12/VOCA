package br.com.voca.modelos;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// Mapeia a classe para a tabela "formacoes_academicas".
@Entity
@Table(name = "formacoes_academicas")
public class FormacaoAcademica {
    // Define o ID e a estratégia de geração.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String curso; // Nome do curso.
    private String instituicao; // Nome da instituição.
    private String areaAtuacao; // Área de atuação.
    private LocalDate dataInicio; // Data de início.
    private LocalDate dataConclusao; // Data de conclusão.

    @Enumerated(EnumType.STRING) // Salva o Enum como texto.
    private SituacaoCurso situacaoCurso;

    @Transient // Não salva este campo no banco.
    private String situacaoCursoStr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id")
    private Curriculo curriculo;

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

    public enum SituacaoCurso {
        CONCLUIDO,
        CURSANDO,
        TRANCADO,
        DESISTENTE
    }

    public void setCurso(String curso) {
        if (curso != null && !curso.isBlank()) {
            this.curso = curso;
        }
    }

    public void setInstituicao(String instituicao) {
        if (instituicao != null && !instituicao.isBlank()) {
            this.instituicao = instituicao;
        }
    }

    public void setAreaAtuacao(String areaAtuacao) {
        if (areaAtuacao != null && !areaAtuacao.isBlank()) {
            this.areaAtuacao = areaAtuacao;
        }
    }

    // Converte a String de data de início para LocalDate.
    public void setDataInicio (String dataInicio) {
        if (dataInicio == null || dataInicio.isBlank()) {
            this.dataInicio = null;
            return;
        }
        try {
            String dataCompleta = "01/" + dataInicio;
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.dataInicio = LocalDate.parse(dataCompleta, formatador);
        } catch (DateTimeParseException e) {
            System.err.println("Formato de data de início inválido: " + dataInicio);
            e.printStackTrace();
            this.dataInicio = null;
        }
    }

    // Converte a String de data de conclusão para LocalDate.
    public void setDataConclusao (String dataConclusao) {
        if (dataConclusao == null || dataConclusao.isBlank()) {
            this.dataConclusao = null;
            return;
        }
        try {
            String dataCompleta = "01/" + dataConclusao;
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.dataConclusao = LocalDate.parse(dataCompleta, formatador);
        } catch (DateTimeParseException e) {
            System.err.println("Formato de data de conclusão inválido: " + dataConclusao);
            e.printStackTrace();
            this.dataConclusao = null;
        }
    }

    // Converte a String de situação do curso para o Enum.
    public void setSituacaoCurso(String situacaoCurso) {
        try {
            this.situacaoCurso = SituacaoCurso.valueOf(situacaoCurso.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Tratamento de exceção.
        }
    }


    // Getters
    public String getCurso() {
        return curso;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public String getAreaAtuacao() {
        return areaAtuacao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public SituacaoCurso getSituacaoCurso() {
        return situacaoCurso;
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