package hw4;

import api.Card;

/**
 * Evaluator for a hand containing (at least) three cards of the same rank. The
 * number of cards required is three.
 * 
 * The name of this evaluator is "Three of a Kind".
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class ThreeOfAKindEvaluator extends AbstractEvaluator {

	/**
	 * Constructs the evaluator.
	 * 
	 * @param ranking  ranking of this hand
	 * @param handSize number of cards in a hand
	 */
	public ThreeOfAKindEvaluator(int rank, int size) {
		super(rank, size, "Three of a Kind", 3);
	}

	@Override
	public boolean canSatisfy(Card[] mainCards) {
		try {
			// make sure there are 3 main cards and they all equal each other
			if (mainCards.length == this.cardsRequired() && mainCards[0].compareToIgnoreSuit(mainCards[1]) == 0
					&& mainCards[1].compareToIgnoreSuit(mainCards[2]) == 0) {
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException Ex) {
			return false;
		}

		return false;
	}
}
