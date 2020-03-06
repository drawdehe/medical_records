public class Authorization {

	public Authorization() {
		
	}
	/*
	 * tar subject strängen vi får från certifikatet och parsar den, hämtar ut
	 * fältet Common Name (CN = ) och returerar den
	 */
	public String getName(String subject) {
		String name = "";
		for (int i = 0; i < subject.length() - 2; i++) {
			if (subject.charAt(i) == 'C' && subject.charAt(i + 1) == 'N' && subject.charAt(i + 2) == '=') {
				while (subject.charAt(i + 3) != ',') {
					name += subject.charAt(i + 3);
					i++;
				}
				break;
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
}