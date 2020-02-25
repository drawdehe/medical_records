import java.util.UUID;

public class Government extends Role {
	private String name;
	
	public Government(String name) {
		this.name = name;
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
}
