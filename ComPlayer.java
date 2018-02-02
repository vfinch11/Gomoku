import java.util.Random;
import cs251.lab2.*;
import cs251.lab2.GomokuModel.*;
public class ComPlayer {
	private Player controlledPlayer;
	private Square[][] board;
public ComPlayer(Player player, Square[][] board) {
		this.controlledPlayer = player;
		this.controlledPlayer.enableAI();
		this.board = board;
	}
public void getMove() {
		int[] move = new int[2];
		Random rand = new Random();
		int r = -1;
		int c = -1;
		do {
			r = rand.nextInt(board.length);
			c = rand.nextInt(board[0].length);
		} while (board[r][c] != Square.EMPTY);
		this.board[r][c] = this.controlledPlayer.toSquare();
		this.controlledPlayer.setMove(r, c);
	}
}