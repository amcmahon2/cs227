package hw4;

import api.Card;

/**
 * Evaluator for a generalized full house. The number of required cards is equal
 * to the hand size. If the hand size is an odd number n, then there must be (n
 * / 2) + 1 cards of the matching rank and the remaining (n / 2) cards must be
 * of matching rank. In this case, when constructing a hand, the larger group
 * must be listed first even if of lower rank than the smaller group</strong>
 * (e.g. as [3 3 3 5 5] rather than [5 5 3 3 3]). If the hand size is even, then
 * half the cards must be of matching rank and the remaining half of matching
 * rank. Any group of cards, all of which are the same rank, always satisfies
 * this evaluator.
 * 
 * The name of this evaluator is "Full House".
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class FullHouseEvaluator extends AbstractEvaluator {
	/**
	 * Constructs the evaluator.
	 * 
	 * @param ranking  ranking of this hand
	 * @param handSize number of cards in a hand
	 */
	public FullHouseEvaluator(int ranking, int handSize) {
		super(ranking, handSize, "Full House", handSize);
	}

	@Override
	public boolean canSatisfy(Card[] mainCards) {
		// make sure size > 0
		if (mainCards.length < 1) {
			return false;
		}

		// if size is even
		else if (mainCards.length % 2 == 0) {
			if (allSame(mainCards)) {
				return true;
			}
			Card[] list1 = new Card[mainCards.length / 2];
			Card[] list2 = new Card[mainCards.length / 2];
			// initialize
			for (int i = 0; i < list1.length; i++) {
				list1[i] = mainCards[i];
				list2[i] = mainCards[list1.length + i];
			}
			// since the lists are already ranked in order, if the first half cards are the
			// same and second half are the same return true
			if (allSame(list1) && allSame(list2)) {
				return true;
			}
		}
		// if size is odd
		else {
			if (allSame(mainCards)) {
				return true;
			}

			if (mainCards[(mainCards.length / 2) + 1].getRank() == mainCards[mainCards.length / 2].getRank()) {
				// even first
				Card[] list1v1 = new Card[(mainCards.length / 2)];
				Card[] list2v2 = new Card[(mainCards.length / 2)];
				for (int i = 0; i < list1v1.length; i++) {
					list1v1[i] = mainCards[i];
					list2v2[i] = mainCards[i];
				}

				Card[] list1v2 = new Card[mainCards.length / 2 + 1];
				Card[] list2v1 = new Card[mainCards.length / 2 + 1];
				for (int x = 0; x < list1v2.length; x++) {
					// System.out.println(list1v2.length);
					list1v2[x] = mainCards[list1v1.length + x];
					list2v1[x] = mainCards[list1v1.length + x];
				}
				if ((allSame(list1v1) && allSame(list2v1)) || (allSame(list1v2) && allSame(list2v2))) {
					return true;
				}
			} else {
				// odd first
				Card[] list1v1 = new Card[(mainCards.length / 2) + 1];
				Card[] list2v2 = new Card[(mainCards.length / 2) + 1];
				for (int i = 0; i < list1v1.length; i++) {
					list1v1[i] = mainCards[i];
					list2v2[i] = mainCards[i];
				}

				Card[] list1v2 = new Card[mainCards.length / 2];
				Card[] list2v1 = new Card[mainCards.length / 2];
				for (int x = 0; x < list1v2.length; x++) {
					// System.out.println(list1v2.length);
					list1v2[x] = mainCards[list1v1.length + x];
					list2v1[x] = mainCards[list1v1.length + x];
				}
				if ((allSame(list1v1) && allSame(list2v1)) || (allSame(list1v2) && allSame(list2v2))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * helper method
	 * 
	 * @param cards
	 * @return boolean value: true if all cards in given list are of the same rank,
	 *         false if they differ in value
	 */
	private boolean allSame(Card[] cards) {
		if (cards.length < 1) {
			return false;
		} else {
			int commonRank = cards[0].getRank();
			for (int i = 1; i < cards.length; i++) {
				if (cards[i].getRank() != commonRank) {
					return false;
				}
			}
			return true;
		}
	}
}
