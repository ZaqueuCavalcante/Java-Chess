package chess.pieces;

import board.layer.Board;
import board.layer.Position;
import chess.layer.ChessMatch;
import chess.layer.ChessPiece;
import view.layer.Color;

public class Pawn extends ChessPiece {

	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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

			// Special move: En passant
			if (position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
						&& getBoard().getPiece(left) == chessMatch.getEnPassantVulnerable()) {
					possibleMovesMatrix[left.getRow() - 1][left.getColumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
						&& getBoard().getPiece(right) == chessMatch.getEnPassantVulnerable()) {
					possibleMovesMatrix[right.getRow() - 1][right.getColumn()] = true;
				}
			}
		} else {
			if (this.getMoveCount() == 0) {
				if (checkUpFront(possibleMovesMatrix, +1, 0)) {
					checkUpFront(possibleMovesMatrix, +2, 0);
				}
			}
			checkUpFront(possibleMovesMatrix, +1, 0); // Down

			checkUpDiagonal(possibleMovesMatrix, +1, -1); // Between left and down
			checkUpDiagonal(possibleMovesMatrix, +1, +1); // Between down and right

			// Special move: En passant
			if (position.getRow() == 4) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
						&& getBoard().getPiece(left) == chessMatch.getEnPassantVulnerable()) {
					possibleMovesMatrix[left.getRow() + 1][left.getColumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
						&& getBoard().getPiece(right) == chessMatch.getEnPassantVulnerable()) {
					possibleMovesMatrix[right.getRow() + 1][right.getColumn()] = true;
				}
			}
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
