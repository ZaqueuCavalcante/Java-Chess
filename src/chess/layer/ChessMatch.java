package chess.layer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import board.layer.Board;
import board.layer.Piece;
import board.layer.Position;
import chess.pieces.King;
import chess.pieces.Rook;
import view.layer.Color;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private boolean isInCheck;
	private boolean isInCheckMate;
//	private ChessPiece enPassantVulnerable;
//	private ChessPiece promoted;

	private Board board;

	private List<Piece> piecesOnTheBoard;
	private List<Piece> capturedPieces;

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		piecesOnTheBoard = new ArrayList<>();
		capturedPieces = new ArrayList<>();
		placeInitialPieces();
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getIsInCheck() {
		return isInCheck;
	}
	
	public boolean getIsInCheckMate() {
		return isInCheckMate;
	}
	
 	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	public List<Piece> getCapturedPieces() {
		return capturedPieces;
	}
	
	public List<ChessPiece> getCapturedChessPieces() {
		List<ChessPiece> capturedChessPieces = new ArrayList<>();
		for (Piece piece : capturedPieces) {
			capturedChessPieces.add((ChessPiece) piece); 
		}
		return capturedChessPieces;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private void placeInitialPieces() {
		placePiece(new Rook(board, Color.WHITE), 'h', 7);
		placePiece(new Rook(board, Color.WHITE), 'd', 1);
//		placePiece(new Knight(board, Color.WHITE), 'b', 1);
//		placePiece(new Bishop(board, Color.WHITE), 'c', 1);
//		placePiece('d', 1, new Queen(board, Color.WHITE));
		placePiece(new King(board, Color.WHITE), 'e', 1);
//		placePiece('f', 1, new Bishop(board, Color.WHITE));
//		placePiece('g', 1, new Knight(board, Color.WHITE));
//		placePiece('h', 1, new Rook(board, Color.WHITE));
//		placePiece('a', 2, new Pawn(board, Color.WHITE, this));
//		placePiece('b', 2, new Pawn(board, Color.WHITE, this));
//		placePiece('c', 2, new Pawn(board, Color.WHITE, this));
//		placePiece('d', 2, new Pawn(board, Color.WHITE, this));
//		placePiece('e', 2, new Pawn(board, Color.WHITE, this));
//		placePiece('f', 2, new Pawn(board, Color.WHITE, this));
//		placePiece('g', 2, new Pawn(board, Color.WHITE, this));
//		placePiece('h', 2, new Pawn(board, Color.WHITE, this));

		placePiece(new Rook(board, Color.BLACK), 'b', 8);
//	    placePiece('b', 8, new Knight(board, Color.BLACK));
//	        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
//	        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placePiece(new King(board, Color.BLACK), 'a', 8);
//	        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
//	        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
//	        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
//	        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
//	        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
//	        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
//	        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
//	        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
//	        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
//	        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
//	        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
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
		Piece capturedPiece = makeMove(source, target);
		
		if (isInCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check.");
		}
		
		Color opponentPlayer = getOpponentColor(currentPlayer);
		isInCheck = (isInCheck(opponentPlayer)) ? true : false;
		
		if (isInCheckMate(opponentPlayer)) {
			isInCheckMate = true;
		} else {
			nextTurn();
		}
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

	private Piece makeMove(Position source, Position target) {
		Piece currentPlayerPiece = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(currentPlayerPiece, target);
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece lastPieceMoved = board.removePiece(target);
		board.placePiece(lastPieceMoved, source);
		
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private Color getOpponentColor(Color playerColor) {
		return (playerColor == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece getKingByColor(Color playerColor) {
		List<Piece> playerPieces = piecesOnTheBoard.stream().
				filter(piece -> ((ChessPiece)piece).getColor() == playerColor).
				collect(Collectors.toList());
		for (Piece piece : playerPieces) {
			if (piece instanceof King) {
				return (ChessPiece) piece;
			}
		}
		throw new IllegalStateException("There is no " + playerColor + " king on the board.");
	}
	
	private boolean isInCheck(Color playerColor) {
		ChessPiece playerKing = getKingByColor(playerColor);
		Position playerKingPosition = playerKing.getChessPosition().toPosition();
		int playerKingRow = playerKingPosition.getRow();
		int playerKingColumn = playerKingPosition.getColumn();
		
		List<Piece> opponentPieces = piecesOnTheBoard.stream().
				filter(piece -> ((ChessPiece)piece).getColor() == getOpponentColor(playerColor)).
				collect(Collectors.toList());
		
		boolean[][] opponentPiecePossibleMovesMatrix;
		boolean playerKingInCheck;
		for (Piece piece : opponentPieces) {
			opponentPiecePossibleMovesMatrix = piece.possibleMoves();
			playerKingInCheck = opponentPiecePossibleMovesMatrix[playerKingRow][playerKingColumn];
			if (playerKingInCheck) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isInCheckMate(Color playerColor) {
		if (isInCheck(playerColor)) {
			List<Piece> playerPieces = piecesOnTheBoard.stream().
					filter(piece -> ((ChessPiece)piece).getColor() == playerColor).
					collect(Collectors.toList());
			
			boolean[][] playerPiecePossibleMovesMatrix;
			for (Piece piece : playerPieces) {
				playerPiecePossibleMovesMatrix = piece.possibleMoves();
				for (int row=0; row<board.getRows(); row++) {
					for (int column=0; column<board.getColumns(); column++) {
						if (playerPiecePossibleMovesMatrix[row][column]) {
							Position sourcePiecePosition = ((ChessPiece) piece).getChessPosition().toPosition();
							Position targetPiecePosition = new Position(row, column);
							Piece capturedPiece = makeMove(sourcePiecePosition, targetPiecePosition);
							boolean isInCheck = isInCheck(playerColor);
							undoMove(sourcePiecePosition, targetPiecePosition, capturedPiece);
							if (!isInCheck) {
								return false;
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
//	public ChessPiece replacePrmotedPiece(String type) {
//		return new ChessPiece(board, null);
//	}
	
}
