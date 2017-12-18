
public class CompGuess extends Guess implements Comparable{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private int index;
	
	public CompGuess(int value, boolean isW, int index)
	{
		super(value,isW);
		this.index = index;
	}
	
	public boolean equals(CompGuess guess)
	{
		if(guess.getIndex() == index &&guess.getValue() == this.getValue())
			return true;
		return false;
	}
	public int getIndex()
	{
		return index;
	}
	
	public String toString()
	{
		return ("index: "+index+" ("+this.getValue()+"/"+this.isWhite()+")");
	}
	
	public void moveIndexDown()
	{
		index--;
	}
	
	public void moveIndexUp()
	{
		index++;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
