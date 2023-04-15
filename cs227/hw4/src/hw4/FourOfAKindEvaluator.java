package hw4;

import api.Card;

/**
 * Evaluator for a hand containing (at least) four cards of the same rank. The
 * number of cards required is four.
 * 
 * The name of this evaluator is "Four of a Kind".
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class FourOfAKindEvaluator extends AbstractEvaluator {

	/**
	 * Constructs the evaluator.
	 * 
	 * @param ranking  ranking of this hand
	 * @param handSize number of cards in a hand
	 */
	public FourOfAKindEvaluator(int rank, int size) {
		super(rank, size, "Four of a Kind", 4);
	}

	@Override
	public boolean canSatisfy(Card[] mainCards) {
		try {
			// make sure there are 4 main cards and they all equal each other
			if (mainCards.length == this.cardsRequired() && mainCards[0].compareToIgnoreSuit(mainCards[1]) == 0
					&& mainCards[1].compareToIgnoreSuit(mainCards[2]) == 0
					&& mainCards[2].compareToIgnoreSuit(mainCards[3]) == 0
					&& mainCards[3].compareToIgnoreSuit(mainCards[3]) == 0) {
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException Ex) {
			return false;
		}

		return false;
	}

}
