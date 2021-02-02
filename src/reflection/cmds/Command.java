package reflection.cmds;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import reflection.Main;

public class Command {
	public final String name;
	
	public Command(String s) {
		name = Main.simplify(s);
	}
	
	public boolean equals(Object o) {
		if (o instanceof Command) {
			return ((Command) o).name.equals(name);
		} else if (o instanceof String) {
			return ((String) o).equals(name);
		} else {
			return o.equals(this);
		}
	}
	@Invocable
	public Object invoke() { return null; }
	public final Object invoker(Object... o) {
		Annotation[] annotations;
		Method[] methods = Command.class.getMethods();
		List<Object> parameters = new ArrayList<>();
		Class<?>[] paramterTypes;
		
		for (int i = 0; i < methods.length; i++) {
			annotations = methods[i].getAnnotations();
			
			for (int j = 0; j < annotations.length; j++) {
				if (annotations[j] instanceof Invocable) {
					try {
						paramterTypes = methods[i].getParameterTypes();
						
						for (int k = 0; k < paramterTypes.length; k++) {
							parameters.add(paramterTypes[k].cast(o));
						}
						
						methods[i].invoke(this, parameters.toArray());
					} catch (Exception e) {
						break;
					}
					
					parameters.clear();
				}
			}
		}
		
		return invoke();
	}
}
