package board.layer;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;

	public Board(int rows, int columns) {
		checksInputsRange(rows, columns);
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Piece getPiece(int row, int column) {
		checksPositionExists(row, column);
		return pieces[row][column];
	}

	public Piece getPiece(Position position) {
		checksPositionExists(position);
		return pieces[position.getRow()][position.getColumn()];
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void placePiece(Piece piece, Position position) {
		checksOccupiedPosition(position);
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	public Piece removePiece(Position position) {
		return new Piece();
	}
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private boolean positionExists(int row, int column) {
		boolean insideRowsRange = row >= 0 && row < rows;
		boolean insideColumnsRange = column >= 0 && column < columns;
		return insideRowsRange && insideColumnsRange;
	}

	private boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}

	public boolean thereIsAPiece(Position position) {
		checksPositionExists(position);
		return getPiece(position) != null;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private void checksInputsRange(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
	}

	private void checksOccupiedPosition(Position position) {
		if (thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position" + position);
		}
	}

	private void checksPositionExists(int row, int column) {
		if (!positionExists(row, column)) {
			throw new BoardException("Position not on the board");
		}
	}

	private void checksPositionExists(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
	}

}
