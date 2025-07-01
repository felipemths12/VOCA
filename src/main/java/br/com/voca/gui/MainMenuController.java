package br.com.voca.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class MainMenuController {

    @FXML
    private void handleCadastro() {
        MainApp.showCadastroView();
    }

    @FXML
    private void handleBusca() {
        MainApp.showBuscaView();
    }

    @FXML
    private void handleSair() {
        Platform.exit();
    }
}