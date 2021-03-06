package view.layer;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.layer.ChessMatch;
import chess.layer.ChessPiece;
import chess.layer.ChessPosition;

public class UserInterface {

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public static ChessPosition readChessPosition(Scanner scanner) {
		try {
			String userInput = scanner.nextLine();
			char column = userInput.charAt(0);
			int row = Integer.parseInt(userInput.substring(1));
			return new ChessPosition(column, row);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
		}
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public static void print(ChessMatch chessMatch) {
		print(chessMatch.getPieces());
		System.out.println();
		print(chessMatch.getCapturedChessPieces());
		System.out.println();
		System.out.println("Turn: " + chessMatch.getTurn());
		if (!chessMatch.getIsInCheckMate()) {
			System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
			if (chessMatch.getIsInCheck()) {
				System.out.println("CHECK!");
			}
		} else {
			System.out.println("CHECKMATE!");
			System.out.println("Winner: " + chessMatch.getCurrentPlayer());
		}
		
	}

	public static void print(ChessPiece[][] chessPieceMatrix, boolean[][] possibleMovesMatrix) {
		int size = chessPieceMatrix.length;
		for (int row = 0; row < size; row++) {
			System.out.print((size - row) + " ");
			for (int column = 0; column < size; column++) {
				print(chessPieceMatrix[row][column], possibleMovesMatrix[row][column]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h ");
	}

	public static void print(ChessPiece[][] chessPieceMatrix) {
		int size = chessPieceMatrix.length;
		for (int row = 0; row < size; row++) {
			System.out.print((size - row) + " ");
			for (int column = 0; column < size; column++) {
				print(chessPieceMatrix[row][column], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h ");
	}

	private static void print(ChessPiece chessPiece, boolean background) {
		if (background) {
			System.out.print(AnsiColors.ANSI_BLUE_BACKGROUND);
		}
		if (chessPiece == null) {
			System.out.print("-" + AnsiColors.ANSI_RESET);
		} else {
			if (chessPiece.getColor() == Color.WHITE) {
				System.out.print(AnsiColors.ANSI_WHITE + chessPiece + AnsiColors.ANSI_RESET);
			} else {
				System.out.print(AnsiColors.ANSI_YELLOW + chessPiece + AnsiColors.ANSI_RESET);
			}
		}
		System.out.print(" ");
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public static void print(List<ChessPiece> capturedPieces) {
		List<ChessPiece> whitePieces = capturedPieces.stream().
				filter(piece -> piece.getColor() == Color.WHITE).
				collect(Collectors.toList());
		List<ChessPiece> blackPieces = capturedPieces.stream().
				filter(piece -> piece.getColor() == Color.BLACK).
				collect(Collectors.toList());
		System.out.println("Captured pieces: ");
		System.out.print("White: " + AnsiColors.ANSI_WHITE);
		System.out.println(Arrays.toString(whitePieces.toArray()));
		System.out.print(AnsiColors.ANSI_RESET);
		System.out.print(AnsiColors.ANSI_YELLOW + "Black: " + AnsiColors.ANSI_YELLOW);
		System.out.println(Arrays.toString(blackPieces.toArray()));
		System.out.print(AnsiColors.ANSI_RESET);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //

}
