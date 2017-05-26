import java.util.Comparator;

public class ProductionComparator implements Comparator<Location> {

	@Override
	public int compare(Location o1, Location o2) {
		if(o1.getSite().production > o2.getSite().production)
			return 1;
		if(o1.getSite().production < o2.getSite().production)
			return -1;
		return 0;
	}

}
