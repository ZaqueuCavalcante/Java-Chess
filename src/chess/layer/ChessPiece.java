package chess.layer;

import board.layer.Board;
import board.layer.Piece;
import board.layer.Position;

public abstract class ChessPiece extends Piece {

	private Color color;
	private int moveCount;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
		this.moveCount = 0;
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

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public ChessPosition getChessPosition() {
		return new ChessPosition('z', 42);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece opponentChessPiece = (ChessPiece) getBoard().getPiece(position);
		return opponentChessPiece != null && opponentChessPiece.getColor() != this.color;
	}

}
