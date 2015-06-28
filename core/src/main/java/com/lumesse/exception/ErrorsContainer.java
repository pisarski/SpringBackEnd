package com.lumesse.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for validation errors.
 */
public class ErrorsContainer {

	private List<CustomError> customErrors = new ArrayList<>();

	public void add(String fieldName, String errorCode) {
		add(fieldName, errorCode, new Object[0]);
	}

	public void add(String fieldName, String errorCode, Object... params) {
		CustomError customError = new CustomError(fieldName, errorCode);
		customError.setMessageVariables(params);

		customErrors.add(customError);
	}

	public List<CustomError> getCustomErrors() {
		return customErrors;
	}

}
