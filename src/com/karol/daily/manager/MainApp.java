package com.karol.daily.manager;

import java.io.IOException;
import java.time.LocalDate;

import com.karol.daily.manager.model.*;

import com.karol.daily.manager.view.NewTaskViewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    //private ObservableList<Task> tasksList = FXCollections.observableArrayList();
    
    public MainApp() {

//    	tasksList.add(new Task("Wash the dishes", "Do it fast",
//                10, 0, 20, 5, LocalDate.of(2018, 4, 10),
//                LocalDate.of(2018, 8, 15)));
//    	tasksList.add(new Task("Work", "Java project",
//                0, 30, 0, 0, LocalDate.of(2017, 7, 12),
//                LocalDate.of(2018, 5, 15)));

    }

//    public ObservableList<Task> getTasksList() {
//        return tasksList;
//    }

    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();	
        showNewTaskView();
        //showPersonOverview();
    }

    /**
     * Initializes the root layout.
     */

    
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout, 500,  800);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
//    public void showPersonOverview() {
//        try {
//            // Load person overview.
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
//            AnchorPane personOverview = (AnchorPane) loader.load();
//
//            // Set person overview into the center of root layout.
//            rootLayout.setCenter(personOverview);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    
    public void showNewTaskView() {
    	try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/NewTaskView.fxml"));
            GridPane newTaskView = (GridPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(newTaskView);

            NewTaskViewController ctrl = loader.getController();
            ctrl.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
//    public Stage getPrimaryStage() {
//        return primaryStage;
//    }

    public static void main(String[] args) {
        launch(args);
    }
}