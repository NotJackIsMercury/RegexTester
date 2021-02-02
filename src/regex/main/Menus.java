package regex.main;

import java.awt.Component;

import javax.swing.JMenuBar;

import regex.menus.File;

@SuppressWarnings("serial")
public class Menus extends JMenuBar {
	public File file;
	
	public Menus(Component c) {
		add(file = new File(c));
	}
}
