package regex.menus.items;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

@SuppressWarnings("serial")
public class Open extends JMenuItem implements ActionListener {
	public final Component parent;
	public JTextComponent text;
	
	public Open(Component c) {
		super("Open");
		
		parent = c;
		
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae) {
		JFileChooser chooser = new JFileChooser();
		
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			try {
				StringBuilder builder = new StringBuilder();
				int c;
				FileReader reader = new FileReader(chooser.getSelectedFile());
				
				while ((c = reader.read()) > 0) {
					builder.append((char) c);
					reader.skip(0);
				}
				
				reader.close();
				text.setText(builder.toString());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(parent, e.getLocalizedMessage(), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
				
			}
		}
	}
	public Open setText(JTextComponent t) { text = t; return this; }
}
