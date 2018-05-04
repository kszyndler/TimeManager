package com.karol.daily.manager;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.prefs.Preferences;

import com.karol.daily.manager.model.*;

import com.karol.daily.manager.view.NewTaskViewController;
import com.karol.daily.manager.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private GridPane newTaskView;
    private ObservableList<Task> taskData = FXCollections.observableArrayList();
    
    public MainApp() {}

    public ObservableList<Task> getTasksData() {
        return taskData;
    }

    public void addTaskToTaskData(Task newTask){
        taskData.add(newTask);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Daily Manager");
        loadTaskDataFromFile(new File("taskData.xml"));
        initRootLayout();
    }


    public void initRootLayout(String message) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout, 400,  400);
            primaryStage.setScene(scene);
            primaryStage.show();

            RootLayoutController ctrl = loader.getController();
            ctrl.setMainApp(this, message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initRootLayout() {
        initRootLayout("");
    }

    public void initNewTaskView(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/NewTaskView.fxml"));
            newTaskView = (GridPane) loader.load();

            Scene scene = new Scene(newTaskView, 600,  800);
            primaryStage.setScene(scene);
            primaryStage.show();

            NewTaskViewController ctrl = loader.getController();
            ctrl.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void loadTaskDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(TaskListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            TaskListWrapper wrapper = (TaskListWrapper) um.unmarshal(file);

            taskData.clear();
            taskData.addAll(wrapper.getTasks());

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }


    public void saveTaskDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TaskListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            TaskListWrapper wrapper = new TaskListWrapper();
            wrapper.setTasks(taskData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }
}