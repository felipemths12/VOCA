package br.com.voca.service;

import br.com.voca.dao.CandidatoDAO;
import br.com.voca.modelos.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.io.FileWriter;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ExportacaoService {

    public void exportarCandidatosParaCSV(String nomeArquivo) {
        // Exporta todos os candidatos para um arquivo CSV.
        CandidatoDAO candidatoDAO = new CandidatoDAO();
        List<Candidato> candidatos = candidatoDAO.buscarTodos();

        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            // Cabeçalho
            writer.append("ID,Nome,Email,Telefone,Area de Atuacao,Anos de Experiencia\n");

            // Dados
            for (Candidato candidato : candidatos) {
                String areaDeAtuacao = getAreaDeAtuacaoPrincipal(candidato);
                String anosDeExperiencia = calcularTotalExperiencia(candidato);

                writer.append(String.valueOf(candidato.getId())).append(",");
                writer.append(candidato.getNome()).append(",");
                writer.append(candidato.getEmail()).append(",");
                writer.append(candidato.getTelefone()).append(",");
                writer.append(areaDeAtuacao).append(",");
                writer.append(anosDeExperiencia).append("\n");
            }
            System.out.println("Exportação para CSV concluída com sucesso! Arquivo gerado: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao exportar dados para CSV: " + e.getMessage());
        } finally {
            candidatoDAO.fechar();
        }
    }

    public void exportarCandidatosParaPDF(String nomeArquivo) {
        // Exporta todos os candidatos para um arquivo PDF.
        CandidatoDAO candidatoDAO = new CandidatoDAO();
        List<Candidato> candidatos = candidatoDAO.buscarTodos();

        try (PdfWriter writer = new PdfWriter(nomeArquivo);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            document.add(new Paragraph("Relatório de Candidatos")
                    .setBold()
                    .setFontSize(18)
                    .setMarginBottom(20));

            // Tabela
            float[] columnWidths = {1, 3, 4, 3, 3, 2};
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            table.useAllAvailableWidth();

            // Cabeçalho da tabela
            table.addHeaderCell(new Cell().add(new Paragraph("ID")));
            table.addHeaderCell(new Cell().add(new Paragraph("Nome")));
            table.addHeaderCell(new Cell().add(new Paragraph("Email")));
            table.addHeaderCell(new Cell().add(new Paragraph("Telefone")));
            table.addHeaderCell(new Cell().add(new Paragraph("Área de Atuação")));
            table.addHeaderCell(new Cell().add(new Paragraph("Experiência")));

            // Dados dos candidatos
            for (Candidato candidato : candidatos) {
                table.addCell(String.valueOf(candidato.getId()));
                table.addCell(candidato.getNome());
                table.addCell(candidato.getEmail());
                table.addCell(candidato.getTelefone());
                table.addCell(getAreaDeAtuacaoPrincipal(candidato));
                table.addCell(calcularTotalExperiencia(candidato));
            }

            document.add(table);
            System.out.println("Exportação para PDF concluída com sucesso! Arquivo gerado: " + nomeArquivo);

        } catch (IOException e) {
            System.err.println("Erro ao exportar dados para PDF: " + e.getMessage());
        } finally {
            candidatoDAO.fechar();
        }
    }

    private String getAreaDeAtuacaoPrincipal(Candidato candidato) {
        if (candidato.getCurriculo() == null || candidato.getCurriculo().getFormacaoAcademica() == null || candidato.getCurriculo().getFormacaoAcademica().isEmpty()) {
            return "N/A";
        }
        // Retorna a primeira área de atuação encontrada.
        return candidato.getCurriculo().getFormacaoAcademica().iterator().next().getAreaAtuacao();
    }

    private String calcularTotalExperiencia(Candidato candidato) {
        if (candidato.getCurriculo() == null || candidato.getCurriculo().getExperienciaProfissional() == null) {
            return "0 anos";
        }

        Set<ExperienciaProfissional> experiencias = candidato.getCurriculo().getExperienciaProfissional();
        long totalMeses = 0;
        for (ExperienciaProfissional exp : experiencias) {
            if (exp.getInicio() != null && exp.getFim() != null) {
                totalMeses += ChronoUnit.MONTHS.between(exp.getInicio(), exp.getFim());
            }
        }
        long anos = totalMeses / 12;
        return String.format("%d anos", anos);
    }

    /**
     * Exporta uma lista específica de candidatos para um arquivo PDF.
     * @param candidatos A lista de candidatos a ser exportada.
     * @param nomeArquivo O caminho do arquivo PDF a ser gerado.
     */
    public void exportarCandidatosParaPDF(List<Candidato> candidatos, String nomeArquivo) {
        try (PdfWriter writer = new PdfWriter(nomeArquivo);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            document.add(new Paragraph("Relatório de Candidatos Filtrados")
                    .setBold()
                    .setFontSize(18)
                    .setMarginBottom(20));

            float[] columnWidths = {1, 3, 4, 3, 3, 2};
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            table.useAllAvailableWidth();

            // Cabeçalho da tabela
            table.addHeaderCell(new Cell().add(new Paragraph("ID")));
            table.addHeaderCell(new Cell().add(new Paragraph("Nome")));
            table.addHeaderCell(new Cell().add(new Paragraph("Email")));
            table.addHeaderCell(new Cell().add(new Paragraph("Telefone")));
            table.addHeaderCell(new Cell().add(new Paragraph("Área de Atuação")));
            table.addHeaderCell(new Cell().add(new Paragraph("Experiência")));

            // Dados dos candidatos
            for (Candidato candidato : candidatos) {
                table.addCell(String.valueOf(candidato.getId()));
                table.addCell(candidato.getNome());
                table.addCell(candidato.getEmail());
                table.addCell(candidato.getTelefone());
                table.addCell(getAreaDeAtuacaoPrincipal(candidato));
                table.addCell(calcularTotalExperiencia(candidato));
            }

            document.add(table);
            System.out.println("Exportação para PDF concluída com sucesso! Arquivo gerado: " + nomeArquivo);

        } catch (IOException e) {
            System.err.println("Erro ao exportar dados para PDF: " + e.getMessage());
        }
    }

    public void exportarCandidatosParaCSV(List<Candidato> candidatos, String nomeArquivo) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.append("ID,Nome,Email,Telefone,Area de Atuacao,Anos de Experiencia\n");

            for (Candidato candidato : candidatos) {
                String areaDeAtuacao = getAreaDeAtuacaoPrincipal(candidato);
                String anosDeExperiencia = calcularTotalExperiencia(candidato);

                writer.append(String.valueOf(candidato.getId())).append(",");
                writer.append(candidato.getNome()).append(",");
                writer.append(candidato.getEmail()).append(",");
                writer.append(candidato.getTelefone()).append(",");
                writer.append(areaDeAtuacao).append(",");
                writer.append(anosDeExperiencia).append("\n");
            }
            System.out.println("Exportação para CSV concluída com sucesso! Arquivo gerado: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao exportar dados para CSV: " + e.getMessage());
        }
    }
}