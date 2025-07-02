package br.com.voca.gui;

import br.com.voca.dao.CandidatoDAO;
import br.com.voca.modelos.Candidato;
import br.com.voca.modelos.FormacaoAcademica;
import br.com.voca.service.CalculoExperienciaService;
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
import java.util.List;

public class BuscaController {

    @FXML private TextField filtroAreaField;
    @FXML private TextField filtroAnosExpField;
    @FXML private Button exportarPdfButton;
    @FXML private Button exportarCsvButton;
    @FXML private TableView<Candidato> resultadosTable;
    @FXML private TableColumn<Candidato, String> resultadoNomeColumn;
    @FXML private TableColumn<Candidato, String> resultadoEmailColumn;
    @FXML private TableColumn<Candidato, String> resultadoTelefoneColumn;
    @FXML private TableColumn<Candidato, String> resultadoAreaColumn;
    @FXML private TableColumn<Candidato, String> resultadoExperienciaColumn;

    private final CandidatoDAO candidatoDAO = new CandidatoDAO();
    private final CalculoExperienciaService experienciaService = new CalculoExperienciaService();
    private final ObservableList<Candidato> resultadosData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        resultadoNomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        resultadoEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        resultadoTelefoneColumn.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        resultadoAreaColumn.setCellValueFactory(cellData -> {
            String area = getAreaDeAtuacaoPrincipal(cellData.getValue());
            return new SimpleStringProperty(area);
        });

        resultadoExperienciaColumn.setCellValueFactory(cellData -> {
            String experiencia = experienciaService.calcularTotalExperiencia(cellData.getValue());
            return new SimpleStringProperty(experiencia);
        });

        resultadosTable.setRowFactory(tv -> {
            TableRow<Candidato> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Candidato rowData = row.getItem();
                    MainApp.showEdicaoView(rowData.getId());
                }
            });
            return row;
        });

        resultadosTable.setItems(resultadosData);
    }

    @FXML
    private void filtrarCandidatos() {
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

        boolean semResultados = resultadosData.isEmpty();
        exportarPdfButton.setDisable(semResultados);
        exportarCsvButton.setDisable(semResultados);

        if (semResultados) {
            showAlert(Alert.AlertType.INFORMATION, "Busca Concluída", "Nenhum candidato encontrado.");
        }
    }

    @FXML
    private void exportarResultadosPDF() {
        exportar(ExportType.PDF);
    }

    @FXML
    private void exportarResultadosCSV() {
        exportar(ExportType.CSV);
    }

    private void exportar(ExportType type) {
        if (resultadosData.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Nenhum resultado", "Não há dados na tabela para exportar.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        String initialFileName = "relatorio_candidatos." + type.name().toLowerCase();
        String extensionFilter = "Arquivos " + type.name() + " (*." + type.name().toLowerCase() + ")";

        fileChooser.setTitle("Salvar Relatório " + type.name());
        fileChooser.setInitialFileName(initialFileName);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(extensionFilter, "*." + type.name().toLowerCase());
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = (Stage) resultadosTable.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            ExportacaoService exportacaoService = new ExportacaoService();
            if (type == ExportType.PDF) {
                exportacaoService.exportarCandidatosParaPDF(resultadosData, file.getAbsolutePath());
            } else {
                exportacaoService.exportarCandidatosParaCSV(resultadosData, file.getAbsolutePath());
            }
            showAlert(Alert.AlertType.INFORMATION, "Exportação Concluída", "O arquivo " + type.name() + " foi gerado com sucesso.");
        }
    }

    private String getAreaDeAtuacaoPrincipal(Candidato candidato) {
        if (candidato.getCurriculo() == null || candidato.getCurriculo().getFormacaoAcademica().isEmpty()) {
            return "N/A";
        }
        FormacaoAcademica formacao = candidato.getCurriculo().getFormacaoAcademica().iterator().next();
        return formacao.getAreaAtuacao();
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

    private enum ExportType { PDF, CSV }
}