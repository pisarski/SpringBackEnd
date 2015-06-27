package com.lumesse.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.oval.configuration.annotation.IsInvariant;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lumesse.exception.CustomError;
import com.lumesse.exception.ErrorsContainer;
import com.lumesse.exception.ValidationException;

public class BaseServiceTest {

	@Rule
	public ExpectedException expected = ExpectedException.none();

	private BaseService service;

	@Before
	public void setUp() {
		service = new BaseService() {
		};
	}

	@Test
	public void shouldDoNothingForValidObject() {
		service.validate(new Entity());
	}

	@Test
	public void shouldThrowExceptionForCustomError() {
		// given
		Entity properEntity = new Entity();
		ErrorsContainer container = new ErrorsContainer();
		container.add("test", "errorCode");

		// when
		try {
			service.validate(properEntity, container);
			fail("ValidationException expected");

			// then
		} catch (ValidationException e) {
			CustomError error = e.getCustomErrors().get(0);

			assertEquals("test", error.getField());
			assertEquals("errorCode", error.getErrorCode());
			assertTrue(error.getMessageVariables().length == 0);
		}
	}

	@Test
	public void shouldThrowExceptionOnWrongContext() {
		// then
		expected.expect(IllegalArgumentException.class);
		expected.expectMessage("Validation supports only field annotations");

		// when
		service.validate(new EntityWithAnnotatedMethods());
	}

	@Test
	public void shouldProperlyMapSimpleViolationError() {
		// given
		Entity entity = new Entity();
		entity.setField(null);

		// when
		try {
			service.validate(entity);
			fail("ValidationException expected");

			// then
		} catch (ValidationException e) {
			CustomError error = e.getCustomErrors().get(0);

			assertEquals("field", error.getField());
			assertEquals("NotNull", error.getErrorCode());
			assertNull(error.getMessageVariables()[0]);
		}
	}

	@Test
	public void shouldProperlyMapAnnotationWithParams() {
		// given
		Entity entity = new Entity();
		entity.setParameters("");

		// when
		try {
			service.validate(entity);
			fail("ValidationException expected");

			// then
		} catch (ValidationException e) {
			CustomError error = e.getCustomErrors().get(0);

			assertEquals("parameters", error.getField());
			assertEquals("Length", error.getErrorCode());
			assertEquals("", error.getMessageVariables()[0]);
			assertEquals("4", error.getMessageVariables()[1]);
			assertEquals("1", error.getMessageVariables()[2]);
		}
	}

	@Test
	public void shouldTakeCustomErrorMessageCode() {
		// given
		Entity entity = new Entity();
		entity.setCustomMessage(null);

		// when
		try {
			service.validate(entity);
			fail("ValidationException expected");

			// then
		} catch (ValidationException e) {
			CustomError error = e.getCustomErrors().get(0);

			assertEquals("customMessage", error.getField());
			assertEquals("custom", error.getErrorCode());
			assertEquals(null, error.getMessageVariables()[0]);
		}
	}

	private static class Entity {

		@NotNull
		private String field = "";

		@Length(min = 1, max = 4)
		private String parameters = "test";

		@NotNull(errorCode = "custom")
		private String customMessage = "";

		public void setField(String field) {
			this.field = field;
		}

		public void setParameters(String parameters) {
			this.parameters = parameters;
		}

		public void setCustomMessage(String customMessage) {
			this.customMessage = customMessage;
		}

	}

	private static class EntityWithAnnotatedMethods {

		private String value;

		@IsInvariant
		@NotNull
		public String getValue() {
			return value;
		}
	}
}
