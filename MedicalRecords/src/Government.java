import java.util.UUID;

public class Government extends Role {
	private String name;
	private String id;
	
	public Government(String name) {
		this.name = name;
		id = UUID.randomUUID().toString();
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
}
