package com.lumesse.editors;

import java.text.SimpleDateFormat;

import org.springframework.beans.propertyeditors.CustomDateEditor;

public class DateEditor extends CustomDateEditor {

	public static final String FORMAT = "dd-MM-yyyy HH:mm:ss";

	public DateEditor() {
		super(new SimpleDateFormat(FORMAT), true);
	}

}
