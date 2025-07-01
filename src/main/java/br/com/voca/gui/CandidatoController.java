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

public class CandidatoController {

    // DAOs e Serviços
    private final CandidatoDAO candidatoDAO = new CandidatoDAO();
    private final EnderecoRequisicao enderecoRequisicao = new EnderecoRequisicao();

    // Campos do Candidato e Endereço
    @FXML private TextField nomeField, dataNascimentoField, emailField, telefoneField, nacionalidadeField;
    @FXML private TextField cepField, logradouroField, numeroField, bairroField, localidadeField, ufField;

    // Componentes de Formação Acadêmica
    @FXML private TableView<FormacaoAcademica> formacaoTable;
    @FXML private TableColumn<FormacaoAcademica, String> cursoColumn, instituicaoColumn, situacaoColumn;
    @FXML private TableColumn<FormacaoAcademica, LocalDate> formacaoInicioColumn;
    @FXML private TableColumn<FormacaoAcademica, LocalDate> formacaoFimColumn;
    @FXML private TextField formacaoCursoField, formacaoInstituicaoField, formacaoAreaField, formacaoInicioField, formacaoFimField;
    @FXML private ComboBox<FormacaoAcademica.SituacaoCurso> formacaoSituacaoCombo;
    private final ObservableList<FormacaoAcademica> formacaoData = FXCollections.observableArrayList();

    // Componentes de Experiência Profissional
    @FXML private TableView<ExperienciaProfissional> experienciaTable;
    @FXML private TableColumn<ExperienciaProfissional, String> empresaColumn, cargoColumn;
    @FXML private TableColumn<ExperienciaProfissional, LocalDate> expInicioColumn;
    @FXML private TableColumn<ExperienciaProfissional, LocalDate> expFimColumn;
    @FXML private TextField expEmpresaField, expCargoField, expInicioField, expFimField, expPalavraChaveField;
    private final ObservableList<ExperienciaProfissional> experienciaData = FXCollections.observableArrayList();

    // Componentes de Habilidades
    @FXML private TableView<Habilidades> habilidadesTable;
    @FXML private TableColumn<Habilidades, String> habilidadeColumn, habilidadeNivelColumn;
    @FXML private TextField habilidadeField;
    @FXML private ComboBox<Habilidades.Nivel> habilidadeNivelCombo;
    private final ObservableList<Habilidades> habilidadesData = FXCollections.observableArrayList();

