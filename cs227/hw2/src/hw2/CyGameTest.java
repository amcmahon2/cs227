package hw2;

public class CyGameTest {
public static void main(String args[]) {
	
	CyGame game = new CyGame(16, 200); 
	game.buyUnit();
	game.roll(5);
	System.out.println(game);
	//Message: Player1 has 2 units and $150, Player2 lands on PAY_PLAYER
	//square with $200, Player1 money should be $190 and
	//Player2 should be $160 expected:<190> but was:<170>
	}
}
