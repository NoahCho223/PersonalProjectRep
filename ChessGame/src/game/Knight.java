package game;

public class Knight extends Piece{

	public Knight(Piece[][] board, int rowPos, int colPos, boolean isWhite) {
		super(board, rowPos, colPos, isWhite);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateMoveList(Piece[][] board) {
		this.moves=getUpdatedMoveList(board);	
	}

	@Override
	public boolean[][] noPinMoveList(Piece[][] board) {
		boolean[][] moves=new boolean[board.length][board[0].length];
		//Flat L's
		for(int i=rowPos-1; i<=rowPos+1 && i<board.length; i=i+2) {
			for(int j=colPos-2; j<=colPos+2 && j<board[0].length; j=j+4) {
				if(i>=0 && j>=0) {
					if(board[i][j]==null || board[i][j].isWhite!=this.isWhite) {//check if enemy piece, if so then can move there
						moves[i][j]=true;
					}
				}
			}
		}
		//Tall L's
		for(int i=rowPos-2; i<=rowPos+2 && i<board.length; i=i+4) {
			for(int j=colPos-1; j<=colPos+1 && j<board[0].length; j=j+2) {
				if(i>=0 && j>=0) {
					if(board[i][j]==null || board[i][j].isWhite!=this.isWhite) {//check if enemy piece, if so then can move there
						moves[i][j]=true;
					}
				}
			}
		}
		return moves;
	}
	
	@Override
	public String toString() {
		String pieceStr;
		if(isWhite) {
			pieceStr="w.N";
		}
		else {
			pieceStr="b.N";
		}
		return pieceStr;
	}

}
