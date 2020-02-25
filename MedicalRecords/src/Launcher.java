
public class Launcher {
	
	public static void main (String [] args) {
		
		//For test purposes
		
		Government government = new Government("The government");
		
		Division divisionA = new Division("Division A");
		Division divisionB = new Division("Division B");
		
		Doctor doctorA = new Doctor("Doctor A", "000000-00", divisionA);
		Doctor doctorB = new Doctor("Doctor B", "000000-00", divisionB);
		
		Nurse nurseA = new Nurse("Nurse A", "000000-00", divisionA);
		Nurse nurseB = new Nurse("Nurse B", "000000-00", divisionA);
		Nurse nurseC = new Nurse("Nurse C", "000000-00", divisionB);
		Nurse nurseD = new Nurse("Nurse D", "000000-00", divisionB);
		
		Patient patientA = new Patient("Patient A", "000000-00", divisionA);
		Patient patientB = new Patient("Patient B", "000000-00", divisionB);
		Patient patientC = new Patient("Patient C", "000000-00", divisionB);
		
		System.out.println(nurseA.getName());
		System.out.println(nurseA.getSsn());
	}
}
