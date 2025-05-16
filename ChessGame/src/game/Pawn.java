package game;

public class Pawn extends Piece{

	private boolean moved; //used for the double move
	private boolean promoted;
	public Pawn(Piece[][] board, int rowPos, int colPos, boolean isWhite) {
		super(board, rowPos, colPos, isWhite);
		moved=false;
		promoted=false;
	}

	public void move(Piece[][] board, int newRowPos, int newColPos) {
		super.move(board, newRowPos, newColPos);
		this.moved=true;
		if(this.isWhite) {
			if(rowPos==7) {
				promoted=true;
			}
		}
		else {
			if(rowPos==0) {
				promoted=true;
			}
		}
	}
	
	@Override
	public void updateMoveList(Piece[][] board) {
		this.moves=getUpdatedMoveList(board);
	}

	@Override
	public boolean[][] noPinMoveList(Piece[][] board) {
		boolean[][] moves=new boolean[board.length][board[0].length];
		//movement for white pawn
		if(rowPos+1<board.length && board[rowPos+1][colPos]==null && this.isWhite==true) { //up and down movement, cannot move onto enemy pieces
			moves[rowPos+1][colPos]=true;
		}
		if(!moved && rowPos+2<board.length && board[rowPos+2][colPos]==null && isWhite) {
			moves[rowPos+2][colPos]=true;
		}
		//diagonal attacking moves for white
		if(isWhite) {
			for(int i=colPos-1; i<=colPos+1; i=i+2) {
				if(i>=0 && i<board[0].length) {
					if(rowPos+1<board.length && board[rowPos+1][i]!=null && board[rowPos+1][i].getIsWhite()!=this.isWhite) {
						moves[rowPos+1][i]=true;
					}
				}
			}
		}
		//movement for black pawn
		if(rowPos-1>=0 && board[rowPos-1][colPos]==null && this.isWhite==false) { //up and down movement, cannot move onto enemy pieces
			moves[rowPos-1][colPos]=true;
		}
		if(rowPos-2>=0 && !moved && board[rowPos-2][colPos]==null && !isWhite) {
			moves[rowPos-2][colPos]=true;
		}
		//diagonal attacking for black
		if(!isWhite) {
			for(int i=colPos-1; i<=colPos+1; i=i+2) {
				if(i>=0 && i<board[0].length) {
					if(rowPos-1>=0 && board[rowPos-1][i]!=null && board[rowPos-1][i].getIsWhite()!=this.isWhite) {
						moves[rowPos-1][i]=true;
					}
				}
			}
		}
		return moves;
	}
	
	public boolean promotion(Piece[][] board, String promotionInput) {
		boolean valid=false;
		if(promotionInput.equals("Queen")) {
			board[rowPos][colPos]=new Queen(board, rowPos, colPos, this.isWhite);
			valid=true;
		}
		else if(promotionInput.equals("Knight")) {
			board[rowPos][colPos]=new Knight(board, rowPos, colPos, this.isWhite);
			valid=true;
		}
		else if(promotionInput.equals("Rook")) {
			board[rowPos][colPos]=new Rook(board, rowPos, colPos, this.isWhite);
			valid=true;
		}
		else if(promotionInput.equals("Bishop")) {
			board[rowPos][colPos]=new Bishop(board, rowPos, colPos, this.isWhite);
			valid=true;
		}
		else {
			valid=false;
		}
		return valid;
	}
	
	@Override
	public String toString() {
		String pieceStr;
		if(isWhite) {
			pieceStr="w.P";
		}
		else {
			pieceStr="b.P";
		}
		return pieceStr;
	}
	
	public boolean getPromoted() {
		return this.promoted;
	}
	public void setMoved(boolean moved) {
		this.moved=moved;
	}
	public void setPromoted(boolean promoted) {
		this.promoted=promoted;
	}

}
