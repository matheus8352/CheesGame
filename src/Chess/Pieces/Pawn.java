package Chess.Pieces;

import BoardGame.Board;
import BoardGame.Position;
import Chess.ChessPiece;
import Chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position aux = new Position(0,0);
		
		if(getColor() == Color.WHITE) {
			aux.setValues(position.getRow() - 1, position.getColumn());
			if(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux))
				matrix[aux.getRow()][aux.getColumn()] = true;
			
			aux.setValues(position.getRow() - 2, position.getColumn());
			Position above = new Position(position.getRow() - 1, position.getColumn());
			if(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux) && getBoard().positionExists(above) && !getBoard().thereIsAPiece(above) && getMoveCount() == 0)
				matrix[aux.getRow()][aux.getColumn()] = true;
			
			aux.setValues(position.getRow() - 1, position.getColumn() - 1);
			if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
				matrix[aux.getRow()][aux.getColumn()] = true;
			
			aux.setValues(position.getRow() - 1, position.getColumn() + 1);
			if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
				matrix[aux.getRow()][aux.getColumn()] = true;
		}
		else{
			aux.setValues(position.getRow() + 1, position.getColumn());
			if(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux))
				matrix[aux.getRow()][aux.getColumn()] = true;
			
			aux.setValues(position.getRow() + 2, position.getColumn());
			Position above = new Position(position.getRow() + 1, position.getColumn());
			if(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux) && getBoard().positionExists(above) && !getBoard().thereIsAPiece(above) && getMoveCount() == 0)
				matrix[aux.getRow()][aux.getColumn()] = true;
			
			aux.setValues(position.getRow() + 1, position.getColumn() - 1);
			if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
				matrix[aux.getRow()][aux.getColumn()] = true;
			
			aux.setValues(position.getRow() + 1, position.getColumn() + 1);
			if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
				matrix[aux.getRow()][aux.getColumn()] = true;
		}
		return matrix;
	}

	@Override
	public String toString() {
		return "P";
	}
}
