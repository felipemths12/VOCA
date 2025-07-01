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

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("VOCA - Banco de Currículos");

        // Garante que a conexão com o banco seja fechada ao fechar a aplicação
        primaryStage.setOnCloseRequest(event -> GenericDAO.fecharFactory());

        showMainMenuView();
    }

    public static void showMainMenuView() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/MainMenuView.fxml"));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showCadastroView() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/CandidatoView.fxml"));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showBuscaView() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/BuscaView.fxml"));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}