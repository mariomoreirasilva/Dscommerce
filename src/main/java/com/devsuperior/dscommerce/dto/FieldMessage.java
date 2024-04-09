package com.devsuperior.dscommerce.dto;

public class FieldMessage {
	private String fieldName;
	private String message;
	public FieldMessage(String fieldName, String message) {		
		this.fieldName = fieldName;
		this.message = message;
	}
	public String getFiledName() {
		return fieldName;
	}

	public String getMessge() {
		return message;
	}
		
}
