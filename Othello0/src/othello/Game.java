package othello;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * CS 2001 - Othello
 * 
 * @author Joseph Mellor
 * @version JavaSE-1.8
 * @since JavaSE-1.8
 */
public class Game {
	private GameBoard gameBoard;

	// I haven't slept very well this past week.
	final public static String INVALID_INPUT = "Invalid input. Please input a valid input.";

	final public static String HELP_MESSAGE_ENTRY = "-----------How to Enter Coordinates-----------\nEnter coordinates in the form \"row,column\" \nwithout spaces or quotes.\n";
	final public static String HELP_MESSAGE_PASS = "-----------------How to Pass------------------\nIf you wish to pass, hit enter without typing\nanything.\n";
	final public static String HELP_MESSAGE_QUIT = "-----------------How to Quit------------------\nIf you wish to quit, type \"x\" or \"X\".\n";
	final public static String HELP_MESSAGE_COORDINATES = "-----------How to Read Coordinates------------\nEverything after the line \"It is _____\'s turn\"\ncontains a list of all valid moves. The last\ntwo numbers are the coordinates where you can\nput your piece, and the rest are the opponent-\ncontrolled squares you will change to your\ncolor. For instance, if you see [3, 3, 3, 2],\nyour valid move is \"3,2\" and you would change\nthe squares \"3,3\" and \"3,2\" to your color.\n";
	final public static String HELP_MESSAGE_MULTIPLE_COORDINATES = "Note that if two lists contain the same valid\nmove (i.e. [3, 3, 3, 2] and [2, 2, 3, 2] both\ncontain \"3,2\"), then all the coordinates in\nboth lists will be filled.\n";
	final public static String HELP_MESSAGE_TOGGLE = "------------------See Help--------------------\nIf you want to turn this help message on/off,\ntype \"h\" or \"H\".\n";
	final public static String HELP_MESSAGE_VALID_MOVES = "-----How to See Valid Moves for This Turn-----\nIf you want to see your valid moves this turn,\ntype \"v\" or \"V\".\n";

	/**
	 * Creates new game object.
	 */
	public Game() {
		gameBoard = new GameBoard();
	}

	/**
	 * Resets the game board
	 */
	public void resetGameBoard() {
		gameBoard = new GameBoard();
	}

	private void endGame(GameBoard gB) {
		System.out.println("Game Over");
		// This if block determined the winner of the game.
		if (gB.getScore(Color.Black) > gB.getScore(Color.White)) {
			System.out.println("Black Wins!");
		} else if (gB.getScore(Color.Black) == gB.getScore(Color.White)) {
			System.out.println("Draw!");
		} else {
			System.out.println("White Wins!");
		}
		System.out.println("Final Score");
		System.out.printf("Black:%2d\n", gB.getScore(Color.Black));
		System.out.printf("White:%2d\n", gB.getScore(Color.White));
		// Asks if you would like to play again.
		System.out.println("Would you like to play again? Y/N");
	}

	private void displayHelp() {
		System.out.println(HELP_MESSAGE_ENTRY);
		System.out.println(HELP_MESSAGE_PASS);
		System.out.println(HELP_MESSAGE_QUIT);
		System.out.println(HELP_MESSAGE_COORDINATES);
		System.out.println(HELP_MESSAGE_MULTIPLE_COORDINATES);
		System.out.println(HELP_MESSAGE_TOGGLE);
		System.out.println(HELP_MESSAGE_VALID_MOVES);

	}

	private void determineTurn(int count, boolean disp) {
		if (!disp) {
			return;
		}
		switch (count % 2) {
		case 0:
			System.out.println("It is Black's turn.");
			break;
		case 1:
			System.out.println("It is White's Turn.");
			break;
		}
	}

	private void printValidMoves(GameBoard tempGameBoard, Color c) {
		ArrayList<String> moveList = tempGameBoard.getValidMoves(c);
		for (int i = 0; i < moveList.size(); i++) {
			System.out.println(moveList.get(i));
		}
	}

	private void displayGameBoard(boolean disp) {
		if (disp) {
			System.out.println(gameBoard.printBoard());
		}
	}

