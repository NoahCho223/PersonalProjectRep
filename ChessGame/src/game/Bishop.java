package game;

public class Bishop extends Piece{

	public Bishop(Piece[][] board, int rowPos, int colPos, boolean isWhite) {
		super(board, rowPos, colPos, isWhite);
	}

	//updates the move list every turn
	@Override
	public void updateMoveList(Piece[][] board) {
		this.moves=getUpdatedMoveList(board);
	}
	
	@Override
	public boolean[][] noPinMoveList(Piece[][] board) {
		boolean[][] moves=new boolean[board.length][board[0].length];
		//check diagonal movement
		boolean obstruction=false;
		for(int i=rowPos-1, j=colPos-1; i>=0 && j>=0 && !obstruction; i--, j--) { //Top left
			if(board[i][j]==null || board[i][j].isWhite!=this.isWhite) {//check if enemy piece, if so then can move there
				moves[i][j]=true;
			}
			if(board[i][j]!=null) {
				obstruction=true;
			}
		}
		obstruction=false;
		for(int i=rowPos-1, j=colPos+1; j<board[0].length && i>=0 && !obstruction; j++, i--) { //Top Right
			if(board[i][j]==null || board[i][j].isWhite!=this.isWhite) {//check if enemy piece, if so then can move there
				moves[i][j]=true;
			}
			if(board[i][j]!=null) {
				obstruction=true;
			}
		}
		obstruction=false;
		for(int i=rowPos+1, j=colPos-1; j>=0 && i<board.length && !obstruction; j--, i++) { //Bottom left
			if(board[i][j]==null || board[i][j].isWhite!=this.isWhite) {//check if enemy piece, if so then can move there
				moves[i][j]=true;
			}
			if(board[i][j]!=null) {
				obstruction=true;
			}
		}
		obstruction=false;
		for(int i=rowPos+1, j=colPos+1; i<board.length && j<board[0].length && !obstruction; i++, j++) { //Bottom right
			if(board[i][j]==null || board[i][j].isWhite!=this.isWhite) {//check if enemy piece, if so then can move there
				moves[i][j]=true;
			}
			if(board[i][j]!=null) {
				obstruction=true;
			}
		}
		return moves;
	}
	
	@Override
	public String toString() {
		String pieceStr;
		if(isWhite) {
			pieceStr="w.B";
		}
		else {
			pieceStr="b.B";
		}
		return pieceStr;
	}

}
