package regex.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.TextComponent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.JTextComponent;

import regex.swing.JScrollText;
import regex.swing.RegexFilter;

@SuppressWarnings("serial")
public class Window extends JFrame {
	private ComponentListener bounds = new ComponentListener() {
		public void componentHidden(ComponentEvent ce) {}
		public void componentMoved(ComponentEvent ce) {}
		public void componentResized(ComponentEvent ce) {
			Component origin = ce.getComponent();
			
			regex.setBounds(0, 0, origin.getWidth(), regex.getGraphics().getFontMetrics().getHeight() + 5);
			input.setBounds(0, regex.getHeight(), getWidth() / 2 - 8, getHeight() - regex.getHeight() - 60);
			output.setBounds(input.getWidth(), regex.getHeight(), input.getWidth(), input.getHeight());
		}
		public void componentShown(ComponentEvent ce) {}
	};
	private DocumentListener listener = new DocumentListener() {
		public void changedUpdate(DocumentEvent de) {
			try {
				//final JTextComponent i = input.getComponent();
				final JTextComponent o = output.getComponent();
				String inputText = input.getComponent().getText();
				Matcher matcher = Pattern.compile("(" + regex.getText() + ")").matcher(inputText);
				
				o.setText("");
				while (matcher.find()) {
					o.setText(o.getText() + "\n" + matcher.group(0).replace("\n", "\\n"));
				} 
				
				String s = o.getText();
				
				if (s.startsWith("\n")) {
					o.setText(s.replaceFirst("\n+", ""));
				}
			} catch (Exception e) {
				output.getComponent().setText(e.toString());
			}
		}
		public void insertUpdate(DocumentEvent de) { changedUpdate(de); }
		public void removeUpdate(DocumentEvent de) { changedUpdate(de); }
	};
	private WindowStateListener states = new WindowStateListener() {
		public void windowStateChanged(WindowEvent we) {
			bounds.componentResized(new ComponentEvent(pane, 101));
		}
	};
	public JScrollText input;
	public Menus menuBar;
	public JScrollText output;
	public Container pane;
	public JTextPane regex;
	
	public Window() {
		super("Regex Simulator");
		
		input = new JScrollText(new JTextArea());
		menuBar = new Menus(this);
		output = new JScrollText(new JTextArea());
		pane = getContentPane();
		regex = new JTextPane(new DefaultStyledDocument());
		
		addWindowStateListener(states);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setJMenuBar(menuBar);
		setSize(1200, 720);
		setLocationRelativeTo(null);
		menuBar.file.open.text = input.getComponent();
		menuBar.file.save.text = input.getComponent();
		pane.add(input);
		pane.add(output);
		pane.add(regex);
		pane.addComponentListener(bounds);
		pane.setLayout(null);
		input.getComponent().getDocument().addDocumentListener(listener);
		input.getComponent().setToolTipText("<html><body><p>Write testing texts here.</p></body></html>");
		output.getComponent().setEditable(false);
		output.getComponent().setToolTipText("<html><body><p>Captured text from the input (left).</p></body></html>");
		regex.getDocument().addDocumentListener(listener);
		((AbstractDocument) regex.getDocument()).setDocumentFilter(new RegexFilter(regex.getStyledDocument()));
		//regex.setDocument(new DefaultStyledDocument(StyleContext.getDefaultStyleContext()));
		regex.setToolTipText("<html><body><p>Input your regex here!</p></body></html>");
		
		setBackground(new Color(47, 47, 47));
		setForeground(Color.WHITE);
		setFont(new Font("Consolas", Font.PLAIN, 15));
	}

	public void setBackground(Color c) {
		super.setBackground(c);
		Color invert = new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
		
		try {
			input.setBackground(c);
			input.setCaretColor(invert);
			output.setBackground(c);
			output.setCaretColor(invert);
			pane.setBackground(c);
			regex.setBackground(c);
			regex.setCaretColor(invert);
		} catch (Exception e) {}
	}
	public void setFont(Font f) {
		super.setFont(f);
		input.setFont(f);
		output.setFont(f);
		regex.setFont(f);
	}
	public void setForeground(Color c) {
		super.setForeground(c);
		try {
			input.setForeground(c);
			output.setForeground(c);
			pane.setForeground(c);
			regex.setForeground(c);
		} catch (Exception e) {}
	}
	public synchronized void start() {
		setVisible(true);
		input.getComponent().setText("The quick brown fox jumped over the lazy dogs.");
		regex.setText("\\S+");
	}
}
