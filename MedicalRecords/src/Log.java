import java.time.LocalDateTime;

public class Log {
	public Role user;
	public LocalDateTime time;
	public String text;
	
	public Log(Role user, LocalDateTime time, String text) {
		this.user = user;
		this.time = time;
		this.text = text;
	}
}
