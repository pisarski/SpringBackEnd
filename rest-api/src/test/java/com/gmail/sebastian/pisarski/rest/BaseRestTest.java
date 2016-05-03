package com.gmail.sebastian.pisarski.rest;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Before;

public abstract class BaseRestTest {

	private Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();

	@Before
	public void setUpTest() {
		setUp();
		dispatcher.getRegistry().addSingletonResource(getRestService());
	}

	public MockHttpResponse invoke(MockHttpRequest request) {
		MockHttpResponse response = new MockHttpResponse();
		dispatcher.invoke(request, response);
		return response;
	}

	protected abstract void setUp();

	protected abstract Object getRestService();

}
