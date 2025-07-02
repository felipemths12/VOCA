package br.com.voca.gui;

import br.com.voca.dao.CandidatoDAO;
import br.com.voca.modelos.*;
import br.com.voca.service.EnderecoRequisicao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;

public class EdicaoController {

    // DAOs, Serviços e objeto do candidato atual
    private final CandidatoDAO candidatoDAO = new CandidatoDAO();
    private final EnderecoRequisicao enderecoRequisicao = new EnderecoRequisicao();
    private Candidato candidatoAtual;

    // --- FXML - Dados Pessoais e Endereço ---
    @FXML private TextField nomeField, dataNascimentoField, emailField, telefoneField, nacionalidadeField;
    @FXML private TextField cepField, logradouroField, numeroField, bairroField, localidadeField, ufField;

    // --- FXML - Formação Acadêmica ---
    @FXML private TableView<FormacaoAcademica> formacaoTable;
    @FXML private TableColumn<FormacaoAcademica, String> cursoColumn, instituicaoColumn, situacaoColumn;
    @FXML private TableColumn<FormacaoAcademica, LocalDate> formacaoInicioColumn, formacaoFimColumn;
    @FXML private TextField formacaoCursoField, formacaoInstituicaoField, formacaoAreaField, formacaoInicioField, formacaoFimField;
    @FXML private ComboBox<FormacaoAcademica.SituacaoCurso> formacaoSituacaoCombo;
    private final ObservableList<FormacaoAcademica> formacaoData = FXCollections.observableArrayList();

    // --- FXML - Experiência Profissional ---
    @FXML private TableView<ExperienciaProfissional> experienciaTable;
    @FXML private TableColumn<ExperienciaProfissional, String> empresaColumn, cargoColumn;
    @FXML private TableColumn<ExperienciaProfissional, LocalDate> expInicioColumn, expFimColumn;
    @FXML private TextField expEmpresaField, expCargoField, expInicioField, expFimField, expPalavraChaveField;
    private final ObservableList<ExperienciaProfissional> experienciaData = FXCollections.observableArrayList();

    // --- FXML - Habilidades ---
    @FXML private TableView<Habilidades> habilidadesTable;
    @FXML private TableColumn<Habilidades, String> habilidadeColumn, habilidadeNivelColumn;
    @FXML private TextField habilidadeField;
    @FXML private ComboBox<Habilidades.Nivel> habilidadeNivelCombo;
    private final ObservableList<Habilidades> habilidadesData = FXCollections.observableArrayList();

    // --- FXML - Idiomas ---
    @FXML private TableView<Idioma> idiomasTable;
    @FXML private TableColumn<Idioma, String> idiomaColumn, idiomaNivelColumn;
    @FXML private TextField idiomaField;
    @FXML private ComboBox<Idioma.Nivel> idiomaNivelCombo;
    private final ObservableList<Idioma> idiomasData = FXCollections.observableArrayList();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

    @FXML
    public void initialize() {
        configurarTabelas();
        configurarComboBoxes();
    }

    public void carregarCandidato(Long candidatoId) {
        this.candidatoAtual = candidatoDAO.buscarPorId(candidatoId);
        if (candidatoAtual == null) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Candidato com ID " + candidatoId + " não encontrado.");
            handleVoltar();
            return;
        }

        // Popula Dados Pessoais
        nomeField.setText(candidatoAtual.getNome());
        emailField.setText(candidatoAtual.getEmail());
        telefoneField.setText(candidatoAtual.getTelefone());
        nacionalidadeField.setText(candidatoAtual.getNacionalidade());
        if (candidatoAtual.getDataNascimento() != null) {
            dataNascimentoField.setText(candidatoAtual.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }

        Curriculo curriculo = candidatoAtual.getCurriculo();
        if (curriculo == null) {
            curriculo = new Curriculo();
            candidatoAtual.setCurriculo(curriculo);
        }

        // Popula Endereço
        if (curriculo.getEndereco() != null) {
            Endereco endereco = curriculo.getEndereco();
            cepField.setText(endereco.getCep());
            logradouroField.setText(endereco.getLogradouro());
            numeroField.setText(endereco.getNumero());
            bairroField.setText(endereco.getBairro());
            localidadeField.setText(endereco.getLocalidade());
            ufField.setText(endereco.getUf());
        }

        // Popula Tabelas
        formacaoData.setAll(curriculo.getFormacaoAcademica());
        experienciaData.setAll(curriculo.getExperienciaProfissional());
        habilidadesData.setAll(curriculo.getHabilidades());
        idiomasData.setAll(curriculo.getIdioma());
    }

