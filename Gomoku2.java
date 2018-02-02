import cs251.lab2.*;



public class Gomoku implements GomokuModel {

	

	enum Turn {

		CROSS {

			@Override

			Square toSquare() {

				return Square.CROSS;

			}



			@Override

			char toChar() {

				return Square.CROSS.toChar();

			}



			@Override

			Turn getNextTurn() {

				return RING;

			}



			@Override

			Outcome getWinState() {

				return Outcome.CROSS_WINS;

			}

		}, RING {

			@Override

			Square toSquare() {

				return Square.RING;

			}



			@Override

			char toChar() {

				return Square.RING.toChar();

			}



			@Override

			Turn getNextTurn() {

				return CROSS;

			}



			@Override

			Outcome getWinState() {

				return Outcome.RING_WINS;

			}

		};

		

		abstract Square toSquare();

		abstract char toChar();

		abstract Turn getNextTurn();

		abstract Outcome getWinState();

	}

	

	private Turn currentTurn = Turn.CROSS;

	private Square[][] board;

	

	public  static  void  main( String [] args ) {

		Gomoku  game =   new  Gomoku ();

		if(args.length  > 0) {

			game.setComputerPlayer(args [0]);

		}

		game.board = new Square[game.getNumRows()][game.getNumCols()];

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

			board[row][col] = currentTurn.toSquare();

			return checkMove(row, col);

			

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



	}

	

	private Outcome checkMove(int row, int col) {

		if (getNumInLineForWin() > 1) {

			int searchStartX = Math.max(col-getNumInLineForWin()+1, 0);

			int searchEndX = Math.min(col+getNumInLineForWin()-1, getNumCols()-1);

			int searchStartY = Math.max(row-getNumInLineForWin()+1, 0);

			int searchEndY = Math.min(row+getNumInLineForWin()-1, getNumRows()-1);

			

			String lineHorizontal = "";

			String lineVertical = "";

			String lineDiagonalDown = "";

			String lineDiagonalUp = "";

			

			for(int y = searchStartY; y <= searchEndY; y++) {

				for(int x = searchStartX; x <= searchEndX; x++) {

					if(y == row) lineHorizontal += board[y][x].toChar();

					if(x == col) lineVertical += board[y][x].toChar();

					if(row-y == col-x) lineDiagonalDown += board[y][x].toChar();

					if(row-y == -col+x) lineDiagonalUp += board[y][x].toChar();

				}

			}

			

			String winString = "";

			for(int i=0; i<getNumInLineForWin(); i++) {

				winString += currentTurn.toChar();

			}

			

			if(lineHorizontal.contains(winString)) return currentTurn.getWinState();

			if(lineVertical.contains(winString)) return currentTurn.getWinState();

			if(lineDiagonalDown.contains(winString))  return currentTurn.getWinState();

			if(lineDiagonalUp.contains(winString)) return currentTurn.getWinState();

			currentTurn = currentTurn.getNextTurn();

			if(getBoardString().indexOf(Square.EMPTY.toChar()) < 0) return Outcome.DRAW;

			return Outcome.GAME_NOT_OVER;

		}

		return Outcome.GAME_NOT_OVER;

	}



}