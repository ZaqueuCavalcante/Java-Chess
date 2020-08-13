package chess.pieces;

import board.layer.Board;
import board.layer.Position;
import chess.layer.ChessPiece;
import view.layer.Color;

public class Queen extends ChessPiece {

	public Queen(Board board, Color color) {
		super(board, color);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@Override
	public String toString() {
		return "Q";
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

		while (thereNoIsAPiece(nextPositionToCheck)) {
			possibleMovesMatrix[newRow][newColumn] = true;
			newRow += rowIncrement;
			newColumn += columnIncrement;
			nextPositionToCheck.setRowAndColumn(newRow, newColumn);
		}
		if (thereIsOpponentPiece(nextPositionToCheck)) {
			possibleMovesMatrix[newRow][newColumn] = true;
		}
	}

	private boolean thereNoIsAPiece(Position nextPositionToCheck) {
		if (!this.getBoard().positionExists(nextPositionToCheck))
			return false;
		boolean thereNoIsAPiece = !this.getBoard().thereIsAPiece(nextPositionToCheck);
		return thereNoIsAPiece;
	}

	private boolean thereIsOpponentPiece(Position nextPositionToCheck) {
		if (!this.getBoard().positionExists(nextPositionToCheck))
			return false;
		boolean thereIsOpponentPiece = this.isThereOpponentPiece(nextPositionToCheck);
		return thereIsOpponentPiece;
	}
}