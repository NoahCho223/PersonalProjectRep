package game;

import java.util.ArrayList;

public class King extends Piece {

	
	public King(Piece[][] board, int xPos, int yPos, boolean isWhite) {
		super(board, xPos, yPos, isWhite);
	}

	private boolean inCheck;
	private boolean castled;
	
	@Override
	public void updateMoveList(Piece[][] board) {
		this.moves=getUpdatedMoveList(board);
	}
	
	@Override
	public boolean[][] getUpdatedMoveList(Piece[][] board) {
		boolean[][] moves=new boolean[board.length][board[0].length];
//		boolean[][] unsafeSpaces=unsafeSpaces(board);
		//find all the possible moves for the king, however cannot move king into danger
		//check moves around the king
		for(int i=rowPos-1; i<=rowPos+1; i++) {
			for(int j=colPos-1; j<=colPos+1; j++) {
				if(i>=0 && j>=0 && i<board[0].length && j<board.length) { //if the move is within the boundaries
					//check if the move is legal, (not blocked by own piece, not moving into check)
					if(board[i][j]==null || board[i][j].isWhite!=this.isWhite) {
						//move the king temporarily, then check if anything attacking it
						Piece tempIJ=board[i][j];
						board[i][j]=this;
						int oldRowPos=rowPos;
						int oldColPos=colPos;
						rowPos=i;
						colPos=j;
						board[oldRowPos][oldColPos]=null;
						ArrayList<Piece> attackerList=listAttackingPieces(board);
						board[i][j]=tempIJ;
						rowPos=oldRowPos;
						colPos=oldColPos;
						board[rowPos][colPos]=this;
						
						//check if the space is attacked
						if(attackerList.size()==0) { //if the space is safe, then king can move there
							moves[i][j]=true;
						}
					}
				}
			}
		}
		
		//check if the king castled, if not, and if the king is not in check then add castling to the possible moves
		
		
		return moves;
	}
	
	//returns spaces that are being attacked by enemy pieces
//	private boolean[][] unsafeSpaces(Piece[][] board) {
//		//get the spaces that are being attacked by looping through board for every piece's movelist
//		boolean[][] unsafeSpaces=new boolean[board.length][board[0].length];
//		for(int i=0; i<board.length; i++) {
//			for(int j=0; j<board[0].length; j++) {
//				if(board[i][j]!=null) {
//					if(board[i][j].isWhite!=this.isWhite) { //check if the attacking piece is enemy
//						boolean[][] pieceAttacks=board[i][j].noPinMoveList(board); //checking enemy attack
//						addUnsafe(unsafeSpaces, pieceAttacks); //adding enemy possible attack
//					}
//				}
//			}
//		}
//		return unsafeSpaces;
//	}
	
	//adds attacked squares as an unsafe square to the current
//	private void addUnsafe(boolean[][] current, boolean[][] add) {
//		for(int i=0; i<current.length; i++) {
//			for(int j=0; j<current[0].length; j++) {
//				if(add[i][j]==true) {
//					current[i][j]=true;
//				}
//			}
//		}
//	}
	
	public boolean getInCheck() {
		return inCheck;
	}
	public void setInCheck(boolean inCheck) {
		this.inCheck=inCheck;
	}
	@Override
	public String toString() {
		String pieceStr;
		if(isWhite) {
			pieceStr="w.K";
		}
		else {
			pieceStr="b.K";
		}
		return pieceStr;
	}
	
	//returns list of all pieces attacking the king
	public ArrayList<Piece> listAttackingPieces(Piece[][] board){
		ArrayList<Piece> attacking=new ArrayList<>();
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				if(board[i][j]!=null && board[i][j].isWhite!=this.isWhite && board[i][j].noPinMoveList(board)[rowPos][colPos]==true) {
					attacking.add(board[i][j]);
				}
			}
		}
		return attacking;
	}

	//king can't be pinned, but this returns the moves that piece can make assuming no restrictions
	@Override
	public boolean[][] noPinMoveList(Piece[][] board) {
		boolean[][] moves=new boolean[board.length][board[0].length];
		//find all the possible moves for the king, however cannot move king into danger
		//check moves around the king
		for(int i=rowPos-1; i<=rowPos+1; i++) {
			for(int j=colPos-1; j<=colPos+1; j++) {
				if(i>=0 && j>=0 && i<board[0].length && j<board.length) { //if the move is within the boundaries
					//check if the move is legal, (not blocked by own piece, not moving into check)
					if(board[i][j]==null) {
						moves[i][j]=true;
					}
					else {
						if(board[i][j].isWhite!=this.isWhite) { //check if enemy piece, if not then can't move there
							moves[i][j]=true;
						}
					}
				}
			}
		}
		return moves;
	}
	
}
