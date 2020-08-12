package application;

import java.util.InputMismatchException;

import java.util.Scanner;

import chess.layer.ChessException;
import chess.layer.ChessMatch;
import chess.layer.ChessPosition;
import view.layer.UserInterface;

public class Program {

	public static void main(String[] args) {

		ChessMatch chessMatch = new ChessMatch();
		Scanner scanner = new Scanner(System.in);
		
		while (!chessMatch.getIsInCheckMate()) {
			try {
				UserInterface.clearScreen();
				UserInterface.print(chessMatch);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UserInterface.readChessPosition(scanner);
				
				boolean[][] possibleMovesMatrix = chessMatch.possibleMoves(source);
				UserInterface.clearScreen();
				UserInterface.print(chessMatch.getPieces(), possibleMovesMatrix);

				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UserInterface.readChessPosition(scanner);

				chessMatch.performChessMove(source, target);
				
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				scanner.nextLine();
			}
		}
		UserInterface.clearScreen();
		UserInterface.print(chessMatch);
	}

}
