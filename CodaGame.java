import java.util.ArrayList;
import java.util.Collections;

public class CodaGame {

	Game newGame;

	private Panel userRanPan;
	private Panel compRanPan;
	private int playerGuessIndex;
	private int playerGuessValue;
	private int computerGuessIndex;
	private int computerGuessValue;

	private final String PLAYER_WIN = " \n\n PLAYER WINS";
	private final String COMPUTER_WIN = "\n\n COMPUTER WINS \n";

	private ArrayList<Guess> userGuess = new ArrayList<Guess>();

	public CodaGame()
	{
		newGame = new Game(false);
	}

	public int getComputerGuessIndex()
	{
		return computerGuessIndex;
	}

	public int getComputerGuessValue()
	{
		return computerGuessValue;
	}

	public int getPlayerGuessIndex()
	{
		return playerGuessIndex;
	}

	public int getPlayerGuessValue()
	{
		return playerGuessValue;
	}

	public GameState playerDrawPanel(boolean isWhite)
	{
		while(newGame.containsColor(isWhite) == false)
		{
			return GameState.PLR_DRAW;
		}

		userRanPan = newGame.getRandomPanel(isWhite);
		return GameState.PLR_CHOOSE_PANEL;
	}

	public GameState playerPickGuessPanel(int index)
	{
		//System.out.println("Player chose Panel: "+ index);
		int panelG = index;
		if(panelG>=newGame.getCompHandLength() || panelG<0|| newGame.getCompHand().getPanel(panelG).isFlipped())
		{
			return GameState.PLR_CHOOSE_PANEL;
		}
		playerGuessIndex = index;
		return GameState.PLR_CHOOSE_VALUE;

	}

	public GameState playerGuessValue(int value)
	{
		//System.out.println("Player guessed " + value);
		if(value >12 || value<-2)
		{
			return GameState.PLR_CHOOSE_VALUE;
		}
		playerGuessValue = value;

		Guess PLRGuess = new Guess(playerGuessValue,newGame.getCompHand().getPanel(playerGuessIndex).getIsWhite());
		userGuess.add(PLRGuess);

		//if the guess is correct flip computer panel and give user the option to go again
		if(newGame.getCompHand().getPanel(playerGuessIndex).getValue() == playerGuessValue)
		{
			System.out.println("player guessed correctly");
			newGame.getCompHand().getPanel(playerGuessIndex).flip(); 
			return GameState.PLR_CHOOSE_GO_AGAIN;
		}
		else
		{//adds user random panel (flipped) to user hand
			System.out.println("player guessed incorrectly");
			System.out.println("Player Turn is over");
			
			int indexOfNewPan = newGame.getUserHand().getAddedPanelIndex(userRanPan, true);
			
			newGame.getUserHand().addPanel(userRanPan,true);
			
			ArrayList<CompGuess> guesses = new ArrayList<CompGuess>();
			newGame.getCompGuesses().add(indexOfNewPan, guesses);
			return GameState.PLR_TURN_OVER;
		}
	}

	public GameState playerGoAgain(boolean goAgain)
	{
		if(goAgain)
			return GameState.PLR_CHOOSE_PANEL;
		else
		{
			System.out.println("Player Turn is over");
			int indexOfNewPan = newGame.getUserHand().getAddedPanelIndex(userRanPan, false);
		
			newGame.getUserHand().addPanel(userRanPan,false);
			
			ArrayList<CompGuess> guesses = new ArrayList<CompGuess>();
			newGame.getCompGuesses().add(indexOfNewPan, guesses);

			userGuess.clear();
			//slide(previousGuess,userRanPan);

			return GameState.PLR_TURN_OVER;
		}
	}


	public GameState computerDrawPanel()
	{
		System.out.println("Computer is drawing panel");
		ArrayList<Panel> known = new ArrayList<Panel>();
		known.addAll(getKnownPanels(false));

		ArrayList<Range> range1 = ranges(newGame.getUserHand(),known,false);
		int index = guessLocation(range1);
		boolean isWhite = newGame.getUserHand().getPanel(index).getIsWhite();

		if(newGame.remainingContainsColor(isWhite) == false)
		{
			if(isWhite == true)
				isWhite = false;
			else if(isWhite == false)
				isWhite = true;
		}
		compRanPan = newGame.getRandomPanel(isWhite);


		return GameState.COMP_GUESS;
	}

