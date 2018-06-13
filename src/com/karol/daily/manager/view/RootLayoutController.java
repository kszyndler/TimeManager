package com.karol.daily.manager.view;

import com.karol.daily.manager.MainApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class RootLayoutController {

    @FXML
    private Button newTaskMenuButton;

    @FXML
    private Button tasksListMenuButton;

    @FXML
    private Label information;

    private MainApp mainApp;

    public RootLayoutController() {

    }

    private void initialize() {
    }

    public void setMainApp(MainApp mainApp, String message) {
        this.mainApp = mainApp;
        if (!message.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Task successfully added");
            alert.setHeaderText("Task was added successfully. ");
            alert.showAndWait();
        }
        newTaskMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainApp.initNewTaskView();
            }
        });

        tasksListMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainApp.initTasksListView();
            }
        });

    }

}
