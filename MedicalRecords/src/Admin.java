import java.util.UUID;

public class Admin extends Role {
	private String name;
	private final String ssn;
	
	public Admin(String name, String ssn) {
		this.name = name;
		this.ssn = ssn;
		
	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return name;
	}
	public String getSsn() {
		return ssn;
	}
	
}
