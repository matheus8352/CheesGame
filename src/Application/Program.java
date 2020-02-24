package Application;

import Chess.ChessMatch;

public class Program {

	public static void main(String[] args) {
		ChessMatch match = new ChessMatch();
		UI.printBoard(match.getPieces());
	}

}
