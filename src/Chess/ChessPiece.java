package Chess;

import BoardGame.Board;
import BoardGame.Piece;
import BoardGame.Position;

public abstract class ChessPiece extends Piece {
	
	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	protected boolean isThereOpponentPiece(Position position){
		ChessPiece piece = (ChessPiece)getBoard().getPiece(position);
		return piece != null && piece.getColor() != this.color;
	}
}
