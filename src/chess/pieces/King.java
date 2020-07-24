package chess.pieces;

import board.layer.Board;
import chess.layer.ChessPiece;
import chess.layer.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "K";
	}

}
