package application;

import chess.layer.ChessMatch;
import view.layer.UserInterface;

public class Program {

	public static void main(String[] args) {
		
		ChessMatch chessMatch = new ChessMatch();
		UserInterface.print(chessMatch.getPieces());
	}

}
