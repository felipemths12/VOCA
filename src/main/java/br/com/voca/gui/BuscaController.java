package br.com.voca.gui;

import br.com.voca.dao.CandidatoDAO;
import br.com.voca.modelos.Candidato;
import br.com.voca.modelos.ExperienciaProfissional;
import br.com.voca.modelos.FormacaoAcademica;
import br.com.voca.service.ExportacaoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

public class BuscaController {

    @FXML private TextField filtroAreaField;
    @FXML private TextField filtroAnosExpField;
    @FXML private Button exportarPdfButton;
    @FXML private TableView<Candidato> resultadosTable;
    @FXML private TableColumn<Candidato, String> resultadoNomeColumn;
    @FXML private TableColumn<Candidato, String> resultadoEmailColumn;
    @FXML private TableColumn<Candidato, String> resultadoTelefoneColumn;
    @FXML private TableColumn<Candidato, String> resultadoAreaColumn;
    @FXML private TableColumn<Candidato, String> resultadoExperienciaColumn;


    private final CandidatoDAO candidatoDAO = new CandidatoDAO();
    private final ObservableList<Candidato> resultadosData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configura as colunas da tabela.
        resultadoNomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        resultadoEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        resultadoTelefoneColumn.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        // Configura a coluna "Área de Atuação".
        resultadoAreaColumn.setCellValueFactory(cellData -> {
            String area = getAreaDeAtuacaoPrincipal(cellData.getValue());
            return new SimpleStringProperty(area);
        });

        // Configura a coluna "Experiência".
        resultadoExperienciaColumn.setCellValueFactory(cellData -> {
            String experiencia = calcularTotalExperiencia(cellData.getValue());
            return new SimpleStringProperty(experiencia);
        });

        resultadosTable.setItems(resultadosData);
    }

    @FXML
    private void filtrarCandidatos() {
        // Filtra os candidatos com base nos campos de busca.
        String area = filtroAreaField.getText();
        String anosExpStr = filtroAnosExpField.getText();
        Integer anosExperiencia = null;

        if (anosExpStr != null && !anosExpStr.isBlank()) {
            try {
                anosExperiencia = Integer.parseInt(anosExpStr);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Formato Inválido", "Insira um número válido para anos.");
                return;
            }
        }

        List<Candidato> candidatosFiltrados = candidatoDAO.buscarPorFiltros(area, anosExperiencia);
        resultadosData.clear();
        resultadosData.addAll(candidatosFiltrados);
        exportarPdfButton.setDisable(resultadosData.isEmpty());

        if (resultadosData.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Busca Concluída", "Nenhum candidato encontrado.");
        }
    }

    @FXML
    private void exportarResultadosPDF() {
        // Exporta os resultados da busca para um arquivo PDF.
        if (resultadosData.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Nenhum resultado", "Não há dados na tabela para exportar.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Relatório PDF");
        fileChooser.setInitialFileName("relatorio_candidatos.pdf");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivos PDF (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = (Stage) resultadosTable.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            ExportacaoService exportacaoService = new ExportacaoService();
            exportacaoService.exportarCandidatosParaPDF(resultadosData, file.getAbsolutePath());
            showAlert(Alert.AlertType.INFORMATION, "Exportação Concluída", "O arquivo PDF foi gerado com sucesso em: " + file.getAbsolutePath());
        }
    }

    // Métodos auxiliares para cálculo das colunas.
    private String getAreaDeAtuacaoPrincipal(Candidato candidato) {
        if (candidato == null || candidato.getCurriculo() == null || candidato.getCurriculo().getFormacaoAcademica() == null || candidato.getCurriculo().getFormacaoAcademica().isEmpty()) {
            return "N/A";
        }
        // Pega a primeira área de formação encontrada.
        FormacaoAcademica formacao = candidato.getCurriculo().getFormacaoAcademica().iterator().next();
        return formacao.getAreaAtuacao();
    }

    private String calcularTotalExperiencia(Candidato candidato) {
        if (candidato == null || candidato.getCurriculo() == null || candidato.getCurriculo().getExperienciaProfissional() == null) {
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


    @FXML
    private void handleVoltar() {
        MainApp.showMainMenuView();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}