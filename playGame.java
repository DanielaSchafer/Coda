import java.applet.Applet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class playGame {

	Game newGame;
	
	private boolean isUserTurn = false;
	private boolean isPuttingInDash = false;
	private Panel userRanPan;
	private Panel compRanPan;
	private boolean isWaiting;
	
	private final String PLAYER_WIN = " \n\n PLAYER WINS";
	private final String COMPUTER_WIN = "\n\n COMPUTER WINS \n";

	private ArrayList<CompGuess> previousGuess = new ArrayList<CompGuess>();

	private ArrayList<Guess> userGuess = new ArrayList<Guess>();
	
	//private CodaGraphics foo = new CodaGraphics();

	//private CodaGraphics foo = new CodaGraphics();
	

	

	Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		playGame poo = new playGame(false);
		poo.runTheGame();
	}

	public void runTheGame()
	{
		userTurn(true);
	}

	public playGame(boolean withDash)
	{
		newGame = new Game(withDash);
	}

	public boolean compWon()
	{
		for(int i = 0; i<newGame.getUserHandLength(); i++)
		{
			if(newGame.getUserHand().getPanel(i).isFlipped() == false )
			{
				return false;
			}
		}
		return true;
	}

	public void getWaitingF()
	{
		isWaiting = false;
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

	public boolean userWon()
	{
		for(int i = 0; i<newGame.getCompHandLength(); i++)
		{
			if(newGame.getCompHand().getPanel(i).isFlipped() == false)
			{
				return false;
			}
		}
		return true;
	}
	
	public void startingScreen()
	{
		System.out.println("CODA");
		System.out.println("type in 'swag' to start");
		String userIn = sc.next();
		
		while(userIn.compareTo("swag") != 0 && userIn.compareTo("Swag") != 0)
		{
			System.out.println("if ur that dumb, u shouldn't be playing this game \n TRY AGAIN DUMMY");
			userIn = sc.next();
		}
		System.out.println("\n\n\n");
		userTurn(true);
	}

	//-----------------------------------------------------------------------------------------------------------
	//the computer's turn
	public void computerTurn(CompGuess guess, ArrayList<Range> ranges)
	{	
		//-------------------------------------------------------------------
		//computer makes a guess
		System.out.println("Computer Guess " + guess);
		boolean rightGuess;
		if(newGame.getUserHand().getPanel(guess.getIndex()).getValue() == guess.getValue())
			rightGuess = true;
		else rightGuess = false;

		if(rightGuess) //what the computer will do if the guess is correct
		{
			newGame.getUserHand().getPanel(guess.getIndex()).flip();
			System.out.println("The computer guessed your panel!");
			boolean goAgain = guessAgain(ranges,getCompRange());

			if(goAgain)
			{
				if(compWon())
				{
					System.out.println(COMPUTER_WIN);
					System.exit(0);
				}
				
				System.out.println("Computer is going again \n");
				ArrayList<Panel> known = new ArrayList<Panel>();
				known.addAll(getKnownPanels(true));
				ArrayList<Range> range2 = ranges(newGame.getUserHand(),known,false);
				CompGuess myGuess = guess(range2,getKnownPanels(true));
				computerTurn(myGuess, range2);
			}
			else if(goAgain == false)
			{
				if(compWon())
				{
					System.out.println(COMPUTER_WIN);
					System.exit(0);
				}
				
				System.out.println("Computer is ending turn \n\n");
				if(compRanPan.getValue() == -1)
				{
					int position = (int)(Math.random()*newGame.getCompHandLength());
					newGame.getCompHand().compAddDash(compRanPan, false, position);
				}
				newGame.getCompHand().addPanel(compRanPan,false);
				
				if(compWon() == false)
					userTurn(true);
				else
				{
					System.out.println(COMPUTER_WIN);
					System.exit(0);
				}
			}
		}
		else // what happens when computer's guess is incorrect
		{
			System.out.println("The Computer Has Guessed Incorrectly \n\n");
			newGame.getCompHand().addPanel(compRanPan, true);
			
			if(compWon() == false)
				userTurn(true);
			else
			{
				System.out.println(COMPUTER_WIN);
				System.exit(0);
			}
		}
	}
	//-----------------------------------------------------------------------------------------------------------------
	// the user's turn
	public void userTurn(boolean firstTime)
	{
		if(userWon())
		{
			System.out.println(PLAYER_WIN);
			System.exit(0);
		}
		else if(compWon())
		{
			System.out.println(COMPUTER_WIN);
			System.exit(0);
		}
		
		boolean guessColor;
		int guessValue;

		//-------------------------------- REPAINT -----------------------------------
		
		System.out.println("computer hand: ");
		newGame.getCompHand().compCode();
		System.out.println("\n");
		System.out.println("your hand: \n"+newGame.getUserHand());
		System.out.println();
		
		//-------------------------------- REPAINT -----------------------------------
		
		for(int i = 0; i<newGame.getCompHandLength(); i++)
		{
			newGame.getCompHand().getPanel(i).getButton().setEnabled(true);
		}


		if(firstTime)
		{
			System.out.println("What color do you want your tile to be?");
			String color = sc.next();
			boolean isWhite;

			if(color.compareTo("black") == 0 || color.compareTo("Black") == 0)
				isWhite = false;
			else
				isWhite = true;

			while(newGame.containsColor(isWhite) == false)
			{
				System.out.println("Sorry, there are no more " + color + " tiles. Please choose another color");
				color = sc.next();

				if(color.compareTo("black") == 0 || color.compareTo("Black") == 0)
					isWhite = false;
				else
					isWhite = true;
			}
			userRanPan = newGame.getRandomPanel(isWhite);
			
			//------------------------------- REPAINT ------------------------------------
			
			System.out.println("your random panel: "+userRanPan);
			
			//------------------------------- REPAINT ------------------------------------
		}
		System.out.println("Which panel number would you like to guess?");
		int panelG = sc.nextInt();
		while(panelG>=newGame.getCompHandLength() || panelG<0|| newGame.getCompHand().getPanel(panelG).isFlipped())
		{
			if(panelG>=newGame.getCompHandLength() || panelG<0)
				System.out.println("sorry, your guess is out of range, enter another panel");
			else if(newGame.getCompHand().getPanel(panelG).isFlipped())
				System.out.println("sorry, the panel you have guessed has already been flipped, enter another panel");
			panelG = sc.nextInt();
		}

		System.out.println("What value do you think this tile holds?");
		int valueG = sc.nextInt();
		while(valueG >12 || valueG<-2)
		{
			System.out.println("sorry, your guess is out of range, enter another panel");
			valueG = sc.nextInt();
		}

		guessValue = valueG;
		guessColor = newGame.getCompHand().getPanel(panelG).getIsWhite();

		if(newGame.getCompHand().getPanel(panelG).getValue() == valueG)
		{
			newGame.getCompHand().getPanel(panelG).flip(); 
			rightGuess();
		}
		else
		{
			wrongGuess();
		}

		Guess temp = new Guess(guessValue, guessColor);
		userGuess.add(temp);
	}

	//-----------------------------------------------------------------------------------------------------------
	//the random computer guessing
	public boolean computerGuess()
	{
		boolean isWhite;
		int randomBool = (int) (Math.random()*50);
		if(randomBool<25)
			isWhite = true;
		else
			isWhite = false;
		while(newGame.containsColor(isWhite) == false)
		{
			randomBool = (int) (Math.random()*50);
			if(randomBool<25)
				isWhite = true;
			else
				isWhite = false;
		}
		int randomPanel = (int) (Math.random()*newGame.getUserHandLength());
		//System.out.println(randomPanel);
		int randomGuess = (int) (Math.random()*12);
		if(newGame.getCompHand().contains(new Panel(isWhite,randomGuess,false)))
		{
			if(newGame.getUserHand().getPanel(randomPanel).compareTo(new Panel(isWhite,randomGuess,false)) == 0)
				return true;
			else 
				return false;
		}
		return false;
	}



	//-----------------------------------------------------------------------------------------------------------
	//what happens when the User guesses correctly
	public void rightGuess()
	{
		if(userWon())
		{
			System.out.println(PLAYER_WIN);
			System.exit(0);
		}

		System.out.println("good job! you've correctly guessed the panel \n would you like to guess again? if so enter yes, if you would like to add your tile to your code, reply no\n\n");
		String answer = sc.next();
		if(answer.compareTo("yes") == 0 || answer.compareTo("Yes") == 0)
		{
			userTurn(false);
		}
		else
		{
			if(userRanPan.getValue() == -1)
				newGame.getUserHand().addDash(false,userRanPan);
			else
				newGame.getUserHand().addPanel(userRanPan,false);

			userGuess.clear();
			slide(previousGuess,userRanPan);

			if(userWon() == false)
				compTurn(false);
			else
			{
				System.out.println(PLAYER_WIN);
				System.exit(0);
			}
		}
		//foo.repaint();
	}

	//--------------------------------------------------------------------------------------------------
	//what happens when the User makes an incorrect guess
	public void wrongGuess()
	{
		System.out.println("Sorry, your guess is wrong");
		if(userRanPan.getValue() == -1)
			newGame.getUserHand().addDash(true,userRanPan);
		else
			newGame.getUserHand().addPanel(userRanPan,true);

		if(userWon() == false)
			compTurn(false);
		else if(userWon())
		{
			System.out.println(PLAYER_WIN);
			System.exit(0);
		}
	}


	
	//----------------------------------------------------------------------
	//COMPUTER AI

	public void compTurn(boolean withDash)
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
	}
	
	public ArrayList<Range> ranges(Code code,ArrayList<Panel> known, boolean withDash)
	{
		ArrayList<Range> codeRanges = new ArrayList<Range>();
		ArrayList<Integer> starts = new ArrayList<Integer>();
		ArrayList<Integer> ends = new ArrayList<Integer>();
		ArrayList<Boolean> isFlipped = new ArrayList<Boolean>();

		int currentValue = lowestUnknownPanel(code.getPanel(0).getIsWhite(), known);
		//System.out.println(known);

		for(int i = 0; i<code.getCodeLength(); i++)
		{
			//Panel temp = new Panel(code.getPanel(i).getIsWhite(),currentValue,false);

			while(containsValBol(known,currentValue,code.getPanel(i).getIsWhite()))
			{
				//System.out.println("value already in known \n" + currentValue + " " + code.getPanel(i).getIsWhite());
				currentValue++;
				//temp = new Panel(code.getPanel(i).getIsWhite(),currentValue,false);
			}

			if(code.getPanel(i).isFlipped())
			{
				starts.add(-1);
				isFlipped.add(true);

				//white to white
				if(i<code.getCodeLength()-1 && code.getPanel(i).getIsWhite())
					currentValue = code.getPanel(i).getValue() + 1;
				//black to black
				else if(i<code.getCodeLength()-1 && (code.getPanel(i).getIsWhite() == false && code.getPanel(i+1).getIsWhite() == false))
					currentValue = code.getPanel(i).getValue() + 1;
				//black to white
				else if(i<code.getCodeLength()-1 && (code.getPanel(i).getIsWhite() == false && code.getPanel(i+1).getIsWhite()))
					currentValue = code.getPanel(i).getValue();
				//white to black
				else if(i<code.getCodeLength()-1 && (code.getPanel(i).getIsWhite() == false && code.getPanel(i+1).getIsWhite()))
					currentValue = code.getPanel(i).getValue()+1;

				if(i<code.getCodeLength()-1)
				{
					Panel currPanel = new Panel(code.getPanel(i+1).getIsWhite(),currentValue,false);
					while(known.contains(currPanel))
						currentValue++;
				}
			}
			else
			{
				starts.add(currentValue);
				//black to white
				if(i<code.getCodeLength()-1 && code.getPanel(i).getIsWhite() == false && code.getPanel(i+1).getIsWhite())
					currentValue = currentValue;
				//black to black
				else if(i<code.getCodeLength()-1 && code.getPanel(i).getIsWhite() == false && code.getPanel(i+1).getIsWhite() == false)
					currentValue = currentValue+1;
				//white to black
				else if(i<code.getCodeLength()-1 && code.getPanel(i).getIsWhite() && code.getPanel(i+1).getIsWhite() == false)
					currentValue = currentValue+1;
				//white to white
				else if(i<code.getCodeLength()-1 && code.getPanel(i).getIsWhite() && code.getPanel(i+1).getIsWhite())
					currentValue = currentValue+1;

				Panel currPanel; 

				if(i<code.getCodeLength()-1)
				{
					currPanel = new Panel(code.getPanel(i+1).getIsWhite(),currentValue,false);
					while(known.contains(currPanel))
						currentValue++;
				}
			}
		}
		currentValue = highestUnknownPanel(code.getPanel(code.getCodeLength()-1).getIsWhite(), known);
		for(int i = code.getCodeLength()-1; i>=0; i--)
		{

			while(containsValBol(known,currentValue,code.getPanel(i).getIsWhite()))
				currentValue--;	


			if(code.getPanel(i).isFlipped())
			{
				
				ends.add(-1);
				isFlipped.add(true);
				//black to black
				if(i>0 && code.getPanel(i).getIsWhite() == false && code.getPanel(i-1).getIsWhite() == false)
					currentValue = code.getPanel(i).getValue() - 1;
				//white to white
				else if(i>0 && (code.getPanel(i).getIsWhite() == true && code.getPanel(i-1).getIsWhite() == true))
					currentValue = code.getPanel(i).getValue() - 1;
				//white to black
				else if(i>0 && (code.getPanel(i).getIsWhite() == true && code.getPanel(i-1).getIsWhite() == false))
					currentValue = code.getPanel(i).getValue();
				//black to white
				else if(i>0 && (code.getPanel(i).getIsWhite() == false && code.getPanel(i-1).getIsWhite() == true))
					currentValue = code.getPanel(i).getValue()-1;	

				if(i>0 )
				{
					Panel currPanel = new Panel(code.getPanel(i-1).getIsWhite(),currentValue,false);
					while(known.contains(currPanel))
						currentValue--;
				}
			}
			else
			{
				ends.add(currentValue);
				
				//black to white
				if(i>0 && code.getPanel(i).getIsWhite() == false && code.getPanel(i-1).getIsWhite())
					currentValue = currentValue-1; 
				//black to black
				else if(i>0 && code.getPanel(i).getIsWhite() == false && code.getPanel(i-1).getIsWhite() == false)
					currentValue = currentValue-1;
				//white to black
				else if(i>0 && code.getPanel(i).getIsWhite() && code.getPanel(i-1).getIsWhite() == false)
					currentValue = currentValue; 
				//white to white
				else if(i>0 && code.getPanel(i).getIsWhite() && code.getPanel(i-1).getIsWhite())
					currentValue = currentValue-1;

				if(i>0 )
				{
					Panel currPanel = new Panel(code.getPanel(i-1).getIsWhite(),currentValue,false);
					while(known.contains(currPanel))
						currentValue--;
				}
			}
		}

		Collections.reverse(ends);
		
		for(int i = 0; i<starts.size(); i++)
		{
			int s = starts.get(i);
			int e = ends.get(i);
			boolean flipped = code.getPanel(i).isFlipped();
			Range r = new Range(s,e,flipped,withDash);
			codeRanges.add(r);
		}
		//System.out.println("ranges: "+ codeRanges);
		return codeRanges;
	}

	public ArrayList<Panel> getKnownPanels(boolean withRanPan)
	{
		ArrayList<Panel>  known= new ArrayList<Panel>();
		known.addAll(newGame.getCompHand().getPanels());
		known.addAll(newGame.getUserHand().getFlippedPanels());
		//known.addAll(previousGuess);

		for(int i = 0; i<userGuess.size(); i++)
		{
			known.add(userGuess.get(i).getPanel());
		}
		for(int i = 0; i<previousGuess.size(); i++)
		{
			known.add(previousGuess.get(i).getPanel());
		}


		if(withRanPan)
			known.add(compRanPan);

		return known;
	}

	public static int lowestUnknownPanel(boolean isWhite, ArrayList<Panel> knownPanels)
	{
		for(int i = 0; i<knownPanels.size(); i++)
		{

			Panel temp = new Panel(isWhite,i,false);
			if(knownPanels.contains(temp) == false);
			{
				return temp.getValue();
			}
		}
		return -1;
	}

	public static int highestUnknownPanel(boolean isWhite, ArrayList<Panel> knownPanels)
	{	
		int max = -1;
		for(int i = 11; i>=0; i--)
		{
			Panel temp = new Panel(isWhite,i,false);
			if(knownPanels.contains(temp) == false);
			{
				return i;
			}
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
		int ranNum = (int)(Math.random() * (end - start) + start);
		CompGuess temp = new CompGuess(ranNum,newGame.getUserHand().getPanel(index).getIsWhite(),index);

		while( containsValBol(known,temp.getValue(), temp.isWhite()))
		{
			ranNum = (int)(Math.random() * (end - start) + start);
			temp = new CompGuess(ranNum,newGame.getUserHand().getPanel(index).getIsWhite(),index);
		}
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

	public static boolean containsValBol(ArrayList<Panel> known, int value, boolean bool)
	{
		for(int i = 0; i<known.size(); i++)
		{
			if(known.get(i).getValue() == value && known.get(i).getIsWhite() == bool)
				return true;
		}
		return false;
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
		for(int i = 0; i<previous.size(); i++)
		{
			if(previous.get(i).getPanel().compareTo(newPan) < 0)
				previous.get(i).moveIndexDown();

			else if(previous.get(i).getPanel().compareTo(newPan) >0)
				previous.get(i).moveIndexUp();
		}
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


}
