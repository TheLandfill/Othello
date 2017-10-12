package othello;

import java.util.Scanner;

/**
 * CS 2001 - Othello
 * 
 * @author Joseph Mellor
 * @version JavaSE-1.8
 * @since JavaSE-1.8
 */
public class Human implements Player {
	private Color color;
	
	public Human(Color c) {
		color = c;
	}

	@Override
	public String getMove(GameBoard gB, Scanner playerInput, boolean AIPAuse) {
		String input = playerInput.nextLine();
		return input;
	}

	@Override
	public Color getColor() {
		return color;
	}
}