import java.time.LocalDateTime;

public class Log {
	private Role user;
	private LocalDateTime time;
	private String text;
	private String operation;
	
	public Log(Role user, String operation, String text) {
		this.user = user;
		this.text = text;
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
