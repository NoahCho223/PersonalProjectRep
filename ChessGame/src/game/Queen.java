package game;

public class Queen extends Piece{

	public Queen(Piece[][] board, int xPos, int yPos, boolean isWhite) {
		super(board, xPos, yPos, isWhite);
		// TODO Auto-generated constructor stub
	}

	//updates the move list every turn
	@Override
	public void updateMoveList(Piece[][] board) {
		this.moves=getUpdatedMoveList(board);
	}

	public boolean[][] noPinMoveList(Piece[][] board){
		boolean[][] moves=new boolean[board.length][board[0].length];
		boolean obstruction=false;
		//check the possible moves for the Queen, left, right, top down
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

		//check diagonal movement
		obstruction=false;
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
			pieceStr="w.Q";
		}
		else {
			pieceStr="b.Q";
		}
		return pieceStr;
	}
}
