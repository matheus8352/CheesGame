package Chess;

import BoardGame.Board;
import BoardGame.Position;
import Chess.Pieces.Rook;

public class ChessMatch {

	private Board board;
	
	public ChessMatch() {
		board = new Board(8,8);
		initialSetup();
	}
	
	public ChessPiece[][] getPieces(){
		ChessPiece[][] aux = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i = 0; i < board.getRows(); i++)
			for (int j = 0; j < board.getColumns(); j++) 
				aux[i][j] = (ChessPiece) board.getPiece(i, j);
		return aux;
	}
	
	private void initialSetup() {
		board.placePiece(new Rook(board, Color.WHITE), new Position(2,1));
	}
}
