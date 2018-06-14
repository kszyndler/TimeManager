package com.karol.daily.manager.view;

import com.karol.daily.manager.MainApp;
import com.karol.daily.manager.model.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class NewTaskViewController {

	@FXML
	private TextField name;
	
	@FXML
	private TextArea comment;

	@FXML
	private Slider startHourSlider;

	@FXML
	private Slider startMinuteSlider;

	@FXML
	private Label startTimeLabel;

	@FXML
	private Slider finishHourSlider;

	@FXML
	private Slider finishMinuteSlider;

	@FXML
	private Label finishTimeLabel;

	@FXML
	private DatePicker startDate;

	@FXML
	private DatePicker finishDate;

	@FXML
	private Button addTaskButton;

	@FXML
	private Label errorMessage;

	@FXML
	private ComboBox<String> priority;

	@FXML
	private Button cancelButton;

	private MainApp mainApp;
	private Task newTask;

	public NewTaskViewController() {	
	}
	
	@FXML
	private void initialize() {
		errorMessage.setWrapText(true);
		ObservableList<String> options =
				FXCollections.observableArrayList(
						"Low",
						"Medium",
						"High"
				);
		priority.setItems(options);
		priority.getSelectionModel().selectFirst();

	}

	private StringBuffer validate(){
		StringBuffer message = new StringBuffer();

		if (name.getText().equals(""))
		{
			message.append("Name is empty" + System.getProperty("line.separator"));
		}
		if (startDate.getValue() == null)
		{
			message.append("Start date was not chosen" + System.getProperty("line.separator"));
		}
		if (finishDate.getValue() == null)
		{
			message.append("Finish date was not chosen" + System.getProperty("line.separator"));
		}
		else if(startDate.getValue() != null && (startDate.getValue().isAfter(finishDate.getValue()) ||
				startDate.getValue().isEqual(finishDate.getValue()) && (newTask.getStartHour() > newTask.getFinishHour() ||
						newTask.getStartHour() == newTask.getFinishHour() && newTask.getStartMinute() > newTask.getFinishMinute()) ))
		{
			message.append("Finish date must not happen before start date");
		}

		return message;
	}
	
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
		newTask = new Task();

		newTask.nameProperty().bind(name.textProperty());
		newTask.commentProperty().bind(comment.textProperty());

		newTask.startHourProperty().bind(Bindings.createIntegerBinding(()->
				 startHourSlider.valueProperty().intValue()
		, startHourSlider.valueProperty()));

		newTask.startMinuteProperty().bind(Bindings.createIntegerBinding(()->
						startMinuteSlider.valueProperty().intValue()
				, startMinuteSlider.valueProperty()));


		StringExpression startTimeExpression = Bindings.format("%02d:%02d", newTask.startHourProperty(), newTask.startMinuteProperty());
		startTimeLabel.textProperty().bind(startTimeExpression);


		newTask.finishHourProperty().bind(Bindings.createIntegerBinding(()->
						finishHourSlider.valueProperty().intValue()
				, finishHourSlider.valueProperty()));


		newTask.finishMinuteProperty().bind(Bindings.createIntegerBinding(()->
						finishMinuteSlider.valueProperty().intValue()
				, finishMinuteSlider.valueProperty()));

		StringExpression finishTimeExpression = Bindings.format("%02d:%02d", newTask.finishHourProperty(), newTask.finishMinuteProperty());
		finishTimeLabel.textProperty().bind(finishTimeExpression);

		newTask.startDateProperty().bind(startDate.valueProperty());
		newTask.finishDateProperty().bind(finishDate.valueProperty());
		newTask.priorityProperty().bind(priority.valueProperty());

		addTaskButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				errorMessage.setText(validate().toString());

				if (validate().toString().equals("")){
					newTask.setStatus(1);
					mainApp.addTaskToTaskData(newTask);
					mainApp.saveTaskDataToFile(new File("taskData.xml"));
					mainApp.initRootLayout("New task successfully added");
				}

			}
		});

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainApp.initRootLayout();
			}
		});

    }
}
