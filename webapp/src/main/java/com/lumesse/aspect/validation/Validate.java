package com.lumesse.aspect.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation should be used on
 * {@link org.springframework.stereotype.Controller Controller} methods.
 * Annotated method have to return {@link java.lang.String String} and have
 * {@link org.springframework.validation.Errors Errors} on its parameter list.
 * 
 * This annotation enables {@link com.lumesse.ValidationAspect ValidationAspect}
 * which will intercept any {@link com.lumesse.exception.ValidationException
 * ValidationExceptions} and display proper error view.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Validate {

	/**
	 * @return name of error view to display in case of
	 *         {@link com.lumesse.exception.ValidationException
	 *         ValidationException}
	 */
	String value();
}
