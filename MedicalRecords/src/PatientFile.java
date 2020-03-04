public class PatientFile {

		private String[] data;
		
		public PatientFile(String name, String ssn, String doctor, String nurse, String division, String text) {
			data = new String[6];
			data[0] = name;
			data[1] = ssn;
			data[2] = doctor;
			data[3] = nurse;
			data[4] = division;
			data[5] = text;
		}	
		
		public String getPatientName() {
			return data[0];
		}
		
		public String getPatientSSN() {
			return data[1];
		}
		
		public String getDoctorName() {
			return data[2];
		}
		
		public String getNurseName() {
			return data[3];
		}
		
		public String getDivision() {
			return data[4];
		}
		
		public String getMedicalInfo() {
			return data[5];
		}
		
		public void appendMedicalText(String s) {
			data[5] = data[5] + " | " + s;
		}
		
		public String toString() {
			return data[0] + "\n" + data[1] + "\n" + data[2] + "\n" + data[3] + "\n" + data[4] + "\n" + data[5];
		}

}
