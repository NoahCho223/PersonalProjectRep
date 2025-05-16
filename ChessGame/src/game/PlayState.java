package game;

import java.util.ArrayList;

public class PlayState {
		private Board board;
		private boolean p1Turn;
		private int winner; //0 for no winner, 1 for p1 (white) winner, 2 for p2 winner
	//every turn the moveLists for each piece of the board is updated
	
		private Piece[][] boardState; //each index of the array represents a piece on the board
		
		public PlayState() {
			p1Turn=true; //initially start with player 1 first
			winner=0;
		}
		
		//returns whether moving the piece was successful, if so then becomes other players turn
		//use for main program with user input to check if valid, then main program calls method to move pieces or keeps asking for user input
		public boolean movePiece(Piece p, int rowMove, int colMove) {
			boolean moved=legalMove(p, rowMove, colMove);
			if(moved) { //if legal to move, then move the piece
				p.move(boardState, rowMove, colMove);
				p1Turn=!p1Turn;
			}
			return moved;
		}
		//helper method for movePiece()
		//returns whether or not move is legal
		private boolean legalMove(Piece p, int rowMove, int colMove) {
			boolean legal=false;
			boolean[][] legalMoves=moveList(p);
			//check if the move passed in is valid
			if(rowMove<boardState.length && rowMove>=0 && colMove<boardState[0].length && colMove>=0 && legalMoves[rowMove][colMove]==true) {
				legal=true;
			}
			return legal;
		}
		//return possible moves for the current player and current piece
		public boolean[][] moveList(Piece p){
			boolean moves[][];
			Piece king;
			if(p1Turn) {
				king=board.getWhiteKing();
			}
			else {
				king=board.getBlackKing();
			}
			//if the king is in check, then can only move to block or capture attacking piece
			if(inCheck()) { //if the king in check
				if(p instanceof King) { //return the king's moves
					moves=p.getMoveList();
				}
				else {//code for when moving piece other than king
					ArrayList<Piece> attacking=((King)king).listAttackingPieces(boardState);
					moves=p.getMoveList();
					if(attacking.size()==1) {
						//get the updated move list, and remove any move that doesn't block or capture attacking piece
						boolean[][] enemySight=attacking.get(0).getMoveList();
						
						for(int i=0; i<boardState.length; i++) {
							for(int j=0; j<boardState[0].length; j++) {
								//temporarily move defending piece, and then check if the king is still in danger
								if(moves[i][j] && (enemySight[i][j] || boardState[i][j]==attacking.get(0))) { //if piece can move to enemy sight space, check if doing so will stop check
									int tempRow=p.getRowPos();
									int tempCol=p.getColPos();
									Piece temp=p;
									Piece attackTemp=boardState[i][j];
									boardState[i][j]=temp;
									boardState[tempRow][tempCol]=null;
									if(!((King)king).listAttackingPieces(boardState).isEmpty()) {//if king is still attacked, can't move there
										moves[i][j]=false;
									}
									boardState[i][j]=attackTemp;
									boardState[tempRow][tempCol]=temp;
								}
								else {
									moves[i][j]=false; //if the possible move does not move to any space of the attack, then can't move there
								}
							}
						}
					}
					else { //if the # of attacking pieces is more than 1, then only king can move
						moves=new boolean[boardState.length][boardState[0].length];
					}
				}
			}
			else {
				moves=p.getMoveList();
			}
			
			return moves;
		}
		//every turn the list of possible moves are updated for each piece
		public void updateMoveList() {
			//update every piece's moves
			for(int i=0; i<boardState.length; i++) {
				for(int j=0; j<boardState[0].length; j++) {
					if(boardState[i][j]!=null) {
						boardState[i][j].updateMoveList(boardState);
					}
				}
			}
			//check if the king in this turn is in check
			King king;
			if(p1Turn) {
				king=(King)board.getWhiteKing();
			}
			else {
				king=(King)board.getBlackKing();
			}
			if(king.listAttackingPieces(boardState).size()!=0) {
				king.setInCheck(true);
				if(isCheckmated()) {
					if(p1Turn) {
						winner=2;
					}
					else {
						winner=1;
					}
				}
			}
			else {
				king.setInCheck(false);
			}
		}
		private boolean isCheckmated() {
			//loop through every piece, check if the piece is friendly, and if so, then check if they include any movements, if not then checkmated
			boolean checkMated=false;
			if(inCheck()) {
				checkMated=true;
				for(int i=0; i<boardState.length; i++) {
					for(int j=0; j<boardState[0].length; j++) {
						if(boardState[i][j]!=null && boardState[i][j].getIsWhite()==p1Turn) {
							boolean[][] friendMove=moveList(boardState[i][j]);
							for(boolean[] moveAbleRow : friendMove) {
								for(boolean moveAble: moveAbleRow) {
									if(moveAble) {
										checkMated=false;
									}
								}
							}
						}
					}
				}
			}
			return checkMated;
		}
		public void initializeStandardGame(int width, int length) {
			board=new Board(width, length);
			board.arrangeStandardGame();
			boardState=board.getBoardState();
		}
		
		public boolean inCheck() {
			if(p1Turn) {
				return ((King)board.getWhiteKing()).getInCheck();
			}
			else {
				return ((King)board.getBlackKing()).getInCheck();
			}
		}
		public Board getBoard() {
			return this.board;
		}
		public boolean getP1Turn() {
			return this.p1Turn;
		}
		public int getWinner() {
			return this.winner;
		}
}
