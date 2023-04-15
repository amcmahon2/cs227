package hw4;

import api.Card;

/**
 * Evaluator for a hand containing (at least) two cards of the same rank. The
 * number of cards required is two.
 * 
 * The name of this evaluator is "One Pair".
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class OnePairEvaluator extends AbstractEvaluator {

	/**
	 * Constructs the evaluator.
	 * 
	 * @param ranking  ranking of this hand
	 * @param handSize number of cards in a hand
	 */
	public OnePairEvaluator(int rank, int size) {
		super(rank, size, "One Pair", 2);
	}

	@Override
	public boolean canSatisfy(Card[] mainCards) {
		try {
			if (mainCards.length == this.cardsRequired() && mainCards[0].compareToIgnoreSuit(mainCards[1]) == 0) {
				return true;
			}
		}
		// catch exception where array of length 0 is given
		catch (ArrayIndexOutOfBoundsException Ex) {
			return false;
		}

		return false;
	}

}