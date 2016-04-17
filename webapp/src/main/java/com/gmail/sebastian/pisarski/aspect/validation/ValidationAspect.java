package com.gmail.sebastian.pisarski.aspect.validation;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
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

import com.gmail.sebastian.pisarski.exception.CustomError;
import com.gmail.sebastian.pisarski.exception.ValidationException;

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

		try {
			return (String) point.proceed();
		} catch (ValidationException e) {
			Errors errors = getParameter(point, Errors.class);
			setErrors(errors, e.getCustomErrors());
			invokeInitModelIfNeccessary(validate, point);
			return validate.value();
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T getParameter(JoinPoint point, Class<T> clazz) {
		for (Object arg : point.getArgs()) {
			if (clazz.isInstance(arg)) {
				return (T) arg;
			}
		}
		throw new IllegalStateException("Parameter of type " + clazz.getName()
				+ " is required when using @" + Validate.class.getSimpleName()
				+ " annotation. Error in "
				+ point.getSignature().getDeclaringTypeName() + "."
				+ point.getSignature().getName());
	}

	private void setErrors(Errors errorsContainer,
			List<CustomError> customErrors) {
		for (CustomError err : customErrors) {
			errorsContainer.rejectValue(err.getField(), err.getErrorCode(),
					err.getMessageVariables(), null);
		}
	}

	private void invokeInitModelIfNeccessary(Validate validate,
			JoinPoint joinPoint) throws Exception {
		if (StringUtils.isEmpty(validate.initMethod())) {
			return;
		}

		Object target = joinPoint.getTarget();
		Method method = getInitMethod(validate, target);
		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object[] parameters = Stream.of(method.getParameterTypes())
				.map(paramClass -> getArgument(joinPoint, paramClass))
				.toArray();
		method.invoke(target, parameters);
		method.setAccessible(accessible);
	}

	private Method getInitMethod(Validate validate, Object target) {
		for (Method method : target.getClass().getDeclaredMethods()) {
			if (method.getName().equals(validate.initMethod())) {
				return method;
			}
		}
		throw new IllegalStateException("Method " + validate.initMethod()
				+ " not found");
	}

	private Object getArgument(JoinPoint point, Class<?> clazz) {

		List<Object> paramsFilteredList = Stream.of(point.getArgs())
				.filter(arg -> clazz.isInstance(arg))
				.collect(Collectors.toList());
		if (paramsFilteredList.size() != 1) {
			throw new IllegalStateException(
					"There should be one parameter of type " + clazz.getName()
							+ ". Error in "
							+ point.getSignature().getDeclaringTypeName() + "."
							+ point.getSignature().getName());
		}
		return paramsFilteredList.get(0);
	}
}
