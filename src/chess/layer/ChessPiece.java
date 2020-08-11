package chess.layer;

import board.layer.Board;
import board.layer.Piece;
import board.layer.Position;
import view.layer.Color;

public abstract class ChessPiece extends Piece {

	private Color color;
	private int moveCount;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Color getColor() {
		return color;
	}

	public int getMoveCount() {
		return moveCount;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void increaseMoveCount() {
		moveCount++;
	}
	
	public void decreaseMoveCount() {
		moveCount--;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece opponentPiece = (ChessPiece) getBoard().getPiece(position);
		boolean isNotNull = opponentPiece != null;
		boolean isNotSameColor = opponentPiece.getColor() != this.color;
		return isNotNull && isNotSameColor;
	}

}
