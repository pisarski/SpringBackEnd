package com.lumesse.service.impl;

import java.util.Arrays;
import java.util.List;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AnnotationsConfigurer;
import net.sf.oval.configuration.annotation.JPAAnnotationsConfigurer;
import net.sf.oval.context.FieldContext;
import net.sf.oval.context.OValContext;

import com.lumesse.exception.CustomError;
import com.lumesse.exception.ErrorsContainer;
import com.lumesse.exception.ValidationException;

/**
 * Base service class with common service methods.
 */
public abstract class BaseService {

	protected void validate(Object entity) {
		validate(entity, new ErrorsContainer());
	}

	protected void validate(Object entity, ErrorsContainer errorsContainer) {
		List<CustomError> errors = errorsContainer.getCustomErrors();

		Validator validator = new Validator(new AnnotationsConfigurer(),
				new JPAAnnotationsConfigurer());
		List<ConstraintViolation> violations = validator.validate(entity);

		if (violations.size() > 0) {
			for (ConstraintViolation violation : violations) {
				errors.addAll(toCustomErrors(violation));
			}
		}

		if (errors.size() > 0) {
			throw new ValidationException(errors);
		}
	}

	private List<CustomError> toCustomErrors(ConstraintViolation violation) {
		OValContext context = violation.getContext();
		if (context instanceof FieldContext) {
			FieldContext fieldContext = (FieldContext) context;
			String fieldName = fieldContext.getField().getName();
			String errorCode = getErrorCode(violation);
			return Arrays.asList(new CustomError(fieldName, errorCode,
					violation.getMessageVariables(), violation
							.getInvalidValue()));
		} else {
			throw new IllegalArgumentException(
					"Validation supports only field annotations");
		}
	}

	private String getErrorCode(ConstraintViolation violation) {
		String errorCode = violation.getErrorCode();
		return errorCode.contains(".oval.") ? violation.getErrorCode()
				.replaceAll(".*\\.", "") : errorCode;
	}
}
