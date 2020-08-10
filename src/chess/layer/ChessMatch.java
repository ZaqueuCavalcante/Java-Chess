package chess.layer;

import board.layer.Board;
import board.layer.Piece;
import board.layer.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Rook;

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
		placePiece(new Rook(board, Color.BLACK), 'b', 3);
		placePiece(new King(board, Color.BLACK), 'd', 5);
		placePiece(new Bishop(board, Color.BLACK), 'f', 7);
	}
	
	private void placePiece(ChessPiece chessPiece, char column, int row) {
		Position position = new ChessPosition(column, row).toPosition();
		board.placePiece(chessPiece, position);
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
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		return (ChessPiece) capturedPiece;
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position.");
		}
		if (!board.getPiece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece.");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		Piece sourcePiece = board.getPiece(source);
		boolean notPossibleMove = !sourcePiece.possibleMove(target);
		if (notPossibleMove) {
			throw new ChessException("The chosen piece can't move to target position.");
		}
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece currentPlayerPiece = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(currentPlayerPiece, target);
		return capturedPiece;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
//	public ChessPiece replacePrmotedPiece(String type) {
//		return new ChessPiece(board, null);
//	}

}
