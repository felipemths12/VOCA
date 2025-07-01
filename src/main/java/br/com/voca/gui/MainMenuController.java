package br.com.voca.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class MainMenuController {

    @FXML
    private void handleCadastro() {
        // Abre a tela de cadastro.
        MainApp.showCadastroView();
    }

    @FXML
    private void handleBusca() {
        // Abre a tela de busca.
        MainApp.showBuscaView();
    }

    @FXML
    private void handleSair() {
        // Encerra a aplicação.
        Platform.exit();
    }
}