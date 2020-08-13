package chess.pieces;

import board.layer.Board;
import board.layer.Position;
import chess.layer.ChessPiece;
import view.layer.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@Override
	public String toString() {
		return "N";
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@Override
	public boolean[][] possibleMoves() {
		int rows = getBoard().getRows();
		int columns = getBoard().getColumns();
		boolean[][] possibleMovesMatrix = new boolean[rows][columns];
		
		checksInDirection(possibleMovesMatrix, -2, +1);
		checksInDirection(possibleMovesMatrix, -2, -1);
		
		checksInDirection(possibleMovesMatrix, +1, -2);
		checksInDirection(possibleMovesMatrix, -1, -2);
		
		checksInDirection(possibleMovesMatrix, +2, +1);
		checksInDirection(possibleMovesMatrix, +2, -1);
		
		checksInDirection(possibleMovesMatrix, +1, +2);
		checksInDirection(possibleMovesMatrix, -1, +2);

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

}
