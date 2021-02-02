package regex.menus.items;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

@SuppressWarnings("serial")
public class Save extends JMenuItem implements ActionListener {
	public final Component parent;
	public JTextComponent text;
	
	public Save(Component c) {
		super("Save");
		
		parent = c;
		
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae) {
		JFileChooser chooser = new JFileChooser();
		
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			try {
				FileWriter writer = new FileWriter(chooser.getSelectedFile());
				
				writer.write(text.getText());
				writer.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(parent, e.getLocalizedMessage(), e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	public Save setText(JTextComponent t) { text = t; return this; }
}
