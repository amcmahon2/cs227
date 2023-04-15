
import hw4.AllPrimesEvaluator;
import hw4.CatchAllEvaluator;
import hw4.FourOfAKindEvaluator;
import hw4.FullHouseEvaluator;
import hw4.OnePairEvaluator;
import hw4.StraightEvaluator;
import hw4.StraightFlushEvaluator;
import hw4.ThreeOfAKindEvaluator;

import java.util.ArrayList;
import java.util.Arrays;

import api.Card;
import api.Hand;
import api.IEvaluator;
import api.Suit;

/**
 * Some sample code to illustrate the evaluator methods.
 * 
 * This code will not compile until parts of the hw4 package
 * have been implemented.
 */
public class TryEvaluators
{

  public static void main(String[] args)
  {
    Card card1 = new Card(2, Suit.CLUBS);
    Card card2 = new Card(3, Suit.CLUBS);
    int comp = card1.compareTo(card2);
    System.out.println(comp);
    
    // Create a one pair evaluator that has ranking 3
    // and hand size of four cards
    //changed type from IEvaluator to OnePairEvaluator 
    OnePairEvaluator eval = new OnePairEvaluator(3, 4);
    System.out.println(eval.getName());  // "One Pair" 
    
    // Create an array of Cards to test.  This is equivalent to
    // Card[] cards = {new Card(2, Suit.CLUBS), new Card(2, Suit.DIAMONDS)};
    // (see the Card class documentation)
    // This array should satisfy the One Pair evaluator.
    //Card[] cards1 = {new Card(2, Suit.CLUBS), new Card(2, Suit.DIAMONDS), new Card(4, Suit.SPADES), new Card(6, Suit.DIAMONDS)};
    Card[] cards = Card.createArray("2c, 2d");
    Card[] cards1 = {new Card(2, Suit.CLUBS), new Card(2, Suit.DIAMONDS)};
    System.out.println(Arrays.toString(cards));  
    System.out.println(eval.canSatisfy(cards1));  // true
    
    // This one should not satisfy the evaluator
    cards = Card.createArray("Kc, Qd");
    System.out.println(Arrays.toString(cards));
    System.out.println(eval.canSatisfy(cards));  // false
    
    // This one won't either, since it has more than the
    // required number of cards
    cards = Card.createArray("2c, 2d, 3h");
    System.out.println(Arrays.toString(cards));
    System.out.println(eval.canSatisfy(cards));  // false

    // However, it contains a subset that does 
    System.out.println(eval.canSubsetSatisfy(cards)); // true

    // Try a bigger array.  We'll use Arrays.sort to get them
    // in order, as required by the IEvaluator API.  This
    // illustrates the ordering of the Card compareTo() method
    cards = Card.createArray("6s, Jd, Ah, 10h, 6h, Js, 6c, Kh, Qh");
    Arrays.sort(cards); // now [Ah, Kh, Qh, Js, Jd, 10h, 6s, 6h, 6c]
    System.out.println(Arrays.toString(cards));
    System.out.println(eval.canSubsetSatisfy(cards)); // true
    
    // Define a subset consisting of indices 6 and 8
    // and have the evaluator create a Hand from those cards
    int[] subset = {6, 8};
    Hand hand = eval.createHand(cards, subset);
    System.out.println(hand); // One Pair (3) [6s 6c : Ah Kh]
    
    // Subset at indices 0 and 3 doesn't satisfy evaluator
    int[] subset2 = {0, 3};
    hand = eval.createHand(cards, subset2);
    System.out.println(hand); // null
    
    // Finds the best hand from these cards (for this evaluator)
    // which will be the pair of jacks plus ace and king
    cards1 = Card.createArray("7h, 7s, 3s, Kd, 7c, 4s, 6d, 7s");
    Arrays.sort(cards1);
    hand = eval.getBestHand(cards);
    System.out.println(hand); // One Pair (3) [Js Jd : Ah Kh]

    //cards = Card.createArray("6s, Jd, Ah, 10h, 6h, Js, 6c, Kh, Qh");
    // Create a list of some evaluators for 5-card hands
    ArrayList<IEvaluator> evaluators = new ArrayList<IEvaluator>();
    evaluators.add(new OnePairEvaluator(3, 5));
    //evaluators.add(new FullHouseEvaluator(1, 6));
    //evaluators.add(new StraightEvaluator(2, 5, 13));
    evaluators.add(new FourOfAKindEvaluator(2, 6));
    //evaluators.add(new StraightEvaluator(1, 5, 14)); 
    //evaluators.add(new AllPrimesEvaluator(1, 5));
    evaluators.add(new CatchAllEvaluator(5, 5));
    //evaluators.add(new StraightFlushEvaluator(1, 5, 14));
    
    // Now find the best hand we can get from these cards
    Hand best = null;
    for (IEvaluator e : evaluators)
    {
      Hand h = e.getBestHand(cards1);
      if (best == null || h != null && h.compareTo(best) < 0)
      {
        best = h;
      }
    }
    
    // Full House (1) [6s 6h 6c Js Jd]
    System.out.println("Best hand: " + best);
   
    
  //test the AllPrimesEvaluator
    System.out.println(); 
  	Card[] allPrimeCards = Card.createArray("5s, Jd, Kh, 5h, 3h, 3s, 5c, Jh, Kh");
  	Arrays.sort(allPrimeCards);

  	Card[] notPrimeCards = Card.createArray("2s, 6d, Kh, 3s, 6h, 6s, Qh");
  	Arrays.sort(notPrimeCards);
  	
  	AllPrimesEvaluator findsPrime = new AllPrimesEvaluator(1, 5);
  	
  	boolean arePrimes = findsPrime.canSubsetSatisfy(allPrimeCards); 
  	System.out.println(Arrays.toString(allPrimeCards));
  	Hand primes = findsPrime.getBestHand(allPrimeCards);
  	System.out.println("Expected: Kh Kh Jh Jd 5h, actual: " + primes); 
  	System.out.println("Expected: true, actual: " + arePrimes);
  	System.out.println();
  	
  	boolean notPrimes = findsPrime.canSubsetSatisfy(notPrimeCards); 
  	System.out.println(Arrays.toString(notPrimeCards));
  	Hand nPrimes = findsPrime.getBestHand(notPrimeCards); 
  	System.out.println("Expected: null, actual: " + nPrimes); 
  	System.out.println("Expected: false, actual: " + notPrimes);
  	System.out.println(); 
  	
  	//test that the catch all evaluator returns when the prime evaluator doesn't
  	CatchAllEvaluator catchAll = new CatchAllEvaluator(5, 5);
  	
  	ArrayList<IEvaluator> evaluators1 = new ArrayList<IEvaluator>();
  	evaluators1.add(findsPrime);
  	evaluators1.add(catchAll); 
  	
  	boolean canCatchAll = catchAll.canSubsetSatisfy(notPrimeCards);
  	Hand hCatchAll = catchAll.getBestHand(notPrimeCards);
  	System.out.println("actual: " + hCatchAll);
  	System.out.println("Expected for CatchAll: true, actual: " + canCatchAll);
  	System.out.println("Expected for Primes: false, actual: " + notPrimes);
  	
  	Hand best1 = null;
    for (IEvaluator e : evaluators1)
    {
      Hand h1 = e.getBestHand(notPrimeCards);
      if (best1 == null || h1 != null && h1.compareTo(best1) < 0)
      {
        best1 = h1;
      }
    }
    
    System.out.println("Expected: the CatchAll hand, Actual Best hand: " + best1);
    System.out.println();
    
  //test that four of a kind works
    System.out.println("Tests to see if FourOfAKind works");
    Card[] hasFourCards = Card.createArray("5s, 5d, Kh, 5h, 3h, 3s, 5c, Jh, Kh");
    Arrays.sort(hasFourCards);
    Card[] notFourCards = Card.createArray("2s, 5d, Kh, 5h, 3h, 3s, 5c, Jh, Kh");
    Arrays.sort(notFourCards);
    
    FourOfAKindEvaluator fourOfAKind = new FourOfAKindEvaluator(2, 5);
    
    boolean hasFour = fourOfAKind.canSubsetSatisfy(hasFourCards); 
  	Hand four = fourOfAKind.getBestHand(hasFourCards);
  	System.out.println("Expected: 5s, 5d, 5h, 5c and side card Kh, actual: " + four); 
  	System.out.println("Expected: true, actual: " + hasFour);
  	System.out.println();
  	
  	boolean notFour = fourOfAKind.canSubsetSatisfy(notFourCards); 
  	Hand nFour = fourOfAKind.getBestHand(notFourCards); 
  	System.out.println("Expected: null, actual: " + nFour); 
  	System.out.println("Expected: false, actual: " + notFour);
  	System.out.println(); 
  	
  	//test that FullHouse works
  	System.out.println("Tests to see if FullHouse Evaluator works");
  	Card[] hasFullHouseCardsOdd = Card.createArray("Qs, Jd, Qh, Ks, Ks, Jd, 4h, Js");
  	Arrays.sort(hasFullHouseCardsOdd);
  	Card[] hasFullHouseCardsEven = Card.createArray("Ks, 7d, 3h, As, 3d, 7s, 8h, 3d, Ah, Ad");
  	Arrays.sort(hasFullHouseCardsEven);
  	Card[] notFullHouseCards = Card.createArray("Jd, 3s, 2d, 2s, 2h, Kh"); 
  	Arrays.sort(notFullHouseCards);
  	Card[] hasFullHouseCardsAll = Card.createArray("7s, 7d, 7h, 6s, 7d, 7s, 7d");
  	Arrays.sort(hasFullHouseCardsAll);
  	
  	FullHouseEvaluator fullHouseOdd = new FullHouseEvaluator(1, 5);
  	FullHouseEvaluator fullHouseEven = new FullHouseEvaluator(1, 6);
  	FullHouseEvaluator fullHouseAllOdd = new FullHouseEvaluator(1, 5);
  	FullHouseEvaluator fullHouseAllEven = new FullHouseEvaluator(1, 6); 
  	
  	boolean hasFullHouseOdd = fullHouseOdd.canSubsetSatisfy(hasFullHouseCardsOdd);
  	boolean hasFullHouseAllOdd = fullHouseAllOdd.canSubsetSatisfy(hasFullHouseCardsAll);
  	Hand fullHouseOddH = fullHouseOdd.getBestHand(hasFullHouseCardsOdd);
  	Hand fullHouseOddAllH = fullHouseAllOdd.getBestHand(hasFullHouseCardsAll); 
  	System.out.println("Expected: J,J,J,K,K actual: " + fullHouseOddH); 
  	System.out.println("Expected: 7,7,7,7,7 actual: " + fullHouseOddAllH); 
  	System.out.println("Expected: true, actual: " + hasFullHouseOdd);
  	System.out.println("Expected: true, actual: "+ hasFullHouseAllOdd); 
  	System.out.println();
  	
  	boolean hasFullHouseEven = fullHouseEven.canSubsetSatisfy(hasFullHouseCardsEven); 
  	boolean hasFullHouseAllEven = fullHouseEven.canSubsetSatisfy(hasFullHouseCardsAll);
  	Hand fullHouseEvenH = fullHouseEven.getBestHand(hasFullHouseCardsEven);
  	Hand fullHouseEvenAllH = fullHouseAllEven.getBestHand(hasFullHouseCardsAll); 
  	System.out.println("Expected: A,A,A,3,3,3 actual: " + fullHouseEvenH); 
  	System.out.println("Expected: true, actual: " + hasFullHouseEven);
  	System.out.println("Expected: 7,7,7,7,7,7, actual: " + fullHouseEvenAllH);
  	System.out.println("Expected: true, actual: " + hasFullHouseAllEven); 
  	System.out.println();
  	
  	boolean hasFullHouseNot = fullHouseEven.canSubsetSatisfy(notFullHouseCards); 
  	boolean hasFullHouseNotOdd = fullHouseOdd.canSubsetSatisfy(notFullHouseCards);
  	Hand notFullHouseEvenH = fullHouseEven.getBestHand(notFullHouseCards);
  	Hand notFullHouseOddH = fullHouseOdd.getBestHand(notFullHouseCards);
  	System.out.println("Expected: null actual: " + notFullHouseEvenH); 
  	System.out.println("Expected: false, actual: " + hasFullHouseNot);
  	System.out.println("Expected: null actual: " + notFullHouseOddH); 
  	System.out.println("Expected: false, actual: " + hasFullHouseNotOdd);
  	System.out.println();
  	
  	//tests that full house works for different 
  	System.out.println("Tests to see if FullHouseEvaluator works for different lengths");
  	
   	Card[] hasFullHouseCardsOdd9 = Card.createArray("Qs, Jd, Qh, Ks, Ks, Jd, 4h, Js, Qs, Jd, Qh, 3s, Qd");
  	Arrays.sort(hasFullHouseCardsOdd9);
  	Card[] hasFullHouseCardsEven10 = Card.createArray("Ks, 7d, 3h, As, 3d, 7s, 8h, 3d, Ah, Ad");
  	Arrays.sort(hasFullHouseCardsEven10);
  	Card[] notFullHouseCards9 = Card.createArray("Jd, 3s, 2d, 2s, 2h, Kh, As, Qd, 2d, 3d"); 
  	Arrays.sort(notFullHouseCards9);
  	Card[] hasFullHouseCardsAll13 = Card.createArray("3s, 7d, 3h, 6s, 7d, 3s, 7d, 3h, 3d, 3s, 3h, 3s, 3s, 3h, Jh, 3s, 3s, 3s");
  	Arrays.sort(hasFullHouseCardsAll13);
  	
  	FullHouseEvaluator fullHouseOdd9 = new FullHouseEvaluator(1, 9);
  	FullHouseEvaluator fullHouseEven10 = new FullHouseEvaluator(1, 10);
  	FullHouseEvaluator fullHouseAllOdd13 = new FullHouseEvaluator(1, 13);
  	
  	boolean hasFullHouseOdd9 = fullHouseOdd9.canSubsetSatisfy(hasFullHouseCardsOdd9);
  	boolean hasFullHouseAllOdd13 = fullHouseAllOdd13.canSubsetSatisfy(hasFullHouseCardsAll13);
  	Hand fullHouseOddH9 = fullHouseOdd9.getBestHand(hasFullHouseCardsOdd9);
  	Hand fullHouseOddAllH13 = fullHouseAllOdd13.getBestHand(hasFullHouseCardsAll13); 
  	System.out.println("Expected: Q,Q,Q,Q,Q,J,J,J,J actual: " + fullHouseOddH9); 
  	System.out.println("Expected: 3,3,3,3,3,3,3,3,3,3,3,3,3 actual: " + fullHouseOddAllH13); 
  	System.out.println("Expected: true, actual: " + hasFullHouseOdd9);
  	System.out.println("Expected: true, actual: "+ hasFullHouseAllOdd13); 
  	System.out.println();
  	
  	boolean hasFullHouseNot10 = fullHouseEven10.canSubsetSatisfy(notFullHouseCards9); 
  	boolean hasFullHouseNotOdd9 = fullHouseOdd9.canSubsetSatisfy(notFullHouseCards9);
  	Hand notFullHouseEvenH10 = fullHouseEven10.getBestHand(notFullHouseCards9);
  	Hand notFullHouseOddH9 = fullHouseOdd9.getBestHand(notFullHouseCards9);
  	System.out.println("Expected: null actual: " + notFullHouseEvenH10); 
  	System.out.println("Expected: false, actual: " + hasFullHouseNot10);
  	System.out.println("Expected: null actual: " + notFullHouseOddH9); 
  	System.out.println("Expected: false, actual: " + hasFullHouseNotOdd9);
  	System.out.println();
  	
  	//tests for the Straight Evaluator
  	System.out.println("Tests to see if the Straight Evaluator works");
  	Card[] noAcesStraightCards = Card.createArray("Jh, 10s, 3d, 4c, 2d, 9h, 7d, 6s, 8s, 5d");
  	Arrays.sort(noAcesStraightCards);
  	Card[] aceTopStraightCards = Card.createArray("2h, 5d, 4s, Jd, Qd, As, 3s, Kh, 10d"); 
  	Arrays.sort(aceTopStraightCards);
  	Card[] aceBottomStraightCards = Card.createArray("2h, 3d, As, 4d, 5s, 7s"); 
  	Arrays.sort(aceBottomStraightCards);
  	Card[] noStraightCards = Card.createArray("2d, 8d, 9h, As, Kd");
  	Arrays.sort(noStraightCards);
  	Card[] twoAcesStraightCards = Card.createArray("Ad, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d, 10d, Jd, Qd, Kd, As");
  	Arrays.sort(twoAcesStraightCards);
  	
  	StraightEvaluator e5Cards = new StraightEvaluator(1, 5, 13);
  	StraightEvaluator e5Cards2 = new StraightEvaluator(1, 5, 13); 
  	StraightEvaluator isAceBottomStraight = new StraightEvaluator(1, 5, 13); 
  	StraightEvaluator isNoStraight = new StraightEvaluator(1, 5, 13);
  	StraightEvaluator twoAces = new StraightEvaluator(1, 5, 13); 
  	StraightEvaluator twoAcesAll = new StraightEvaluator(1, 14, 13); 
  	
  	boolean hasStraightNoA = e5Cards.canSubsetSatisfy(noAcesStraightCards);
  	Hand noAcesStraightH = e5Cards.getBestHand(noAcesStraightCards);
  	System.out.println("Expected: true, actual: " + hasStraightNoA);
  	System.out.println("Expected: J, 10, 9, 8, 7, actual: " + noAcesStraightH); 
  	System.out.println();
  	
  	boolean hasStraightTopA = e5Cards2.canSubsetSatisfy(aceTopStraightCards);
  	Hand aceTopStraightH = e5Cards2.getBestHand(aceTopStraightCards);
  	System.out.println("Expected: true, actual: " + hasStraightTopA);
  	System.out.println("Expected: A,K,Q,J,10, actual: " + aceTopStraightH); 
  	System.out.println(); 
  	
  	boolean hasStraightBottomA = isAceBottomStraight.canSubsetSatisfy(aceBottomStraightCards);
  	Hand aceBottomStraightH = isAceBottomStraight.getBestHand(aceBottomStraightCards);
  	System.out.println("Expected: true, actual: " + hasStraightBottomA);
  	System.out.println("Expected: 5,4,3,2,A, actual: " + aceBottomStraightH);
  	System.out.println(); 
  	
  	boolean hasStraightNone = isNoStraight.canSubsetSatisfy(noStraightCards);
  	Hand noStraightH = isNoStraight.getBestHand(noStraightCards);
  	System.out.println("Expected: false, actual: " + hasStraightNone); 
  	System.out.println("Expected: null, actual: " + noStraightH);
  	System.out.println(); 
  	
  	boolean hasStraightTwoAces = twoAces.canSubsetSatisfy(twoAcesStraightCards);
  	Hand twoAcesH = twoAces.getBestHand(twoAcesStraightCards);
  	System.out.println("Expected: true, actual: " + hasStraightTwoAces);
  	System.out.println("Expected: A,K,Q,J,10, actual: " + twoAcesH); 
 	System.out.println(); 
  	
  	boolean hasStraightTwoAcesAll = twoAcesAll.canSubsetSatisfy(twoAcesStraightCards);
  	Hand twoAcesAllH = twoAcesAll.getBestHand(twoAcesStraightCards);
  	System.out.println("Expected: false, actual: " + hasStraightTwoAcesAll);
  	System.out.println("Expected: null, actual: " + twoAcesAllH); 
	System.out.println(); 
 	
 	//tests for the Straight Flush Evaluator
  	System.out.println("Tests to see if the StraightFlushEvaluatorWorks"); 
  	Card[] straightFlushCards = Card.createArray("2d, 4d, 5d, 6d, 3d");
  	Arrays.sort(straightFlushCards);
  	Card[] straightFlushCardsTopAce = Card.createArray("4s, 8s, 9s, Ks, Qs, As, Js, 10s, 9d, Kd");
  	Arrays.sort(straightFlushCardsTopAce);
  	Card[] straightFlushCardsBottomAce = Card.createArray("2d, Ad, 3d, 4d, 5d, 7h, 9h");
  	Arrays.sort(straightFlushCardsBottomAce);
  	Card[] noStraightFlushCards = Card.createArray("4d, 3s, Ad, 5h, 2c"); 
  	Arrays.sort(noStraightFlushCards);
  	Card[] straightFlushTwoA = Card.createArray("Ah, 2h, 3h, 4h, 5h, 6h, 7h, 8h, 9h, 10h, Jh, Qh, Kh, Ah"); 
  	Arrays.sort(straightFlushTwoA);
  	
  	StraightFlushEvaluator isStraightFlush = new StraightFlushEvaluator(1, 5, 13);
  	StraightFlushEvaluator isSFTopA = new StraightFlushEvaluator(1, 5, 13); 
  	StraightFlushEvaluator isSFBottomA = new StraightFlushEvaluator(1, 5, 13); 
  	StraightFlushEvaluator notSF = new StraightFlushEvaluator(1, 5, 13);
  	StraightFlushEvaluator sF2A = new StraightFlushEvaluator(1, 14, 13); 
  	
  	boolean hasStraightFlush = isStraightFlush.canSubsetSatisfy(straightFlushCards);
  	Hand straightFlushH = isStraightFlush.getBestHand(straightFlushCards);
  	System.out.println("Expected: true, actual: " + hasStraightFlush);
  	System.out.println("Expected: 6d, 5d, 4d, 3d, 2d, actual: " + straightFlushH);
  	System.out.println(); 
  	
  	boolean hasSFTopA = isSFTopA.canSubsetSatisfy(straightFlushCardsTopAce);
  	Hand SFTopAh = isSFTopA.getBestHand(straightFlushCardsTopAce);
  	System.out.println("Expected: true, actual: " + hasSFTopA);
  	System.out.println("expected: As, Ks, Qs, Js, 10s, actual: " + SFTopAh); 
  	System.out.println(); 
  	
  	boolean hasSFBottomA = isSFBottomA.canSubsetSatisfy(straightFlushCardsBottomAce);
  	Hand SFBottomAh = isSFBottomA.getBestHand(straightFlushCardsBottomAce);
  	System.out.println("expected: true, actual: " + hasSFBottomA);
  	System.out.println("expected: 5d, 4d, 3d, 2d, Ad, actual: " + SFBottomAh); 
  	System.out.println(); 
  	
  	boolean hasSFNoSF = notSF.canSubsetSatisfy(noStraightFlushCards);
  	Hand noSFH = notSF.getBestHand(noStraightFlushCards);
  	System.out.println("expected: false, actual: " + hasSFNoSF);
  	System.out.println("expected: null: actual: " + noSFH);
  	System.out.println(); 
  	
  	boolean hasSF2A = sF2A.canSubsetSatisfy(straightFlushTwoA); 
  	Hand noSFH2A = sF2A.getBestHand(straightFlushTwoA);
  	System.out.println("expected: false, actual: " + hasSF2A); 
  	System.out.println("expected: null, actual: " + noSFH2A); 
  	System.out.println(); 
  	
  	//test cases for three of a kind
  	System.out.println("Test cases to see if Three of a Kind Evaluator works");
  	Card[] hasThreeCards = Card.createArray("10h, Jd, 2d, 5h, 8d, 10d, 10s");
  	Arrays.sort(hasThreeCards);
  	Card[] notThreeCards = Card.createArray("2d, 3h, 5c, 8s, 2d, 9c");
  	Arrays.sort(notThreeCards);
  	Card[] topThreeCards = Card.createArray("2d, 9d, 2c, 2h, 9c, 9h, 8d, Js");
  	Arrays.sort(topThreeCards);
  	
  	ThreeOfAKindEvaluator hasThree = new ThreeOfAKindEvaluator(3, 3);
  	ThreeOfAKindEvaluator notThree = new ThreeOfAKindEvaluator(3, 3);
  	ThreeOfAKindEvaluator topThree = new ThreeOfAKindEvaluator(3, 3); 
  	
  	boolean hasThreeOfAKind = hasThree.canSubsetSatisfy(hasThreeCards);
  	Hand hasThreeH = hasThree.getBestHand(hasThreeCards);
  	System.out.println("expected: true, actual: " + hasThreeOfAKind);
  	System.out.println("expected: 10, 10, 10, actual: " + hasThreeH); 
  	System.out.println(); 
  	
  	boolean notThreeOfAKind = notThree.canSubsetSatisfy(notThreeCards);
  	Hand notThreeH = notThree.getBestHand(notThreeCards);
  	System.out.println("expected: false, actual: " + notThreeOfAKind);
  	System.out.println("expected: null, actual: " + notThreeH); 
  	System.out.println(); 
  	
  	boolean hasTopThree = topThree.canSubsetSatisfy(topThreeCards);
  	Hand topThreeH = topThree.getBestHand(topThreeCards);
  	System.out.println("expected: true, actual: " + hasTopThree);
  	System.out.println("expected: 9,9,9, actual: " + topThreeH); 
  	System.out.println();
  	
  	//test to ensure that the ranking works correctly 
  	System.out.println("Tests to see that it returns the highest ranking when doing all of the evaluators"); 
  	//tests the evaluators with this ranking:
  	//1st: Straight Flush
  	//2nd: Four of a Kind
  	//3rd: All Primes
  	//4th: Full House
  	//5th: Straight
  	//6th: Three of a Kind
  	//7th: One Pair
  	//8th: Catch All
  	
  	Card[] hasStraightFlushCards = Card.createArray("6d, 8c, 9c, 10d, 9d, 8s, 8d, 7d, 5d, 10h, 8c");
  	Arrays.sort(hasStraightFlushCards); 
  	
  	ArrayList<IEvaluator> evaluatorSF = new ArrayList<IEvaluator>();
    evaluatorSF.add(new StraightFlushEvaluator(1, 5, 13));
    evaluatorSF.add(new FourOfAKindEvaluator(2, 4));
    evaluatorSF.add(new AllPrimesEvaluator(3, 5));
    evaluatorSF.add(new FullHouseEvaluator(4, 5));
    evaluatorSF.add(new StraightEvaluator(5, 5, 13));
    evaluatorSF.add(new ThreeOfAKindEvaluator(6, 3));
    evaluatorSF.add(new OnePairEvaluator(7, 2));
    evaluatorSF.add(new CatchAllEvaluator(8, 5)); 
    
    
  	Hand bestSF = null;
    for (IEvaluator e : evaluatorSF)
    {
      Hand hSF = e.getBestHand(hasStraightFlushCards);
      if (bestSF == null || hSF != null && hSF.compareTo(bestSF) < 0)
      {
        bestSF = hSF;
      }
    }
    
    System.out.println("Expected: the Straight Flush hand, Actual Best hand: " + bestSF);
    System.out.println();
    
    Card[] hasFullHouseCards = Card.createArray("4s, 4d, Qs, 3d, 5d, 4s, 2d, 3h");
    Arrays.sort(hasFullHouseCards);
    
    ArrayList<IEvaluator> evaluatorFH = new ArrayList<IEvaluator>();
    evaluatorFH.add(new StraightFlushEvaluator(1, 5, 13));
    evaluatorFH.add(new FourOfAKindEvaluator(2, 4));
    evaluatorFH.add(new AllPrimesEvaluator(3, 5));
    evaluatorFH.add(new FullHouseEvaluator(4, 5));
    evaluatorFH.add(new StraightEvaluator(5, 5, 13));
    evaluatorFH.add(new ThreeOfAKindEvaluator(6, 3));
    evaluatorFH.add(new OnePairEvaluator(7, 2));
    evaluatorFH.add(new CatchAllEvaluator(8, 5));
    
    Hand bestFH = null;
    for (IEvaluator e : evaluatorFH)
    {
      Hand hFH = e.getBestHand(hasFullHouseCards);
      if (bestFH == null || hFH != null && hFH.compareTo(bestFH) < 0)
      {
        bestFH = hFH;
      }
    }
    
    System.out.println("Expected: The Full House hand, Actual Best hand: " + bestFH);
    System.out.println(); 
    
    Card[] nothingCards = Card.createArray("Ah, 2d, 7h, 9d, 10d");
    Arrays.sort(nothingCards);
    
    ArrayList<IEvaluator> evaluatorN = new ArrayList<IEvaluator>();
    evaluatorN.add(new StraightFlushEvaluator(1, 5, 13));
    evaluatorN.add(new FourOfAKindEvaluator(2, 4));
    evaluatorN.add(new AllPrimesEvaluator(3, 5));
    evaluatorN.add(new FullHouseEvaluator(4, 5));
    evaluatorN.add(new StraightEvaluator(5, 5, 13));
    evaluatorN.add(new ThreeOfAKindEvaluator(6, 3));
    evaluatorN.add(new OnePairEvaluator(7, 2));
    evaluatorN.add(new CatchAllEvaluator(8, 5));
    
    Hand bestN = null;
    for (IEvaluator e : evaluatorN)
    {
      Hand hN = e.getBestHand(nothingCards);
      if (bestN == null || hN != null && hN.compareTo(bestN) < 0)
      {
        bestN = hN;
      }
    }
    
    System.out.println("Expected: The Catch All hand, Actual Best hand: " + bestN);
    System.out.println(); 
  }
  
}
