package chess.layer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import board.layer.Board;
import board.layer.Piece;
import board.layer.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;
import view.layer.Color;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private boolean isInCheck;
	private boolean isInCheckMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promotedPiece;

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

	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}

	public ChessPiece getPromotedPiece() {
		return promotedPiece;
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
		placePiece(new Rook(board, Color.WHITE), 'a', 1);
		placePiece(new Knight(board, Color.WHITE), 'b', 1);
		placePiece(new Bishop(board, Color.WHITE), 'c', 1);
		placePiece(new Queen(board, Color.WHITE), 'd', 1);
		placePiece(new King(board, Color.WHITE, this), 'e', 1);
		placePiece(new Bishop(board, Color.WHITE), 'f', 1);
		placePiece(new Knight(board, Color.WHITE), 'g', 1);
		placePiece(new Rook(board, Color.WHITE), 'h', 1);
		placePiece(new Pawn(board, Color.WHITE, this), 'a', 2);
		placePiece(new Pawn(board, Color.WHITE, this), 'b', 2);
		placePiece(new Pawn(board, Color.WHITE, this), 'c', 2);
		placePiece(new Pawn(board, Color.WHITE, this), 'd', 2);
		placePiece(new Pawn(board, Color.WHITE, this), 'e', 2);
		placePiece(new Pawn(board, Color.WHITE, this), 'f', 2);
		placePiece(new Pawn(board, Color.WHITE, this), 'g', 2);
		placePiece(new Pawn(board, Color.WHITE, this), 'h', 2);

		
		placePiece(new Rook(board, Color.BLACK), 'a', 8);
		placePiece(new Knight(board, Color.BLACK), 'b', 8);
		placePiece(new Bishop(board, Color.BLACK), 'c', 8);
		placePiece(new Queen(board, Color.BLACK), 'd', 8);
		placePiece(new King(board, Color.BLACK, this), 'e', 8);
		placePiece(new Bishop(board, Color.BLACK), 'f', 8);
		placePiece(new Knight(board, Color.BLACK), 'g', 8);
		placePiece(new Rook(board, Color.BLACK), 'h', 8);
		placePiece(new Pawn(board, Color.BLACK, this), 'a', 7);
		placePiece(new Pawn(board, Color.BLACK, this), 'b', 7);
		placePiece(new Pawn(board, Color.BLACK, this), 'c', 7);
		placePiece(new Pawn(board, Color.BLACK, this), 'd', 7);
		placePiece(new Pawn(board, Color.BLACK, this), 'e', 7);
		placePiece(new Pawn(board, Color.BLACK, this), 'f', 7);
		placePiece(new Pawn(board, Color.BLACK, this), 'g', 7);
		placePiece(new Pawn(board, Color.BLACK, this), 'h', 7);
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

		ChessPiece movedPiece = (ChessPiece) this.board.getPiece(target);

		// Special move: Promotion
		promotedPiece = null;
		if (movedPiece instanceof Pawn) {
			boolean whitePawn = (movedPiece.getColor() == Color.WHITE && target.getRow() == 0);
			boolean blackPawn = (movedPiece.getColor() == Color.BLACK && target.getRow() == 7);
			if (whitePawn || blackPawn) {
				promotedPiece = (ChessPiece) board.getPiece(target);
				promotedPiece = replacePromotedPiece("Q");
			}
		}

		// Checks check mate
		Color opponentPlayer = getOpponentColor(currentPlayer);
		isInCheck = (isInCheck(opponentPlayer)) ? true : false;

		if (isInCheckMate(opponentPlayer)) {
			isInCheckMate = true;
		} else {
			nextTurn();
		}

		// Special move: En passant
		if (movedPiece instanceof Pawn
				&& (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
			enPassantVulnerable = movedPiece;
		} else {
			enPassantVulnerable = null;
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
		ChessPiece currentPlayerPiece = (ChessPiece) board.removePiece(source);
		currentPlayerPiece.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(currentPlayerPiece, target);

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		// Special move: castling King side rook
		if (currentPlayerPiece instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}

		// Special move: castling Queen side rook
		if (currentPlayerPiece instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}

		// Special move: En passant
		if (currentPlayerPiece instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position pawnPosition;
				if (currentPlayerPiece.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				} else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}

		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece lastPieceMoved = (ChessPiece) board.removePiece(target);
		lastPieceMoved.decreaseMoveCount();
		board.placePiece(lastPieceMoved, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}

		// Special move: castling King side rook
		if (lastPieceMoved instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}

		// Special move: castling Queen side rook
		if (lastPieceMoved instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}

		// Special move: En passant
		if (lastPieceMoved instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				ChessPiece pawn = (ChessPiece) board.removePiece(target);
				Position pawnPosition;
				if (lastPieceMoved.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, target.getColumn());
				} else {
					pawnPosition = new Position(4, target.getColumn());
				}
				board.placePiece(pawn, pawnPosition);
			}
		}
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	private Color getOpponentColor(Color playerColor) {
		return (playerColor == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece getKingByColor(Color playerColor) {
		List<Piece> playerPieces = piecesOnTheBoard.stream()
				.filter(piece -> ((ChessPiece) piece).getColor() == playerColor).collect(Collectors.toList());
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

		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(piece -> ((ChessPiece) piece).getColor() == getOpponentColor(playerColor))
				.collect(Collectors.toList());

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
			List<Piece> playerPieces = piecesOnTheBoard.stream()
					.filter(piece -> ((ChessPiece) piece).getColor() == playerColor).collect(Collectors.toList());

			boolean[][] playerPiecePossibleMovesMatrix;
			for (Piece piece : playerPieces) {
				playerPiecePossibleMovesMatrix = piece.possibleMoves();
				for (int row = 0; row < board.getRows(); row++) {
					for (int column = 0; column < board.getColumns(); column++) {
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
	public ChessPiece replacePromotedPiece(String type) {
		if (promotedPiece == null) {
			throw new IllegalStateException("There is no piece to be promoted.");
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")) {
			return promotedPiece;
		}

		Position pos = promotedPiece.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);

		ChessPiece newPiece = newPiece(type, promotedPiece.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);

		return newPiece;
	}

	private ChessPiece newPiece(String type, Color color) {
		if (type.equals("B"))
			return new Bishop(board, color);
		if (type.equals("N"))
			return new Knight(board, color);
		if (type.equals("Q"))
			return new Queen(board, color);
		return new Rook(board, color);
	}

}
