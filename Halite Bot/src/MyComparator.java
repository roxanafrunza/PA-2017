import java.util.Comparator;

public class MyComparator implements Comparator<Location> {

	@Override
	public int compare(Location o1, Location o2) {

		if(o1.getSite().getSiteValue() < o2.getSite().getSiteValue())
			return 1;
		if(o1.getSite().getSiteValue() > o2.getSite().getSiteValue())
			return -1;
		return 0;
	}

}
