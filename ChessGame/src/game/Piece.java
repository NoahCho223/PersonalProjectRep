package game;

import java.util.ArrayList;
public abstract class Piece {
	
	protected boolean[][] moves;
	protected boolean isWhite;
	protected boolean taken;
	protected int rowPos;
	protected int colPos;
	protected boolean pinned;
	public final int WIDTH;
	public final int LENGTH;
	
	public Piece(Piece[][] board, int rowPos, int colPos, boolean isWhite) {
		this.moves=new boolean[board.length][board[0].length];
		this.rowPos=rowPos;
		this.colPos=colPos;
		//change the usage of board.length to these final ints when time is available
		WIDTH=board.length;
		LENGTH=board[0].length;
		this.isWhite=isWhite;
	}
	
	//moves the piece and replaces enemy piece when moving to square (captured)
	public void move(Piece[][] board, int newRowPos, int newColPos) {
		board[newRowPos][newColPos]=this; //replaces the piece (if any) where this piece moves
		board[rowPos][colPos]=null; //set old coordinates to not contain piece
		rowPos=newRowPos;
		colPos=newColPos;
	}
	
	public abstract void updateMoveList(Piece[][] board);
	
	public boolean[][] getMoveList(){
		return moves;
	}
	
	public boolean[][] getUpdatedMoveList(Piece[][] board){
		boolean[][] moves=new boolean[board.length][board[0].length];
		updatePinned(board);
		if(!pinned) { //if the piece is pinned, then can't move, otherwise, proceed as normal
			moves=noPinMoveList(board);
		}
		else {
			//check moves that still block the enemy attack on king
			King king=(King)findPiece(board, King.class, this.isWhite);
			//temporarily move the piece to each of its nopin squares
			boolean[][] noPinMovement=this.noPinMoveList(board);
			for(int i=0; i<board.length; i++) {
				for(int j=0; j<board[0].length; j++) {
					if(noPinMovement[i][j]) {
						Piece temp=board[rowPos][colPos];
						Piece moveToTemp=board[i][j];
						board[rowPos][colPos]=null;
						board[i][j]=temp;
						int numAttackers=king.listAttackingPieces(board).size();
						if(numAttackers==0) {
							moves[i][j]=true;
						}
						board[rowPos][colPos]=temp;
						board[i][j]=moveToTemp;
					}
				}
			}
		}
		return moves;
	}
	
	public abstract boolean[][] noPinMoveList(Piece[][] board);
	
	public int getRowPos() {
		return rowPos;
	}
	public int getColPos() {
		return colPos;
	}
	public boolean getIsWhite() {
		return isWhite;
	}
	
	//find any piece with specified colour
	public <E> Piece findPiece(Piece[][] board, Class<?> clazz, boolean isWhite) {
		Piece piece=null;
		boolean foundPiece=false;
		for(int i=0; i<board.length && !foundPiece; i++) {
			for(int j=0; j<board[0].length &&!foundPiece; j++) {
				if(board[i][j]!=null && board[i][j].getClass()==clazz && isWhite==board[i][j].getIsWhite()) { //if friendly king then found it
					piece=board[i][j];
					foundPiece=true;
				}
			}
		}
		return piece;
		
	}
	
	//check if the piece is pinned, update this for every turn
		public void updatePinned(Piece[][] board) {
			/*Check rooks, bishops, and queen, as they are the only pieces that can pin. Check their 
			 *check if removing this piece out of the way causes discovered check
			 */
			pinned=false;
			//get the x coordinate and y coordinate of the friendly king
			Piece king=findPiece(board, King.class, this.isWhite);
			int kingXCoor=king.getRowPos();
			int kingYCoor=king.getColPos();
			for(int i=0; i<board.length && !pinned; i++) {
				for(int j=0; j<board.length && !pinned; j++) {
					if(board[i][j]!=null && this.isWhite!=board[i][j].isWhite) {
						if(board[i][j] instanceof Rook || board[i][j] instanceof Bishop || board[i][j] instanceof Queen) {
							//get the movelist of rook with the potentially pinned piece out of the way
							Piece temp=this;
							board[rowPos][colPos]=null;
							boolean[][] enemyMoveListnoBlock=board[i][j].noPinMoveList(board);
							//restore the removed piece on the board
							board[rowPos][colPos]=temp;
							boolean[][] enemyMoveListWithBlock=board[i][j].noPinMoveList(board);
							//if the king is not in danger with block, and king is in danger when block removed then piece is pinned
							if(enemyMoveListnoBlock[kingXCoor][kingYCoor]==true && enemyMoveListWithBlock[kingXCoor][kingYCoor]==false) {
								this.pinned=true;
							}
						}
					}
				}
			}
		}
	
}
