package com.lumesse.aspect.validation;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.validation.Errors;

import com.lumesse.exception.CustomError;
import com.lumesse.exception.ValidationException;

@Configuration
@EnableAspectJAutoProxy
@Aspect
public class ValidationAspect {

	@Autowired
	private MessageSource messageSource;

	@Pointcut("within(@org.springframework.stereotype.Controller *)")
	public void controllerMethod() {
	}

	@Around("controllerMethod() && @annotation(validate) ")
	public String aroundMethodWithValidationEnabled(ProceedingJoinPoint point,
			Validate validate) throws Throwable {

		Errors errors = getErrorsContainer(point);
		try {
			return (String) point.proceed();
		} catch (ValidationException e) {
			setErrors(errors, e.getCustomErrors());
			return validate.value();
		}
	}

	private Errors getErrorsContainer(JoinPoint point) {
		for (Object arg : point.getArgs()) {
			if (arg instanceof Errors) {
				return (Errors) arg;
			}
		}
		throw new IllegalStateException(
				"Errors parameter is required when using @"
						+ Validate.class.getSimpleName()
						+ " annotation. Error in "
						+ point.getSignature().getDeclaringTypeName() + "."
						+ point.getSignature());
	}

	private void setErrors(Errors errorsContainer,
			List<CustomError> customErrors) {
		for (CustomError err : customErrors) {
			errorsContainer.rejectValue(err.getField(), err.getErrorCode(),
					err.getMessageVariables(), null);
		}
	}
}
