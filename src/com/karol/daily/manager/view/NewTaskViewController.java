package com.karol.daily.manager.view;

import com.karol.daily.manager.MainApp;
import com.karol.daily.manager.model.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

	private MainApp mainApp;
	private Task newTask;

	public NewTaskViewController() {	
	}
	
	@FXML
	private void initialize() {
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


		StringExpression startTimeExpression = Bindings.format("%d:%d", newTask.startHourProperty(), newTask.startMinuteProperty());
		startTimeLabel.textProperty().bind(startTimeExpression);


		newTask.finishHourProperty().bind(Bindings.createIntegerBinding(()->
						finishHourSlider.valueProperty().intValue()
				, finishHourSlider.valueProperty()));


		newTask.finishMinuteProperty().bind(Bindings.createIntegerBinding(()->
						finishMinuteSlider.valueProperty().intValue()
				, finishMinuteSlider.valueProperty()));

		StringExpression finishTimeExpression = Bindings.format("%d:%d", newTask.finishHourProperty(), newTask.finishMinuteProperty());
		finishTimeLabel.textProperty().bind(finishTimeExpression);

		newTask.startDateProperty().bind(startDate.valueProperty());
		newTask.finishDateProperty().bind(finishDate.valueProperty());

    }
}
