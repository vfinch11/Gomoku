import cs251.lab2.GomokuModel.*;
public enum Player {
	CROSS(Square.CROSS, Outcome.CROSS_WINS) {
		@Override
		Player next() {
			return RING;
		}
	}, RING(Square.RING, Outcome.RING_WINS) {
		@Override
		Player next() {
			return CROSS;
		}
	};
	abstract Player next();
private int row;
	private int col;
	private final Square square;
	private final Outcome winCondition;
	private Square[][] board;
	private int numInLineForWin;
	private boolean isCom=false;
Player(Square square, Outcome winCondition) {
		this.square = square;
		this.winCondition = winCondition;
		this.row = -1;
		this.col = -1;
	}
public Square toSquare() {
		return this.square;
	}
public char toChar() {
		return this.square.toChar();
	}
public Outcome wins() {
		return this.winCondition;
	}
public void setMove(int row, int col) {
		this.row = row;
		this.col = col;
	}
public int[] getMove() {
		return new int[] {row, col};
	}
public void setBoard(Square[][] board) {
		this.board = board;
	}
public void setNumInLineForWin(int num) {
		this.numInLineForWin = num;
	}
public void enableAI() {
		this.isCom = true;
	}
public boolean getIsCom() {
		return this.isCom;
	}
public String[] getSearchLines(int radius) {
		String[] lines = new String[4];
		for(int i = 0; i<lines.length; i++) {
			lines[i] = "";
		}
		int rowMax = this.board.length - 1;
		int colMax = this.board[0].length - 1;
int startX = Math.max(this.col-radius, 0);
		int endX = Math.min(this.col+radius, colMax);
		int startY = Math.max(this.row-radius, 0);
		int endY = Math.min(this.row+radius, rowMax);
for(int y=startY; y<=endY; y++) {
			for(int x=startX; x<=endX; x++) {
				if(y == this.row) lines[0]+=board[y][x].toChar();
				if(x == this.col) lines[1]+=board[y][x].toChar();
				if(this.col-x == this.row-y) lines[2]+=board[y][x].toChar();
				if(this.col-x == -this.row+y) lines[3]+=board[y][x].toChar();
			}
		}
		return lines;
	}
public boolean checkForWin() {
		String[] searchStrings = this.getSearchLines(numInLineForWin-1);
		String winString = "";
		for(int i=0; i<this.numInLineForWin; i++) {
			winString+=this.toChar();
		}
		for(int i=0; i<searchStrings.length; i++) {
			if(searchStrings[i].contains(winString)) return true;
		}
		return false;
	}
}