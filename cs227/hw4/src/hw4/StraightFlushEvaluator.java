package hw4;

import api.Card;
import api.Suit;
import static api.Suit.*;

/**
 * Evaluator for a hand consisting of a "straight" in which the card ranks are
 * consecutive numbers AND the cards all have the same suit. The number of
 * required cards is equal to the hand size. An ace (card of rank 1) may be
 * treated as the highest possible card or as the lowest (not both) To evaluate
 * a straight containing an ace it is necessary to know what the highest card
 * rank will be in a given game; therefore, this value must be specified when
 * the evaluator is constructed. In a hand created by this evaluator the cards
 * are listed in descending order with high card first, e.g. [10 9 8 7 6] or [A
 * K Q J 10], with one exception: In case of an ace-low straight, the ace must
 * appear last, as in [5 4 3 2 A]
 * 
 * The name of this evaluator is "Straight Flush".
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class StraightFlushEvaluator extends StraightAbstract {
	/**
	 * Constructs the evaluator. Note that the maximum rank of the cards to be used
	 * must be specified in order to correctly evaluate a straight with ace high.
	 * 
	 * @param ranking     ranking of this hand
	 * @param handSize    number of cards in a hand
	 * @param maxCardRank largest rank of any card to be used
	 */
	public StraightFlushEvaluator(int rank, int size, int maxCardVal) {
		super(rank, size, "Straight Flush", maxCardVal);
	}

	/**
	 * This Override is a little different, so I'll javadoc it. Essentially, instead
	 * of writing the same code for Straight and StraightFlush, I decided to write a
	 * superclass for both and simply call an override. If the given suit is a
	 * Straight, it might be a Straight Flush, so I check if the suit is the same,
	 * if it is, then it's a flush!
	 * 
	 * I thought of it as a square is a rectangle but rectangle is not a square type
	 * scenario
	 */
	@Override
	public boolean canSatisfy(Card[] mainCards) {
		if (super.canSatisfy(mainCards)) {
			Suit common = mainCards[0].getSuit();
			for (int i = 1; i < mainCards.length; i++) {
				if (mainCards[i].getSuit() != common) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
