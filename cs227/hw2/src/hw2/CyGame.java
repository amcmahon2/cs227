package hw2;

/**
 * Model of a Monopoly-like game. Two players take turns rolling dice to move
 * around a board. The game ends when one of the players has at least
 * MONEY_TO_WIN money or one of the players goes bankrupt (has negative money).
 * 
 * @author Andrew McMahon
 */
public class CyGame {
	/**
	 * Do nothing square type.
	 */
	public static final int DO_NOTHING = 0;
	/**
	 * Pass go square type.
	 */
	public static final int PASS_GO = 1;
	/**
	 * Cyclone square type.
	 */
	public static final int CYCLONE = 2;
	/**
	 * Pay the other player square type.
	 */
	public static final int PAY_PLAYER = 3;
	/**
	 * Get an extra turn square type.
	 */
	public static final int EXTRA_TURN = 4;
	/**
	 * Jump forward square type.
	 */
	public static final int JUMP_FORWARD = 5;
	/**
	 * Stuck square type.
	 */
	public static final int STUCK = 6;
	/**
	 * Points awarded when landing on or passing over go.
	 */
	public static final int PASS_GO_PRIZE = 200;
	/**
	 * The amount payed to the other player per unit when landing on a
	 * PAY_PLAYER square.
	 */
	public static final int PAYMENT_PER_UNIT = 20;
	/**
	 * The amount of money required to win.
	 */
	public static final int MONEY_TO_WIN = 400;
	/**
	 * The cost of one unit.
	 */
	public static final int UNIT_COST = 50;


	/**
	 * currentPlayer is an int value used to keep track
	 * of the current player in the CyGame.
	 */
	private int currentPlayer = 1;

	/**
	 * currentPlayer is a boolean value used to store as well as check for the status of 
	 * the game, "true" for the game is ended, and "false" for the game is still ongoing.
	 * In the code below, it can be seen that this value is checked and set at multiple points,
	 * which is to ensure the game cannot continue when it is supposed to have ended.
	 */
	private boolean gameEnd;

	/**
	 * p1Money is an int value used to quantify and keep track of player 1's money.
	 */
	private int p1Money = 0;

	/**
	 * p2Money is an int value used to quantify and keep track of player 2's money.
	 */
	private int p2Money = 0;

	/**
	 * p1CurrentSquare is an int value used to keep track of player 1's current square.
	 */
	private int p1CurrentSquare = 0;

	/**
	 * p2CurrentSquare is an int value used to keep track of player 2's current square.
	 */
	private int p2CurrentSquare = 0;

	/**
	 * p1Units is an int value used to keep track of player 1's quantity of property units.
	 */
	private int p1Units = 1;

	/**
	 * p2Units is an int value used to keep track of player 2's quantity of property units.
	 */
	private int p2Units = 1;

	/**
	 * numSquares is an int value used to store the user-requested size of the board game,
	 * from 1 to any int amount.
	 */
	private int numSquares;

	/**
	 * p1HasPassedGo is a boolean value used to store whether player 1 has passed go in the
	 * current turn; is used to ensure PASS_GO_PRIZE cannot be collected twice in the
	 * same turn by player 1
	 */
	private boolean p1HasPassedGo = false;

	/**
	 * p2HasPassedGo is a boolean value used to store whether player 2 has passed go in the
	 * current turn; is used to ensure PASS_GO_PRIZE cannot be collected twice in the
	 * same turn by player 2
	 */
	private boolean p2HasPassedGo = false;

	/**
	 * p1IsStuck is a boolean value used to store whether player 1 has landed on a STUCK
	 * type square. If player 1 has landed on a stuck square, this value is set to "true,"
	 * and stays "true" until a roll by player 1 has an even value, which then makes this variable "false."
	 */
	private boolean p1IsStuck = false;

	/**
	 * p2IsStuck is a boolean value used to store whether player 2 has landed on a STUCK
	 * type square. If player 2 has landed on a stuck square, this value is set to "true,"
	 * and stays "true" until a roll by player 2 has an even value, which then makes this variable "false."
	 */
	private boolean p2IsStuck = false;

	/**
	 * This constructor is used to make creating CyGame objects possible. This
	 * constructor takes user-requested values numSquares and startingMoney and 
	 * assigns it to the user-created CyGame object.
	 * 
	 * @param numSquares is an int value which defines the number of squares
	 * 		  in the particular CyGame object.
	 * @param startingMoney is an int value which define the starting money of
	 * 		  both players in the game (is used to set p1Money and p2Money equal to the requested starting amount)
	 */
	public CyGame(int numSquares, int startingMoney){
		this.numSquares = numSquares;
		p1Money = startingMoney;
		p2Money = startingMoney;
		if(p1Money < 0 || p2Money < 0 || p1Money >= MONEY_TO_WIN || p2Money >= MONEY_TO_WIN) {
			gameEnd = true;
		}

	}

