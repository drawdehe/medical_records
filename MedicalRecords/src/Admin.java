import java.util.UUID;

public class Admin extends Role {
	private String name;
	private final String personalNumber;
	
	public Admin(String name, String personalNumber) {
		this.name = name;
		this.personalNumber = personalNumber;
		
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
	public String getPersonalNumber() {
		return personalNumber;
	}
	
}
