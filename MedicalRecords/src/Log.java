import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

public class Log {
	//private String ssn_user;
	//private String ssn_patient;
	//private String operation;
	//private Boolean permission;
	//private LocalDateTime time;
	//private String text;

//	private BufferedWriter bw;
//	private FileWriter writer;
	
//	public Log() throws IOException {
//		/*this.ssn_user = ssn_user;
//		this.ssn_patient = ssn_patient;
//		this.operation = operation;
//		this.permission = false;
//		this.time = time.now();*/
//		//this.time = time;
//		FileWriter writer = new FileWriter("log.txt");
//		bw = new BufferedWriter(writer);
//	}
	public Log() {
		try {
			FileWriter fw = new FileWriter(new File("Log.txt"));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
			fw.append("(" + System.currentTimeMillis() + ") " + 
					"User: " + ssn_user + ", Operation: " + operation + ", Patient: " + ssn_patient +
					", Access: " + access +"\n");
			fw.close();
		} catch (IOException e) {	
			e.printStackTrace();
		}
	}
	
//	public void accessed(String ssn_user, String ssn_patient, String operation, Boolean permission) throws IOException {
//		LocalDateTime now = LocalDateTime.now();
//		bw.write("user: " + ssn_user + ", operation: " + operation + ", patient: " + ssn_patient + ", date: " + now);
//		bw.newLine();
//	}
	

 }
