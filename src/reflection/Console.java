package reflection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import reflection.cmds.Command;

public class Console implements Runnable {
	static final class LocalVariable {
		public final int location;
		public final String name;
		
		public LocalVariable(String s, int i) {
			location = i;
			name = s;
		}
	
		public boolean equals(Object o) {
			if (o instanceof LocalVariable) {
				return ((LocalVariable) o).name.equals(name);
			} else if (o instanceof String) {
				return ((String) o).equals(name);
			} else {
				return o.equals(this);
			}
		}
	}
	
	final List<Command> commands;
	public final String prefix;
	final List<Object> references;
	final List<LocalVariable> variables;
	
	public Console() {this(""); }
	public Console(String s) {
		commands = new ArrayList<Command>();
		prefix = Main.simplify(s);
		references = new ArrayList<Object>();
		variables = new ArrayList<LocalVariable>();
	}
	
	public void add(Command c) {
		if (!commands.contains(c))
			commands.add(c);
	}
	@SuppressWarnings("unlikely-arg-type")
	public void add(String s) {
		for (int i = 0; i < variables.size(); i++) {
			if (variables.get(i).equals(s)) break;
		}
		
		variables.add(new LocalVariable(s, references.size()));
		references.add(null);
	}
	public Command[] getCommands() {
		return Command[].class.cast(commands.toArray());
	}
	public void remove(Command c) {
		if (commands.contains(c))
			commands.remove(c);
	}
	@SuppressWarnings("unlikely-arg-type")
	public void remove(String s) {
		for (int i = 0; i < commands.size(); i++) {
			if (variables.get(i).equals(s)) {
				variables.remove(i);
				break;
			}
		}
	}
	@SuppressWarnings("unlikely-arg-type")
	public void run() {
		Command c;
		Scanner scanner = new Scanner(System.in);
		String string;
		String[] strings;
		
		while (true) {
			System.out.print(" > ");
			string = Main.normalize(scanner.nextLine());
			
			if (prefix.length() < 1 | string.startsWith(prefix)) {
				strings = Main.normalize(string.replaceFirst(prefix, "")).split(" ");

				if (strings.length > 0) {
					for (int i = 0; i < commands.size(); i++) {
						c = commands.get(i);

						if (c.equals(strings[0])) {
							c.invoker((Object[]) Arrays.copyOfRange(strings, 1, strings.length));
							break;
						}
					}
				}
			}
			
			scanner.reset();
		}
	}
}
