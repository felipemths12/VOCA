package br.com.voca.gui;

import br.com.voca.dao.CandidatoDAO;
import br.com.voca.modelos.*;
import br.com.voca.service.EnderecoRequisicao;
import br.com.voca.service.ExportacaoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class CandidatoController {

    // --- DAOs ---
    private final CandidatoDAO candidatoDAO = new CandidatoDAO();
    private final EnderecoRequisicao enderecoRequisicao = new EnderecoRequisicao();

    // --- Campos Candidato e Endereço ---
    @FXML private TextField nomeField, dataNascimentoField, emailField, telefoneField, nacionalidadeField;
    @FXML private TextField cepField, logradouroField, numeroField, bairroField, localidadeField, ufField;

    // --- Componentes Formação Acadêmica ---
    @FXML private TableView<FormacaoAcademica> formacaoTable;
    @FXML private TableColumn<FormacaoAcademica, String> cursoColumn, instituicaoColumn, situacaoColumn;
    @FXML private TextField formacaoCursoField, formacaoInstituicaoField, formacaoAreaField, formacaoInicioField, formacaoFimField;
    @FXML private ComboBox<FormacaoAcademica.SituacaoCurso> formacaoSituacaoCombo;
    private final ObservableList<FormacaoAcademica> formacaoData = FXCollections.observableArrayList();

    // --- Componentes Experiência Profissional ---
    @FXML private TableView<ExperienciaProfissional> experienciaTable;
    @FXML private TableColumn<ExperienciaProfissional, String> empresaColumn, cargoColumn;
    @FXML private TextField expEmpresaField, expCargoField, expInicioField, expFimField, expPalavraChaveField;
    private final ObservableList<ExperienciaProfissional> experienciaData = FXCollections.observableArrayList();

    // --- Componentes Habilidades ---
    @FXML private TableView<Habilidades> habilidadesTable;
    @FXML private TableColumn<Habilidades, String> habilidadeColumn, habilidadeNivelColumn;
    @FXML private TextField habilidadeField;
    @FXML private ComboBox<Habilidades.Nivel> habilidadeNivelCombo;
    private final ObservableList<Habilidades> habilidadesData = FXCollections.observableArrayList();

    // --- Componentes Idiomas ---
    @FXML private TableView<Idioma> idiomasTable;
    @FXML private TableColumn<Idioma, String> idiomaColumn, idiomaNivelColumn;
    @FXML private TextField idiomaField;
    @FXML private ComboBox<Idioma.Nivel> idiomaNivelCombo;
    private final ObservableList<Idioma> idiomasData = FXCollections.observableArrayList();

    // --- Componentes de Filtro e Resultados ---
    @FXML private TextField filtroAreaField;
    @FXML private TextField filtroAnosExpField;
    @FXML private Button exportarPdfButton;

    @FXML private TableView<Candidato> resultadosTable;
    @FXML private TableColumn<Candidato, String> resultadoNomeColumn;
    @FXML private TableColumn<Candidato, String> resultadoEmailColumn;
    @FXML private TableColumn<Candidato, String> resultadoTelefoneColumn;

    // Lista para armazenar os resultados da busca
    private ObservableList<Candidato> resultadosData = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        // Configura as tabelas e ComboBoxes
        configurarTabelas();
        configurarComboBoxes();
    }

    private void configurarTabelas() {
        // Formação
        cursoColumn.setCellValueFactory(new PropertyValueFactory<>("curso"));
        instituicaoColumn.setCellValueFactory(new PropertyValueFactory<>("instituicao"));
        situacaoColumn.setCellValueFactory(new PropertyValueFactory<>("situacaoCurso"));
        formacaoTable.setItems(formacaoData);

        // Experiência
        empresaColumn.setCellValueFactory(new PropertyValueFactory<>("nomeEmpresa"));
        cargoColumn.setCellValueFactory(new PropertyValueFactory<>("cargoOcupado"));
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

    // --- Métodos de Ação ---

    @FXML
    private void buscarCep() {
        try {
            Endereco endereco = enderecoRequisicao.buscar(cepField.getText()); //
            logradouroField.setText(endereco.getLogradouro()); //
            bairroField.setText(endereco.getBairro()); //
            localidadeField.setText(endereco.getLocalidade()); //
            ufField.setText(endereco.getUf()); //
        } catch (IOException | InterruptedException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de API", "Não foi possível buscar o CEP.");
        }
    }

    @FXML
    private void adicionarFormacao() {
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
        ExperienciaProfissional experiencia = new ExperienciaProfissional(
                expEmpresaField.getText(),
                expCargoField.getText(),
                expInicioField.getText(),
                expFimField.getText(),
                expPalavraChaveField.getText()
        );
        experiencia.adicionarPalavras(); //
        experienciaData.add(experiencia);
        limparCamposExperiencia();
    }

    @FXML
    private void adicionarHabilidade() {
        Habilidades habilidade = new Habilidades(
                habilidadeField.getText(),
                habilidadeNivelCombo.getValue().toString()
        );
        habilidadesData.add(habilidade);
        limparCamposHabilidade();
    }

    @FXML
    private void adicionarIdioma() {
        Idioma idioma = new Idioma(
                idiomaField.getText(),
                idiomaNivelCombo.getValue().toString()
        );
        idiomasData.add(idioma);
        limparCamposIdioma();
    }

    @FXML
    private void salvarCurriculoCompleto() {
        try {
            // 1. Cria o Candidato
            Candidato candidato = new Candidato(
                    nomeField.getText(),
                    dataNascimentoField.getText(),
                    emailField.getText(),
                    telefoneField.getText(),
                    nacionalidadeField.getText()
            );

            // 2. Cria o Endereço
            Endereco endereco = new Endereco(
                    cepField.getText(),
                    logradouroField.getText(),
                    bairroField.getText(),
                    localidadeField.getText(),
                    ufField.getText(),
                    numeroField.getText()
            );

            // 3. Cria o Currículo e associa as informações
            Curriculo curriculo = new Curriculo();
            curriculo.setEndereco(endereco);
            curriculo.setFormacaoAcademica(new HashSet<>(formacaoData));
            curriculo.setExperienciaProfissional(new HashSet<>(experienciaData));
            curriculo.setHabilidades(new HashSet<>(habilidadesData));
            curriculo.setIdioma(new HashSet<>(idiomasData));

            // 4. Define as relações bidirecionais
            curriculo.getFormacaoAcademica().forEach(f -> f.setCurriculo(curriculo));
            curriculo.getExperienciaProfissional().forEach(e -> e.setCurriculo(curriculo));
            curriculo.getHabilidades().forEach(h -> h.setCurriculo(curriculo));
            curriculo.getIdioma().forEach(i -> i.setCurriculo(curriculo));

            // 5. Associa o candidato ao currículo e vice-versa
            candidato.setCurriculo(curriculo);
            curriculo.setCandidato(candidato);

            // 6. Salva o candidato (que salvará tudo em cascata)
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
        MainApp.showMainMenuView();
    }

    @FXML
    private void limparTudo() {
        // Limpa campos principais
        nomeField.clear(); dataNascimentoField.clear(); emailField.clear(); telefoneField.clear(); nacionalidadeField.clear();
        cepField.clear(); logradouroField.clear(); numeroField.clear(); bairroField.clear(); localidadeField.clear(); ufField.clear();
        // Limpa tabelas e listas de dados
        formacaoData.clear();
        experienciaData.clear();
        habilidadesData.clear();
        idiomasData.clear();
        // Limpa campos de formulários das abas
        limparCamposFormacao();
        limparCamposExperiencia();
        limparCamposHabilidade();
        limparCamposIdioma();
    }

    // --- Métodos de Limpeza de Campos ---
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