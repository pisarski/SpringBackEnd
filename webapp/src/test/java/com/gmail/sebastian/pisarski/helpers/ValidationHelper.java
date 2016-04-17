package com.gmail.sebastian.pisarski.helpers;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public final class ValidationHelper {

	private ValidationHelper() {
	}

	public static Validator alwaysFailValidator(final String field,
			final String msg) {
		return new Validator() {

			@Override
			public void validate(Object target, Errors errors) {
				errors.rejectValue(field, msg);
			}

			@Override
			public boolean supports(Class<?> clazz) {
				return true;
			}
		};
	}
}