    @FXML
    private void handleSalvarAlteracoes() {
        try {
            // Atualiza dados pessoais
            candidatoAtual.setNome(nomeField.getText());
            candidatoAtual.setEmail(emailField.getText());
            candidatoAtual.setTelefone(telefoneField.getText());
            candidatoAtual.setNacionalidade(nacionalidadeField.getText());
            candidatoAtual.setDataNascimento(dataNascimentoField.getText());

            // Atualiza endereço
            Endereco endereco = candidatoAtual.getCurriculo().getEndereco();
            if (endereco == null) endereco = new Endereco();
            endereco = new Endereco(cepField.getText(), logradouroField.getText(), bairroField.getText(),
                    localidadeField.getText(), ufField.getText(), numeroField.getText());
            candidatoAtual.getCurriculo().setEndereco(endereco);

            // Atualiza as listas (conjuntos) no objeto currículo
            candidatoAtual.getCurriculo().setFormacaoAcademica(new HashSet<>(formacaoData));
            candidatoAtual.getCurriculo().setExperienciaProfissional(new HashSet<>(experienciaData));
            candidatoAtual.getCurriculo().setHabilidades(new HashSet<>(habilidadesData));
            candidatoAtual.getCurriculo().setIdioma(new HashSet<>(idiomasData));

            // Garante a associação bidirecional
            candidatoAtual.getCurriculo().getFormacaoAcademica().forEach(f -> f.setCurriculo(candidatoAtual.getCurriculo()));
            candidatoAtual.getCurriculo().getExperienciaProfissional().forEach(e -> e.setCurriculo(candidatoAtual.getCurriculo()));
            candidatoAtual.getCurriculo().getHabilidades().forEach(h -> h.setCurriculo(candidatoAtual.getCurriculo()));
            candidatoAtual.getCurriculo().getIdioma().forEach(i -> i.setCurriculo(candidatoAtual.getCurriculo()));

            candidatoDAO.atualizar(candidatoAtual);
            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Currículo atualizado com sucesso!");
            handleVoltar();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Salvar", "Ocorreu um erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleApagarCurriculo() {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Você tem certeza que deseja apagar o currículo de " + candidatoAtual.getNome() + "?");
        confirmacao.setContentText("Esta ação não pode ser desfeita.");

        Optional<ButtonType> resultado = confirmacao.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                candidatoDAO.remover(candidatoAtual.getId());
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Currículo apagado com sucesso!");
                handleVoltar();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erro ao Apagar", "Ocorreu um erro: " + e.getMessage());
            }
        }
    }

    // --- Métodos de Adição e Remoção para as Tabelas ---

    @FXML private void adicionarFormacao() {
        formacaoData.add(new FormacaoAcademica(
                formacaoCursoField.getText(), formacaoInstituicaoField.getText(), formacaoAreaField.getText(),
                formacaoInicioField.getText(), formacaoFimField.getText(), formacaoSituacaoCombo.getValue().toString()
        ));
        limparCamposFormacao();
    }
    @FXML private void removerFormacao() { formacaoData.remove(formacaoTable.getSelectionModel().getSelectedItem()); }

    @FXML private void adicionarExperiencia() {
        experienciaData.add(new ExperienciaProfissional(
                expEmpresaField.getText(), expCargoField.getText(), expInicioField.getText(),
                expFimField.getText(), expPalavraChaveField.getText()
        ));
        limparCamposExperiencia();
    }
    @FXML private void removerExperiencia() { experienciaData.remove(experienciaTable.getSelectionModel().getSelectedItem()); }

