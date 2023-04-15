package hw4;

import java.util.ArrayList;
import java.util.Arrays;

import api.Card;
import api.Hand;
import api.IEvaluator;
import util.SubsetFinder;

/**
 * The class AbstractEvaluator includes common code for all evaluator types.
 * 
 * My class hierachy consists of the Interface IEvaluator, the Abstract classes
 * AbstractEvaluator and StraightAbstract (which extends AbstractEvaluator), and
 * all of the specific evaluators. There are some helper methods in certain
 * places, some specific to certain evals and others not, as one can see.
 * StraightAbstract is used to remove code duplication between the 2 Straight
 * type evals (Straight & Straight Flush). Since their code was rather
 * complicate and much different than the other methods, but similar to one
 * another, I found an Abstract class for both was the most simple way to reduce
 * code duplication. All of the other eval types extend this AbstractEvaluator.
 */
public abstract class AbstractEvaluator implements IEvaluator {
	/**
	 * ranking is an int value which keeps track of the ranking of any evaluator
	 * created under AbstractEvaluator
	 */
	private int ranking;
	/**
	 * handSize is an int value which keeps track of the hand size specified by the
	 * user for a certain evaluator created under AbstractEvaluator
	 */
	private int handSize;
	/**
	 * cardsRequired is an int value which stores the cards required for an
	 * evaluator created under AbstractEvaluator, specified by the user
	 */
	private int cardsRequired;
	/**
	 * name is a String which stores the name of an evaluator after creation by the
	 * user under AbstractEvaluator
	 */
	private String name;

	/**
	 * 
	 * @param rank     ranking of evaluator
	 * @param size     handSize for evaluator
	 * @param names    name of evaluator
	 * @param cardsReq cards required to satisfy the evaluator
	 */
	protected AbstractEvaluator(int rank, int size, String names, int cardsReq) {
		this.name = names;
		this.ranking = rank;
		this.handSize = size;
		this.cardsRequired = cardsReq;
	}

	/**
	 * returns the hand size of specified evaluator
	 */
	public int handSize() {
		return this.handSize;
	}

	/**
	 * returns the ranking of specified evaluator
	 */
	public int getRanking() {
		return this.ranking;
	}

	/**
	 * returns the number of required cards of specified evaluator
	 */
	public int cardsRequired() {
		return this.cardsRequired;
	}