    // Componentes de Idiomas
    @FXML private TableView<Idioma> idiomasTable;
    @FXML private TableColumn<Idioma, String> idiomaColumn, idiomaNivelColumn;
    @FXML private TextField idiomaField;
    @FXML private ComboBox<Idioma.Nivel> idiomaNivelCombo;
    private final ObservableList<Idioma> idiomasData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabelas();
        configurarComboBoxes();
    }

    private void configurarTabelas() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        // Tabela de Formação Acadêmica
        cursoColumn.setCellValueFactory(new PropertyValueFactory<>("curso"));
        instituicaoColumn.setCellValueFactory(new PropertyValueFactory<>("instituicao"));
        situacaoColumn.setCellValueFactory(new PropertyValueFactory<>("situacaoCurso"));
        formacaoInicioColumn.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        formacaoInicioColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null || empty ? null : formatter.format(item));
            }
        });
        formacaoFimColumn.setCellValueFactory(new PropertyValueFactory<>("dataConclusao"));
        formacaoFimColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null || empty ? null : formatter.format(item));
            }
        });
        formacaoTable.setItems(formacaoData);


        // Tabela de Experiência Profissional
        empresaColumn.setCellValueFactory(new PropertyValueFactory<>("nomeEmpresa"));
        cargoColumn.setCellValueFactory(new PropertyValueFactory<>("cargoOcupado"));
        expInicioColumn.setCellValueFactory(new PropertyValueFactory<>("inicio"));
        expInicioColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null || empty ? null : formatter.format(item));
            }
        });
        expFimColumn.setCellValueFactory(new PropertyValueFactory<>("fim"));
        expFimColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null || empty ? null : formatter.format(item));
            }
        });
        experienciaTable.setItems(experienciaData);


        // Tabela de Habilidades
        habilidadeColumn.setCellValueFactory(new PropertyValueFactory<>("habilidade"));
        habilidadeNivelColumn.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        habilidadesTable.setItems(habilidadesData);

        // Tabela de Idiomas
        idiomaColumn.setCellValueFactory(new PropertyValueFactory<>("idioma"));
        idiomaNivelColumn.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        idiomasTable.setItems(idiomasData);
    }

    private void configurarComboBoxes() {
        formacaoSituacaoCombo.setItems(FXCollections.observableArrayList(FormacaoAcademica.SituacaoCurso.values()));
        habilidadeNivelCombo.setItems(FXCollections.observableArrayList(Habilidades.Nivel.values()));
        idiomaNivelCombo.setItems(FXCollections.observableArrayList(Idioma.Nivel.values()));
    }

    @FXML
    private void buscarCep() {
        // Busca o endereço a partir do CEP informado.
        try {
            Endereco endereco = enderecoRequisicao.buscar(cepField.getText());
            if (endereco != null) {
                logradouroField.setText(endereco.getLogradouro());
                bairroField.setText(endereco.getBairro());
                localidadeField.setText(endereco.getLocalidade());
                ufField.setText(endereco.getUf());
            }
        } catch (IOException | InterruptedException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de API", "Não foi possível buscar o CEP. Verifique o número e sua conexão.");
        }
    }

    @FXML
    private void adicionarFormacao() {
        // Adiciona uma nova formação acadêmica à lista.
        FormacaoAcademica formacao = new FormacaoAcademica(
                formacaoCursoField.getText(),
                formacaoInstituicaoField.getText(),
                formacaoAreaField.getText(),
                formacaoInicioField.getText(),
                formacaoFimField.getText(),
                formacaoSituacaoCombo.getValue().toString()
        );
        formacaoData.add(formacao);
        limparCamposFormacao();
    }

    @FXML
    private void adicionarExperiencia() {
        // Adiciona uma nova experiência profissional à lista.
        ExperienciaProfissional experiencia = new ExperienciaProfissional(
                expEmpresaField.getText(),
                expCargoField.getText(),
                expInicioField.getText(),
                expFimField.getText(),
                expPalavraChaveField.getText()
        );
        experiencia.adicionarPalavras();
        experienciaData.add(experiencia);
        limparCamposExperiencia();
    }

    @FXML
    private void adicionarHabilidade() {
        // Adiciona uma nova habilidade à lista.
        Habilidades habilidade = new Habilidades(
                habilidadeField.getText(),
                habilidadeNivelCombo.getValue().toString()
        );
        habilidadesData.add(habilidade);
        limparCamposHabilidade();
    }

    @FXML
    private void adicionarIdioma() {
        // Adiciona um novo idioma à lista.
        Idioma idioma = new Idioma(
                idiomaField.getText(),
                idiomaNivelCombo.getValue().toString()
        );
        idiomasData.add(idioma);
        limparCamposIdioma();
    }

    @FXML
    private void salvarCurriculoCompleto() {
        // Salva o currículo completo do candidato no banco de dados.
        try {
            Candidato candidato = new Candidato(
                    nomeField.getText(),
                    dataNascimentoField.getText(),
                    emailField.getText(),
                    telefoneField.getText(),
                    nacionalidadeField.getText()
            );

            Endereco endereco = new Endereco(
                    cepField.getText(),
                    logradouroField.getText(),
                    bairroField.getText(),
                    localidadeField.getText(),
                    ufField.getText(),
                    numeroField.getText()
            );

            Curriculo curriculo = new Curriculo();
            curriculo.setEndereco(endereco);
            curriculo.setFormacaoAcademica(new HashSet<>(formacaoData));
            curriculo.setExperienciaProfissional(new HashSet<>(experienciaData));
            curriculo.setHabilidades(new HashSet<>(habilidadesData));
            curriculo.setIdioma(new HashSet<>(idiomasData));

            curriculo.getFormacaoAcademica().forEach(f -> f.setCurriculo(curriculo));
            curriculo.getExperienciaProfissional().forEach(e -> e.setCurriculo(curriculo));
            curriculo.getHabilidades().forEach(h -> h.setCurriculo(curriculo));
            curriculo.getIdioma().forEach(i -> i.setCurriculo(curriculo));

            candidato.setCurriculo(curriculo);
            curriculo.setCandidato(candidato);

            candidatoDAO.salvar(candidato);

            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Currículo completo salvo com sucesso!");
            limparTudo();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Salvar", "Ocorreu um erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVoltar() {
        // Retorna ao menu principal.
        MainApp.showMainMenuView();
    }

    @FXML
    private void limparTudo() {
        // Limpa todos os campos do formulário.
        nomeField.clear(); dataNascimentoField.clear(); emailField.clear(); telefoneField.clear(); nacionalidadeField.clear();
        cepField.clear(); logradouroField.clear(); numeroField.clear(); bairroField.clear(); localidadeField.clear(); ufField.clear();
        formacaoData.clear();
        experienciaData.clear();
        habilidadesData.clear();
        idiomasData.clear();
        limparCamposFormacao();
        limparCamposExperiencia();
        limparCamposHabilidade();
        limparCamposIdioma();
    }

    private void limparCamposFormacao() {
        formacaoCursoField.clear(); formacaoInstituicaoField.clear(); formacaoAreaField.clear();
        formacaoInicioField.clear(); formacaoFimField.clear(); formacaoSituacaoCombo.getSelectionModel().clearSelection();
    }

    private void limparCamposExperiencia() {
        expEmpresaField.clear(); expCargoField.clear(); expInicioField.clear();
        expFimField.clear(); expPalavraChaveField.clear();
    }

    private void limparCamposHabilidade() {
        habilidadeField.clear();
        habilidadeNivelCombo.getSelectionModel().clearSelection();
    }

    private void limparCamposIdioma() {
        idiomaField.clear();
        idiomaNivelCombo.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}