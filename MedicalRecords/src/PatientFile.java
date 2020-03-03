
public class PatientFile {

		private String[] data;
		
		public PatientFile(String name, String doctor, String nurse, String division, String text) {
			data = new String[5];
			data[0] = name;
			data[1] = doctor;
			data[2] = nurse;
			data[3] = division;
			data[4] = text;
		}	
		
		public String getPatientName() {
			return data[0];
		}
		
		public String getDoctorName() {
			return data[1];
		}
		
		public String getNurseName() {
			return data[2];
		}
		
		public String getDivision() {
			return data[3];
		}
		
		public String getMedicalInfo() {
			return data[4];
		}
		
		public void appendMedicalText(String s) {
			data[4] = data[4] + " | " + s;
		}
		
		public String toString() {
			return data[0] + "\n" + data[1] + "\n" + data[2] + "\n" + data[3] + "\n" + data[4];
		}

}
