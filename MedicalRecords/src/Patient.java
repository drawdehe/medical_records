import java.util.UUID;

public class Patient extends Role{
	private String name;
	private String id;
	private Division division;
	
	public Patient(String name, Division division) {
		this.name = name;
		id = UUID.randomUUID().toString();
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

	@Override
	public String getId() {
		return id;
	}
	public Division getDivision() {
		return division;
		
	}
}
