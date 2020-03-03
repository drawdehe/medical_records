import java.time.LocalDateTime;

public class Log {
	private String user;
	private LocalDateTime time;
	private String text;
	private String operation;
	
	//User should probably not be a string
	
	public Log(String user, String operation) {
		this.user = user;
		this.operation = operation;
	}
	public void accessed() {
		if (operation == "read") {
			
		}
		if (operation == "write") {
			
		}
		if (operation == "create") {
			
		}
		if (operation == "delete") {
			
		}
	}
}
