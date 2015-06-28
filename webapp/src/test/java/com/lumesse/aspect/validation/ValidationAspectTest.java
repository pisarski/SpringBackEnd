package com.lumesse.aspect.validation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.Errors;

import com.lumesse.exception.CustomError;
import com.lumesse.exception.ValidationException;

@RunWith(MockitoJUnitRunner.class)
public class ValidationAspectTest {

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private ProceedingJoinPoint point;

	@Mock
	private Validate validate;

	@Mock
	private Errors errors;

	private ValidationAspect validationAspect;

	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Before
	public void setUp() {
		validationAspect = new ValidationAspect();
		when(point.getArgs()).thenReturn(new Object[] { errors });
	}

	@Test
	public void shouldDoNothingWhenNoValidationErrors() throws Throwable {
		// given
		String viewName = "view";
		when(point.proceed()).thenReturn(viewName);

		// when
		String result = validationAspect.aroundMethodWithValidationEnabled(
				point, validate);

		// then
		assertEquals(viewName, result);
		verify(point).proceed();
	}

	@Test
	public void shouldThrowExceptionIfErrorsNotPresentInParamsList()
			throws Throwable {
		// given
		when(point.getArgs()).thenReturn(new Object[] { 1, "test" });
		when(point.proceed()).thenThrow(
				new ValidationException(new ArrayList<>()));

		// then
		expected.expect(IllegalStateException.class);

		// when
		validationAspect.aroundMethodWithValidationEnabled(point, validate);
	}

	@Test
	public void shouldSetErrors() throws Throwable {
		// given
		List<CustomError> customErrors = new ArrayList<>();
		CustomError error = new CustomError("field", "errorCode");
		error.setMessageVariables(new Object[0]);
		customErrors.add(error);

		ValidationException validationException = new ValidationException(
				customErrors);
		when(point.proceed()).thenThrow(validationException);

		// when
		validationAspect.aroundMethodWithValidationEnabled(point, validate);

		// then
		verify(errors).rejectValue(error.getField(), error.getErrorCode(),
				error.getMessageVariables(), null);
	}

	@Test
	public void shouldReturnErrorView() throws Throwable {
		// given
		String errorView = "error_view";
		ValidationException validationException = new ValidationException(
				new ArrayList<>());
		when(point.proceed()).thenThrow(validationException);
		when(validate.value()).thenReturn(errorView);

		// when
		String result = validationAspect.aroundMethodWithValidationEnabled(
				point, validate);

		// then
		assertEquals(errorView, result);
	}

}
