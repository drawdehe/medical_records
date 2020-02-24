import java.util.UUID;

public class Admin extends Role {
	private String name;
	private String id;
	
	public Admin(String name) {
		this.name = name;
		id = UUID.randomUUID().toString();
		
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

	@Override
	public String getId() {
		return id;
	}
	
}
