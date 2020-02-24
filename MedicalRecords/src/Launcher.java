
public class Launcher {
	
	public static void main (String [] args) {
		
		//For test purposes
		
		Government government = new Government("The government");
		
		Division divisionA = new Division("Division A");
		Division divisionB = new Division("Division B");
		
		Doctor doctorA = new Doctor("Doctor A", divisionA);
		Doctor doctorB = new Doctor("Doctor B", divisionB);
		
		Nurse nurseA = new Nurse("Nurse A", divisionA);
		Nurse nurseB = new Nurse("Nurse B", divisionA);
		Nurse nurseC = new Nurse("Nurse C", divisionB);
		Nurse nurseD = new Nurse("Nurse D", divisionB);
		
		Patient patientA = new Patient("Patient A", divisionA);
		Patient patientB = new Patient("Patient B", divisionB);
		Patient patientC = new Patient("Patient C", divisionB);
		
		System.out.println(nurseA.getName());
		System.out.println(nurseA.getId());
	}
}
