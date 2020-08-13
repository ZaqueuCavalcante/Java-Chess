package chess.pieces;

import board.layer.Board;
import board.layer.Position;
import chess.layer.ChessPiece;
import view.layer.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@Override
	public String toString() {
		return "P";
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@Override
	public boolean[][] possibleMoves() {
		int rows = getBoard().getRows();
		int columns = getBoard().getColumns();
		boolean[][] possibleMovesMatrix = new boolean[rows][columns];

		if (this.getColor() == Color.WHITE) {
			if (this.getMoveCount() == 0) {
				if (checkUpFront(possibleMovesMatrix, -1, 0)) {
					checkUpFront(possibleMovesMatrix, -2, 0);
				}
			}
			checkUpFront(possibleMovesMatrix, -1, 0); // Above

			checkUpDiagonal(possibleMovesMatrix, -1, -1); // Between above and left
			checkUpDiagonal(possibleMovesMatrix, -1, +1); // Between right and above
		} else {
			if (this.getMoveCount() == 0) {
				if (checkUpFront(possibleMovesMatrix, +1, 0)) {
					checkUpFront(possibleMovesMatrix, +2, 0);
				}
			}
			checkUpFront(possibleMovesMatrix, +1, 0); // Down

			checkUpDiagonal(possibleMovesMatrix, +1, -1); // Between left and down
			checkUpDiagonal(possibleMovesMatrix, +1, +1); // Between down and right
		}

		return possibleMovesMatrix;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private boolean checkUpFront(boolean[][] possibleMovesMatrix, int rowIncrement, int columnIncrement) {
		Position nextPositionToCheck = new Position(0, 0);
		int newRow = this.position.getRow() + rowIncrement;
		int newColumn = this.position.getColumn() + columnIncrement;
		nextPositionToCheck.setRowAndColumn(newRow, newColumn);

		if (conditionsToMove(nextPositionToCheck)) {
			possibleMovesMatrix[newRow][newColumn] = true;
			return true;
		}
		return false;
	}

	private boolean conditionsToMove(Position nextPositionToCheck) {
		if (!this.getBoard().positionExists(nextPositionToCheck))
			return false;
		boolean thereNoIsAPiece = !this.getBoard().thereIsAPiece(nextPositionToCheck);
		boolean isThereOpponentPiece = this.isThereOpponentPiece(nextPositionToCheck);
		return thereNoIsAPiece || isThereOpponentPiece;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private boolean checkUpDiagonal(boolean[][] possibleMovesMatrix, int rowIncrement, int columnIncrement) {
		Position nextPositionToCheck = new Position(0, 0);
		int newRow = this.position.getRow() + rowIncrement;
		int newColumn = this.position.getColumn() + columnIncrement;
		nextPositionToCheck.setRowAndColumn(newRow, newColumn);

		if (conditionsToMoveInDiagonal(nextPositionToCheck)) {
			possibleMovesMatrix[newRow][newColumn] = true;
			return true;
		}
		return false;
	}

	private boolean conditionsToMoveInDiagonal(Position nextPositionToCheck) {
		if (!this.getBoard().positionExists(nextPositionToCheck))
			return false;
		boolean isThereOpponentPiece = this.isThereOpponentPiece(nextPositionToCheck);
		return isThereOpponentPiece;
	}

}
