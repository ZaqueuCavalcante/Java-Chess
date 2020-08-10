package view.layer;

import java.util.InputMismatchException;

import chess.layer.ChessPiece;
import chess.layer.ChessPosition;
import chess.layer.Color;

public class UserInterface {

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public static ChessPosition readChessPosition(String userInput) {
		try {
			char column = userInput.charAt(0);
			int row = Integer.parseInt(userInput.substring(1));
			return new ChessPosition(column, row);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
		}
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public static void print(ChessPiece[][] chessPieceMatrix) {
		int size = chessPieceMatrix.length;
		for (int row = 0; row < size; row++) {
			System.out.print((size - row) + " ");
			for (int column = 0; column < size; column++) {
				print(chessPieceMatrix[row][column]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h ");
	}

	private static void print(ChessPiece chessPiece) {
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

}
