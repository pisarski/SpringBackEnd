package com.gmail.sebastian.pisarski.rest;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Before;

import com.gmail.sebastian.pisarski.rest.exception.RestExceptionMapper;

public abstract class BaseRestTest {

	private Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();

	@Before
	public void setUpTest() throws Exception {
		setUp();
		dispatcher.getRegistry().addSingletonResource(getRestService());
		dispatcher.getProviderFactory().registerProvider(RestExceptionMapper.class);
	}

	public MockHttpResponse invoke(MockHttpRequest request) {
		MockHttpResponse response = new MockHttpResponse();
		dispatcher.invoke(request, response);
		return response;
	}

	protected String jsonOf(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	protected abstract void setUp() throws Exception;

	protected abstract Object getRestService();

}
