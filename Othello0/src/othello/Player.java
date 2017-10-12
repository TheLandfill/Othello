package othello;

import java.util.Scanner;

/**
 * CS 2001 - Othello
 * 
 * @author Joseph Mellor
 * @version JavaSE-1.8
 * @since JavaSE-1.8
 */
public interface Player {
	/**
	 * Gets the color of this Player
	 * @return
	 */
	public Color getColor();

	/**
	 * Gets the move
	 * @param gB
	 * @param playerInput
	 * @param AIPause
	 * @return
	 * A string move
	 */
	public String getMove(GameBoard gB, Scanner playerInput, boolean AIPause);
}