	/**
	 * Plays the game Othello.
	 * 
	 */
	public void playGame() {
		Scanner input = new Scanner(System.in);
		boolean simulation = false;
		boolean pauseBeforeAIMove = true;
		boolean displayGameBoard = true;
		boolean mainMenuExit = false;
		ArrayList<Integer> listOfSpread = new ArrayList<Integer>();
		int numIterations = 1;
		int count = 0;
		Player[] playersC = { new Human(Color.Black), new Human(Color.White) };
		long startTime = 0;
		
		while (true) {
			if (!simulation) {
				String mode = "";
				while (mode.isEmpty() || (!mode.equals("1") && !mode.equals("2") && !mode.equals("3") && !mode.equals("4"))) {
					System.out.println("Othello\n1. Single Player\n2. Two Player\n3. Simulation\n4. Quit\n[1, 2, 3, 4]?");
					mode = input.nextLine();
				}
				switch (mode) {
				case "1":
					playersC[0] = new Human(Color.Black);
					playersC[1] = new AI(Color.White);
					break;
				case "2":
					break;
				case "3":
					playersC[0] = new AI(Color.Black);
					playersC[1] = new AI(Color.White);
					simulation = true;
					pauseBeforeAIMove = false;
					displayGameBoard = false;
					while (true) {
						System.out.println("How many iterations?");
						String strIterations = input.nextLine();

						if (!strIterations.isEmpty()) {
							try {
								numIterations = Integer.parseInt(strIterations);
								if (numIterations > 0) {
									break;
								}
							} catch (NumberFormatException e) {
								System.out.println(INVALID_INPUT);
							}
						}
					}
					startTime = System.nanoTime();
					break;
				case "4":
					mainMenuExit = true;
					break;
				}
			}
			if (mainMenuExit) {
				break;
			}
			displayGameBoard(displayGameBoard);

			boolean validMovesLastPlayer = true;
			for (int i = 0, maxTurns = 60; i < maxTurns; i++) {
				boolean validMovesCurrPlayer = gameBoard.checkAnyValidMoves(playersC[i % 2].getColor());

				/*
				 * This next if block determines if the game should end because neither player
				 * has a valid move.
				 */
				if ((!validMovesCurrPlayer) && (validMovesLastPlayer)) {
					if (!simulation) {
						switch (i % 2) {
						case 0:
							System.out.println("Black has no valid moves and was forced to\npass.");
							break;
						case 1:
							System.out.println("White has no valid moves and was forced to\npass.");
							break;
						}
					}
					validMovesLastPlayer = false;
					maxTurns++;
					continue;
				} else if ((!validMovesCurrPlayer) && (!validMovesLastPlayer)) {
					// Ends the game if neither player has a valid move
					break;
				} else {
					// Resets valid moves to be true if the current player has a valid move
					validMovesLastPlayer = true;
				}

				determineTurn(i, displayGameBoard);

				// Takes input
				String coordinates = playersC[i % 2].getMove(gameBoard, input, pauseBeforeAIMove);

				// Player hits enter and passes
				if (coordinates.isEmpty()) {
					maxTurns++;
					displayGameBoard(displayGameBoard);
					continue;
				}

				if (coordinates.equalsIgnoreCase("x")) {
					break;
				}

				if (coordinates.equalsIgnoreCase("h")) {
					displayHelp();
					displayGameBoard(displayGameBoard);
					i--;
					continue;
				}

				if (coordinates.equalsIgnoreCase("v")) {
					printValidMoves(gameBoard, playersC[i % 2].getColor());
					displayGameBoard(displayGameBoard);
					i--;
					continue;
				}

				String[] splitCoordinates = coordinates.split(",");
				/*
				 * This if statement makes sure that there are two elements in the array
				 * splitCoordinates. "1,2", "alph,6", "10,100", "a,b", and "1, 2" will pass this
				 * test. "1", "1,2,3", "a", and "7" will not.
				 */
				if (splitCoordinates.length != 2) {
					/*
					 * game is printed before the message so that the message appears at the bottom
					 * of the game board because it's easier to see.
					 */
					displayGameBoard(displayGameBoard);
					System.out.println(INVALID_INPUT);
					i--;
					continue;
				}
				/*
				 * This try block is to make sure that Integer.parseInt doesn't fail if the user
				 * inputs an invalid string with one comma. "1,2" and "10,100" will pass. "a,b",
				 * "alph,7", and "1, 2" will not.
				 */
				try {
					int row = Integer.parseInt(splitCoordinates[0]);
					int column = Integer.parseInt(splitCoordinates[1]);

					if (gameBoard.isValidMove(row, column, playersC[i % 2].getColor())) {
						gameBoard.setPiece(row, column, playersC[i % 2].getColor());
						displayGameBoard(displayGameBoard);
					} else {
						displayGameBoard(displayGameBoard);
						System.out.println(INVALID_INPUT);
						i--;
					}
				} catch (NumberFormatException e) {
					displayGameBoard(displayGameBoard);
					System.out.println(INVALID_INPUT);
					i--;
					continue;
				} // end try-catch
			} // end for

			boolean running = true;
			if (!simulation) {
				endGame(gameBoard);
				String response;
				// Determines whether or not to continue or break the loop
				while (running) {
					response = input.nextLine();
					if (response.isEmpty()) {
						continue;
					} else if (response.equalsIgnoreCase("y")) {
						break;
					} else if (response.equalsIgnoreCase("n")) {
						running = false;
						break;
					}
				}
			} else {
				listOfSpread.add(gameBoard.getScore(Color.Black) - gameBoard.getScore(Color.White));
				count++;
			}

			// Uses running to determine whether to start a new game.
			if (running && count < numIterations) {
				resetGameBoard();
				continue;
			} else if (running && count == numIterations) {
				int[] indexedSpread = new int[129];
				int average = 0;
				for (int i = 0; i < listOfSpread.size(); i++) {
					indexedSpread[listOfSpread.get(i) + 64]++;
				}
				
				for (int i = -64; i <= 64; i++) {
					System.out.println(i+ "," +indexedSpread[i+64]);
					average += i*indexedSpread[i+64];
				}
				
				System.out.println((double)average/listOfSpread.size());
				resetGameBoard();
				simulation = false;
				count = 0;
				System.out.println((double)(System.nanoTime()-startTime)/1000000000);
				continue;
			} else {
				break;
			}
		}
		input.close();
	}
}