	/**
	Method called to indicate the current player attempts to buy one unit. The purchase is only allowed if the player is 
	currently on a square type of DO_NOTHING and has sufficient money to buy one unit at UNIT_COST. If allowed, subtract 
	the cost from the player's money and increment the player's number of units by one.
	If the current player successfully buys a unit their turn has ends (they are not allowed to take any further action or roll the dice). 
	Update the turn to the other player. This method does nothing if the game has ended. 
	 **/
	public void buyUnit(){
		if(p1Money < 0 || p2Money < 0 || p1Money >= MONEY_TO_WIN || p2Money >= MONEY_TO_WIN|| gameEnd == true) {
			gameEnd = true;
		}
		else {
			if(currentPlayer == 1 &&p1Money >= UNIT_COST && p1CurrentSquare-1 == DO_NOTHING){
				p1Money -= UNIT_COST;
				p1Units++;
				endTurn();
			}
			else if(currentPlayer == 2 && p2Money >= UNIT_COST && p2CurrentSquare-1 == DO_NOTHING){
				p2Money -= UNIT_COST;
				p2Units++;
				endTurn();
			}
			if(p1Money < 0 || p2Money < 0){
				gameEnd = true;
			}
		}
	}


	/**
	Method called to indicate the current player attempts to sell one unit. The sale is only allowed only if the player has at 
	least one unit to sell. If allowed, pay the current player UNIT_COST and decrease the player's number of units by one.
	If the current player successfully sells a unit their turn ends (they are not allowed to take any further action or roll the dice). 
	Update the turn to the other player. This method does nothing if the game has ended.
	 */
	public void sellUnit(){
		if(p1Money < 0 || p2Money < 0 || p1Money >= MONEY_TO_WIN || p2Money >= MONEY_TO_WIN || gameEnd == true) {
			gameEnd = true;
		}
		else {
			if(currentPlayer == 1 && p1Units > 0){
				p1Money += UNIT_COST;
				p1Units--;
				endTurn();
			}
			else if(currentPlayer == 2 && p2Units > 0){
				p2Money += UNIT_COST;
				p2Units--;
				endTurn();
			}
		}
	}

	/**
	 * Method called to indicate the current player passes or completes their turn. 
	 * Change the current turn from Player 1 to Player 2 or vice versa.
	 */
	public void endTurn(){
		if(currentPlayer == 1) {
			currentPlayer = 2;
		}
		else {
			currentPlayer = 1;
		}

		if(p1Money < 0 || p2Money < 0 || p1Money >= MONEY_TO_WIN || p2Money >= MONEY_TO_WIN) {
			gameEnd = true;
		}
	}

	/**
	 * This method is used to get the current player of the CyGame.
	 * @return 1 if currentPlayer is 1, 2 if otherwise.
	 */
	public int getCurrentPlayer(){
		return currentPlayer;
	}

	/**
	 * This method is used to get the current money for player 1. This method also checks
	 * if either player 1 or player 2's money is below 0 or above the MONEY_TO_WIN, if any of the
	 * 4 cases are true, the game is "ended." Then, the money for player 1 is returned, no matter the status
	 * of the CyGame. 
	 * @return int p1Money (player 1's money)
	 */
	public int getPlayer1Money(){
		if(p1Money < 0 || p2Money < 0 || p1Money >= MONEY_TO_WIN || p2Money >= MONEY_TO_WIN) {
			gameEnd = true;
		}
		return p1Money;
	}

	/**
	 * This method is used to get the current square player 1 is on.
	 * @return int p1CurrentSquare (player 1's current square)
	 */
	public int getPlayer1Square(){
		return p1CurrentSquare;
	}

	/**
	 * This method is used to get the current amount of units player 1 has.
	 * @return int p1Units (player 1's current amount of units)
	 */
	public int getPlayer1Units(){
		return p1Units;
	}

	/**
	 * This method is used to get the current money for player 2. This method also checks
	 * if either player 1 or player 2's money is below 0 or above the MONEY_TO_WIN, if any of the
	 * 4 cases are true, the game is "ended." Then, the money for player 2 is returned, no matter the status
	 * of the CyGame. 
	 * @return int p2Money (player 2's money)
	 */
	public int getPlayer2Money(){
		if(p1Money < 0 || p2Money < 0 || p1Money >= MONEY_TO_WIN || p2Money >= MONEY_TO_WIN) {
			gameEnd = true;
		}
		return p2Money;
	}

	/**
	 * This method is used to get the current square player 2 is on.
	 * @return int p2CurrentSquare (player 2's current square)
	 */
	public int getPlayer2Square(){
		return p2CurrentSquare;
	}

