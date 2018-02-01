import cs251.lab2.*; 


public class Gomoku implements GomokuModel {

	enum Turn {
		CROSS,RING;
	}
	
	
	
	private Turn currentTurn = Turn.CROSS;
	private Square[][] board;
	
	public static void main(String[] args) {
		Gomoku game = new Gomoku(); if(args.length > 0)
		{ game.setComputerPlayer(args[0]); } 
		game.board = new Square[game.getNumRows()][game.getNumCols()];
        game.startNewGame();
		GomokuGUI.showGUI(game);
		game.startNewGame();
	}
	

	

	@Override
	public String getBoardString() {
		String BoardString = "";
		for(int r = 0; r<getNumRows();r++) {
			for(int c = 0; c<getNumCols();c++) {
			
				BoardString += board [r][c].toChar();
				
			}
			BoardString+= "\n";
		}
		return BoardString;
	}

	@Override
	public int getNumCols() {
		// TODO Auto-generated method stub
		return DEFAULT_NUM_COLS;
	}

	@Override
	public int getNumInLineForWin() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumRows() {
		// TODO Auto-generated method stub
		return DEFAULT_NUM_ROWS;
	}

	@Override
	public Outcome handleClickAt(int row, int col) {
		if (board[row][col] == Square.EMPTY) {
			if(currentTurn == Turn.CROSS) {
                board[row][col] = Square.CROSS;
                currentTurn = Turn.RING;
            } else {
                board[row][col] = Square.RING;
                currentTurn = Turn.CROSS;
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
		for(int r = 0; r<getNumRows();r++) {
			for(int c = 0; c<getNumCols();c++) {
				board[r][c]=Square.EMPTY;
			}
		}
		
		
		
	}

}
