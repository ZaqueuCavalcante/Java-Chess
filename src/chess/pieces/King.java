package chess.pieces;

import board.layer.Board;
import board.layer.Position;
import chess.layer.ChessMatch;
import chess.layer.ChessPiece;
import view.layer.Color;

public class King extends ChessPiece {
	
	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@Override
	public String toString() {
		return "K";
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@Override
	public boolean[][] possibleMoves() {
		int rows = getBoard().getRows();
		int columns = getBoard().getColumns();
		boolean[][] possibleMovesMatrix = new boolean[rows][columns];

		checksInDirection(possibleMovesMatrix, -1, 0); // Above
		checksInDirection(possibleMovesMatrix, 0, -1); // Left
		checksInDirection(possibleMovesMatrix, +1, 0); // Down
		checksInDirection(possibleMovesMatrix, 0, +1); // Right
		
		checksInDirection(possibleMovesMatrix, -1, -1); // Between above and left
		checksInDirection(possibleMovesMatrix, +1, -1); // Between left and down
		checksInDirection(possibleMovesMatrix, +1, +1); // Between down and right
		checksInDirection(possibleMovesMatrix, -1, +1); // Between right and above

		return possibleMovesMatrix;
	}

	private void checksInDirection(boolean[][] possibleMovesMatrix, int rowIncrement, int columnIncrement) {
		Position nextPositionToCheck = new Position(0, 0);
		int newRow = this.position.getRow() + rowIncrement;
		int newColumn = this.position.getColumn() + columnIncrement;
		nextPositionToCheck.setRowAndColumn(newRow, newColumn);

		if (conditionsToMove(nextPositionToCheck)) {
			possibleMovesMatrix[newRow][newColumn] = true;
		}
	}

	private boolean conditionsToMove(Position nextPositionToCheck) {
		if (!this.getBoard().positionExists(nextPositionToCheck)) return false;
		boolean thereNoIsAPiece = !this.getBoard().thereIsAPiece(nextPositionToCheck);
		boolean isThereOpponentPiece = this.isThereOpponentPiece(nextPositionToCheck);
		return thereNoIsAPiece || isThereOpponentPiece;
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private boolean testRookCastling(Position rookPosition) {
		ChessPiece rook = (ChessPiece) getBoard().getPiece(rookPosition);
		boolean notNull = rook != null;
		boolean classMatch = rook instanceof Rook;
		boolean colorMatch = rook.getColor() == this.getColor();
		boolean hasZeroMoves = rook.getMoveCount() == 0;
		return notNull && classMatch && colorMatch && hasZeroMoves;
		
	}

}
