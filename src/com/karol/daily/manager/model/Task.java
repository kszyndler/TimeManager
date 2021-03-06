package com.karol.daily.manager.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.*;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


public class Task {

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}

	private final StringProperty name;

	public String getComment() {
		return comment.get();
	}

	public StringProperty commentProperty() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment.set(comment);
	}


	public int getStartHour() {
		return startHour.get();
	}

	public IntegerProperty startHourProperty() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour.set(startHour);
	}

	private final IntegerProperty startHour;

	public int getStartMinute() {
		return startMinute.get();
	}

	public String getStartHourFormatted() {
	    return String.format("%02d", getStartHour()) + ":" + String.format("%02d", getStartMinute());
    }

    public String getFinishHourFormatted() {
        return String.format("%02d", getFinishHour()) + ":" + String.format("%02d", getFinishMinute());
    }

	public IntegerProperty startMinuteProperty() {
		return startMinute;
	}

	public void setStartMinute(int startMinute) {
		this.startMinute.set(startMinute);
	}

	private final IntegerProperty startMinute;

	public int getFinishHour() {
		return finishHour.get();
	}

	public IntegerProperty finishHourProperty() {
		return finishHour;
	}

	public void setFinishHour(int finishHour) {
		this.finishHour.set(finishHour);
	}

	public int getFinishMinute() {
		return finishMinute.get();
	}

	public IntegerProperty finishMinuteProperty() {
		return finishMinute;
	}

	public void setFinishMinute(int finishMinute) {
		this.finishMinute.set(finishMinute);
	}

	private final IntegerProperty finishHour;
	private final IntegerProperty finishMinute;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getStartDate() {
		return startDate.get();
	}

	public String getStartDateTimeFormatted(){
		if (getStartDate().equals(LocalDate.of(1970,1,1)))
			return "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLL yy");
		String formattedString = getStartDate().format(formatter);
		formattedString += System.lineSeparator() + getStartHourFormatted();
		return formattedString;
	}

	public String getFinishDateTimeFormatted(){
		if (getFinishDate().equals(LocalDate.of(1970,1,1)))
			return "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLL yy");
		String formattedString = getFinishDate().format(formatter);
		formattedString += System.lineSeparator() + getFinishHourFormatted();
		return formattedString;
	}

	public ObjectProperty<LocalDate> startDateProperty() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate.set(startDate);
	}

	private final ObjectProperty<LocalDate> startDate;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getFinishDate() {
		return finishDate.get();
	}

	public ObjectProperty<LocalDate> finishDateProperty() {
		return finishDate;
	}

	public void setFinishDate(LocalDate finishDate) {
		this.finishDate.set(finishDate);
	}

	private final ObjectProperty<LocalDate> finishDate;
	private final StringProperty comment;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	private Integer status;

    public String getPriority() {
        return priority.get();
    }

    public StringProperty priorityProperty() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

    private StringProperty priority;

	public Task() {
		this(null, null, 0, 0, 0, 0,
				LocalDate.of(1970,1,1), LocalDate.of(1970,1,1), "");
	}

	public Task(String name, String comment, Integer startHour, Integer startMinute, Integer finishHour, Integer finishMinute,
				LocalDate startDate, LocalDate finishDate, String priority ) {
        this.name = new SimpleStringProperty(name);
        this.comment = new SimpleStringProperty(comment);
		this.startHour = new SimpleIntegerProperty(startHour);
		this.startMinute = new SimpleIntegerProperty(startMinute);
		this.finishHour = new SimpleIntegerProperty(finishHour);
		this.finishMinute = new SimpleIntegerProperty(finishMinute);
		this.startDate = new SimpleObjectProperty<>(startDate);
		this.finishDate = new SimpleObjectProperty<>(finishDate);
		this.priority = new SimpleStringProperty(priority);
		this.status = 0;
	}
}
