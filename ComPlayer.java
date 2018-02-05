import java.util.Arrays;

import java.util.Random;

import cs251.lab2.GomokuModel.*;

/**
 * 
 * This class is used to control the computer player. Its strategies are fairly
 * 
 * simplistic and easily countered, but it at least makes an effort to win.
 * 
 * 
 * 
 * @author arleydecker
 *
 * 
 * 
 */

public class ComPlayer {

	private final int[] BLANK_MOVE = new int[] { -1, -1 };

	private Player controlledPlayer;

	private Player opponent;

	private Square[][] board;

	private int numToWin;

	private MoveDirection direction = MoveDirection.NORTH;

	private int[] lineMove = BLANK_MOVE;

	/**
	 * 
	 * Gets references to the player this AI has been put in control of, the play
	 * 
	 * area, and the number of squares in a row required to win. Also sets the
	 * 
	 * controlled player as an AI and checks who the opponent is.
	 * 
	 * 
	 * 
	 * @param player
	 * 
	 * @param board
	 * 
	 * @param numToWin
	 * 
	 */

	public ComPlayer(Player player, Square[][] board, int numToWin) {

		this.controlledPlayer = player;

		this.controlledPlayer.enableAI();

		this.opponent = player.next();

		this.board = board;

		this.numToWin = numToWin;

	}

	/**
	 * 
	 * First checks if the player has made what it considers a "threatening move",
	 * 
	 * two short of the win condition, and attempts to block it. If there is no
	 * 
	 * threat, it will attempt to build its own chain. If it is blocked or reaches
	 * 
	 * the edge of the board, it will find a random empty square on the board and
	 * 
	 * choose a random direction to build in.
	 * 
	 */

	public void getMove() {

		int[] move;

		int[] blockMove = tryBlock();

		int[] buildMove = tryLine();

		if (!Arrays.equals(blockMove, BLANK_MOVE)) {

			move = blockMove;

		} else if (!Arrays.equals(buildMove, BLANK_MOVE)) {

			move = buildMove;

			lineMove = move;

		} else {

			move = randomMove();

			lineMove = move;

		}

		this.board[move[0]][move[1]] = this.controlledPlayer.toSquare();

		this.controlledPlayer.setMove(move);

	}

	/**
	 * 
	 * Randomly selects an empty space on the board. Also sets the direction to a
	 * 
	 * random value.
	 * 
	 * 
	 * 
	 * @return move in format {row, col}
	 * 
	 */

	private int[] randomMove() {

		int[] move = new int[2];

		Random rand = new Random();

		direction = MoveDirection.values()[rand.nextInt(MoveDirection.values().length)];

		int r = -1;

		int c = -1;

		do {

			r = rand.nextInt(board.length);

			c = rand.nextInt(board[0].length);

		} while (board[r][c] != Square.EMPTY);

		move[0] = r;

		move[1] = c;

		return move;

	}

	/**
	 * 
	 * Searches around the opponent's move using {@link Player#getSearchLines(int)}
	 * 
	 * and determines if the lines contain "XXX" or "OOO" (the length is always 2
	 * 
	 * less than {@link Gomoku#getNumInLineForWin()}) next to an empty space. This
	 * 
	 * is considered a "threatening" move and the computer will place a piece to
	 * 
	 * block it.
	 * 
	 * 
	 * 
	 * @return The blocking move in the format {row, col}. If no blocking move was
	 * 
	 *         found, returns {-1, -1}
	 * 
	 */

	private int[] tryBlock() {

		int[] move;

		int[] opponentLoc = opponent.getMove();

		String threatString = "";

		for (int i = 0; i < numToWin - 2; i++) {

			threatString += opponent.toChar();

		}

		String threatString1 = threatString + Square.EMPTY.toChar();

		String threatString2 = Square.EMPTY.toChar() + threatString;

		String[] opponentSearch = opponent.getSearchLines(numToWin - 1);

		if (opponentSearch[0].contains(threatString1)) {

			int loc = opponentSearch[0].indexOf(threatString1) - 1;

			move = opponentLoc;

			move[1] += loc;

		} else if (opponentSearch[0].contains(threatString2)) {

			int loc = opponentSearch[0].indexOf(threatString2) - numToWin + 1;

			move = opponentLoc;

			move[1] += loc;

		} else if (opponentSearch[1].contains(threatString1)) {

			int loc = opponentSearch[1].indexOf(threatString1) - 1;

			move = opponentLoc;

			move[0] += loc;

		} else if (opponentSearch[1].contains(threatString2)) {

			int loc = opponentSearch[1].indexOf(threatString2) - numToWin + 1;

			move = opponentLoc;

			move[0] += loc;

		} else if (opponentSearch[2].contains(threatString1)) {

			int loc = opponentSearch[2].indexOf(threatString1) - 1;

			move = opponentLoc;

			move[0] += loc;

			move[1] += loc;

		} else if (opponentSearch[2].contains(threatString2)) {

			int loc = opponentSearch[2].indexOf(threatString2) - numToWin + 1;

			move = opponentLoc;

			move[0] += loc;

			move[1] += loc;

		} else if (opponentSearch[3].contains(threatString1)) {

			int loc = opponentSearch[3].indexOf(threatString1) - 1;

			move = opponentLoc;

			move[0] += loc;

			move[1] -= loc;

		} else if (opponentSearch[3].contains(threatString2)) {

			int loc = opponentSearch[3].indexOf(threatString2) - numToWin + 1;

			move = opponentLoc;

			move[0] += loc;

			move[1] -= loc;

		} else {

			move = BLANK_MOVE;

		}

		return move;

	}

	/**
	 * 
	 * Will check if the next space on the board in the current line is empty. If
	 * 
	 * so, it will return that space as a two-member array. Otherwise it will return
	 * 
	 * {-1, -1}
	 * 
	 * 
	 * 
	 * @return move in format {row, col} if next space empty, {-1, -1} otherwise
	 * 
	 */

	private int[] tryLine() {

		int[] move;

		int[] checkMove = new int[] { lineMove[0] + direction.yOffset, lineMove[1] + direction.xOffset };

		try {

			if (Arrays.equals(lineMove, BLANK_MOVE)) {

				move = BLANK_MOVE;

			} else if (board[checkMove[0]][checkMove[1]] == Square.EMPTY) {

				move = checkMove;

			} else {

				move = BLANK_MOVE;

			}

		} catch (ArrayIndexOutOfBoundsException e) {

			move = BLANK_MOVE;

		}

		return move;

	}

}