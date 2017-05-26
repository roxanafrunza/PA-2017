import java.util.Comparator;

public class StrengthComparator implements Comparator<Location> {

	@Override
	public int compare(Location o1, Location o2) {
		if (o1.getSite().strength > o2.getSite().strength)
			return 1;
		if (o1.getSite().strength < o2.getSite().strength)
			return -1;
		return 0;
	}

}
