package consoleGame;

import game.*;

//console game implementation of chess game
public class ConsoleMain {

	private static final int WIDTH=8;
	private static final int LENGTH=8;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PlayState state=new PlayState();
		state.initializeStandardGame(WIDTH, LENGTH);
		ConsoleModel model=new ConsoleModel(state);
		boolean exit=false;
		//Start the game
		while(!exit) {
			model.printBoard();
			state.updateMoveList();
			if(model.declareWinner()) {
				exit=true;
			}
			else {
				Piece chosenPiece=model.userPickPiece();
				model.getUserMoves(chosenPiece);
			}
		}
	}
}
