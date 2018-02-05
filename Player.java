import cs251.lab2.GomokuModel.*;

/**
 * 
 * <h1>Player</h1> Player is an enum containing only two members, CROSS and
 * 
 * RING. It primarily consists of helper functions for handling movement on the
 * 
 * board for each player and the flow of turns;
 * 
 * 
 * 
 * @author Vlad Fincher
 *
 * 
 * 
 */

public enum Player {

	CROSS(Square.CROSS, Outcome.CROSS_WINS) {

		@Override

		Player next() {

			return RING;

		}

	},

	RING(Square.RING, Outcome.RING_WINS) {

		@Override

		Player next() {

			return CROSS;

		}

	};

	/**
	 * 
	 * This function should return the next player in the turn order. Since there
	 * 
	 * are only two players, it just flip-flops between them.
	 * 
	 * 
	 * 
	 * @return next Player in turn order
	 * 
	 */

	abstract Player next();

	private int row;

	private int col;

	private final Square square;

	private final Outcome winCondition;

	private Square[][] board;

	private int numInLineForWin;

	private boolean isCom = false;

	Player(Square square, Outcome winCondition) {

		this.square = square;

		this.winCondition = winCondition;

		this.row = -1;

		this.col = -1;

	}

	/**
	 * 
	 * Returns the {@link cs251.lab2.GomokuModel.Square} associated with this
	 * 
	 * player. Player.CROSS returns Square.CROSS and player.RING returns
	 * 
	 * Square.RING.
	 * 
	 * 
	 * 
	 * @return Square associated with this player.
	 * 
	 * @see cs251.lab2.GomokuModel.Square
	 * 
	 */

	public Square toSquare() {

		return this.square;

	}

	/**
	 * 
	 * Returns a character representation of this Player for placement on the board.
	 * 
	 * Character is borrowed from the {@link cs251.lab2.GomokuModel.Square}
	 * 
	 * associated with this player.
	 * 
	 * 
	 * 
	 * @return 'X' or 'O'
	 * 
	 * @see cs251.lab2.GomokuModel.Square#toChar()
	 * 
	 */

	public char toChar() {

		return this.square.toChar();

	}

	/**
	 * 
	 * Returns the win outcome associated with the current player.
	 * 
	 * 
	 * 
	 * @return Outcome.CROSS_WINS or Outcome.RING_WINS
	 * 
	 * @see cs251.lab2.GomokuModel.Outcome
	 * 
	 */

	public Outcome wins() {

		return this.winCondition;

	}

	/**
	 * 
	 * Sets this player's most recent move to the specified row and column on the
	 * 
	 * board.
	 * 
	 * 
	 * 
	 * @param row
	 * 
	 *            Row of move
	 * 
	 * @param col
	 * 
	 *            Column of move
	 * 
	 */

	public void setMove(int row, int col) {

		this.row = row;

		this.col = col;

	}

	/**
	 * 
	 * Sets this player's most recent move to the first two values in the array in
	 * 
	 * the format {row, column}.
	 * 
	 * 
	 * 
	 * @param move
	 * 
	 *            2 member array containing {row, col}
	 * 
	 */

	public void setMove(int[] move) {

		this.row = move[0];

		this.col = move[1];

	}

	/**
	 * 
	 * Get's this player's most recent move as a 2-member array in the format {row,
	 * 
	 * column}.
	 * 
	 * 
	 * 
	 * @return Most recent move as array containing {row, col}
	 * 
	 */

	public int[] getMove() {

		return new int[] { row, col };

	}

	/**
	 * 
	 * Gets a reference to the game board, a two-dimensional array of
	 * 
	 * {@link cs251.lab2.GomokuModel.Square}s, from the main class. Should only be
	 * 
	 * called during initialization.
	 * 
	 * 
	 * 
	 * @param board
	 * 
	 *            2D array of Squares
	 * 
	 */

	public void setBoard(Square[][] board) {

		this.board = board;

	}

	/**
	 * 
	 * Gets the number of squares in a row required to win from the main class.
	 * 
	 * Should only be called during initialization;
	 * 
	 * 
	 * 
	 * @param num
	 * 
	 */

	public void setNumInLineForWin(int num) {

		this.numInLineForWin = num;

	}

	/**
	 * 
	 * Converts this player into a computer player.
	 * 
	 */

	public void enableAI() {

		this.isCom = true;

	}

	/**
	 * 
	 * Returns true if AI has been enabled for this player.
	 * 
	 * 
	 * 
	 * @return Whether this player is human(false) or a computer(true)
	 * 
	 */

	public boolean getIsCom() {

		return this.isCom;

	}

	/**
	 * 
	 * Returns 4 strings representing all the squares in a straight line from this
	 * 
	 * players most recent move up to the specified number of squares away. The four
	 * 
	 * strings are in the following order and format:<br>
	 * 
	 * <u>Horizontal:</u>
	 * 
	 * <pre>
	
	 * <code>---
	
	 * 012
	
	 * ---</code>
	 * 
	 * </pre>
	 * 
	 * <u>Vertical:</u>
	 * 
	 * <pre>
	
	 * <code>-0-
	
	 * -1-
	
	 * -2-</code>
	 * 
	 * </pre>
	 * 
	 * <u>Diagonal Down:</u>
	 * 
	 * <pre>
	
	 * <code>0--
	
	 * -1-
	
	 * --2</code>
	 * 
	 * </pre>
	 * 
	 * <u>Diagonal Up:</u>
	 * 
	 * <pre>
	
	 * <code>--0
	
	 * -1-
	
	 * 2--</code>
	 * 
	 * </pre>
	 * 
	 * 
	 * 
	 * @param radius
	 * 
	 *            Number of squares away from move to search.
	 * 
	 * @return
	 * 
	 */

	public String[] getSearchLines(int radius) {

		String[] lines = new String[4];

		for (int i = 0; i < lines.length; i++) {

			lines[i] = "";

		}

		int rowMax = this.board.length - 1;

		int colMax = this.board[0].length - 1;

		int startX = Math.max(this.col - radius, 0);

		int endX = Math.min(this.col + radius, colMax);

		int startY = Math.max(this.row - radius, 0);

		int endY = Math.min(this.row + radius, rowMax);

		for (int y = startY; y <= endY; y++) {

			for (int x = startX; x <= endX; x++) {

				if (y == this.row)

					lines[0] += board[y][x].toChar();

				if (x == this.col)

					lines[1] += board[y][x].toChar();

				if (this.col - x == this.row - y)

					lines[2] += board[y][x].toChar();

				if (this.col - x == -this.row + y)

					lines[3] += board[y][x].toChar();

			}

		}

		return lines;

	}

	/**
	 * 
	 * Generates a string in the form "XXX" or "OOO" depending on which player is
	 * 
	 * being referenced. The length of the string is equal to the output of
	 * 
	 * {@link Gomoku#getNumInLineForWin()}. This string is then compared against the
	 * 
	 * output of {@link #getSearchLines(int)}. If any of the search strings contain
	 * 
	 * the winning string, it is considered a victory and the function returns true.
	 * 
	 * Otherwise false.
	 * 
	 * 
	 * 
	 * @return True if win condition met, false if not
	 * 
	 */

	public boolean checkForWin() {

		String[] searchStrings = this.getSearchLines(numInLineForWin - 1);

		String winString = "";

		for (int i = 0; i < this.numInLineForWin; i++) {

			winString += this.toChar();

		}

		for (int i = 0; i < searchStrings.length; i++) {

			if (searchStrings[i].contains(winString))

				return true;

		}

		return false;

	}

}