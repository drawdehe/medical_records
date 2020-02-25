import java.util.UUID;

public class Doctor extends Role {
	private String name;
	private final String ssn;
	private Division division;
	
	public Doctor(String name, String ssn, Division division) {
		this.name = name;
		this.ssn = ssn;
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
	public String getSsn() {
		return ssn;
	}
	public Division getDivision() {
		return division;
		
	}
}
