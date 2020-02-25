import java.util.UUID;

public class Patient extends Role{
	private String name;
	private final String personalNumber;
	private Division division;
	
	public Patient(String name, String personalNumber, Division division) {
		this.name = name;
		this.personalNumber = personalNumber;
		this.division = division;
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPermission() {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return name;
	}
	public String getPersonalNumber() {
		return personalNumber;
	}
	public Division getDivision() {
		return division;
		
	}
}
