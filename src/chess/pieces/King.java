package chess.pieces;

import board.layer.Board;
import board.layer.Piece;
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

		if (this.conditionsToRookCastling()) {
			int kingRow = this.position.getRow();
			int kingColumn = this.position.getColumn();

			// Special move: castling King side rook
			Position kingRookPosition = new Position(kingRow, kingColumn + 3);
			if (testRookCastling(kingRookPosition)) {
				Piece kingRightPiece1 = this.getBoard().getPiece(kingRow, kingColumn + 1);
				Piece kingRightPiece2 = this.getBoard().getPiece(kingRow, kingColumn + 2);
				if (kingRightPiece1 == null && kingRightPiece2 == null) {
					possibleMovesMatrix[kingRow][kingColumn + 2] = true;
				}
			}
			// Special move: castling Queen side rook
			Position queenRookPosition = new Position(kingRow, kingColumn - 4);
			if (testRookCastling(queenRookPosition)) {
				Piece kingLeftPiece1 = this.getBoard().getPiece(kingRow, kingColumn - 1);
				Piece kingLeftPiece2 = this.getBoard().getPiece(kingRow, kingColumn - 2);
				Piece kingLeftPiece3 = this.getBoard().getPiece(kingRow, kingColumn - 3);
				if (kingLeftPiece1 == null && kingLeftPiece2 == null && kingLeftPiece3 == null)
					possibleMovesMatrix[kingRow][kingColumn - 2] = true;
			}
		}

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
		if (!this.getBoard().positionExists(nextPositionToCheck))
			return false;
		boolean thereNoIsAPiece = !this.getBoard().thereIsAPiece(nextPositionToCheck);
		boolean isThereOpponentPiece = this.isThereOpponentPiece(nextPositionToCheck);
		return thereNoIsAPiece || isThereOpponentPiece;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private boolean testRookCastling(Position rookPosition) {
		ChessPiece rook = (ChessPiece) getBoard().getPiece(rookPosition);
		if (rook != null) {
			boolean classMatch = rook instanceof Rook;
			boolean colorMatch = rook.getColor() == this.getColor();
			boolean hasZeroMoves = rook.getMoveCount() == 0;
			return classMatch && colorMatch && hasZeroMoves;
		}
		return false;
	}

	private boolean conditionsToRookCastling() {
		boolean hasZeroMoves = this.getMoveCount() == 0;
		boolean isNotInCheck = !this.chessMatch.getIsInCheck();
		return hasZeroMoves && isNotInCheck;
	}

}
