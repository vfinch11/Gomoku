import cs251.lab2.*;
public class Gomoku implements GomokuModel {
private Player player = Player.CROSS;
	private Square[][] board;
public  static  void  main( String [] args ) {
		Gomoku  game =   new  Gomoku ();
		if(args.length > 0) {
			game.setComputerPlayer(args [0]);
		}
		game.board = new Square[game.getNumRows()][game.getNumCols()];
		Player.CROSS.setBoard(game.board);
		Player.CROSS.setNumInLineForWin(game.getNumInLineForWin());
		Player.RING.setBoard(game.board);
		Player.RING.setNumInLineForWin(game.getNumInLineForWin());
		game.startNewGame();
		GomokuGUI.showGUI(game);
	}
@Override
	public String getBoardString() {
		String boardString = "";
		for(int r=0; r<getNumRows(); r++) {
			for(int c=0; c<getNumCols(); c++) {
				boardString += board[r][c].toChar();
			}
		boardString += "\n";
		}
		return boardString;
	}
@Override
	public int getNumCols() {
		return DEFAULT_NUM_COLS;
	}
@Override
	public int getNumInLineForWin() {
		return SQUARES_IN_LINE_FOR_WIN;
	}
@Override
	public int getNumRows() {
		return DEFAULT_NUM_ROWS;
	}
@Override
	public Outcome handleClickAt(int row, int col) {
		if (board[row][col] == Square.EMPTY) {
			player.setMove(row, col);
			board[row][col] = player.toSquare();
			if(player.checkForWin()) {
				return player.wins();
			} else {
				player = player.next();
				if(getBoardString().indexOf(Square.EMPTY.toChar()) < 0) {
					return Outcome.DRAW;
				} else {
					return Outcome.GAME_NOT_OVER;
				}
			}
}
		return Outcome.GAME_NOT_OVER;
	}
@Override
	public void setComputerPlayer(String arg0) {
		// TODO Auto-generated method stub
}
@Override
	public void startNewGame() {
		for(int r=0; r<getNumRows(); r++) {
			for(int c=0; c<getNumCols(); c++) {
				board[r][c] = Square.EMPTY;
			}
		}
		Player.CROSS.setMove(-1, -1);
		Player.RING.setMove(-1, -1);
}
}

