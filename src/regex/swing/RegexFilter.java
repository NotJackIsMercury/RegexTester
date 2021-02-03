package regex.swing;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class RegexFilter extends DocumentFilter {
	final StyledDocument style;
	
	final StyleContext context = StyleContext.getDefaultStyleContext();
	final AttributeSet boundary =
			context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(30, 181, 64));
	final AttributeSet characterClass =
			context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(204, 109, 186));
	final AttributeSet escaped =
			context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(121, 171, 255));
	final AttributeSet logic =
			context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(180, 236, 110));
	final AttributeSet lookAround =
			context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(180, 236, 110));
	final AttributeSet normal =
		context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, Color.WHITE);
	final AttributeSet posix =
			context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(152, 108, 35));
	final AttributeSet quantifier =
			context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(104, 141, 187));

	Pattern boundaryPattern = Pattern.compile("(\\^|\\$|\\\\[bBaGZz])");
	Pattern characterClassPattern = Pattern.compile("\\{(javaLowerCase|javaUpperCase|javaWhitespace|javaMirrored)\\}");
	Pattern escapedPattern = Pattern.compile("\\\\(x(10(?:[0-9a-fA-F]){4}|(?:[0-9a-fA-F]{2,5}))|u(?:[0-9a-fA-F]){4}|0(?:[0-3])?(?:[0-7]){1,2}|c[0-9]|.)");
	Pattern logicPattern = Pattern.compile("\\[[^\\[](?:&{2}\\[[^\\]]\\])+\\]|\\^|\\|");
	Pattern lookAroundPattern = Pattern.compile("\\?(\\<name\\>|:|idmsuxU-idmsux|idmsux-idmsux\\:|!|<=|<!|>)");
	Pattern posixPattern = Pattern.compile("\\{(Lower|Upper|ASCII|Alpha|Digit|Alnum|punc|Graph|Print|Blank|Cntrol|xDigit|Space)\\}");
	Pattern quantifierPattern = Pattern.compile("[\\?\\*\\+]|\\{(\\d+|(\\d+,\\d+))\\}");
	public RegexFilter(StyledDocument s) {
		style = s;
	}

	public void insertString(FilterBypass fb, int i, String s, AttributeSet as) throws BadLocationException {
		super.insertString(fb, i, clearLineBreaks(s), as);
		
		textChanged();
	}
	
	public void remove(FilterBypass fb, int i1, int i2) throws BadLocationException {
		super.remove(fb, i1, i2);
		
		textChanged();
	}
	
	public void replace(FilterBypass fb, int i1, int i2, String s, AttributeSet as) throws BadLocationException {
		super.replace(fb, i1, i2, clearLineBreaks(s), as);
		
		textChanged();
	}

	String clearLineBreaks(String s) {
		return s.replace("\n", "");
	}
	
	void textChanged() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { updateStyles(); }
		});
	}
	
	void updateStyles() {
		style.setCharacterAttributes(0, style.getLength(), normal, true);
		
		Matcher matcher;
		
		try {
			matcher = escapedPattern.matcher(style.getText(0, style.getLength()));
			while (matcher.find()) {
				style.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), escaped, false);
			}

			matcher = boundaryPattern.matcher(style.getText(0, style.getLength()));
			while (matcher.find()) {
				style.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), boundary, false);
			}

			matcher = logicPattern.matcher(style.getText(0, style.getLength()));
			while (matcher.find()) {
				style.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), logic, false);
			}

			matcher = posixPattern.matcher(style.getText(0, style.getLength()));
			while (matcher.find()) {
				style.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), posix, false);
			}

			matcher = characterClassPattern.matcher(style.getText(0, style.getLength()));
			while (matcher.find()) {
				style.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), characterClass, false);
			}

			matcher = quantifierPattern.matcher(style.getText(0, style.getLength()));
			while (matcher.find()) {
				style.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), quantifier, false);
			}

			matcher = lookAroundPattern.matcher(style.getText(0, style.getLength()));
			while (matcher.find()) {
				style.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), lookAround, false);
			}
		} catch (BadLocationException e) {}
	}
}
