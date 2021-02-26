 package Chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import BoardGame.Board;
import BoardGame.Piece;
import BoardGame.Position;
import Chess.Pieces.Bishop;
import Chess.Pieces.King;
import Chess.Pieces.Knight;
import Chess.Pieces.Pawn;
import Chess.Pieces.Queen;
import Chess.Pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece getEnPassabtVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
	}
	
	public ChessMatch() {
		board = new Board(8,8);
		turn = 1;
		currentPlayer = Color.WHITE;
		check = false;
		initialSetup();
	}
	
	public ChessPiece[][] getPieces(){
		ChessPiece[][] aux = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i = 0; i < board.getRows(); i++)
			for (int j = 0; j < board.getColumns(); j++) 
				aux[i][j] = (ChessPiece) board.getPiece(i, j);
		return aux;
	}
	
	public boolean[][] possibleMoves(ChessPosition source){
		Position position = source.toPosition();
		validateSourcePosition(position);
		return board.getPiece(position).possibleMoves();
		
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validadeTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		
		if(testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in check");
		}
		
		ChessPiece movedPiece = (ChessPiece)board.getPiece(target);
		
		//promotion
		promoted = null;
		if(movedPiece instanceof Pawn) {
			if((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
				promoted = (ChessPiece)board.getPiece(target);
				promoted = replacePromotedPiece("Q");
			}
		}
		
		check = testCheck(getOpponent(currentPlayer)) ? true : false;
		
		if(testCheckMate(getOpponent(currentPlayer)))
			checkMate = true;
		else
			nextTurn();
		
		//en passant
		if(movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2))
			enPassantVulnerable = movedPiece;
		else
			enPassantVulnerable = null;
		
		return (ChessPiece)capturedPiece;
	}
	
	public ChessPiece replacePromotedPiece(String type) {
		if(promoted == null)
			throw new IllegalStateException("There is no piece to be promoted");
		
		if(!type.contentEquals("B") && !type.contentEquals("N") && !type.contentEquals("R") && !type.contentEquals("Q"))
			throw new InvalidParameterException("Invalid type for promotion");
		
		Position position = promoted.getChessPosition().toPosition();
		Piece piece = board.removePiece(position);
		piecesOnTheBoard.remove(piece);
		
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, position);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}
	
	private ChessPiece newPiece(String type, Color color) {
		switch(type) {
			case "B":
				return new Bishop(board, color);
			
			case "N":
				return new Knight(board, color);
				
			case "R":
				return new Rook(board, color);
		}
		
		return new Queen(board, color);
	}
	
	private Piece makeMove(Position source, Position target) {
		ChessPiece piece = (ChessPiece)board.removePiece(source);
		piece.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);			
		board.placePiece(piece, target);
		
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		//castling king side rook
		if(piece instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position rookSource = new Position(source.getRow(), source.getColumn() + 3);
			Position rookTarget = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(rookSource);
			board.placePiece(rook, rookTarget);
			rook.increaseMoveCount();
		}
		
		//castling queen side rook
		if(piece instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position rookSource = new Position(source.getRow(), source.getColumn() - 4);
			Position rookTarget = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(rookSource);
			board.placePiece(rook, rookTarget);
			rook.increaseMoveCount();
		}
		
		//en passant
		if(piece instanceof Pawn) {
			if(source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position pawnPosition;
				if(piece.getColor() == Color.WHITE) 
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				else
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece piece = (ChessPiece)board.removePiece(target);
		piece.decreaseMoveCount();
		board.placePiece(piece, source);
		
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		//castling king side rook
		if(piece instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position rookSource = new Position(source.getRow(), source.getColumn() + 3);
			Position rookTarget = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(rookTarget);
			board.placePiece(rook, rookSource);
			rook.decreaseMoveCount();
		}
		
		//castling queen side rook
		if(piece instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position rookSource = new Position(source.getRow(), source.getColumn() - 4);
			Position rookTarget = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(rookTarget);
			board.placePiece(rook, rookSource);
			rook.decreaseMoveCount();
		}
		
		//en passant
		if(piece instanceof Pawn) {
			if(source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				ChessPiece pawn = (ChessPiece)board.getPiece(target);
				Position pawnPosition;
				if(piece.getColor() == Color.WHITE) 
					pawnPosition = new Position(3, target.getColumn());
				else
					pawnPosition = new Position(4, target.getColumn());
				
				board.placePiece(pawn, pawnPosition);
			}
		}
	}
	
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position))
			throw new ChessException("There is no piece on source position");
		
		if(currentPlayer != ((ChessPiece)board.getPiece(position)).getColor())
			throw new ChessException("The chosen piece is not yours");
		
		if(!board.getPiece(position).isThereAnyPossibleMove())
			throw new ChessException("There is no possible moves for the chosen piece");
	}
	
	private void validadeTargetPosition(Position source, Position target) {
		if(!board.getPiece(source).possibleMove(target))
			throw new ChessException("The chosen piece can't move to targert position");
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private Color getOpponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece getKing(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p : list) {
			if(p instanceof King)
				return (ChessPiece)p;
		}
		throw new IllegalStateException("There is no " + color + " King on the board");
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = getKing(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == getOpponent(color)).collect(Collectors.toList());
		for(Piece p : opponentPieces) {
			boolean[][] matrix = p.possibleMoves();
			if(matrix[kingPosition.getRow()][kingPosition.getColumn()])
				return true;
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if(!testCheck(color))
			return false;
		
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		
		for(Piece p : list) {
			boolean[][]mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if(mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck)
							return false;
					}
				}
			}
		}
		
		return true;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	    piecesOnTheBoard.add(piece);
	}
	
	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
}
