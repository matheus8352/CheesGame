package Chess.Pieces;

import BoardGame.Board;
import BoardGame.Position;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.Color;

public class King extends ChessPiece{
	
	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);	
		this.chessMatch = chessMatch;
	}
	
	@Override
	public String toString() {
		return "K";
	}
	
	private boolean canMove(Position position) {
		ChessPiece aux = (ChessPiece)getBoard().getPiece(position);
		return aux == null || aux.getColor() != getColor();
	}
	
	private boolean testRookCastling(Position position) {
		ChessPiece piece = (ChessPiece)getBoard().getPiece(position);
		return piece != null && piece instanceof Rook && piece.getColor() == getColor() && piece.getMoveCount() == 0;
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position positionAux = new Position(0, 0);
		
		//Above
		positionAux.setValues(position.getRow() -1, position.getColumn());
		if(getBoard().positionExists(positionAux) && canMove(positionAux))
			matrix[positionAux.getRow()][positionAux.getColumn()] = true;
		
		//Below
		positionAux.setValues(position.getRow() +1, position.getColumn());
		if(getBoard().positionExists(positionAux) && canMove(positionAux))
			matrix[positionAux.getRow()][positionAux.getColumn()] = true;
		
		//Left
		positionAux.setValues(position.getRow(), position.getColumn() -1);
		if(getBoard().positionExists(positionAux) && canMove(positionAux))
			matrix[positionAux.getRow()][positionAux.getColumn()] = true;
		
		//Right
		positionAux.setValues(position.getRow(), position.getColumn() +1);
		if(getBoard().positionExists(positionAux) && canMove(positionAux))
			matrix[positionAux.getRow()][positionAux.getColumn()] = true;
		
		//NW
		positionAux.setValues(position.getRow() -1, position.getColumn() -1);
		if(getBoard().positionExists(positionAux) && canMove(positionAux))
			matrix[positionAux.getRow()][positionAux.getColumn()] = true;
		
		//NE
		positionAux.setValues(position.getRow() -1, position.getColumn() +1);
		if(getBoard().positionExists(positionAux) && canMove(positionAux))
			matrix[positionAux.getRow()][positionAux.getColumn()] = true;
		
		//SW
		positionAux.setValues(position.getRow() +1, position.getColumn() -1);
		if(getBoard().positionExists(positionAux) && canMove(positionAux))
			matrix[positionAux.getRow()][positionAux.getColumn()] = true;
		
		//SE
		positionAux.setValues(position.getRow() +1, position.getColumn() +1);
		if(getBoard().positionExists(positionAux) && canMove(positionAux))
			matrix[positionAux.getRow()][positionAux.getColumn()] = true;
		
		//castling
		if(getMoveCount() == 0 && !chessMatch.getCheck()) {
			
			//castling king side rook
			Position kingRook = new Position(position.getRow(), position.getColumn() + 3);
			if(testRookCastling(kingRook)) {
				Position aux1 = new Position(position.getRow(), position.getColumn() + 1);
				Position aux2 = new Position(position.getRow(), position.getColumn() + 2);
				if(getBoard().getPiece(aux1) == null && getBoard().getPiece(aux2) == null)
					matrix[position.getRow()][position.getColumn() + 2] = true;
			}
				
				//castling queen side rook
				Position queenRook = new Position(position.getRow(), position.getColumn() - 4);
				if(testRookCastling(queenRook)) {
					Position aux1 = new Position(position.getRow(), position.getColumn() - 1);
					Position aux2 = new Position(position.getRow(), position.getColumn() - 2);
					Position aux3 = new Position(position.getRow(), position.getColumn() - 3);
					if(getBoard().getPiece(aux1) == null && getBoard().getPiece(aux2) == null && getBoard().getPiece(aux3) == null)
						matrix[position.getRow()][position.getColumn() - 2] = true;
			}
		}
		
		return matrix;
	}

}
