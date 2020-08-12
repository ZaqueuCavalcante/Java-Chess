package chess.pieces;

import board.layer.Board;
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
		return null;
	}

}
