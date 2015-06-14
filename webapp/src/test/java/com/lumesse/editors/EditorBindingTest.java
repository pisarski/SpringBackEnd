package com.lumesse.editors;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.web.bind.WebDataBinder;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import com.lumesse.controller.BaseController;

public class EditorBindingTest {

	private static final String EDITORS_PACKAGE = "com.lumesse.editors";

	@Test
	public void shouldBindAllCustomEditors() {
		// given
		BaseController baseController = new BaseController();
		WebDataBinder binder = mock(WebDataBinder.class);
		List<Class<?>> customEditorsClasses = getCustomEditorsClasses();

		// when
		baseController.initBinder(binder);

		// then
		ArgumentCaptor<PropertiesEditor> propertyEditorCapture = ArgumentCaptor
				.forClass(PropertiesEditor.class);
		verify(binder).registerCustomEditor(any(Class.class),
				propertyEditorCapture.capture());

		List<PropertiesEditor> editors = propertyEditorCapture.getAllValues();

		for (Object editor : editors) {
			Class<?> editorClass = editor.getClass();
			if (customEditorsClasses.contains(editorClass)) {
				customEditorsClasses.remove(editorClass);
			}
		}

		if (customEditorsClasses.size() > 0) {
			fail("There are editors which are not bind: "
					+ customEditorsClasses);
		}

	}

	private List<Class<?>> getCustomEditorsClasses() {
		List<Class<?>> classes = new ArrayList<>();
		try {
			ImmutableSet<ClassInfo> classesFromEditorsPackage = getClassesFromEditorsPackage();
			for (ClassInfo info : classesFromEditorsPackage) {
				String className = info.getName();
				Class<?> clazz = Class.forName(className);
				if (isSubclassOfPropertyEditor(clazz)) {
					classes.add(clazz);
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}

		return classes;
	}

	private ImmutableSet<ClassInfo> getClassesFromEditorsPackage()
			throws IOException {
		ClassPath classPath = ClassPath.from(EditorBindingTest.class
				.getClassLoader());
		return classPath.getTopLevelClassesRecursive(EDITORS_PACKAGE);
	}

	private boolean isSubclassOfPropertyEditor(Class<?> clazz) {
		if (Object.class.equals(clazz)) {
			return false;
		}
		if (PropertiesEditor.class.equals(clazz)
				|| PropertyEditorSupport.class.equals(clazz)) {
			return true;
		}
		return isSubclassOfPropertyEditor(clazz.getSuperclass());
	}
}
