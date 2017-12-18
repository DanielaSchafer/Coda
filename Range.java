import java.util.ArrayList;
import java.util.Comparator;

public class Range implements Comparable<Range>{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private int start;
	private int end;
	private double percent;
	private boolean isFlipped;
	private int range;

	public Range(int s, int e, boolean withDash, boolean isFlipped)
	{
		start = s;
		end = e;
		if(withDash)
			percent = (e-s)/13;
		else
			percent = (e-s)/12;
		this.isFlipped = isFlipped;
		
		range = e-s;
	}

	public int getRange()
	{
		return range;
	}
	
	public String toString()
	{
		return ("(start: "+ start+ " end: "+ end + " range: "+ range+")");
	}
	
	public int getStart()
	{
		return start;
	}
	
	public int getEnd()
	{
		return end;
	}
	
	public double getPercent()
	{
		return percent;
	}
	
	public boolean getIsFlipped()
	{
		return isFlipped;
	}

	@Override
	public int compareTo(Range compareRange) {
		int compareValue = compareRange.getRange();
		return this.range - compareValue;
	}
	
	public static Comparator<Range> NameComparator = new Comparator<Range>() {
		@Override
		public int compare(Range r1, Range r2) {

			return r1.compareTo(r2);
		}
	};
}
