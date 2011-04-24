package javamud.util;

/**
 * the core of all random behaviour is the rolling of a die
 * @author Matthew Proctor
 *
 */
public class Dice {

	private int numDice,numSides;
	
	public Dice(int numDice,int numSides) {
		this.numDice = numDice;
		this.numSides = numSides;
	}
	
	public int roll() {
		int sum=0;
		for (int i=0; i< numDice;i++) {
			sum += Math.round((Math.random() * numSides));
		}
		
		return sum;
	}
}
