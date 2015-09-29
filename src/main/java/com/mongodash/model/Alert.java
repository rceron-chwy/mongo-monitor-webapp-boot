package com.mongodash.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Alert extends BaseModel {

	public static enum fields {
		operation, field, value, recipientList;
	}
	
	public static int BELOW = 1;
	public static int ABOVE = 2;

	private boolean active;

	@NotEmpty
	@NotNull
	private String operation;

	@NotEmpty
	@NotNull
	private String field;

	@NotNull
	private Integer value;

	private String recipientList;


	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getRecipientList() {
		return recipientList;
	}

	public void setRecipientList(String recipientList) {
		this.recipientList = recipientList;
	}

	public String getText() {

		AlertField alertField = AlertField.valueOf(field);
		OperationType operationType = OperationType.valueOf(operation);

		return alertField.getText() + " " + operationType.getText() + " " + value;
	}

}
