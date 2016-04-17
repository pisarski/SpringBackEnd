package com.gmail.sebastian.pisarski.editors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import com.gmail.sebastian.pisarski.editors.DateEditor;

@RunWith(JUnitParamsRunner.class)
public class DateEditorTest {

	private DateEditor dateEditor;

	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Before
	public void setUp() {
		dateEditor = new DateEditor();
	}

	@Test
	public void shouldMapStringToDate() throws Exception {
		// given
		String dateAndTimeStr = "24-06-2015 12:01:43";
		dateEditor.setAsText(dateAndTimeStr);

		// when
		Date date = (Date) dateEditor.getValue();

		// then
		SimpleDateFormat format = new SimpleDateFormat(DateEditor.FORMAT);
		assertEquals(dateAndTimeStr, format.format(date));
	}

	@Test
	@Parameters(method = "getParametersForShouldThrowExceptionOnInvalidString")
	public void shouldThrowExceptionOnInvalidString(String dateStr) {
		// then
		expected.expect(IllegalArgumentException.class);

		// when
		dateEditor.setAsText(dateStr);
	}

	@Test
	@Parameters(method = "getParametersForShouldDoNothingWithEmptyValues")
	public void shouldDoNothingWithEmptyValues(String empty) {
		// when
		dateEditor.setAsText(empty);

		// then
		assertNull(dateEditor.getValue());
	}

	@SuppressWarnings("unused")
	private String[] getParametersForShouldThrowExceptionOnInvalidString() {
		return new String[] { "text", "10-12-2015", "12:12:12", "12321312" };
	}

	@SuppressWarnings("unused")
	private String[] getParametersForShouldDoNothingWithEmptyValues() {
		return new String[] { null, "", "      " };
	}
}
