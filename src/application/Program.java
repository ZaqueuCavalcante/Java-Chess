package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.layer.ChessException;
import chess.layer.ChessMatch;
import chess.layer.ChessPiece;
import chess.layer.ChessPosition;
import view.layer.UserInterface;

public class Program {

	public static void main(String[] args) {

		ChessMatch chessMatch = new ChessMatch();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			try {
				UserInterface.clearScreen();
				UserInterface.print(chessMatch.getPieces());
				System.out.println();
				System.out.print("Source: ");
				String userInput = scanner.next();
				ChessPosition source = UserInterface.readChessPosition(userInput);

				System.out.println();
				System.out.print("Target: ");
				userInput = scanner.next();
				ChessPosition target = UserInterface.readChessPosition(userInput);

				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				scanner.nextLine();
			}
		}
	}

}
