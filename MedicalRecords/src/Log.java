import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

public class Log {
	//private String ssn_user;
	//private String ssn_patient;
	//private String operation;
	//private Boolean permission;
	private LocalDateTime time;
	//private String text;
	private PrintWriter writer;
	
	public Log() throws UnsupportedEncodingException, FileNotFoundException {
		/*this.ssn_user = ssn_user;
		this.ssn_patient = ssn_patient;
		this.operation = operation;
		this.permission = false;
		this.time = time.now();*/
		writer = new PrintWriter("log.txt", "UTF-8");
	}
	public void accessed(String ssn_user, String ssn_patient, String operation, Boolean permission) {
		writer.println("user: " + ssn_user + ", operation: " + operation + ", patient: " + ssn_patient + ", date: " + time);
	}
	public static void main (String [] args) throws UnsupportedEncodingException, FileNotFoundException {
		//For test purposes
		Log log = new Log();
		Boolean perm = true;
		String doctorsson = "doctorsson";
		String patientsson = "patientsson";
		String write = "write";
		log.accessed(doctorsson, patientsson, write, perm);
	}
 }
