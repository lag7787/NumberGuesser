import java.util.Comparator;

/**
 * 
 * @author Luc Garabrant
 *
 */

public class GuessesSortingComparator implements Comparator<Player> {

	@Override
	public int compare(Player obj1, Player obj2) {
		
		return obj1.getNumberOfGuesses() - obj2.getNumberOfGuesses();
	}

	
	
}
