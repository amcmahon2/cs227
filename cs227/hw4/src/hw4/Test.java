package hw4;
import api.Card;
import api.Hand;

import java.util.Arrays;

import api.IEvaluator;

public class Test {
		public static void main(String[] args) {
			Card[] oddLowFirst = Card.createArray("2h, 2d, 2c, 4s, 4h");
			Card[] oddLowLast = Card.createArray("2h, 2d, 4c, 4s, 4h");
			Card[] even = Card.createArray("2h, 2d, 2c, 4s, 4h, 4c");
			
			FullHouseEvaluator oddEval = new FullHouseEvaluator(3, 5);
			FullHouseEvaluator evenEval = new FullHouseEvaluator(3, 6);
			
			boolean ans1 = oddEval.canSatisfy(oddLowFirst);
			System.out.println("Expected true, actual: " + ans1);
			System.out.println();
			
			boolean ans2 = oddEval.canSatisfy(oddLowLast);
			System.out.println("Exp3ected true, actual: " + ans2);
			System.out.println();
			
			boolean ans3 = evenEval.canSatisfy(even);
			System.out.println("Expected true, actual: " + ans3);
			System.out.println();
		
	    
	}
}
