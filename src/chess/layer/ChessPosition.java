package chess.layer;

public class ChessPosition {

	private char column;
	private int row;

	public ChessPosition(char column, int row) {
		checksInputsRange(column, row);
		this.column = column;
		this.row = row;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private void checksInputsRange(char column, int row) {
		boolean outColumnRange = column < 'a' || column > 'h';
		boolean outRowRange = row < 1 || row > 8;
		if (outColumnRange || outRowRange) {
			throw new ChessException("Error instantiating ChessPosition: Valid values are from a1 to h8.");
		}
	}
	
}
