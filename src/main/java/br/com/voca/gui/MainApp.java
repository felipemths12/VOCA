package br.com.voca.gui;

import br.com.voca.dao.GenericDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private static Stage primaryStage;
    // Define um tamanho padrão para a janela
    private static final double DEFAULT_WIDTH = 1200;
    private static final double DEFAULT_HEIGHT = 800;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("VOCA - Banco de Currículos");
        primaryStage.setOnCloseRequest(event -> GenericDAO.fecharFactory());
        showMainMenuView();
    }

    public static void showMainMenuView() {
        loadScene("/fxml/MainMenuView.fxml", null);
    }

    public static void showCadastroView() {
        loadScene("/fxml/CandidatoView.fxml", null);
    }

    public static void showBuscaView() {
        loadScene("/fxml/BuscaView.fxml", null);
    }

    public static void showEdicaoView(Long candidatoId) {
        loadScene("/fxml/EdicaoView.fxml", controller -> {
            if (controller instanceof EdicaoController) {
                ((EdicaoController) controller).carregarCandidato(candidatoId);
            }
        });
    }

    private static void loadScene(String fxmlPath, ControllerInitializer initializer) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlPath));
            Parent root = loader.load();
            if (initializer != null) {
                initializer.initialize(loader.getController());
            }
            // Cria a cena com o tamanho padrão definido
            primaryStage.setScene(new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    interface ControllerInitializer {
        void initialize(Object controller);
    }

    public static void main(String[] args) {
        launch(args);
    }
}