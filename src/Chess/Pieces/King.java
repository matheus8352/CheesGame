package Chess.Pieces;

import BoardGame.Board;
import BoardGame.Position;
import Chess.ChessPiece;
import Chess.Color;

public class King extends ChessPiece{

	public King(Board board, Color color) {
		super(board, color);	}
	
	@Override
	public String toString() {
		return "K";
	}
	
	private boolean canMove(Position position) {
		ChessPiece aux = (ChessPiece)getBoard().getPiece(position);
		return aux == null || aux.getColor() != getColor();
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
		
		return matrix;
	}

}
