package regex.swing;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

@SuppressWarnings("serial")
public class JScrollText extends JScrollPane {
	JTextComponent component;
	
	public JScrollText(JTextComponent c) {
		super(c);
		
		component = c;
	}

	public void setBackground(Color c) {
		super.setBackground(c);
		try {
			component.setBackground(c);
		} catch (Exception e) {}
	}
	public void setCaretColor(Color c) { component.setCaretColor(c); }
	public JTextComponent getComponent() { return component; }
	public void setForeground(Color c) {
		super.setForeground(c);
		try {
			component.setForeground(c);
		} catch (Exception e) {}
	}
	public JTextComponent setComponent(JTextComponent c) { return component = c; }
	public void setFont(Font f) {
		super.setFont(f);
		
		if (component != null)
			component.setFont(f);
	}
}