	/**
	 * returns the name of specified evaluator
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Determines whether there exists a subset of the given cardsthat satisfies the
	 * criteria for this evaluator. The length ofthe given array must be greater
	 * than or equal to the valuereturned by cardsRequired(). Thegiven array must be
	 * sorted with highest-ranked card firstaccording to Card.compareTo(). The
	 * arrayis not modified by this operation.
	 */
	public boolean canSubsetSatisfy(Card[] allCards) {
		ArrayList<Card[]> goodSubsets = cardReturner(allCards);
		for (int i = 0; i < goodSubsets.size(); i++) {
			if (canSatisfy(goodSubsets.get(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @param allCards
	 * @return returnerList, an arraylist of card lists which contains all card sets
	 *         which satisfy the given evaluator
	 */
	private ArrayList<Card[]> cardReturner(Card[] allCards) {
		ArrayList<int[]> subSets = SubsetFinder.findSubsets(allCards.length, this.cardsRequired());
		ArrayList<Card[]> returnerList = new ArrayList<Card[]>();
		for (int i = 0; i < subSets.size(); i++) {
			Card[] temp = new Card[subSets.get(i).length];

			for (int j = 0; j < subSets.get(i).length; j++) {
				temp[j] = allCards[subSets.get(i)[j]];
			}
			// if the card satisfies the evaluator, add it to the list
			if (canSatisfy(temp)) {
				returnerList.add(temp);
			}
			// ace low straight check
			else if (temp[temp.length - 1].getRank() == 2 && temp[0].getRank() == 1) {
				// make new list, put ace last
				Card[] list = new Card[temp.length];
				for (int r = 0; r < list.length - 1; r++) {
					list[r] = temp[r + 1];
				}
				list[list.length - 1] = temp[0];

				if (canSatisfy(list)) {
					// return original indices

					returnerList.add(list);
				}
			}
		}
		// return the list of all of the sets of cards which satisfy the evaluator
		return returnerList;
	}

	/**
	 *
	 * @param allCards
	 * @return subReturner, an arraylist of int lists which contains all indices
	 *         which satisfy the given evaluator
	 */
	private ArrayList<int[]> subsetReturner(Card[] allCards) {
		ArrayList<int[]> subSets = SubsetFinder.findSubsets(allCards.length, this.cardsRequired());
		ArrayList<int[]> subReturner = new ArrayList<int[]>();
		for (int i = 0; i < subSets.size(); i++) {
			Card[] temp = new Card[subSets.get(i).length];
			for (int j = 0; j < subSets.get(i).length; j++) {
				temp[j] = allCards[subSets.get(i)[j]];
			}
			// if the card satisfies the evaluator, add it to the list
			if (canSatisfy(temp)) {
				// if the card at that given index satisifies, add the index to the return list
				subReturner.add(subSets.get(i));
			}
			// ace low straight check (if first card is ace but card inbetween is blocking
			// the straight)
			else if (allCards[allCards.length - 1].getRank() == 2 && allCards[0].getRank() == 1) {
				int[] newSub = new int[this.cardsRequired];
				Card[] list = new Card[this.cardsRequired];
				// move ace to back
				list[list.length - 1] = allCards[0];
				newSub[newSub.length - 1] = 0;
				for (int a = 0; a < list.length - 2; a++) {
					// offset by 2 to ignore the blocking cards
					list[a] = allCards[a + 2];
					newSub[a + 1] = a + 2;
				}
				list[list.length - 2] = allCards[allCards.length - 1];
				// put indices in correct order
				int[] actual = new int[newSub.length];
				newSub[newSub.length - 1] = allCards.length - 1;
				for (int u = 0; u < newSub.length - 1; u++) {
					actual[u] = newSub[u + 1];
				}
				actual[actual.length - 1] = newSub[0];
				// now, the list is organized to where the method can recognize it as an ace low
				// straight
				if (canSatisfy(list)) {
					// if the card at that given index satisifies, add the index to the return list
					subReturner.add(actual);
				}
			}
		}
		// if the method didn't work (used for straight/straightflush/fullhouse), double
		// check to make sure
		if (subReturner.size() < 1) {
			ArrayList<Card[]> cardList = cardReturner(allCards);
			for (int i = 0; i < cardList.size(); i++) {
				ArrayList<Integer> indices = new ArrayList<Integer>();
				for (int j = 0; j < cardList.get(i).length; j++) {
					for (int k = 0; k < allCards.length; k++) {
						// check if the card's rank is in the list
						if (allCards[k].getRank() == cardList.get(i)[j].getRank()) {
							// add to list
							indices.add(k);
						}
					}
				}
				int[] list = new int[indices.size()];
				for (int x = 0; x < indices.size(); x++) {
					list[x] = indices.get(x);
				}
				subReturner.add(list);
				indices.clear();
			}
		}
		return subReturner;
	}

	/**
	 * Returns a hand whose main cards consist of the indicated subsetof the given
	 * cards. If the indicated subset doesnot satisfy the criteria for this
	 * evaluator, thismethod returns null. The subset is described asan ordered
	 * array of indices to be selected from the givenCard array. The number of main
	 * cards in the handwill be the value of getRequiredCards()and the total number
	 * of cards in the hand willbe the value of handSize(). If the lengthof the
	 * given array of cards is less than handSize(),this method returns null.
	 * Thegiven array must be sorted with highest-ranked card firstaccording to
	 * Card.compareTo(). The arrayis not modified by this operation.
	 */
	public Hand createHand(Card[] allCards, int[] subset) {

		// The side cards are the highest ranking leftover cards after picking out the
		// main cards, which are used to get the total number of cards to handSize.
		Card[] mainCards = new Card[this.cardsRequired];
		Card[] sideCards = new Card[allCards.length - subset.length];
		ArrayList<Integer> nonUsedArrayList = new ArrayList<Integer>();

		if (allCards.length < this.handSize) {
			return null;
		}

		// loop through indices to create main cards
		for (int i = 0; i < subset.length; i++) {
			mainCards[i] = allCards[subset[i]];
		}

		// initialize the non-used indices list
		for (int k = 0; k < allCards.length; k++) {
			nonUsedArrayList.add(k);
		}
		// remove indices specified for main cards
		for (int i = 0; i < nonUsedArrayList.size(); i++) {
			for (int j = 0; j < subset.length; j++) {
				// if the current index is an index that appears anywhere in the user given
				// indices list, remove it
				if (nonUsedArrayList.get(i) == subset[j]) {
					nonUsedArrayList.remove(i);
					if (i > 0) {
						// account for removed index
						i--;
					}
				}
			}
		}
		// convert arrayList to array so Hand can be created
		int[] nonUsed = new int[nonUsedArrayList.size()];
		for (int x = 0; x < nonUsedArrayList.size(); x++) {
			nonUsed[x] = nonUsedArrayList.get(x);
		}
		// check to make sure all main cards are valid
		// if a subset doesnt pass, return null, per specification
		if (!canSubsetSatisfy(mainCards)) {
			return null;
		}
		// loop through non-used indices to create side cards
		for (int j = 0; j < nonUsed.length; j++) {
			sideCards[j] = allCards[nonUsed[j]];
		}
		// sort sideCards so we can cut it
		Arrays.sort(sideCards);

		// cut sideCards list so that handSize-mainCards.length = sideCards.length
		int sideCardLength = allCards.length - mainCards.length;
		// pick the top values
		Card[] sides = new Card[sideCardLength];

		// since sideCards is sorted from highest to lowest, the first n cards that
		// weren't indicised will be the highest cards
		for (int y = 0; y < sides.length; y++) {
			sides[y] = sideCards[y];
		}
		Hand returner = new Hand(mainCards, sides, this);
		return returner;
	}

	/**
	 * Returns the best possible hand satisfying this evaluator'scriteria that can
	 * be formed from the given list of cards."Best" is defined to be first
	 * according to the compareTo() method ofHand. Returns null if there is no such
	 * hand. The number of main cardsin the hand will be the value of
	 * getRequiredCards() and the total number of cards in the hand willbe the value
	 * of handSize(). If the lengthof the given array of cards is less than
	 * totalCards(),this method returns null. Thegiven array must be sorted with
	 * highest-ranked card firstaccording to Card.compareTo(). The arrayis not
	 * modified by this operation.
	 */
	public Hand getBestHand(Card[] allCards) {
		ArrayList<Hand> handList = new ArrayList<Hand>();

		if (allCards.length < this.handSize || allCards.length < 1 || !canSubsetSatisfy(allCards)) {
			return null;
		}
		// create list of all possible card subsets which satisfy the evaluator
		ArrayList<Card[]> goodCards = cardReturner(allCards);
		// create list of all indices which satisfy
		ArrayList<int[]> goodSubsets = subsetReturner(allCards);

		// if there are no card subsets that satisfy, return null
		if (goodSubsets.size() < 1 || goodCards.size() < 1) {
			return null;
		}

		Hand test = createHand(allCards, goodSubsets.get(0));
		handList.add(test);
		// if there is only one subset in existence, return the only hand possible
		if (goodCards.get(0).length < 2) {
			return test;
		}

		// iterate through list of indices to compare all possible
		for (int i = 1; i < goodSubsets.size(); i++) {
			// start at j = 1 as the test is at 0
			for (int j = 0; j < goodSubsets.get(i).length; j++) {
				// if the new created hand is better than the original one
				if (createHand(allCards, goodSubsets.get(i)).compareTo(test) < 1) {
					handList.add(createHand(allCards, goodSubsets.get(i)));
				}
			}
			// return last element of list, which will be the greatest hand
		}
		if (handList.size() > 0) {
			return handList.get(handList.size() - 1);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param mainCards list of cards to check
	 * @return boolean (true if the given card list is increasing/decreasing
	 *         linearly, false if not)
	 */
	protected boolean isLinear(Card[] mainCards) {
		boolean isDecreasing = true;
		// check decreasing
		for (int w = 0; w < mainCards.length - 1; w++) {
			if (mainCards[w].getRank() - 1 != mainCards[w + 1].getRank()) {
				isDecreasing = false;
			}
		}
		if (isDecreasing) {
			return true;
		} else {
			// if not decreasing, check increasing
			for (int q = 0; q < mainCards.length - 1; q++) {
				if (mainCards[q].getRank() + 1 != mainCards[q + 1].getRank()) {
					// fails both cases, return false
					return false;
				}
			}
			return true;
		}
	}

}