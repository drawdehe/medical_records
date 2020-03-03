import java.time.LocalDateTime;

public class Log {
	private String ssn_user;
	private String ssn_patient;
	private String operation;
	private Boolean permission;
	private LocalDateTime time;
	private String text;
	
	public Log(String ssn_user, String ssn_patient, String operation, Boolean permission, LocalDateTime time) {
		this.ssn_user = ssn_user;
		this.ssn_patient = ssn_patient;
		this.operation = operation;
		this.permission = false;
		this.time = time.now();
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
