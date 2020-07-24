package chess.pieces;

import board.layer.Board;
import chess.layer.ChessPiece;
import chess.layer.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "R";
	}

}
