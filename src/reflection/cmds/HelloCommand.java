package reflection.cmds;

public class HelloCommand extends Command {
	public static final HelloCommand INSTANCE = new HelloCommand();
	
	public HelloCommand() {
		super("hello");
	}
	
	@Invocable
	public Object invoke() {
		System.out.println("Hello");
		return null;
	}
	@Invocable
	public Object invoke(Object s) {
		System.out.println("Hello " + s);
		return null;
	}
//	public Object invoke(Object o) {
//		System.out.println("Hello");
//		return null;
//	}
}
