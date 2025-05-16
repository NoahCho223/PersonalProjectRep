package consoleGame;

import game.*;
import java.util.Scanner;
import java.util.ArrayList;

public class ConsoleModel {
	private Board board;
	private Piece[][] boardState;
	private PlayState state;
	public ConsoleModel(PlayState state) {
		this.state=state;
		this.board=state.getBoard();
		this.boardState=board.getBoardState();
	}
	
	public void printBoard() { //print the board with text onto screen
		if(state.getP1Turn()) {
			System.out.println("White Turn: ");
		}
		else {
			System.out.println("Black Turn: ");
		}
		System.out.print(" ");
		for(int i=0; i<boardState[0].length; i++) {
			System.out.print(String.format("   %d", i));
		}
		System.out.println();
		System.out.print("  ");
		for(int k=0; k<boardState.length; k++) {
			System.out.print("----");
		}
		System.out.print("-");
		System.out.println();
		for(int i=0; i<boardState.length; i++) {
			System.out.print(String.format("%d ", i));
			for(int j=0; j<boardState[0].length; j++) {
				System.out.print("|");
				if(boardState[i][j]!=null) {
					System.out.print(boardState[i][j].toString());
				}
				else {
					System.out.print("   "); //print 3 spaces instead
				}
			}
			System.out.print("|");
			System.out.println();
			System.out.print("  ");
			for(int k=0; k<boardState.length; k++) {
				System.out.print("----");
			}
			System.out.print("-");
			System.out.println();
		}
	}
	
	
	//get user input to select square, and check if that square contains their colored piece
	public Piece userPickPiece() {
		boolean piecePicked=false;
		Piece pickedPiece=null;
		//check if the user picked a piece with same colour
		while(!piecePicked) {
			Scanner sc=new Scanner(System.in);
			int xSquare=0;
			int ySquare=0;
			System.out.print("Input x coordinate of piece: ");
			xSquare=sc.nextInt();
			System.out.print("Input y coordinate of piece: ");
			ySquare=sc.nextInt();
			if(xSquare>=0 && xSquare<boardState.length && ySquare>=0 && ySquare<boardState[0].length) {
				if(boardState[xSquare][ySquare]!=null && boardState[xSquare][ySquare].getIsWhite()==state.getP1Turn()) {
					pickedPiece=boardState[xSquare][ySquare];
					piecePicked=true;
				}
				else {
					System.out.println("Invalid Piece Chosen, try again");
				}
			}
			else {
				System.out.println("Invalid Piece Chosen, try again");
			}
		}
		System.out.println(String.format("Selected %s", pickedPiece.toString()));
		return pickedPiece;
	}
	
	//get user input for where to move piece, check if the move is legal
	public boolean getUserMoves(Piece p) {
		Scanner sc=new Scanner(System.in);
		int rowMove=0;
		int colMove=0;
		boolean moved=false;
		while(rowMove!=-1 && colMove!=-1 && !moved) {
			System.out.print("Possible moves: ");
			//print out the possible moves to console
			System.out.println(printPossibleMoves(state.moveList(p)));

			System.out.print("Input row coordinate to move piece, or -1 to cancel: ");
			rowMove=sc.nextInt();
			if(rowMove==-1) {
				continue;
			}
			System.out.print("Input col coordinate to move piece: or -1 to cancel: ");
			colMove=sc.nextInt();
			if(state.movePiece(p, rowMove, colMove)==false) { //if move was not legal, then need to ask user again
				System.out.println("Invalid Input! Try Again or Exit with -1");
			}
			else {
				if(p instanceof Pawn) { //if p is pawn, then check if the pawn is promoted after the move, if so then prompt user and transform piece
					if(((Pawn)p).getPromoted()==true) {
						boolean valid=false;
						while(!valid) {
							System.out.print("Pawn promoted! Choose the piece to promote to: (e.g Queen):  ");
							String promoteIn=sc.nextLine();
							valid=((Pawn)p).promotion(boardState, promoteIn);
							if(!valid) {
								System.out.println("Invalid Input, Type Again");
							}
						}
					}
				}
				moved=true;
			}
		}
		//if the piece was not moved, then the user exited from moving the piece
		return moved;
	}
	private String printPossibleMoves(boolean moveList[][]) {
		String possibleMoves="[";
		for(int i=0; i<moveList.length; i++) {
			for(int j=0; j<moveList[0].length; j++) {
				if(moveList[i][j]==true) {
					if(!possibleMoves.equals("[")) {
						possibleMoves+=" [";
					}
					possibleMoves+=String.format("%d, %d", i, j);
					possibleMoves+="]";
				}
			}
		}
		if(possibleMoves.equals("[")) {
			possibleMoves+="]";
		}
		return possibleMoves;
	}
	
	public boolean declareWinner() {
		if(state.getWinner()==1) {
			System.out.println("Checkmate! White Wins!");
			return true;
		}
		else if(state.getWinner()==2) {
			System.out.println("Checkmate! Black Wins!");
			return true;
		}
		else {
			return false;
		}
	}
	
}
