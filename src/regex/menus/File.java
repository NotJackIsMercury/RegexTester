package regex.menus;

import java.awt.Component;

import javax.swing.JMenu;

import regex.menus.items.Open;
import regex.menus.items.Save;

@SuppressWarnings("serial")
public class File extends JMenu {
	public final Save save;
	public final Open open;
	
	public File(Component c) {
		super("File");

		add(open = new Open(c));
		add(save = new Save(c));
	}
}