	public GameState computerGuess()
	{
		System.out.println("Computer is guessing");
		System.err.println("    Computer guesses: " + newGame.getCompGuesses());
		System.out.println("	user hand: " + newGame.getUserHand());
		System.out.println("	comp hand: " + newGame.getCompHand());
		
		//gets known panels
		ArrayList<Panel> known = new ArrayList<Panel>();
		known.addAll(getKnownPanels(true));


		ArrayList<Range> range2 = ranges(newGame.getUserHand(),known,false);
		CompGuess myGuess = guess(range2, known);

		System.out.println(myGuess+"\n");


		//converts compGuesses to panels

		if(newGame.getCompGuesses().get(myGuess.getIndex()) == null) {
			newGame.getCompGuesses().set(myGuess.getIndex(), new ArrayList<CompGuess>());
		}
		else {

			ArrayList<Panel> previousGuessPan = new ArrayList<Panel>();
			for(int i = 0; i<newGame.getCompGuesses().get(myGuess.getIndex()).size(); i++) {
				previousGuessPan.add(newGame.getCompGuesses().get(myGuess.getIndex()).get(i).getPanel());
			}

			if(containsPanel(myGuess.getPanel(),previousGuessPan))
			{
				known.clear();
				known.addAll(getKnownPanels(true,false));
				myGuess = guess(range2, known);
			}
		}
		
		newGame.getCompGuesses().get(myGuess.getIndex()).add(myGuess);
		/*for(int i = 0; i<known.size(); i++)
		{
			if(myGuess.getPanel().compareTo(known.get(i)) == 0)
				System.out.println("ERRRORORORORORORRRRR \n" + known.get(i));
		}*/

		Code.sort(known);
		System.out.println("known: "+known);

		computerGuessIndex = myGuess.getIndex();
		computerGuessValue = myGuess.getValue();

		if(newGame.getUserHand().getPanel(myGuess.getIndex()).getValue() == myGuess.getValue())
		{
			System.out.println("Computer guessed correctly");
			newGame.getUserHand().getPanel(myGuess.getIndex()).flip();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return GameState.COMP_CHOOSE_GO_AGAIN;
		}
		else
		{
			System.out.println("Computer guessed incorrectly");
			System.out.println("Computer turn is over");
			
			newGame.getCompHand().addPanel(compRanPan, true);
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return GameState.COMP_TURN_OVER;
		}
	}



	public GameState computerGoAgain()
	{
		ArrayList<Panel> known = new ArrayList<Panel>();
		known.addAll(getKnownPanels(true));
		ArrayList<Range> ranges = ranges(newGame.getUserHand(),known,false);
		System.out.println("The computer guessed your panel!");
		boolean goAgain = guessAgain(ranges,getCompRange());

		if(goAgain)
		{
			System.out.println("Computer is going again");
			return GameState.COMP_GUESS;
		}
		else
		{
			newGame.getCompHand().addPanel(compRanPan, false);
			
			System.out.println("Computer turn is over");
			return GameState.COMP_TURN_OVER;
		}
	}

