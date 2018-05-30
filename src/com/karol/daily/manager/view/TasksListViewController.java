package com.karol.daily.manager.view;

import com.karol.daily.manager.MainApp;
import com.karol.daily.manager.model.Task;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Paint;

import java.time.LocalDate;


public class TasksListViewController {

    @FXML
    private TableView<Task> tasksListStarted;

    @FXML
    private TableColumn<Task, String> taskStartedPriority;

    @FXML
    private TableColumn<Task, String> taskStartedName;

    @FXML
    private  TableColumn<Task, String> taskStartedTime;

    @FXML
    private TableView<Task> tasksListFuture;

    @FXML
    private TableColumn<Task, String> taskFuturePriority;

    @FXML
    private TableColumn<Task, String> taskFutureName;

    @FXML
    private  TableColumn<Task, String> taskFutureTime;

    @FXML
    private Label startedDetailsName;

    @FXML
    private Label futuredDetailsName;

    @FXML
    private Label startedDetailsStart;
    @FXML
    private Label futureDetailsStart;

    @FXML
    private Label startedDetailsFinsish;
    @FXML
    private Label futureDetailsFinsish;

    @FXML
    private Label startedDetailsPriority;
    @FXML
    private Label futureDetailsPriority;

    @FXML
    private Label startedDetailsComment;
    @FXML
    private Label futureDetailsComment;

    @FXML
    private Button backButton;

    private MainApp mainApp;

    @FXML
    private void initialize(){
        taskStartedName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        taskStartedPriority.setCellValueFactory(cellData -> cellData.getValue().priorityProperty());
        taskStartedTime.setCellValueFactory(cellData -> Bindings.createStringBinding(()->
            cellData.getValue().getFinishDate().equals(LocalDate.now()) ?
                    cellData.getValue().getFinishHourFormatted() :
                    cellData.getValue().getFinishDate().toString()
        , cellData.getValue().finishHourProperty(), cellData.getValue().finishMinuteProperty(), cellData.getValue().finishDateProperty()) );

        taskFutureName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        taskFuturePriority.setCellValueFactory(cellData -> cellData.getValue().priorityProperty());
        taskFutureTime.setCellValueFactory(cellData -> Bindings.createStringBinding(()->
           cellData.getValue().getStartDate().equals(LocalDate.now()) ?
                   cellData.getValue().getStartHourFormatted() :
                   cellData.getValue().getStartDate().toString()
                , cellData.getValue().startHourProperty(), cellData.getValue().startMinuteProperty(), cellData.getValue().startDateProperty()) );

        startedDetailsName.setWrapText(true);
        startedDetailsPriority.setWrapText(true);
        startedDetailsComment.setWrapText(true);
        startedDetailsFinsish.setWrapText(true);
        startedDetailsStart.setWrapText(true);
        futuredDetailsName.setWrapText(true);
        futureDetailsPriority.setWrapText(true);
        futureDetailsComment.setWrapText(true);
        futureDetailsFinsish.setWrapText(true);
        futureDetailsStart.setWrapText(true);

    }

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
        tasksListStarted.setItems(mainApp.getTasksData().filtered((c)->
            !c.getStartDate().isAfter(LocalDate.now()) && !c.getFinishDate().isBefore(LocalDate.now())
        ));
        tasksListFuture.setItems(mainApp.getTasksData().filtered((c)->
                c.getStartDate().isAfter(LocalDate.now())
        ));
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainApp.initRootLayout();
            }
        });


        tasksListStarted.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue observableValue, Task oldValue, Task newValue) {
                if(tasksListStarted.getSelectionModel().getSelectedItem() != null)
                {
                    bindStartedDetails(tasksListStarted.getSelectionModel().getSelectedItem());
                } else {
                    bindStartedDetails(new Task());
                }
            }
        });


        tasksListFuture.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue observableValue, Task oldValue, Task newValue) {
                if(tasksListFuture.getSelectionModel().getSelectedItem() != null)
                {
                    bindFuturedDetails(tasksListFuture.getSelectionModel().getSelectedItem());
                } else {
                    bindFuturedDetails(new Task());
                }
            }
        });
    }


    protected void bindStartedDetails(Task selectedTask){
        startedDetailsName.textProperty().unbind();
        startedDetailsName.textProperty().bind(selectedTask.nameProperty());
        startedDetailsStart.textProperty().unbind();
        startedDetailsStart.textProperty().bind(Bindings.createStringBinding(()->selectedTask.getStartDateTimeFormatted(),
                selectedTask.startDateProperty(), selectedTask.startHourProperty(),selectedTask.startMinuteProperty()));
        startedDetailsFinsish.textProperty().unbind();
        startedDetailsFinsish.textProperty().bind(Bindings.createStringBinding(()->selectedTask.getFinishDateTimeFormatted(),
                selectedTask.finishDateProperty(), selectedTask.finishHourProperty(),selectedTask.finishMinuteProperty()));
        startedDetailsPriority.textProperty().unbind();
        startedDetailsPriority.textProperty().bind(selectedTask.priorityProperty());
        startedDetailsComment.textProperty().unbind();
        startedDetailsComment.textProperty().bind(selectedTask.commentProperty());
        startedDetailsPriority.textFillProperty().unbind();
        startedDetailsPriority.textFillProperty().bind(Bindings.createObjectBinding(()->(selectedTask.getPriority().equals("Low") ? Paint.valueOf("#008000") :
                (selectedTask.getPriority().equals("Medium") ? Paint.valueOf("#ffd700") : Paint.valueOf("#e50000")))));
    }

    protected void bindFuturedDetails(Task selectedTask){
        futuredDetailsName.textProperty().unbind();
        futuredDetailsName.textProperty().bind(selectedTask.nameProperty());
        futureDetailsStart.textProperty().unbind();
        futureDetailsStart.textProperty().bind(Bindings.createStringBinding(()->selectedTask.getStartDateTimeFormatted(),
                selectedTask.startDateProperty(), selectedTask.startHourProperty(),selectedTask.startMinuteProperty()));
        futureDetailsFinsish.textProperty().unbind();
        futureDetailsFinsish.textProperty().bind(Bindings.createStringBinding(()->selectedTask.getFinishDateTimeFormatted(),
                selectedTask.finishDateProperty(), selectedTask.finishHourProperty(),selectedTask.finishMinuteProperty()));
        futureDetailsPriority.textProperty().unbind();
        futureDetailsPriority.textProperty().bind(selectedTask.priorityProperty());
        futureDetailsComment.textProperty().unbind();
        futureDetailsComment.textProperty().bind(selectedTask.commentProperty());
        futureDetailsPriority.textFillProperty().unbind();
        futureDetailsPriority.textFillProperty().bind(Bindings.createObjectBinding(()->(selectedTask.getPriority().equals("Low") ? Paint.valueOf("#008000") :
                (selectedTask.getPriority().equals("Medium") ? Paint.valueOf("#ffd700") : Paint.valueOf("#e50000")))));

    }

}
