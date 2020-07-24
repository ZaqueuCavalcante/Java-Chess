package view.layer;

import chess.layer.ChessPiece;

public class UserInterface {

	public static void print(ChessPiece[][] chessPieceMatrix) {
		int size = chessPieceMatrix.length;
		for (int row=0; row < size; row++) {
			System.out.print((size - row) + " ");
			for (int column=0; column < size; column++) {
				print(chessPieceMatrix[row][column]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h ");
	}
	
	private static void print(ChessPiece chessPiece) {
		if (chessPiece == null) {
			System.out.print("-");
		} else {
			System.out.print(chessPiece);
		}
		System.out.print(" ");
	}
}
