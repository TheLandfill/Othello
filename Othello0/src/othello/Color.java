package othello;

/**
 * CS 2001 - Othello
 * 
 * @author Joseph Mellor
 * @version JavaSE-1.8
 * @since JavaSE-1.8
 */
public enum Color {
	White, Black, Empty;
	/**
	 * 
	 * @return The opponent's color or empty if this is empty
	 */
	public Color getOpposite() {
		switch (this) {
		case Black:
			return White;
		case White:
			return Black;
		default:
			return Empty;
		}
	}
}