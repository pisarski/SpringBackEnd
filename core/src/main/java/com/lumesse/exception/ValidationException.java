package com.lumesse.exception;

import java.util.List;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 6706694584769761654L;

	private final List<CustomError> customErrors;

	public ValidationException(List<CustomError> customErrors) {
		this.customErrors = customErrors;
	}

	public List<CustomError> getCustomErrors() {
		return customErrors;
	}

}
