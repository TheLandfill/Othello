package othello;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * CS 2001 - Othello
 * 
 * @author Joseph Mellor
 * @version JavaSE-1.8
 * @since JavaSE-1.8
 */
public class AI implements Player {
	private Color color;
	private Random r;
	/**
	 * Creates an AI with color c
	 * 
	 * @param c
	 */
	public AI(Color c) {
		color = c;
		r = new Random();
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public String getMove(GameBoard gB, Scanner playerInput, boolean AIPause) {
		if (AIPause) {
			playerInput.nextLine();
		}
		ArrayList<String> validMoves = gB.getValidMoves(getColor());
		int randMove = r.nextInt(validMoves.size());
		String move = validMoves.get(randMove);
		validMoves.removeAll(validMoves);
		return move;
	}
}