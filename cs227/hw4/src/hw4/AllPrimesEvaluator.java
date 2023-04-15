package hw4;

import api.Card;

/**
 * Evaluator for a hand in which the rank of each card is a prime number. The
 * number of cards required is equal to the hand size.
 * 
 * The name of this evaluator is "All Primes".
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class AllPrimesEvaluator extends AbstractEvaluator {
	private int req;
	private boolean canContinue;

	/**
	 * Constructs the evaluator.
	 * 
	 * @param ranking  ranking of this hand
	 * @param handSize number of cards in a hand
	 */
	public AllPrimesEvaluator(int ranking, int handSize) {
		super(ranking, handSize, "All Primes", handSize);
		this.req = handSize;
	}

	@Override
	public boolean canSatisfy(Card[] mainCards) {
		canContinue = true;
		// make list of primes from 0 to 13
		int[] primes = new int[] { 2, 3, 5, 7, 11, 13 };
		if (mainCards.length != req) {
			return false;
		} else {
			for (int i = 0; i < mainCards.length && canContinue; i++) {
				// reset canContinue so that the array is searched again and must have a prime
				// in it for the loop to continue
				canContinue = false;
				for (int j = 0; j < primes.length; j++) {
					// make sure all of the main cards ranks are prime
					if (mainCards[i].getRank() == primes[j]) {
						// make value so that when returning back to original for loop it is ensured the
						// last index was a prime number
						canContinue = true;
					}
				}
			}
			// if all numbers until the last one of mainCards were prime, we can just return
			// canContinue, as it
			// will be true if the last value is prime, and false (not set to true in the if
			// statement) if the value wasnt prime
			return canContinue;
		}

	}

}
