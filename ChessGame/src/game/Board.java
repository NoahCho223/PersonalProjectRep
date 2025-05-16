package game;

public class Board {
	private Piece[][] boardState; //each index of the array represents a piece on the board
	private final int WIDTH;
	private final int LENGTH;
	private Piece whiteKing;
	private Piece blackKing;
	public Board(int width, int length) {
		boardState=new Piece[width][length];
		WIDTH=width;
		LENGTH=length;
	}
	
	public void arrangeStandardGame() { //initializes the pieces and places them in correct locations
		//place King
		King whiteKing=new King(boardState, 0, 4, true);
		King blackKing=new King(boardState, 7, 4, false);
		boardState[0][4]=whiteKing;
		boardState[7][4]=blackKing;
		//place Bishops
		Bishop whiteBishop1=new Bishop(boardState, 0, 2, true);
		Bishop whiteBishop2=new Bishop(boardState, 0, 5, true);
		Bishop blackBishop1=new Bishop(boardState, 7, 2, false);
		Bishop blackBishop2=new Bishop(boardState, 7, 5, false);
		boardState[0][2]=whiteBishop1;
		boardState[0][5]=whiteBishop2;
		boardState[7][2]=blackBishop1;
		boardState[7][5]=blackBishop2;
		//place Rooks
		Rook whiteRook1=new Rook(boardState, 0, 0, true);
		Rook whiteRook2=new Rook(boardState, 0, 7, true);
		Rook blackRook1=new Rook(boardState, 7, 0, false);
		Rook blackRook2=new Rook(boardState, 7, 7, false);
		boardState[0][0]=whiteRook1;
		boardState[0][7]=whiteRook2;
		boardState[7][0]=blackRook1;
		boardState[7][7]=blackRook2;
		//place Queen
		Queen whiteQueen=new Queen(boardState, 0, 3, true);
		Queen blackQueen=new Queen(boardState, 7, 3, false);
		boardState[0][3]=whiteQueen;
		boardState[7][3]=blackQueen;
		
		//place Knight
		Knight whiteKnight1=new Knight(boardState, 0, 1, true);
		Knight whiteKnight2=new Knight(boardState, 0, 6, true);
		Knight blackKnight1=new Knight(boardState, 7, 1, false);
		Knight blackKnight2=new Knight(boardState, 7, 6, false);
		boardState[0][1]=whiteKnight1;
		boardState[0][6]=whiteKnight2;
		boardState[7][1]=blackKnight1;
		boardState[7][6]=blackKnight2;
		
		//place pawns
		boardState[1][0]=new Pawn(boardState,1,0,true);
		boardState[1][1]=new Pawn(boardState,1,1,true);
		boardState[1][2]=new Pawn(boardState,1,2,true);
		boardState[1][3]=new Pawn(boardState,1,3,true);
		boardState[1][4]=new Pawn(boardState,1,4,true);
		boardState[1][5]=new Pawn(boardState,1,5,true);
		boardState[1][6]=new Pawn(boardState,1,6,true);
		boardState[1][7]=new Pawn(boardState,1,7,true);
		
		boardState[6][0]=new Pawn(boardState,6,0,false);
		boardState[6][1]=new Pawn(boardState,6,1,false);
		boardState[6][2]=new Pawn(boardState,6,2,false);
		boardState[6][3]=new Pawn(boardState,6,3,false);
		boardState[6][4]=new Pawn(boardState,6,4,false);
		boardState[6][5]=new Pawn(boardState,6,5,false);
		boardState[6][6]=new Pawn(boardState,6,6,false);
		boardState[6][7]=new Pawn(boardState,6,7,false);
		
		//set the white king and black king
		this.whiteKing=whiteKing;
		this.blackKing=blackKing;
	}
	public Piece[][] getBoardState() {
		return boardState;
	}
	public Piece getWhiteKing() {
		return whiteKing;
	}
	public Piece getBlackKing() {
		return blackKing;
	}
}
