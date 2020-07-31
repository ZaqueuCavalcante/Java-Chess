package chess.layer;

import board.layer.Board;

import board.layer.Position;
import chess.pieces.Rook;
import chess.pieces.Bishop;
import chess.pieces.King;

public class ChessMatch {

//	private int8 turn;
//	private Color currentPlayer;
//	private boolean isInCheck;
//	private boolean isInCheckMate;
//	private ChessPiece enPassantVulnerable;
//	private ChessPiece promoted;
	
	private Board board;

	public ChessMatch() {
		board = new Board(8, 8);
		initializeMatch();
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private void initializeMatch() {
		board.placePiece(new Rook(board, Color.BLACK), new Position(2, 3));
		board.placePiece(new King(board, Color.BLACK), new Position(4, 5));
		board.placePiece(new Bishop(board, Color.BLACK), new Position(6, 6));
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public ChessPiece[][] getPieces() {
		ChessPiece[][] chessPieceMatrix = new ChessPiece[board.getRows()][board.getColumns()];
		for (int row = 0; row < board.getRows(); row++) {
			for (int column = 0; column < board.getColumns(); column++) {
				chessPieceMatrix[row][column] = (ChessPiece) board.getPiece(row, column);
			}
		}
		return chessPieceMatrix;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public boolean possibleMoves(ChessPosition sourcePosition) {
		return true;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public ChessPosition performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		return new ChessPosition('z', 42);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public ChessPiece replacePrmotedPiece(String type) {
		return new ChessPiece(board, null);
	}

}
