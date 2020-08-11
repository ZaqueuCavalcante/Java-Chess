package board.layer;

public abstract class Piece {

	protected Position position;
	private Board board;

	public Piece(Board board) {
		this.board = board;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void setPosition(Position position) {
		this.position = position;
	}
	
	protected Board getBoard() {
		return board;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public abstract boolean[][] possibleMoves();

	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}

	public boolean isThereAnyPossibleMove() {
		boolean[][] possibleMovesMatrix = possibleMoves();
		int size = possibleMovesMatrix.length;
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				if (possibleMovesMatrix[row][column]) {
					return true;
				}
			}
		}
		return false;
	}

}