	public GameState gameOverComp()
	{

		for(int i = 0; i<newGame.getCompHandLength(); i++)
		{
			if(newGame.getCompHand().getPanel(i).isFlipped() == false)
				return GameState.PLR_DRAW; 
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return GameState.GAME_OVER;

	}

	public GameState gameOverPLR()
	{
		for(int i = 0; i<newGame.getUserHandLength(); i++)
		{
			if(newGame.getUserHand().getPanel(i).isFlipped() == false)
				return GameState.COMP_DRAW;
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return GameState.GAME_OVER;
	}

	public Panel getUserRanPan()
	{
		return userRanPan;
	}

	public Panel getCompRanPan()
	{
		return compRanPan;
	}

	public Game getGame()
	{
		return newGame;
	}

	/*public void compTurn(boolean withDash)
	{
		//REPAINT HERE
		ArrayList<Panel> known = new ArrayList<Panel>();
		known.addAll(getKnownPanels(false));

		ArrayList<Range> range1 = ranges(newGame.getUserHand(),known,withDash);
		int index = guessLocation(range1);
		boolean isWhite = newGame.getUserHand().getPanel(index).getIsWhite();
		compRanPan = newGame.getRandomPanel(isWhite);

		known.clear();
		known.addAll(getKnownPanels(true));

		ArrayList<Range> range2 = ranges(newGame.getUserHand(),known,withDash);
		CompGuess myGuess = guess(range2, getKnownPanels(true));
		computerTurn(myGuess, range2);

		//REPAINT HERE
	}*/



	public static int getFirstUnflippedPanel(Code code)
	{
		for(int i = 0; i<code.getCodeLength(); i++)
		{
			if(code.getPanel(i).isFlipped() == false)
				return i;
		}
		return -1;
	}

	public static int getLastUnflippedPanel(Code code)
	{
		for(int i = code.getCodeLength()-1; i>=0; i--)
		{
			if(code.getPanel(i).isFlipped() == false)
				return i;
		}
		return -1;
	}

	public boolean containsPanel(Panel p, ArrayList<Panel> panels)
	{
		for(int i = 0; i<panels.size(); i++)
		{
			if(panels.get(i) != null && panels.get(i).getValue() == p.getValue() && panels.get(i).getIsWhite() == p.getIsWhite())
				return true;
		}
		return false;
	}

	public ArrayList<Panel> getKnownPanels(boolean withRanPan)
	{
		ArrayList<Panel>  known= new ArrayList<Panel>();
		known.addAll(newGame.getCompHand().getPanels());
		known.addAll(newGame.getUserHand().getFlippedPanels());
		//known.addAll(previousGuess);

		System.out.println("user guess: "+ userGuess);

		for(int i = 0; i<userGuess.size(); i++)
		{
			known.add(userGuess.get(i).getPanel());
		}
		/*for(int i = 0; i<previousGuess.size(); i++)
		{
			known.add(previousGuess.get(i).getPanel());
		}*/


		if(withRanPan)
			known.add(compRanPan);

		return known;
	}

	public ArrayList<Panel> getKnownPanels(boolean withRanPan, boolean withUserGuesses)
	{
		ArrayList<Panel>  known= new ArrayList<Panel>();
		known.addAll(newGame.getCompHand().getPanels());
		known.addAll(newGame.getUserHand().getFlippedPanels());
		//known.addAll(previousGuess);

		System.out.println("user guess: "+ userGuess);

		if(withUserGuesses)
		{
			for(int i = 0; i<userGuess.size(); i++)
			{
				known.add(userGuess.get(i).getPanel());
			}
		}

		/*for(int i = 0; i<previousGuess.size(); i++)
		{
			known.add(previousGuess.get(i).getPanel());
		}*/


		if(withRanPan)
			known.add(compRanPan);

		return known;
	}

	public int lowestUnknownPanel(boolean isWhite, ArrayList<Panel> knownPanels)
	{
		//ArrayList<Panel> known = new ArrayList<Panel>();
		//known.addAll(getKnownPanels(true,false));

		System.out.println("\nlowestUnknownPanel known: "+ knownPanels +"\ncolor " + isWhite);
		for(int i = 0; i<12; i++)
		{
			Panel temp = new Panel(isWhite,i,false);
			if(containsPanel(temp,knownPanels) == false)//containsValBol(knownPanels,i,isWhite == false))
			{
				System.out.println("lowest unknown panel: " + i + " " + isWhite);
				return i;
			}
			System.out.println("highestUnknownPanel already exists: " + i + " " + isWhite);
		}
		return -1;
	}

	public int highestUnknownPanel(boolean isWhite, ArrayList<Panel> knownPanels)
	{	
		ArrayList<Panel> known = new ArrayList<Panel>();
		known.addAll(getKnownPanels(true,false));

		System.out.println("\nhighestUnknownPanel known: "+ known+"\ncolor " + isWhite);
		for(int i = 11; i>=0; i--)
		{
			Panel temp = new Panel(isWhite,i,false);
			if(containsPanel(temp,known) == false)
			{
				System.out.println("highest unknown panel: " + i + " " + isWhite);
				return i;
			}
			System.out.println("highestUnknownPanel already exists: " + i + " " + isWhite);
		}

		return -1;
	}

	public static boolean containsColor(boolean color, ArrayList<Panel> knownPanels)
	{
		for(int i = 0; i<knownPanels.size(); i++)
		{
			if(knownPanels.get(i).getIsWhite() == color)
				return true;
		}
		return false;
	}

	public CompGuess guess(ArrayList<Range> ranges, ArrayList<Panel> known)
	{
		System.out.println("\nim in the guess method");
		int minR = 12;
		int start = 0;
		int end = 0;
		int index = 0;

		//determines index with the smallest range
		for(int i = 0; i<ranges.size(); i++)
		{
			System.out.println("i am in the for loop of the guess method");
			if(ranges.get(i).getStart() != -1 && ranges.get(i).getRange() <minR)
			{
				if(ranges.get(i).getRange() == 0)
				{
					minR = ranges.get(i).getRange();
					start = ranges.get(i).getStart();
					end = ranges.get(i).getEnd();
					index = i;
					break;
				}
				minR = ranges.get(i).getRange();
				start = ranges.get(i).getStart();
				end = ranges.get(i).getEnd();
				index = i;
			}
		}

		System.out.println("I have exited the for loop of the guess method");

		//gets random value for index with the smallest range
		int ranNum = (int)(Math.random() * (end - start) + start);
		CompGuess temp = new CompGuess(ranNum,newGame.getUserHand().getPanel(index).getIsWhite(),index);
		Panel p = temp.getPanel();

		if(ranges.get(index).getRange() == 0 && containsPanel(p,known))
			return temp;
		
		else {
			ArrayList<Panel> knownWithoutUserGuess = getKnownPanels(true,false);
			while( containsPanel(p,knownWithoutUserGuess) || (newGame.getCompGuesses().get(index) != null && newGame.getCompGuesses().get(index).contains(temp)))
			{
				if(end < 0)
				{
					System.err.println("I am in the while loop. End: " + end + " Start: " + start);
					try {
						Thread.sleep(1000000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				ranNum = (int)(Math.random() * (end - start) + start);
				temp = new CompGuess(ranNum,newGame.getUserHand().getPanel(index).getIsWhite(),index);
				p = temp.getPanel();
				/*System.out.println("	new comp guess: " + temp);
				System.out.println("    ranges: "+ ranges);
				System.out.println("    known: " + knownWithoutUserGuess);
				System.out.println("    userHand: " + newGame.getUserHand());*/
			}
			System.out.println("i have exited the while loop");
		}
		System.out.println("im out of the guess method\n");
		return temp;
	}

	public int guessLocation(ArrayList<Range> ranges)
	{
		int minR = 12;
		int start = 0;
		int end = 0;
		int index = 0;

		for(int i = 0; i<ranges.size(); i++)
		{
			if(ranges.get(i).getStart() != -1 && ranges.get(i).getRange() <minR)
			{
				minR = ranges.get(i).getRange();
				start = ranges.get(i).getStart();
				end = ranges.get(i).getEnd();
				index = i;
			}
		}
		return index;
	}

	//determines whether the computer should guess again
	public boolean guessAgain(ArrayList<Range> ranges, ArrayList<Range> compRanges)
	{
		int minR = 12;
		int minRComp = 12;

		for(int i = 0; i<ranges.size(); i++)
		{
			if(ranges.get(i).getStart() != -1 && ranges.get(i).getRange() <minR)
			{
				minR = ranges.get(i).getRange();
			}
		}

		for(int i = 0; i <compRanges.size(); i++)
		{
			if(ranges.get(i).getStart() != -1 && ranges.get(i).getRange() <minR)
			{
				minR = ranges.get(i).getRange();
			}
		}


		if(minR == 0 || minR == 1)
			return true;

		if(minRComp<2)
			return false;

		if(minR == 2)
		{	
			int ranNum = (int)(Math.random()*10);
			if(ranNum<6)
				return true;
			else 
				return false;
		}
		if(minR == 3)
		{
			int ranNum = (int)(Math.random()*10);
			if(ranNum<4)
				return true;
			else 
				return false;
		}
		if(minR == 4)
		{
			int ranNum = (int)(Math.random()*10);
			if(ranNum<3)
				return true;
			else 
				return false;
		}
		return false;
	}

	//figures out where old guess are located when a new panel is added to the player's code
	public ArrayList<CompGuess> slide(ArrayList<CompGuess> previous, Panel newPan)
	{
		System.out.println("\n slide before:" + previous);
		for(int i = 0; i<previous.size(); i++)
		{
			if(previous.get(i).getPanel().compareTo(newPan) < 0)
				previous.get(i).moveIndexDown();

			else if(previous.get(i).getPanel().compareTo(newPan) >0)
				previous.get(i).moveIndexUp();
		}
		System.out.println("slide after:" + previous + "\n");
		return previous;
	}


	//calculates the range of the computer code based on flipped tiles
	public ArrayList<Range> getCompRange()
	{
		ArrayList<Panel> known = new ArrayList<Panel>();
		for(int i = 0; i<newGame.getCompHandLength(); i++)
		{
			if(newGame.getCompHand().getPanel(i).isFlipped())
			{
				known.add(newGame.getCompHand().getPanel(i));
			}
		}
		for(int i = 0; i<newGame.getUserHandLength(); i++)
		{
			if(newGame.getUserHand().getPanel(i).isFlipped())
			{
				known.add(newGame.getUserHand().getPanel(i));
			}
		}
		return(ranges(newGame.getCompHand(),known,false));
	}

	//finds equivalent panels between a code and a array
	public ArrayList<Panel> findSimilarPanels(Code code1, ArrayList<Panel> known)
	{
		ArrayList<Panel> similars = new ArrayList<Panel>();
		for(int i = 0; i<code1.getCodeLength(); i++)
		{
			if(containsPanel(code1.getPanel(i),known))
				similars.add(code1.getPanel(i));
		}
		return similars;
	}


	//calculates the range of each panel
	public ArrayList<Range> ranges(Code code,ArrayList<Panel> known, boolean withDash)
	{
		System.err.println("similar panels: "+findSimilarPanels(code,known));
		System.err.println("known: " + known);
		//System.err.println("user: "+ newGame.getUserHand());

		ArrayList<Range> codeRanges = new ArrayList<Range>();
		ArrayList<Integer> starts = new ArrayList<Integer>();
		ArrayList<Integer> ends = new ArrayList<Integer>();
		//ArrayList<Panel> knownWithoutUserGuess = getKnownPanels(true,false);

		int currentValue = lowestUnknownPanel(code.getPanel(getFirstUnflippedPanel(code)).getIsWhite(), known);
		System.out.println("lowestUnknownPanel current: " + currentValue);
		//System.out.println(known);

		for(int i = 0; i<code.getCodeLength(); i++)
		{
			//System.out.println("currentValue " + currentValue);

			//Panel temp = new Panel(code.getPanel(i).getIsWhite(),currentValue,false);

			/*while(containsPanel(temp,known))
			{
				System.out.println("im in while loop #1");
				currentValue++;
				temp = new Panel(code.getPanel(i).getIsWhite(),currentValue,false);
			}*/
			//System.out.println("value already in known \n" + currentValue + " " + code.getPanel(i).getIsWhite());

			//temp = new Panel(code.getPanel(i).getIsWhite(),currentValue,false);


			if(code.getPanel(i).isFlipped())
			{
				starts.add(-1);
				if(i<code.getCodeLength()-1)
				{
					//if white is first
					if(code.getPanel(i).getIsWhite())
						currentValue = code.getPanel(i).getValue() + 1;
					//black to black
					else if(code.getPanel(i).getIsWhite() == false && code.getPanel(i+1).getIsWhite() == false)
						currentValue = code.getPanel(i).getValue() + 1;
					//black to white
					else if(code.getPanel(i).getIsWhite() == false && code.getPanel(i+1).getIsWhite())
						currentValue = code.getPanel(i).getValue();

					Panel currPanel = new Panel(code.getPanel(i+1).getIsWhite(),currentValue,false);
					while(containsPanel(currPanel,known))
					{
						//System.out.println("im in while loop #2");
						currentValue++;
						currPanel = new Panel(code.getPanel(i+1).getIsWhite(),currentValue,false);
					}
				}
			}
			else
			{
				starts.add(currentValue);

				if(i<code.getCodeLength()-1)
				{
					//black to black
					if(code.getPanel(i).getIsWhite() == false && code.getPanel(i+1).getIsWhite() == false)
						currentValue++;
					//starts as white
					else if(code.getPanel(i).getIsWhite())
						currentValue++;
					//black to white has no change

					Panel currPanel; 

					currPanel = new Panel(code.getPanel(i+1).getIsWhite(),currentValue,false);
					while(containsPanel(currPanel,known))
					{
						//System.out.println("im in while loop #3");
						currentValue++;
						currPanel = new Panel(code.getPanel(i+1).getIsWhite(),currentValue,false);
					}
				}
			}
		}



		currentValue = highestUnknownPanel(code.getPanel(getLastUnflippedPanel(code)).getIsWhite(), known);
		System.out.println("highestUnknownPanel current: " + currentValue);

		for(int i = code.getCodeLength()-1; i>=0; i--)
		{
			//if the panel if flipped
			if(code.getPanel(i).isFlipped())
			{
				//indicated that the panel is flipped in the array
				ends.add(-1);

				if(i>0)
				{
					//if it starts as black
					if(code.getPanel(i).getIsWhite() == false) {
						//System.err.println("starts as black");
						currentValue = code.getPanel(i).getValue() - 1;
						//System.out.println("current value: " + currentValue);
					}
					//white to white
					else if(code.getPanel(i).getIsWhite() == true && code.getPanel(i-1).getIsWhite() == true) {
						//System.err.println("white to white");
						currentValue = code.getPanel(i).getValue() - 1;
						//System.out.println("current value: " + currentValue);
					}
					//white to black
					else if(code.getPanel(i).getIsWhite() == true && code.getPanel(i-1).getIsWhite() == false) {
						//System.err.println("white to black");
						currentValue = code.getPanel(i).getValue();
						//System.out.println("current value: " + currentValue);
					}

					Panel currPanel = new Panel(code.getPanel(i-1).getIsWhite(),currentValue,false);
					while(containsPanel(currPanel,known))
					{
						//System.out.println("im in while loop #5");
						currentValue--;
						currPanel = new Panel(code.getPanel(i-1).getIsWhite(),currentValue,false);
					}
				}

			}
			else
			{
				ends.add(currentValue);

				if(i>0)
				{
					//starts as black
					if(i>0 && code.getPanel(i).getIsWhite() == false)
						currentValue = currentValue-1; 

					//white to white
					else if(i>0 && code.getPanel(i).getIsWhite() && code.getPanel(i-1).getIsWhite())
						currentValue = currentValue-1;

					Panel currPanel = new Panel(code.getPanel(i-1).getIsWhite(),currentValue,false);
					while(containsPanel(currPanel,known))
					{
						System.out.println("im in while loop #6");
						currentValue--;
						currPanel = new Panel(code.getPanel(i-1).getIsWhite(),currentValue,false);
					}
					//white to black stays the same
				}
			}
			//System.out.println("ends: "+ends);
		}

		Collections.reverse(ends);

		for(int i = 0; i<starts.size(); i++)
		{
			int s = starts.get(i);
			int e = ends.get(i);
			boolean flipped = code.getPanel(i).isFlipped();
			Range r = new Range(s,e,flipped,withDash);
			codeRanges.add(r);
			if(r.getRange()<0)
			{
				ArrayList<Panel> foo = getKnownPanels(true,false);
				System.out.println("comp hand: " + newGame.getCompHand());
				System.out.println("without guessed: "+foo);
				System.out.println("with guessed: " +known);
				System.out.println("index: " + i + "  " +r );
				System.out.println("\n--------NEGATIVE RANGE----------\n");
				//codeRanges = ranges(code,foo,withDash);
			}
		}
		//System.out.println("ranges: "+ codeRanges);
		System.err.println("user hand: " + newGame.getUserHand());
		System.err.println("\n"+codeRanges);
		return codeRanges;
	}
}