    @FXML private void adicionarHabilidade() {
        habilidadesData.add(new Habilidades(habilidadeField.getText(), habilidadeNivelCombo.getValue().toString()));
        limparCamposHabilidade();
    }
    @FXML private void removerHabilidade() { habilidadesData.remove(habilidadesTable.getSelectionModel().getSelectedItem()); }

    @FXML private void adicionarIdioma() {
        idiomasData.add(new Idioma(idiomaField.getText(), idiomaNivelCombo.getValue().toString()));
        limparCamposIdioma();
    }
    @FXML private void removerIdioma() { idiomasData.remove(idiomasTable.getSelectionModel().getSelectedItem()); }

    // --- Métodos Auxiliares ---

    @FXML private void buscarCep() {
        try {
            Endereco endereco = enderecoRequisicao.buscar(cepField.getText());
            if (endereco != null) {
                logradouroField.setText(endereco.getLogradouro());
                bairroField.setText(endereco.getBairro());
                localidadeField.setText(endereco.getLocalidade());
                ufField.setText(endereco.getUf());
            }
        } catch (IOException | InterruptedException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de API", "Não foi possível buscar o CEP.");
        }
    }

    @FXML private void handleVoltar() { MainApp.showBuscaView(); }

    private void configurarTabelas() {
        // Formação
        cursoColumn.setCellValueFactory(new PropertyValueFactory<>("curso"));
        instituicaoColumn.setCellValueFactory(new PropertyValueFactory<>("instituicao"));
        situacaoColumn.setCellValueFactory(new PropertyValueFactory<>("situacaoCurso"));
        formacaoInicioColumn.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        formatarColunaData(formacaoInicioColumn);
        formacaoFimColumn.setCellValueFactory(new PropertyValueFactory<>("dataConclusao"));
        formatarColunaData(formacaoFimColumn);
        formacaoTable.setItems(formacaoData);

        // Experiência
        empresaColumn.setCellValueFactory(new PropertyValueFactory<>("nomeEmpresa"));
        cargoColumn.setCellValueFactory(new PropertyValueFactory<>("cargoOcupado"));
        expInicioColumn.setCellValueFactory(new PropertyValueFactory<>("inicio"));
        formatarColunaData(expInicioColumn);
        expFimColumn.setCellValueFactory(new PropertyValueFactory<>("fim"));
        formatarColunaData(expFimColumn);
        experienciaTable.setItems(experienciaData);

        // Habilidades
        habilidadeColumn.setCellValueFactory(new PropertyValueFactory<>("habilidade"));
        habilidadeNivelColumn.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        habilidadesTable.setItems(habilidadesData);

        // Idiomas
        idiomaColumn.setCellValueFactory(new PropertyValueFactory<>("idioma"));
        idiomaNivelColumn.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        idiomasTable.setItems(idiomasData);
    }

    private void configurarComboBoxes() {
        formacaoSituacaoCombo.setItems(FXCollections.observableArrayList(FormacaoAcademica.SituacaoCurso.values()));
        habilidadeNivelCombo.setItems(FXCollections.observableArrayList(Habilidades.Nivel.values()));
        idiomaNivelCombo.setItems(FXCollections.observableArrayList(Idioma.Nivel.values()));
    }

    private <T> void formatarColunaData(TableColumn<T, LocalDate> column) {
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formatter.format(item));
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // --- Métodos para Limpar Campos de Adição ---
    private void limparCamposFormacao() { formacaoCursoField.clear(); formacaoInstituicaoField.clear(); formacaoAreaField.clear(); formacaoInicioField.clear(); formacaoFimField.clear(); formacaoSituacaoCombo.getSelectionModel().clearSelection(); }
    private void limparCamposExperiencia() { expEmpresaField.clear(); expCargoField.clear(); expInicioField.clear(); expFimField.clear(); expPalavraChaveField.clear(); }
    private void limparCamposHabilidade() { habilidadeField.clear(); habilidadeNivelCombo.getSelectionModel().clearSelection(); }
    private void limparCamposIdioma() { idiomaField.clear(); idiomaNivelCombo.getSelectionModel().clearSelection(); }
}