package com.btm.btmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloController {
    public static String currentview;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private void handleNewTaskButton(ActionEvent event) throws IOException {
        currentview = "new";
        FXMLLoader loader = new FXMLLoader(getClass().getResource("new_task_view.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleViewTasksButton(ActionEvent event) throws IOException {
        currentview = "inspect";
        System.out.println("Methode angesprochen");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view_tasks_view.fxml"));
        System.out.println("FXMLLoader versucht view zu laden");
        Parent root = loader.load();
        System.out.println("FXMLLoader hat erfolgreich geladen");
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


}