	/**
	 * This method is used to get the current amount of units player 2 has.
	 * @return int p1Units (player 2's current amount of units)
	 */
	public int getPlayer2Units(){
		return p2Units;
	}

	/**
	 * This method is used to get the square type of a user-requested square on a CyGame board.
	 * The description of how to determine each square's type can be found below: 
	 * 
	 * Square 0 is type PASS_GO.
	 * The very last square (before wrapping back to 0) is CYCLONE.
	 * Every 5th square (e.g., 5, 10, 15...) is PAY_PLAYER.
	 * Every 7th and every 11th square is EXTRA_TURN.
	 * Every 3rd square is STUCK.
	 * Every 2nd square is JUMP_FORWARD.
	 * All remaining squares are DO_NOTHING.
	 * 
	 * @param square is an int value which is taken in by getSquareType and used to determine
	 * 	      what square type the param "square" is.
	 * @return one of the final static int values declared in the JavaDoc, where
	 *         it is used in the "roll" method to determine what action should happen after landing on a 
	 *         certain square
	 */
	public int getSquareType(int square){
		if(square == 0 || square == numSquares){
			return PASS_GO;
		}
		else if(square == numSquares - 1){
			return CYCLONE;
		}
		else if(square % 5 == 0){
			return PAY_PLAYER;
		}	
		else if(square % 7 == 0 || square % 11 == 0){
			return EXTRA_TURN;
		}
		else if(square%3 == 0){
			return STUCK;
		}
		else if(square%2 == 0) {
			return JUMP_FORWARD;
		}
		else {
			return DO_NOTHING;
		}

	}

	/**
	 * Returns true if game is over, false otherwise. The game is over when either player has at least MONEY_TO_WIN money or either player has a 	    	 * negative amount of money.
	 * @return true if game is over, false if the game is not over
	 */
	public boolean isGameEnded(){
		return gameEnd;
	}

