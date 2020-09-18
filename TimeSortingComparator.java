import java.util.Comparator;

/**
 * 
 * @author Luc Garabrant
 *
 */

public class TimeSortingComparator implements Comparator<Player>{

	@Override
	public int compare(Player obj1, Player obj2) {
		// TODO Auto-generated method stub
		
		return (int) (obj1.getTimePlayed() - obj2.getTimePlayed() );
	}
	
	
	
	
}
