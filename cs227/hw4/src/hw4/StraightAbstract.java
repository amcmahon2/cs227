package hw4;

import api.Card;

/**
 * 
 * @author amcmahon2 This class is an abstract shared between Straight and
 *         StraightFlush AbstractEvaluator types
 */
public class StraightAbstract extends AbstractEvaluator {
	private int handSize;
	private int maxCardRank;

	/**
	 * 
	 * @param rank     specified ranking
	 * @param size     specified handSize
	 * @param name     specified name
	 * @param cardsReq specified cards required
	 */
	protected StraightAbstract(int rank, int size, String name, int cardsReq) {
		super(rank, size, name, size);
		this.handSize = size;
		this.maxCardRank = cardsReq;
	}

	public boolean canSatisfy(Card[] mainCards) {
		if (mainCards.length == this.handSize) {
			// if ace is last card, check for ace-low straight
			// System.out.println(mainCards[mainCards.length-1].getRank());
			if (mainCards[mainCards.length - 1].getRank() == 1) {
				// if the first card is the length (4 cards, ranks 4 3 2 A), then an Ace (1)
				// must be the end
				// aka check for ace low straight
				if (mainCards[0].getRank() == mainCards.length) {
					// create card list which excludes the ace (everything but last card)
					Card[] checker = new Card[mainCards.length - 1];
					for (int i = 0; i < mainCards.length - 1; i++) {
						checker[i] = mainCards[i];
					}
					// see if the list is linear
					return isLinear(checker);
				}
				return false;
			}
			// check for ace high straight (ace is first card)
			else if (mainCards[0].getRank() == 1) {
				// set ace value to max+1
				// if the next card is one away from ace, see if its linear
				if (mainCards[1].getRank() == maxCardRank || mainCards[1].getRank() == 2) {
					// set ace value equal to max+1
					if (mainCards[1].getRank() == maxCardRank) {
						mainCards[0] = new Card(maxCardRank + 1, mainCards[0].getSuit());
					}
					// create card list which excludes the ace (-1 in length)
					Card[] checker = new Card[mainCards.length - 1];
					for (int i = 0; i < mainCards.length - 1; i++) {
						checker[i] = mainCards[i + 1];
					}
					// see if the list is linear
					return isLinear(checker);
				}
			}
			// if no aces are present, just straightforward linear check
			else {
				return isLinear(mainCards);
			}
		}
		// if array is not of required length, return false
		return false;
	}
}
