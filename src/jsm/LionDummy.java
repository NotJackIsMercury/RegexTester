package jsm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LionDummy {
	public static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		int answer;
		int guess;
		int leftTurns;
		int losses;
		int maxTurns;
		int range;
		boolean running = true;
		double score;
		boolean settings;
		long sum;
		int turns;

		try {
			BufferedReader reader = new BufferedReader(new FileReader("D:/save.txt"));
			losses = reader.read();
			maxTurns = reader.read();
			range = reader.read();
			score = reader.read() + reader.read() * 32_767;
			turns = reader.read();
			sum = reader.read() + reader.read() * 32_767;

			if (sum != losses + maxTurns + range + (long) score + turns) {
				throw new Exception();
			}
		} catch (Exception e) {
			losses = 0;
			maxTurns = 4;
			range = 10;
			score = 0;
			turns = 4;
		}

		while (running) {
			sum = losses + maxTurns + range + (long) score + turns;
			save(
				(char) losses,
				(char) maxTurns,
				(char) range,
				((char) ((long) score % 32_767)),
				((char) ((long) score / 32_767)),
				(char) turns,
				((char) ((long) sum % 32_767)),
				((char) ((long) sum / 32_767))
			);

			switch (menuPrompt("MainMenu", "Play", "Settings", "Score", "Quit")) {
			case 0:
				answer = (int) (Math.random() * (range - 1) + 1);

				for (leftTurns = turns; leftTurns >= 0;) {
					leftTurns--;
					guess = menuMessagePrompt("Play", "Guess the number between 1 and " + range + ", you have " + leftTurns + " chances.");

					if (guess < answer) {
						menuMessage("Too low!");
					} else if (guess > answer) {
						menuMessage("Too high!");
					} else {
						menuMessage("You got it!");
						losses = -1;
						break;
					}
				} losses = Math.min(5, losses + 1);
				if (losses <= 0) {
					score += (range + 0D) * leftTurns / turns;
				} else {
					score = Math.max(0, score - turns * losses);
					menuMessage("The answer was: " + answer);
				} menuMessage("Your score is now: " + (int) score);

				break;
			case 1:
				settings = true;

				while (settings) {
					switch (menuPrompt("Settings", "Range", "Turns", "Clear Data", "Back")) {
					case 0:
						range = Math.max(10, Math.min(50, menuMessagePrompt("Settings > Range", "Choose a number from 10 to 50")));
						float m = range;
						maxTurns = 1;
						while ((m = Math.round(m / 2F)) > 1) {
							System.out.println(m);
							maxTurns++;
						} turns = Math.min(maxTurns, turns);
						menuMessage("Range succesfully change to " + range);
						break;
					case 1:
						turns = Math.max(1, Math.min(maxTurns, menuMessagePrompt("Settings > Turns", "Choose a number from 1 to " + maxTurns)));
						menuMessage("Turns succesfully change to " + turns);
						break;
					case 2:
						if (0 == menuPrompt("Settings > ClearData", "Yes, I agree to clear my saved score")) {
							score = 0;
						} break;
					case 3:
						settings = false;
						break;
					}
				}

				break;
			case 2:
				menuMessage("Your score is currently " + score);
				break;
			case 3:
				running = false;
				break;
			}
		}
	}

	public static void line() { System.out.println(" |"); }

	public static void menuMessage(String message) { System.out.println(" |   " + message); }

	public static int menuMessagePrompt(String title, String message) {
		line(); message(title); menuMessage(message);
		System.out.print(" > ");

		try {
			return Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public static int menuPrompt(String title) {
		line(); message(title);
		System.out.print(" > ");

		try {
			return Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public static int menuPrompt(String title, String... option) {
		line(); message(title);
		for (int i = 0; i < option.length; i++) {
			menuMessage(i + " - " + option[i]);
		} System.out.print(" > ");

		try {
			return Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public static void message(String message) { System.out.println(" | " + message); }

	public static void save(char... dataStream) {
		try {
			FileWriter writer = new FileWriter("D:/save.txt");

			writer.write(dataStream);
			writer.close();
		} catch (IOException e) {}
	}
}