	/**
	 * This method is used to take a user-requested dice roll and move the current player by the number
	 * requested (which theoretically would be any whole number between 1 and 6, inclusive). This
	 * roll value is then used to determine what happens to the player. The action taken is dependent on 
	 * getSquareType, where the square type is returned, stored as a currentSquareTypevariable, and then used to determine
	 * what square the current player is on, and what happens to the player. Dependent on the resultant square of the player,
	 * the turn is either passed to the other player, or kept (EXTRA_TURN). All outcomes, or each of the square types and 
	 * their effects on the game, can be viewed in the JavaDoc for CyGame.
	 * 
	 * This method also checks at practically every point to see if the game has ended, using an "if" statement to 
	 * determine whether the game-ending conditions have been met.
	 * 
	 * @param value is an int value used to quantify the value of the roll (1-6, per a standard die).
	 */
	public void roll(int value){
		//checks for game being ended, if the game has ended, it stops the "roll" method from being implemented, otherwise
		//the "roll" method is continued.
		if(gameEnd == true || p1Money < 0 || p2Money < 0 || p1Money >= MONEY_TO_WIN || p2Money >= MONEY_TO_WIN ){
			gameEnd = true;
		}
		else{
			//checks for current player
			if(currentPlayer == 1){
				//checks to make sure player 1 is not stuck, however, if the player is stuck, the method will continue 
				//if the roll is of an even number.
				if(p1IsStuck == false || value%2 == 0){
					//finds the raw number of squares, which can be greater than the board's length. 
					int squareTotal = p1CurrentSquare + value;
					p1CurrentSquare = (p1CurrentSquare+value) % numSquares;
					int currentSquareTypeP1 = getSquareType(p1CurrentSquare);
					//this "if" statement is crucial, as it awards the PASS_GO_PRIZE if player 1 has not yet passed go, and the current 
					//square player 1 is on is of type PASS_GO or if the square player 1 is on is larger than the board's length, meaning 
					//player 1 has passed go.
					if((currentSquareTypeP1 == PASS_GO && p1HasPassedGo == false) || (squareTotal > numSquares -1 && p1HasPassedGo == false)) {
						p1Money += PASS_GO_PRIZE;
						p1HasPassedGo = true;
						if(p1Money >= MONEY_TO_WIN) {
							gameEnd = true;
						}
					}
					else if(currentSquareTypeP1 == CYCLONE) {
						p1CurrentSquare = p2CurrentSquare;
					}
					else if(currentSquareTypeP1 == PAY_PLAYER){
						p1Money -= (PAYMENT_PER_UNIT) * p2Units;
						p2Money += (PAYMENT_PER_UNIT) * p2Units;

						if(p1Money < 0 || p2Money >= MONEY_TO_WIN){
							gameEnd = true;
						}
					}
					else if(currentSquareTypeP1 == STUCK){
						p1IsStuck = true;
					}
					//this "if" statement is used to award the PASS_GO_PRIZE if the player has not passed go this turn,
					//and if the current square of the player after the jump is past the board's length, meaning the 
					//player has completed a full rotation around the board.
					else if(currentSquareTypeP1 == JUMP_FORWARD){		
						if(p1CurrentSquare + 4 >= numSquares && p1HasPassedGo == false){
							p1Money += PASS_GO_PRIZE;
							p1HasPassedGo = true;
						}
						//resets player square to actual value
						p1CurrentSquare = (p1CurrentSquare+4) % numSquares;

						if(STUCK == getSquareType(p1CurrentSquare)){
							p1IsStuck = true;
						}

					}	
					//this statement gives the current player another turn if the square the player is on's value
					//is equal to the EXTRA_TURN type square value.
					if(currentSquareTypeP1 == EXTRA_TURN){
						currentPlayer = 1;
					}
					else {
						endTurn();
					}

				}	
				else{
					p1IsStuck = true;
					endTurn();
				}
			}
			//checks for current player
			else if(currentPlayer == 2){
				//checks to make sure player 2 is not stuck, however, if the player is stuck, the method will continue 
				//if the roll is of an even number.
				if(p2IsStuck == false || value%2 == 0){
					//finds the raw number of squares, which can be greater than the board's length. 
					int squareTotal2 = p2CurrentSquare + value;
					p2CurrentSquare = (p2CurrentSquare+value) % numSquares;
					int currentSquareTypeP2 = getSquareType(p2CurrentSquare);
					//this "if" statement is crucial, as it awards the PASS_GO_PRIZE if player 2 has not yet passed go, and the current 
					//square player 2 is on is of type PASS_GO or if the square player 2 is on is larger than the board's length, meaning 
					//player 2 has passed go.
					if((currentSquareTypeP2 == PASS_GO && p2HasPassedGo == false) || (squareTotal2 > numSquares-1 && p2HasPassedGo == false)) {
						p2Money += PASS_GO_PRIZE;
						p2HasPassedGo = true;
						if(p2Money >= MONEY_TO_WIN) {
							gameEnd = true;
						}
					}
					else if(currentSquareTypeP2 == CYCLONE) {
						p2CurrentSquare = p1CurrentSquare;
					}
					else if(currentSquareTypeP2 == PAY_PLAYER){
						p2Money = (p2Money) - ((PAYMENT_PER_UNIT)*p1Units);
						p1Money = (p1Money) + ((PAYMENT_PER_UNIT)*p1Units);

						if(p2Money < 0 || p1Money >= MONEY_TO_WIN){
							gameEnd = true;
						}
					}
					else if(currentSquareTypeP2 == STUCK){
						p2IsStuck = true;
					}
					//this "if" statement is used to award the PASS_GO_PRIZE if the player has not passed go this turn,
					//and if the current square of the player after the jump is past the board's length, meaning the 
					//player has completed a full rotation around the board.
					else if(currentSquareTypeP2 == JUMP_FORWARD){
						if(p2CurrentSquare + 4 >= numSquares && p2HasPassedGo == false){
							p2Money += PASS_GO_PRIZE;
							p2HasPassedGo = true;
						}	
						//resets player square to actual value
						p2CurrentSquare = (p2CurrentSquare+4) % numSquares;
						if(STUCK == getSquareType(p2CurrentSquare)){
							p2IsStuck = true;
						}

					}	
					//this statement gives the current player another turn if the square the player is on's value
					//is equal to the EXTRA_TURN type square value.
					if(currentSquareTypeP2 == EXTRA_TURN){
						currentPlayer = 2;
					}
					else {
						endTurn();
					}

				}
				else{
					p2IsStuck = true;
					endTurn();
				}
			}

		}

	}


	/**
	 * Returns a one-line string representation of the current game state. The
	 * format is:
	 * <p>
	 * <tt>Player 1*: (0, 0, $0) Player 2: (0, 0, $0)</tt>
	 * <p>
	 * The asterisks next to the player's name indicates which players turn it
	 * is. The numbers (0, 0, $0) indicate which square the player is on, how
	 * many units the player has, and how much money the player has
	 * respectively.
	 * 
	 * @return one-line string representation of the game state
	 */
	public String toString(){
		String fmt = "Player 1%s: (%d, %d, $%d) Player 2%s: (%d, %d, $%d)";
		String player1Turn = "";
		String player2Turn = "";
		if (getCurrentPlayer() == 1){
			player1Turn = "*";
		} 

		else{
			player2Turn = "*";
		}
		return String.format(fmt,
				player1Turn, getPlayer1Square(), getPlayer1Units(), getPlayer1Money(),
				player2Turn, getPlayer2Square(), getPlayer2Units(), getPlayer2Money());
	}
}