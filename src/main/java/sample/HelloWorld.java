package sample;

public class HelloWorld {

	private String message;

	public HelloWorld(String message) {
		this.message = message;
	}

	public String getMessage() {
		System.out.println("test CI");
		return message;
	}
}
