package sample;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloWorldTest {

	@Test
	public void shouldReturnProperMessage() {
		// given
		String message = "Hello, World!";
		HelloWorld helloWorld = new HelloWorld(message);

		// when
		String returnMessage = helloWorld.getMessage();

		// then
		assertEquals(message, returnMessage);
	}
}
