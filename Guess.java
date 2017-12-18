
public class Guess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private Panel p;
	private int num;
	private boolean isWhite;
	
	
	public Guess(int n, boolean isW)
	{
		isWhite = isW;
		num = n;
		p = new Panel(isW,n,false);
	}
	
	
	
	public String toString()
	{
		String color;
		if(isWhite)
			color = "white";
		else
			color = "black";
		return(num + "/" + color);
	}
	
	public int getValue()
	{
		return num;
	}
	
	public boolean isWhite()
	{
		return isWhite;
	}
	
	public Panel getPanel()
	{
		return p;
	}
	
	

}
