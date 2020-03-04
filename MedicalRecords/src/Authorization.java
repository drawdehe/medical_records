import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Authorization {

	public Authorization() {
		
	}
	/*
	 * öppnar filen file.tx, läser in rader, br.readline tar nästa rad i texten,
	 * läsningen avslutas när det inte finns någon line kvar eller om man har hittat
	 * rätt username. Varje line i textfilen ser ut såhär "username group". line
	 * splittas vid mellanrum och blir till en array av strings -> [username,
	 * group]. när rätt username hittats returneras group. om rätt username inte
	 * hittas returneras "-1"
	 */

	/*
	 * isAssociated går in i en map som ska vara sparad för varje patient, om
	 * patientens namn är Alban skulle mapen heta Alban, while loopen stegar igenom
	 * varje line med br.readline(), om den hittar en line som är samma subjektets
	 * namn returneras true, om det aldrig hittas returneras false. varje line i
	 * file.txt består endast av ett personnummer på en läkare eller sjuksyster som
	 * är associerad med patienten.
	 */

	/*
	 * isAssociated kollar i en patients journal om en doctor eller nurse är
	 * associerad med patienten. Var fil inleds med information om vilka doctors och
	 * nurses som är associerade med patienten, samt dess avdelning. Den
	 * informationen kollas igenom för att se om det angivna namnet "name" matchar.
	 */
	public Boolean isAssociated(String employee_id, String ssn_patient) throws FileNotFoundException, IOException {
		String file = ssn_patient + ".txt";
		BufferedReader br = new BufferedReader(new FileReader(file));
		try {
			String line = br.readLine();

			while (line != null) {
				if (line.equals(employee_id)) {
					return true;
				}
				line = br.readLine();
			}
			return false;
		} finally {
			br.close();
		}
	}

	/*
	 * getPass tar in vad subjektet vill göra, subjektet som vi fått från
	 * certifikatet,patientens namn och namnet på journalen (doc). det finns sedan
	 * olika cases som tar hänsyn till vad subjektet har för roll, samt de andra
	 * variablerna. funktionen använder sig av get groupprivilege isAssociated och
	 * getName
	 */
	public Boolean getPass(String action, String subject, String patient, String doc)
			throws FileNotFoundException, IOException {
		String[] groupPrivilege = getGroupPrivilege(subject);
		String name = getName(subject);
		switch (groupPrivilege[0]) {

		case "government":
			if (action.equals("delete")) {
				return true;
			}
			if (action.equals("read")) {
				return true;
			}
			break;

		case "doctor":
			if (action.equals("read")) {
				if (groupPrivilege[1].equals(getGroup(patient))) {
					return true;
				}
				return false;
			}
			if (action.equals("write")) {
				if (isAssociated(name, patient)) {
					return true;
				}
				return false;
			}
			break;

		case "nurse":
			if (action.equals("read")) {
				if (groupPrivilege[1].equals(getGroup(patient))) {
					return true;
				}
				return false;
			}
			if (action.equals("write") && !doc.equals("new")) {
				if (isAssociated(name, patient)) {
					return true;
				}
				return false;
			}
			break;

		}
		return false;
	}
	
	/*
	 * tar subject strängen vi får från certifikatet och parsar den, hämtar ut
	 * fältet Common Name (CN = ) och returerar den
	 */
	public String getName(String subject) {
		String name = "";
		for (int i = 0; i < subject.length() - 2; i++) {
			if (subject.charAt(i) == 'C' && subject.charAt(i + 1) == 'N' && subject.charAt(i + 2) == '='
					&& subject.charAt(i + 3) == '"') {
				while (subject.charAt(i + 4) != '"') {
					name += subject.charAt(i + 4);
					i++;
				}
				break;

				// testkommentar
			}
		}
		return name;
	}
	
	/*
	 * hämtar ut Organization (O) och Organisation Unit (OU) från Subject strängen
	 * vi får av certifikatet, kan t.ex vara O = division B och OU = doctor
	 */
	public String[] getGroupPrivilege(String subject) {
		String group = "";
		String privilege = "";
		String[] result = new String[2];
		for (int i = 0; i < subject.length() - 2; i++) {
			if (subject.charAt(i) == 'O' && subject.charAt(i + 1) == 'U' && subject.charAt(i + 2) == '=') {
				while (subject.charAt(i + 3) != ',') {
					group += subject.charAt(i + 3);
					i++;
				}
				break;
			}
		}
		for (int i = 0; i < subject.length() - 2; i++) {
			if (subject.charAt(i) == 'O' && subject.charAt(i + 1) == '=') {
				while (subject.charAt(i + 2) != ',') {
					privilege += subject.charAt(i + 2);
					i++;
				}
				break;
			}
		}
		result[0] = group;
		result[1] = privilege;
		return result;
	}
	
	
	
	public String getGroup(String ssn_patient) throws FileNotFoundException, IOException {
//
//		String file = ssn_patient + ".txt";
//		BufferedReader br = new BufferedReader(new FileReader(file));
//		try {
//			for(int i = 0; i < §)
//			String line = br.readLine();
//			while (line != null) {
//				String[] user = line.split(" ", 0);
//				System.out.println(user[0]);
//				if (user[0].equals(ssn_patient)) {
//					return user[1];
//				}
//				line = br.readLine();
//			}
//			return "-1";
//		} finally {
//			br.close();
//		}

		return "-1";
		
	}


	/*
	 * isAssociated går in i en map som ska vara sparad för varje patient, om
	 * patientens namn är Alban skulle mapen heta Alban, while loopen stegar igenom
	 * varje line med br.readline(), om den hittar en line som är samma subjektets
	 * namn returneras true, om det aldrig hittas returneras false. varje line i
	 * file.txt består endast av ett personnummer på en läkare eller sjuksyster som
	 * är associerad med patienten.
	 */

	/*
	 * isAssociated kollar i en patients journal om en doctor eller nurse är
	 * associerad med patienten. Var fil inleds med information om vilka doctors och
	 * nurses som är associerade med patienten, samt dess avdelning. Den
	 * informationen kollas igenom för att se om det angivna namnet "name" matchar.
	 */
//	public Boolean isAssociated(String employee_id, String ssn_patient) throws FileNotFoundException, IOException {
//		String file = ssn_patient + ".txt";
//		BufferedReader br = new BufferedReader(new FileReader(file));
//		try {
//			String line = br.readLine();
//
//			while (line != null) {
//				if (line.equals(employee_id)) {
//					return true;
//				}
//				line = br.readLine();
//			}
//			return false;
//		} finally {
//			br.close();
//		}
//	}
	

}
