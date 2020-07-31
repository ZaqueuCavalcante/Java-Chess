package board.layer;

public class Piece {

	private Position position;
	private Board board;

	public Piece() { // APAGAR
	}

	public Piece(Board board) {
		this.board = board;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	protected Board getBoard() {
		return board;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public boolean possibleMoves() {
		return true;
	}

	public boolean possibleMove(Position position) {
		return true;
	}

	public boolean isThereAnyPossibleMove() {
		return true;
	}

}
