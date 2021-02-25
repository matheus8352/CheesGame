package Chess.Pieces;

import BoardGame.Board;
import BoardGame.Position;
import Chess.ChessPiece;
import Chess.Color;

public class Bishop extends ChessPiece{

	public Bishop(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position aux = new Position(0,0);

		//nw
		aux.setValues(position.getRow() - 1, position.getColumn() - 1);
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
			aux.setValues(aux.getRow() - 1, aux.getColumn() - 1);
		}
		
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
			matrix[aux.getRow()][aux.getColumn()] = true;
		
		//ne
		aux.setValues(position.getRow() - 1, position.getColumn() + 1);
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
			aux.setValues(aux.getRow() - 1, aux.getColumn() + 1);
		}
		
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
			matrix[aux.getRow()][aux.getColumn()] = true;
		
		//se
		aux.setValues(position.getRow() + 1, position.getColumn() + 1);
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
			aux.setValues(aux.getRow() + 1, aux.getColumn() + 1);
		}
		
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
			matrix[aux.getRow()][aux.getColumn()] = true;
		
		//sw
		aux.setValues(position.getRow() + 1, position.getColumn() - 1);
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
			aux.setValues(aux.getRow() + 1, aux.getColumn() - 1);
		}
		
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
			matrix[aux.getRow()][aux.getColumn()] = true;
		
		return matrix;
	}
}
