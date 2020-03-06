import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Log {

	public Log() {

	}
	public static void newLogEntry(String ssn_user, String ssn_patient, String operation, Boolean permission) {
		String access;
		if(permission) {
			access = "GRANTED";
		} else {
			access = "DENIED";
		}

		try {
			FileWriter fw = new FileWriter("Log.txt", true);
			LocalDateTime time = LocalDateTime.now();
			fw.append("(" + time + ") " + 
					"User: " + ssn_user + ", Operation: " + operation + ", Patient: " + ssn_patient +
					", Access: " + access +"\n");
			fw.close();
		} catch (IOException e) {	
			e.printStackTrace();
		}
	}
 }