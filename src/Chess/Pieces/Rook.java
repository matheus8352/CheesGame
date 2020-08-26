package Chess.Pieces;

import BoardGame.Board;
import BoardGame.Position;
import Chess.ChessPiece;
import Chess.Color;

public class Rook extends ChessPiece{

	public Rook(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position aux = new Position(0,0);

		//above
		aux.setValues(position.getRow() - 1, position.getColumn());
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
			aux.setRow(aux.getRow() - 1);
		}
		
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
			matrix[aux.getRow()][aux.getColumn()] = true;
		
		//left
		aux.setValues(position.getRow(), position.getColumn() - 1);
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
			aux.setColumn(aux.getColumn() - 1);
		}
		
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
			matrix[aux.getRow()][aux.getColumn()] = true;
		
		//right
		aux.setValues(position.getRow(), position.getColumn() + 1);
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
			aux.setColumn(aux.getColumn() + 1);
		}
		
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
			matrix[aux.getRow()][aux.getColumn()] = true;
		
		//below
		aux.setValues(position.getRow() + 1, position.getColumn());
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			matrix[aux.getRow()][aux.getColumn()] = true;
			aux.setRow(aux.getRow() + 1);
		}
		
		if(getBoard().positionExists(aux) && isThereOpponentPiece(aux))
			matrix[aux.getRow()][aux.getColumn()] = true;
		
		return matrix;
	}
}
