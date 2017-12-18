import java.util.ArrayList;

public class Game extends Code{

	private ArrayList<Panel> remainingPanels;
	private ArrayList<ArrayList<CompGuess>> previousCompGuess = new ArrayList<ArrayList<CompGuess>>();

	private Code user;
	private Code computer;

	public ArrayList<Panel> getRemainingPanels()
	{
		return remainingPanels;
	}

	public int getNumOfRemainingPanels()
	{
		return remainingPanels.size();
	}

	public boolean containsColor(boolean isWhite) 
	{
		for(int i = 0; i<remainingPanels.size(); i++)
		{
			if(remainingPanels.get(i).getIsWhite() == isWhite)
				return true;
		}
		return false;
	}

	public Game() {}

	public Game(boolean hasDash)
	{
		remainingPanels = resetPanels(hasDash);
		user = new Code(hasDash, remainingPanels);
		computer = new Code(hasDash, remainingPanels);
		for(int i = 0; i<user.getCodeLength(); i++)
		{
			ArrayList<CompGuess> foo = new ArrayList<CompGuess>();
			previousCompGuess.add(foo);
		}
	}

	public  ArrayList<ArrayList<CompGuess>> getCompGuesses()
	{
		return previousCompGuess;
	}
	
	public String toString()
	{
		return ("user " + user + "\n computer " + computer);
	}

	public Code getUserHand()
	{
		return user;
	}

	public Code getCompHand()
	{
		return computer;
	}

	public int getCompHandLength()
	{
		return computer.getCodeLength();
	}

	public int getUserHandLength()
	{
		return user.getCodeLength();
	}
	
	public boolean remainingContainsColor(boolean isWhite)
	{
		for(int i = 0; i<remainingPanels.size(); i++)
		{
			if(remainingPanels.get(i).getIsWhite() == isWhite)
				return true;
		}
		return false;
	}

	/*public int getGuessIndex(int guessIndex)
	{
		return guessIndex;
	}

	public int getGuessValue(int guessValue)
	{
		return guessValue;
	}*/


	public Panel getRandomPanel(boolean isWhite)
	{
		int ran1 = (int)(Math.random()*remainingPanels.size());
		while(remainingPanels.get(ran1).getIsWhite() != isWhite)
		{
			ran1 = (int)(Math.random()*remainingPanels.size());
		}
		Panel one = new Panel((remainingPanels.get(ran1).getIsWhite()),remainingPanels.get(ran1).getValue(),false);
		remainingPanels.remove(ran1);

		return one;
	}

	/*public boolean guess(Code guesser, Code victim, int guessIndex, int guessValue, Panel ranPanel) 
	{
		if(victim.getPanels().get(guessIndex).getValue() == guessValue)
		{
			rightGuess(false, guesser,victim, ranPanel);
			return true;
		}
		else {
			guesser.addPanel(ranPanel, true);
			return false;
		}
	}

	public void rightGuess(boolean wantAnotherGuess,Code guesser, Code victim, Panel ranPanel)
	{
		if(wantAnotherGuess)
		{
			int gI = getGuessIndex(0);
			int gV = getGuessValue(0);
			guess(guesser,victim,gI,gV, ranPanel);
		}
		else
		{
			guesser.addPanel(ranPanel, false);
		}
	}*/
}
