import java.util.ArrayList;
/**\
 * 
 * @author LucGarabrant
 *
 */
public class Player {

	private String name;
	private int level;
	private double timePlayed;
	private int numberOfGuesses;
	private int max;
	private int generatedInt;
	private ArrayList<Integer> generatedValues;

	public Player(String name, int level, int numberOfGuesses,ArrayList<Integer> generatedValues) {
		this.name = name;
		this.level = level;
		this.numberOfGuesses = numberOfGuesses;
		this.generatedValues = generatedValues;
	}
	
	public Player(String name, int level, int numberOfGuesses, int max, int generatedInt) {
		this.name = name;
		this.level = level;
		this.numberOfGuesses = numberOfGuesses;
		this.max = max;
		this.generatedInt = generatedInt;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getTimePlayed() {
		return timePlayed;
	}
	
	public void setTimePlayed(double timePlayed) {
		this.timePlayed = timePlayed;
	}

	public int getNumberOfGuesses() {
		return numberOfGuesses;
	}

	public void setNumberOfGuesses(int numberOfGuesses) {
		this.numberOfGuesses = numberOfGuesses;
	}
	
	public ArrayList<Integer> getGeneratedValues() {
		return generatedValues;
	}

	public void setGeneratedValues(ArrayList<Integer> generatedValues) {
		this.generatedValues = generatedValues;
	}
	
	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getGeneratedInt() {
		return generatedInt;
	}

	public void setGeneratedInt(int generatedInt) {
		this.generatedInt = generatedInt;
	}

	public void incrementGuesses() {
		this.numberOfGuesses++;
		
	}

	public String toString() {
		return (""+name+","+level+","+timePlayed+","+numberOfGuesses);
		
	}
	
	
	
	
	
}
