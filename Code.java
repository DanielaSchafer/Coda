import java.util.ArrayList;

import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Code extends Panel{

	Scanner sc = new Scanner(System.in);
	private ArrayList<Panel> panels;

	private ArrayList<Boolean> isFlipped;

	public Code(){}

	public Code(ArrayList<Panel> array)
	{
		panels = array;
		//panels.addAll(array);
		sort(panels);
	}

	public Code(boolean wantDash, ArrayList<Panel> remainingPanels)
	{
		panels = getStartingHand(wantDash, remainingPanels);
		sort(panels);
		remainingPanels = deleteUsedPanels(remainingPanels,panels, wantDash);
		isFlipped = startBoolean();
	}

	// a non static sort
	public void sort(Code code)
	{
		Collections.sort(code.getPanels());
		for(int i = 0; i<code.getPanels().size()-1; i++)
		{
			if(code.getPanels().get(i).compareTo(code.getPanels().get(i+1)) == 0)
			{
				if(code.getPanels().get(i).getIsWhite() && code.getPanels().get(i+1).getIsWhite() == false)
				{
					Panel temp = code.getPanels().get(i);
					code.getPanels().set(i, code.getPanels().get(i+1));
					code.getPanels().set(i+1, temp);
				}
			}
		}
	}
	//a static sort
	public static void sort(ArrayList<Panel> array)
	{
		Collections.sort(array);
		for(int i = 0; i<array.size()-1; i++)
		{
			if(array.get(i).compareTo(array.get(i+1)) == 0)
			{
				if(array.get(i).getIsWhite() && array.get(i+1).getIsWhite() == false)
				{
					Panel temp = array.get(i);
					array.set(i, array.get(i+1));
					array.set(i+1, temp);
				}
			}
		}
	}

	//returns your current hand of panels
	public ArrayList<Panel> getPanels()
	{
		return panels;
	}

	//gets a panel at a particular index
	public Panel getPanel(int index)
	{
		return panels.get(index);
	}

	//returns whether a panel is flipped
	public boolean isFlipped(int index)
	{
		return isFlipped.get(index);
	}

	//returns length of code
	public int getCodeLength()
	{
		return panels.size();
	}

	//to string method
	public String toString()
	{
		return (panels +"");
	}

	public void compCode()
	{
		String color;
		for(int i = 0; i<panels.size(); i++)
		{
			if(panels.get(i).getIsWhite() == false)
				color = "black";
			else
				color = "white";
			
			if(panels.get(i).isFlipped() == false)
			System.out.print("("+color+ ") ");
			else
				System.out.print("("+panels.get(i)+ ") ");	
		}
	}

	public void compAddDash(Panel panel, boolean isFlip, int index)
	{
		Panel newPanel;
		if(isFlip)
		{
			newPanel = new Panel(panel.getIsWhite(), panel.getValue(), isFlip);
		}
		else
			newPanel = panel;

		panels.add(index,newPanel);
	}
	//Puts a dash panel in the players desired position
	public void addDash(boolean isFlip, Panel panel)
	{
		System.out.println("Where would you like to put the dash?");
		int spot = sc.nextInt();

		Panel newPanel;
		if(isFlip)
		{
			newPanel = new Panel(panel.getIsWhite(), panel.getValue(), isFlip);
		}
		else
			newPanel = panel;

		panels.add(spot,newPanel);
	}

	//Removes panel from hand
	public void removePanel(int index)
	{
		panels.remove(index);
	}

	//Returns true if the panel is flipped over
	public ArrayList<Boolean> areFlippled()
	{
		return isFlipped;
	}

	//Adds Panel to hand in the correct order
	public void addPanel(Panel panel, boolean isFlip)
	{
		Panel newPanel;
		if(isFlip)
		{
			newPanel = new Panel(panel.getIsWhite(), panel.getValue(), isFlip);
		}
		else
			newPanel = panel;

		panels.add(newPanel);
		sort(panels);
	}
	
	public int getAddedPanelIndex(Panel panel, boolean isFlip)
	{
		ArrayList<Panel> tempPanels = new ArrayList<Panel>();
		tempPanels.addAll(panels);
		
		Panel newPanel;
		if(isFlip)
		{
			newPanel = new Panel(panel.getIsWhite(), panel.getValue(), isFlip);
		}
		else
			newPanel = panel;

		tempPanels.add(newPanel);
		sort(tempPanels);
		
		for(int i = 0; i<tempPanels.size(); i++)
		{
			if(panel.compareTo(tempPanels.get(i)) == 0)
				return i;
		}
		return -1;
	}

	//puts all the tiles back in the center
	public static ArrayList<Panel> resetPanels(boolean wantDash)
	{
		ArrayList<Panel> remainingPanels = new ArrayList<Panel>();

		//Black Panels
		Panel b0 = new Panel(false,0,false);
		remainingPanels.add(b0);
		Panel b1 = new Panel(false,1,false);
		remainingPanels.add(b1);
		Panel b2 = new Panel(false,2,false);
		remainingPanels.add(b2);
		Panel b3 = new Panel(false,3,false);
		remainingPanels.add(b3);
		Panel b4 = new Panel(false,4,false);
		remainingPanels.add(b4);
		Panel b5 = new Panel(false,5,false);
		remainingPanels.add(b5);
		Panel b6 = new Panel(false,6,false);
		remainingPanels.add(b6);
		Panel b7 = new Panel(false,7,false);
		remainingPanels.add(b7);
		Panel b8 = new Panel(false,8,false);
		remainingPanels.add(b8);
		Panel b9 = new Panel(false,9,false);
		remainingPanels.add(b9);
		Panel b10 = new Panel(false,10,false);
		remainingPanels.add(b10);
		Panel b11 = new Panel(false,11,false);
		remainingPanels.add(b11);

		//White Panels
		Panel w0 = new Panel(true,0,false);
		remainingPanels.add(w0);
		Panel w1 = new Panel(true,1,false);
		remainingPanels.add(w1);
		Panel w2 = new Panel(true,2,false);
		remainingPanels.add(w2);
		Panel w3 = new Panel(true,3,false);
		remainingPanels.add(w3);
		Panel w4 = new Panel(true,4,false);
		remainingPanels.add(w4);
		Panel w5 = new Panel(true,5,false);
		remainingPanels.add(w5);
		Panel w6 = new Panel(true,6,false);
		remainingPanels.add(w6);
		Panel w7 = new Panel(true,7,false);
		remainingPanels.add(w7);
		Panel w8 = new Panel(true,8,false);
		remainingPanels.add(w8);
		Panel w9 = new Panel(true,9,false);
		remainingPanels.add(w9);
		Panel w10 = new Panel(true,10,false);
		remainingPanels.add(w10);
		Panel w11 = new Panel(true,11,false);
		remainingPanels.add(w11);

		//Dash Panels
		if(wantDash)
		{
			Panel bDash = new Panel(false,-1,false);
			remainingPanels.add(bDash);
			Panel wDash = new Panel(true,-1,false);
			remainingPanels.add(wDash);
		}

		return remainingPanels;

	}

	//generates a random starting hand
	public static ArrayList<Panel> getStartingHand(boolean withDash,ArrayList<Panel> panelsLeft)
	{
		ArrayList<Panel> startPanels = new ArrayList<Panel>();

		int ran1 = (int)(Math.random()*panelsLeft.size());
		Panel one = new Panel((panelsLeft.get(ran1).getIsWhite()),panelsLeft.get(ran1).getValue(),false);
		panelsLeft.remove(ran1);
		if(isDash(one) == false)
			startPanels.add(one);
		else
			startPanels.add(addDashCon(0), one); //CHANGE FROM 0

		int ran2 = (int)(Math.random()*panelsLeft.size());
		Panel two = new Panel((panelsLeft.get(ran2).getIsWhite()),panelsLeft.get(ran2).getValue(),false);
		panelsLeft.remove(ran2);
		if(isDash(two) == false)
			startPanels.add(two);
		else
			startPanels.add(addDashCon(0), two);		

		int ran3 = (int)(Math.random()*panelsLeft.size());
		Panel three = new Panel((panelsLeft.get(ran3).getIsWhite()),panelsLeft.get(ran3).getValue(),false);
		panelsLeft.remove(ran3);
		if(isDash(three) == false)
			startPanels.add(three);
		else
			startPanels.add(addDashCon(0), three);

		int ran4 = (int)(Math.random()*panelsLeft.size());
		Panel four = new Panel((panelsLeft.get(ran4).getIsWhite()),panelsLeft.get(ran4).getValue(),false);
		panelsLeft.remove(ran4);
		if(isDash(four) == false)
			startPanels.add(four);
		else
			startPanels.add(addDashCon(0), four);

		return startPanels;
	}

	public static int addDashCon(int index)
	{
		return index;
	}

	public static ArrayList<Panel> deleteUsedPanels(ArrayList<Panel> hand, ArrayList<Panel> remaining, boolean hasDash)
	{
		remaining = resetPanels(hasDash);
		for(int i = 0; i< hand.size(); i++)
		{
			if(remaining.contains(hand.get(i)))
			{
				for(int j = 0; j<remaining.size(); j++)
				{	
					if(remaining.get(j).compareTo(hand.get(i)) == 0)
						remaining.remove(j);
				}
			}
		}
		return remaining;
	}

	public static ArrayList<Boolean> startBoolean()
	{
		ArrayList<Boolean> array = new ArrayList<Boolean>();
		for(int i = 0; i<4; i++)
		{
			array.add(true);
		}
		return array;
	}

	public boolean contains(Panel panel) {
		for(int i = 0; i<panels.size(); i++)
		{
			if(panels.get(i).compareTo(panel) == 0)
				return true;
		}
		return false;
	}
	
	public ArrayList<Panel> getFlippedPanels()
	{
		ArrayList<Panel> temp = new ArrayList<Panel>();
		for(int i = 0; i<panels.size(); i++)
		{
			if(panels.get(i).isFlipped())
				temp.add(panels.get(i));
		}
		return temp;
	}

}
