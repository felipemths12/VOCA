package br.com.voca.gui;

import br.com.voca.dao.GenericDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CandidatoView.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("VOCA - Cadastro de Candidatos");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        // Garante que a conexão com o banco de dados seja fechada ao fechar a aplicação
        primaryStage.setOnCloseRequest(event -> GenericDAO.fecharFactory());
    }

    public static void main(String[] args) {
        launch(args);
    }
}