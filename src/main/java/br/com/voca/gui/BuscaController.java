package br.com.voca.gui;

import br.com.voca.dao.CandidatoDAO;
import br.com.voca.modelos.Candidato;
import br.com.voca.service.ExportacaoService;
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
    @FXML private TableView<Candidato> resultadosTable;
    @FXML private TableColumn<Candidato, String> resultadoNomeColumn;
    @FXML private TableColumn<Candidato, String> resultadoEmailColumn;
    @FXML private TableColumn<Candidato, String> resultadoTelefoneColumn;

    private final CandidatoDAO candidatoDAO = new CandidatoDAO();
    private final ObservableList<Candidato> resultadosData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        resultadoNomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        resultadoEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        resultadoTelefoneColumn.setCellValueFactory(new PropertyValueFactory<>("telefone"));
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
        exportarPdfButton.setDisable(resultadosData.isEmpty());

        if (resultadosData.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Busca Concluída", "Nenhum candidato encontrado.");
        }
    }

    @FXML
    private void exportarResultadosPDF() {
        // 1. Verifica se a lista de resultados não está vazia.
        if (resultadosData.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Nenhum resultado", "Não há dados na tabela para exportar.");
            return;
        }

        // 2. Cria e configura o FileChooser para salvar o arquivo.
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Relatório PDF");
        fileChooser.setInitialFileName("relatorio_candidatos.pdf");

        // 3. Define um filtro para mostrar apenas a extensão .pdf
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivos PDF (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        // 4. Obtém o "palco" (janela) atual a partir de qualquer componente da tela.
        Stage stage = (Stage) resultadosTable.getScene().getWindow();

        // 5. Abre a janela de "Salvar como..." e aguarda o usuário escolher um arquivo.
        File file = fileChooser.showSaveDialog(stage);

        // 6. Se o usuário escolheu um arquivo (file não é nulo), prossegue com a exportação.
        if (file != null) {
            ExportacaoService exportacaoService = new ExportacaoService();
            // Chama o serviço passando a lista de candidatos da tabela e o caminho do arquivo.
            exportacaoService.exportarCandidatosParaPDF(resultadosData, file.getAbsolutePath());

            // 7. Mostra uma mensagem de sucesso para o usuário.
            showAlert(Alert.AlertType.INFORMATION, "Exportação Concluída", "O arquivo PDF foi gerado com sucesso em: " + file.getAbsolutePath());
        }
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