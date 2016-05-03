package com.gmail.sebastian.pisarski.rest;

import static org.junit.Assert.assertThat;

import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.gmail.sebastian.pisarski.rest.assertions.StatusMatcher;
import com.gmail.sebastian.pisarski.service.SpittleService;

@RunWith(MockitoJUnitRunner.class)
public class SpittleRestTest extends BaseRestTest {

	@InjectMocks
	private static SpittleRest spittleRest;

	@Mock
	private SpittleService spittleService;

	@Test
	public void test() throws Exception {
		// given
		MockHttpRequest request = MockHttpRequest.get("/spittles");

		// when
		MockHttpResponse response = invoke(request);

		// then
		assertThat(response, StatusMatcher.hasStatus(Status.OK));
	}

	@Override
	protected void setUp() {
	}

	@Override
	protected Object getRestService() {
		return spittleRest;
	}
}
