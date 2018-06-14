package com.karol.daily.manager.view;

import com.karol.daily.manager.MainApp;
import com.karol.daily.manager.model.Task;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Optional;


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
    private  TableColumn<Task, Image> clock;

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
    private Button deleteBtn;
    @FXML
    private Button delete2Btn;

    @FXML
    private Button backButton;

    private MainApp mainApp;

    @FXML
    private void initialize(){
        taskStartedName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        taskStartedPriority.setCellValueFactory(cellData -> cellData.getValue().priorityProperty());
        clock.setCellFactory((p)->{
            ImageView imageview = new ImageView();
            imageview.setFitHeight(15);
            imageview.setFitWidth(15);

            //Set up the Table
            TableCell<Task, Image> cell = new TableCell<Task, Image>() {
                public void updateItem(Image item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty && item != null) {
//                        System.out.println("tak");
                        imageview.setImage(item);
//                    } else {
//                        System.out.println("blad");
                    } else {
                        imageview.setImage(null);
                    }
                }
            };
            // Attach the imageview to the cell
            cell.setGraphic(imageview);
            return cell;
        });
        clock.setCellValueFactory(cellData -> Bindings.createObjectBinding(()-> {
                    Calendar rightNow = Calendar.getInstance();
                    int current_hour = rightNow.get(Calendar.HOUR_OF_DAY);
                    int current_minute = rightNow.get(Calendar.MINUTE);

                    if(cellData.getValue().getStartDate().equals(LocalDate.now())){
                        if(cellData.getValue().getStartHour() > current_hour){
                            return null;
                        }
                        else if(cellData.getValue().getStartHour() == current_hour && cellData.getValue().getStartMinute() > current_minute){
                            return null;
                        }
                    }
                    if(cellData.getValue().getFinishDate().equals(LocalDate.now())){
                        if(cellData.getValue().getFinishHour() < current_hour){
                            return null;
                        }
                        else if(cellData.getValue().getFinishHour() == current_hour && cellData.getValue().getFinishMinute() < current_minute){
                            return null;
                        }
                    }
                    return new Image("time.png");
                }
        ));
        taskStartedTime.setCellValueFactory(cellData -> Bindings.createStringBinding(()->
            cellData.getValue().getFinishDate().equals(LocalDate.now()) ?
                    cellData.getValue().getFinishHourFormatted() :
                    cellData.getValue().getFinishDate().toString()
        , cellData.getValue().finishHourProperty(), cellData.getValue().finishMinuteProperty(), cellData.getValue().finishDateProperty()) );

        taskFutureName.setCellValueFactory((cellData) -> cellData.getValue().nameProperty());
        taskFuturePriority.setCellValueFactory((cellData) -> cellData.getValue().priorityProperty());
        taskFutureTime.setCellValueFactory(cellData -> Bindings.createStringBinding(()->
           cellData.getValue().getStartDate().equals(LocalDate.now()) ?
                   cellData.getValue().getStartHourFormatted() :
                   cellData.getValue().getStartDate().toString()
                , cellData.getValue().startHourProperty(), cellData.getValue().startMinuteProperty(), cellData.getValue().startDateProperty()) );
        deleteBtn.disableProperty().bind(Bindings.createBooleanBinding(()->
                tasksListStarted.getSelectionModel().selectedItemProperty().getValue() == null
        , tasksListStarted.getSelectionModel().selectedItemProperty()));
        delete2Btn.disableProperty().bind(Bindings.createBooleanBinding(()->
                tasksListFuture.getSelectionModel().selectedItemProperty().getValue() == null
        , tasksListFuture.getSelectionModel().selectedItemProperty()));

        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Task toDelete = tasksListStarted.getSelectionModel().selectedItemProperty().getValue();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete task");
                StringBuffer message = new StringBuffer("Are you sure you want to delete task ");
                message.append(toDelete.getName()).append("?");
                alert.setHeaderText(message.toString());

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    mainApp.removeTask(toDelete);
                    mainApp.saveTaskDataToFile(new File("taskData.xml"));
                }
            }
        });

        delete2Btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete task");
                alert.setHeaderText("Are you sure you want to delete this task?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    Task toDelete = tasksListFuture.getSelectionModel().selectedItemProperty().getValue();
                    mainApp.removeTask(toDelete);
                    mainApp.saveTaskDataToFile(new File("taskData.xml"));
                }
            }
        });

        startedDetailsName.setWrapText(true);
        startedDetailsPriority.setWrapText(true);
        startedDetailsComment.setWrapText(true);
        startedDetailsFinsish.setWrapText(true);
        startedDetailsStart.setWrapText(true);
        futuredDetailsName.setWrapText(true);
        futureDetailsPriority.setWrapText(true);
        futureDetailsComment.setWrapText(true);
        futureDetailsFinsish.setWrapText(true);
        futureDetailsFinsish.setTextAlignment(TextAlignment.JUSTIFY);
        futureDetailsStart.setWrapText(true);

    }

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;

        FilteredList<Task> filteredList = mainApp.getTasksData().filtered((c)->
                !c.getStartDate().isAfter(LocalDate.now()) && !c.getFinishDate().isBefore(LocalDate.now())
        );
        SortedList<Task> sortedList = new SortedList<>(filteredList);
        tasksListStarted.setItems(sortedList);
        sortedList.comparatorProperty().bind(tasksListStarted.comparatorProperty());

        FilteredList<Task> filteredList2 = mainApp.getTasksData().filtered((c)->
                c.getStartDate().isAfter(LocalDate.now()));
        SortedList<Task> sortedList2 = new SortedList<>(filteredList2);
        tasksListFuture.setItems(sortedList2);
        sortedList2.comparatorProperty().bind(tasksListFuture.comparatorProperty());

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainApp.initRootLayout();
            }
        });

        tasksListStarted.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue observableValue, Task oldValue, Task newValue) {
                if(newValue != null)
                {
                    bindStartedDetails(newValue);
                } else {
                    bindStartedDetails(new Task()); //dla rozpoczecia programu
                }
            }
        });


        tasksListFuture.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue observableValue, Task oldValue, Task newValue) {
                if(newValue != null)
                {
                    bindFuturedDetails(newValue);
                } else {
                    bindFuturedDetails(new Task());
                }
            }
        });
    }


    protected void bindStartedDetails(Task selectedTask){
        //System.out.println(selectedTask);
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
