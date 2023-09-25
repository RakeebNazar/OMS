package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;



public class Complete implements Initializable {

    @FXML
    private Button success;

    @FXML
    void handleClose(MouseEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        success.setOnAction(e->closes());
    }

    private void closes() {

        success.getScene().getWindow().hide();

    }


}
