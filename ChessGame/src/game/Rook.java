package game;

public class Rook extends Piece{

	
	
	public Rook(Piece[][] board, int xPos, int yPos, boolean isWhite) {
		super(board, xPos, yPos, isWhite);
	}

	//updates the move list every turn
	@Override
	public void updateMoveList(Piece[][] board) {
		this.moves=getUpdatedMoveList(board);
	}

	
	@Override
	public boolean[][] noPinMoveList(Piece[][] board) {
		boolean[][] moves=new boolean[board.length][board[0].length];
		boolean obstruction=false;
		//check the possible moves for the rook, left, right, top down
		for(int i=rowPos+1; i<board.length && !obstruction; i++) { //piece-to-bottom
			if(board[i][colPos]==null || board[i][colPos].isWhite!=this.isWhite) {//check if enemy piece, if so then can move there
				moves[i][colPos]=true;
			}
			if(board[i][colPos]!=null && board[i][colPos]!=this) {
				obstruction=true;
			}
		}
		obstruction=false;
		for(int i=rowPos-1; i>=0 && !obstruction; i--) { //piece-to-top
			if(board[i][colPos]==null || board[i][colPos].isWhite!=this.isWhite) {//check if enemy piece, if so then can move there
				moves[i][colPos]=true;
			}
			if(board[i][colPos]!=null && board[i][colPos]!=this) {
				obstruction=true;
			}
		}
		obstruction=false;
		for(int i=colPos-1; i>=0 && !obstruction; i--) { //piece-to-left
			if(board[rowPos][i]==null || board[rowPos][i].isWhite!=this.isWhite) {//check if enemy piece, if so then can move there
				moves[rowPos][i]=true;
			}
			if(board[rowPos][i]!=null && board[rowPos][i]!=this) {
				obstruction=true;
			}
		}
		obstruction=false;
		for(int i=colPos+1; i<board[0].length && !obstruction; i++) { //piece-to-Right
			if(board[rowPos][i]==null || board[rowPos][i].isWhite!=this.isWhite) {//check if enemy piece, if so then can move there
				moves[rowPos][i]=true;
			}
			if(board[rowPos][i]!=null && board[rowPos][i]!=this) {
				obstruction=true;
			}
		}
		return moves;
	}
	
	@Override
	public String toString() {
		String pieceStr;
		if(isWhite) {
			pieceStr="w.R";
		}
		else {
			pieceStr="b.R";
		}
		return pieceStr;
	}
	
}
