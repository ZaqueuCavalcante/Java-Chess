package chess.layer;

import java.util.ArrayList;
import java.util.List;

import board.layer.Board;
import board.layer.Piece;
import board.layer.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Rook;
import view.layer.Color;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
//	private boolean isInCheck;
//	private boolean isInCheckMate;
//	private ChessPiece enPassantVulnerable;
//	private ChessPiece promoted;

	private Board board;

	private List<ChessPiece> piecesOnTheBoard;
	private List<ChessPiece> capturedPieces;

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		piecesOnTheBoard = new ArrayList<>();
		capturedPieces = new ArrayList<>();
		placePieces();
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	public List<ChessPiece> getCapturedPieces() {
		return capturedPieces;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private void placePieces() {
		placePiece(new Rook(board, Color.WHITE), 'e', 3);
		placePiece(new Rook(board, Color.BLACK), 'b', 3);
		placePiece(new King(board, Color.BLACK), 'd', 5);
		placePiece(new Bishop(board, Color.BLACK), 'f', 7);
	}

	private void placePiece(ChessPiece chessPiece, char column, int row) {
		Position position = new ChessPosition(column, row).toPosition();
		board.placePiece(chessPiece, position);
		piecesOnTheBoard.add(chessPiece);
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
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position source = sourcePosition.toPosition();
		validateSourcePosition(source);
		boolean[][] possibleMovesMatrix = board.getPiece(source).possibleMoves();
		return possibleMovesMatrix;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		makeMove(source, target);
		
		nextTurn();
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position.");
		}
		ChessPiece pieceToBeMoved = (ChessPiece) board.getPiece(position);
		if (currentPlayer != pieceToBeMoved.getColor()) {
			throw new ChessException("The chosen piece is not yours.");
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

	private void makeMove(Position source, Position target) {
		Piece currentPlayerPiece = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(currentPlayerPiece, target);
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add((ChessPiece) capturedPiece);
		}
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
//	public ChessPiece replacePrmotedPiece(String type) {
//		return new ChessPiece(board, null);
//	}

}
