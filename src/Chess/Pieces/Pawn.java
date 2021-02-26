package Chess.Pieces;

import BoardGame.Board;
import BoardGame.Position;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.Color;

public class Pawn extends ChessPiece {

	private ChessMatch chessMatch;
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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
			
			//en passant white
			if(position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().getPiece(left) == chessMatch.getEnPassabtVulnerable())
					matrix[left.getRow() - 1][left.getColumn()] = true;
			}
			
			if(position.getRow() == 3) {
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().getPiece(right) == chessMatch.getEnPassabtVulnerable())
					matrix[right.getRow() - 1][right.getColumn()] = true;
			}
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
			
			//en passant black
			if(position.getRow() == 4) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().getPiece(left) == chessMatch.getEnPassabtVulnerable())
					matrix[left.getRow() + 1][left.getColumn()] = true;
			}
			
			if(position.getRow() == 4) {
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().getPiece(right) == chessMatch.getEnPassabtVulnerable())
					matrix[right.getRow() + 1][right.getColumn()] = true;
			}
		}
		return matrix;
	}

	@Override
	public String toString() {
		return "P";
	}
}
