package reflection;

import reflection.cmds.HelloCommand;
import static java.lang.System.out;

public final class Main {
	public static void main(String[] args) {
		Console c = new Console();
		c.add(HelloCommand.INSTANCE);
		c.run();
	}
	public static String normalize(String s) {
		return s.replaceAll("\\s+", " ").trim();
	}
	public static <T> void print(T t) {
		out.print(" | " + t);
	}
	public static <T> void println(T t) {
		out.println(" | " + t);
	}
	public static String simplify(String s) {
		return s.replaceAll("\\s+", "").replace("$", "");
	}
}
