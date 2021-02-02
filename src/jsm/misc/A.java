package jsm.misc;

import java.io.FileWriter;
import java.util.Arrays;

public class A {
	public static void main(String[] args) throws Exception {
		FileWriter f = new FileWriter("D://test.txt");
		StringBuilder b = new StringBuilder("--input -seed tp s\n");
		String t = "0000000";
		
		for (int i = 0; i < 100_000_000; i++) {
			String s = t.substring(0, 2);
			
			f.append(t + " " + numCaesar0(t.substring(2), -Integer.parseInt(s)) + " " + s + " " + s.charAt(1) + "\n");
			t = inc(t);
		}
		
		f.write(b.toString());
		f.close();
	}
	
	public static String inc(String text) { return inc(text, text.length() - 1); }
	
	public static String inc(String text, int i) {
		if (i > 0) {
			int num = Integer.parseInt(String.valueOf(text.charAt(i)));
			
			if (num++ >= 9) {
				num %= 10;
				
				text = inc(text, i - 1);
			}
			
			text = text.substring(0, i++) + num + text.substring(i);
		}
		
		return text;
	}
	
	public static String numCaesar(String value, int shift) {
		System.out.println(" | Invoking " + value + ", " + shift);
		String text = "";
		
		System.out.println(" | Starting loop - excpecting " + value.length() + " iterations.");
		for (int i = 0; i < value.length(); i++) {
			System.out.println(" | Iteration: " + i);
			int num = Integer.parseInt(String.valueOf(value.charAt(i)));
			System.out.println("   | new digit: " + num);
			num += shift;
			System.out.println("   | digit changed to: " + num);
			text += ((num > 0) ? Math.abs(num % 10) : ((10 - Math.abs(num % 10)) % 10));
		}
		
		System.out.println(" | Returning " + text);
		return text;
	}
	
	public static String numCaesar0(String value, int shift) {
		String text = "";
		
		for (int i = 0; i < value.length(); i++) {
			int num = Integer.parseInt(String.valueOf(value.charAt(i)));
			num += shift;
			text += ((num > 0) ? Math.abs(num % 10) : ((10 - Math.abs(num % 10)) % 10));
		}
		
		return text;
	}

	public static String form(String t) {
		char[] dash = new char[Math.max(7 - t.length(), 0)];
		Arrays.fill(dash, '-');
		
		return String.valueOf(dash) + t;
	}
}
