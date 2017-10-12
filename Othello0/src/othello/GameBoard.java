package othello;

import java.util.ArrayList;

/**
 * CS 2001 - Othello
 * 
 * @author Joseph Mellor
 * @version JavaSE-1.8
 * @since JavaSE-1.8
 */
public class GameBoard {
	public final static int BOARD_WIDTH = 8;
	public final static int BOARD_HEIGHT = 8;
	public final static String HEADER_STRING = "     0   1   2   3   4   5   6   7  \n";
	public final static String ROW_DIVIDER = "   ---------------------------------\n";

	public final static int[] DIRECTIONS = { -1, -1, -1, 0, -1, 1, 0, -1, 0, 1, 1, -1, 1, 0, 1, 1 };

	public final static String LITTLE_HELP_MESSAGE = "\nType \"h\" or \"H\" to display the help message.";

	private Color[][] board = new Color[BOARD_WIDTH][BOARD_HEIGHT];

	/**
	 * Builds a GameBoard filled with empty squares except for the four squares in
	 * the middle, which are set according to the rules of Othello.
	 */
	public GameBoard() {
		for (int row = 0; row < BOARD_WIDTH; row++) {
			for (int column = 0; column < BOARD_HEIGHT; column++) {
				if ((row == 3) && (column == 3) || (row == 4) && (column == 4)) {
					board[row][column] = Color.White;
				} else if ((row == 3) && (column == 4) || (row == 4) && (column == 3)) {
					board[row][column] = Color.Black;
				} else {
					board[row][column] = Color.Empty;
				}
			}
		}
	}

	/**
	 * Checks if the coordinate is on the board.
	 * 
	 * @param row
	 * @param column
	 * @return boolean
	 */
	public boolean isOnBoard(int row, int column) {
		return (row < BOARD_WIDTH) && (row >= 0) && (column < BOARD_HEIGHT) && (column >= 0);
	}

	private void setCoordinate(int row, int column, Color c) {
		board[row][column] = c;
	}

	/**
	 * Returns the color of "row,column".
	 * 
	 * @param row
	 * @param column
	 * @return Color of "row,column"
	 * @throws ArrayOutofBoundsException
	 *             Throws the exception if you put in coordinates that aren't on the
	 *             board, so please don't.
	 */
	public Color getColor(int row, int column) {
		return board[row][column];
	}

	/**
	 * Gets the score for the color.
	 * 
	 * @param c
	 *            Player color
	 * @return The score as an int.
	 */
	public int getScore(Color c) {
		int score = 0;
		for (int row = 0; row < BOARD_WIDTH; row++) {
			for (int column = 0; column < BOARD_HEIGHT; column++) {
				if ((c == getColor(row, column))) {
					score++;
				}
			}
		}
		return score;
	}

	private boolean checkDirection(int row, int column, Color c, int direction) {
		if (getColor(row, column) != Color.Empty) {
			return false;
		}
		
		row += DIRECTIONS[2 * direction];
		column += DIRECTIONS[(2 * direction) + 1];
		
		if (!isOnBoard(row, column)) {
			return false;
		}

		while (isOnBoard(row, column) && getColor(row, column) == c.getOpposite()) {
			row += DIRECTIONS[2 * direction];
			column += DIRECTIONS[(2 * direction) + 1];
			if (!isOnBoard(row, column)) {
				return false;
			}
			if (getColor(row, column) == c) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines if putting a piece of color c at "row,column" is a valid move
	 * 
	 * @param row
	 * @param column
	 * @param c
	 * @return boolean
	 */
	public boolean isValidMove(int row, int column, Color c) {
		if (!isOnBoard(row, column) || getColor(row, column) != Color.Empty) {
			return false;
		}

		for (int i = 0; i < 8; i++) {
			if (checkDirection(row, column, c, i)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Sets the piece at "row,column" to color c and flips every piece it can
	 * 
	 * @param row
	 * @param column
	 * @param c
	 */
	public void setPiece(int row, int column, Color c) {
		for (int i = 0; i < 8; i++) {
			if (checkDirection(row, column, c, i)) {
				int drow = DIRECTIONS[2 * i];
				int dcolumn = DIRECTIONS[(2 * i) + 1];

				int curRow = row + drow;
				int curCol = column + dcolumn;

				while (isOnBoard(curRow, curCol) && getColor(curRow, curCol) != c) {
					setCoordinate(curRow, curCol, c);
					curRow += drow;
					curCol += dcolumn;
				}
			}
		}
		setCoordinate(row, column, c);
	}

	/**
	 * Checks for any valid moves
	 * 
	 * @param c
	 *            The color you want to find valid moves for
	 * @return
	 */
	public boolean checkAnyValidMoves(Color c) {
		for (int row = 0; row < BOARD_HEIGHT; row++) {
			for (int column = 0; column < BOARD_WIDTH; column++) {
				if (getColor(row, column) != Color.Empty) {
					continue;
				} else if (isValidMove(row, column, c)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Finds all the valid moves on the board for a Color c
	 * 
	 * @param c
	 * @return list of Strings
	 */
	public ArrayList<String> getValidMoves(Color c) {
		ArrayList<String> validMoves = new ArrayList<String>(28);
		for (int row = 0; row < BOARD_HEIGHT; row++) {
			for (int column = 0; column < BOARD_WIDTH; column++) {
				if (isValidMove(row, column, c)) {
					validMoves.add(row + "," + column);
				}
			}
		}
		return validMoves;
	}

	private String getDisplayedPiece(int row, int column) {
		if (board[row][column] == Color.Empty) {
			return "   |";
		} else if (board[row][column] == Color.White) {
			return " W |";
		} else {
			return " B |";
		}
	}

	/**
	 * Creates the display for the game board.
	 * 
	 * @return Returns a String that represents the game board.
	 */
	public String printBoard() {
		StringBuffer printedBoard = new StringBuffer(HEADER_STRING);
		for (int row = 0; row < BOARD_HEIGHT; row++) {
			printedBoard.append(ROW_DIVIDER);
			printedBoard.append(" ");
			printedBoard.append(row);
			printedBoard.append(" |");
			for (int column = 0; column < BOARD_WIDTH; column++) {
				printedBoard.append(getDisplayedPiece(row, column));
			}
			printedBoard.append("\n");
		}
		printedBoard.append(ROW_DIVIDER);
		printedBoard.append("Black: ");
		printedBoard.append(getScore(Color.Black));
		printedBoard.append("    White: ");
		printedBoard.append(getScore(Color.White));
		printedBoard.append(LITTLE_HELP_MESSAGE);
		String finalPrintedBoard = printedBoard.toString();
		printedBoard.delete(0, printedBoard.length());
		return finalPrintedBoard;
	}
}